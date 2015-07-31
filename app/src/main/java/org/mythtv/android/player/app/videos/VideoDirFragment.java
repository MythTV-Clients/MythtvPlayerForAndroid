package org.mythtv.android.player.app.videos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.CastMember;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.utils.RefreshVideosTask;
import org.mythtv.android.library.persistence.domain.video.VideoConstants;
import org.mythtv.android.library.persistence.domain.video.VideoDirConstants;
import org.mythtv.android.player.app.AbstractBaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by dmfrey on 7/25/15.
 */
public class VideoDirFragment extends AbstractBaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener, RefreshVideosTask.OnRefreshVideosTaskListener {

    private static final String TAG = VideoDirFragment.class.getSimpleName();

    private static final int VIDEO_DIR_LOADER_ID = 1;
    private static final int VIDEO_LOADER_ID = 2;
    private static final String DEFAULT_PARENT_PATH = "";
    private static final String PARENT_KEY = "parent_path";

//    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mPath, mUp;
    ListView mVideoDirList, mVideoList;
    VideoDirItemAdapter mVideoDirItemAdapter;
    VideoItemAdapter mVideoItemAdapter;

    List<String> history = new ArrayList<>();

    @Override
    public Loader<Cursor> onCreateLoader( int id, Bundle args ) {

        String[] projection = null;
        String selection = null;
        List<String> selectionArgs = new ArrayList<>();

        String parentPath = DEFAULT_PARENT_PATH;

        if( null != args ) {

            parentPath = args.getString( PARENT_KEY );

        }

        switch ( id ) {

            case VIDEO_DIR_LOADER_ID:
                Log.d( TAG, "onCreateLoader : loading video dirs" );

                logHistory();

                if( parentPath.equals( DEFAULT_PARENT_PATH ) ) {

                    mUp.setVisibility( View.GONE );

                } else {

                    mUp.setVisibility( View.VISIBLE );

                }

                if( !history.contains( parentPath ) ) {

                    history.add( parentPath );

                }

                mPath.setText( parentPath );

                selection = VideoDirConstants.FIELD_VIDEO_DIR_PARENT + " = ?";
                selectionArgs.add( parentPath );

                return new CursorLoader( getActivity(), VideoDirConstants.CONTENT_URI, projection, selection, selectionArgs.toArray( new String[ selectionArgs.size() ] ), null );

            case VIDEO_LOADER_ID :
                Log.d( TAG, "onCreateLoader : loading videos" );

                projection = new String[] { "rowid as " + VideoConstants._ID, VideoConstants.FIELD_VIDEO_ID, VideoConstants.FIELD_VIDEO_TITLE, VideoConstants.FIELD_VIDEO_TAGLINE, VideoConstants.FIELD_VIDEO_SUB_TITLE, VideoConstants.FIELD_VIDEO_INETREF, VideoConstants.FIELD_VIDEO_DESCRIPTION, VideoConstants.FIELD_VIDEO_FILEPATH, VideoConstants.FIELD_VIDEO_FILENAME, VideoConstants.FIELD_VIDEO_HOSTNAME, VideoConstants.FIELD_VIDEO_COLLECTIONREF, VideoConstants.FIELD_CAST_MEMBER_NAMES };
                selection = VideoConstants.FIELD_VIDEO_PARENT_PATH + " = ?";
                selectionArgs.add( parentPath );

                if( MainApplication.getInstance().enableParentalControls() ) {

                    int parentalControlLevel = MainApplication.getInstance().getParentalControlLevel();
                    selection += " AND " + VideoConstants.FIELD_VIDEO_PARENTAL_LEVEL + " <= " + parentalControlLevel;

                }

                if( !MainApplication.getInstance().showAdultTab() ) {

                    selection += " AND " + VideoConstants.FIELD_VIDEO_CONTENT_TYPE + " != ?";
                    selectionArgs.add( "ADULT" );

                }

                if( MainApplication.getInstance().restrictRatings() && !MainApplication.getInstance().restrictedRatings().isEmpty() ) {

                    String ratingSelection = " AND (";
                    List<String> restrictedRatings = MainApplication.getInstance().restrictedRatings();
                    for( String rating : restrictedRatings ) {

                        if( ratingSelection.length() > 6 && restrictedRatings.size() > 1 ) {
                            ratingSelection += " OR ";
                        }

                        ratingSelection += VideoConstants.FIELD_VIDEO_CERTIFICATION + " = ?";
                        selectionArgs.add( rating );

                    }
                    ratingSelection += ") ";
                    selection += ratingSelection;

                }

                return new CursorLoader( getActivity(), VideoConstants.CONTENT_URI, projection, selection, selectionArgs.toArray( new String[ selectionArgs.size() ] ), VideoConstants.FIELD_VIDEO_TITLE_SORT );

            default :

                return null;

        }

    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor cursor ) {

        switch( loader.getId() ) {

            case VIDEO_DIR_LOADER_ID :

                mVideoDirItemAdapter.changeCursor( cursor );

                break;

            case VIDEO_LOADER_ID :

                mVideoItemAdapter.changeCursor( cursor );

                break;
        }

    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader ) {

        switch( loader.getId() ) {

            case VIDEO_DIR_LOADER_ID :

                mVideoDirItemAdapter.changeCursor( null );

                break;

            case VIDEO_LOADER_ID :

                mVideoItemAdapter.changeCursor( null );

                break;
        }

    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setRetainInstance(true);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.video_dir_list, container, false );

//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh_layout );
        mPath = (TextView) view.findViewById( R.id.path );
        mUp = (TextView) view.findViewById( R.id.up );
        mUp.setOnClickListener( upClick );
        mVideoDirList = (ListView) view.findViewById( R.id.video_dir_list );
        mVideoDirList.setOnItemClickListener(videoDirItemClick);
        mVideoList = (ListView) view.findViewById( R.id.video_list );
        mVideoList.setOnItemClickListener(videoItemClick);

        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);

//        mSwipeRefreshLayout.setOnRefreshListener( this );

        mVideoDirItemAdapter = new VideoDirItemAdapter( getActivity().getApplicationContext(), null, 0 );
        mVideoDirList.setAdapter( mVideoDirItemAdapter );
        mVideoDirList.setFastScrollEnabled( true );

        mVideoItemAdapter = new VideoItemAdapter( getActivity().getApplicationContext(), null, 0 );
        mVideoList.setAdapter( mVideoItemAdapter );
        mVideoList.setFastScrollEnabled(true);

        getLoaderManager().initLoader( VIDEO_DIR_LOADER_ID, null, this );
        getLoaderManager().initLoader( VIDEO_LOADER_ID, null, this );

    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        super.onCreateOptionsMenu( menu, inflater );

        inflater.inflate(R.menu.menu_videos_module, menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.action_view_videos :

                MainApplication.getInstance().setVideoView( "grid" );

                Intent videos = new Intent( getActivity(), VideosActivity.class );
                videos.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity( videos );

                return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void connected() {

    }

    @Override
    public void notConnected() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRefreshComplete() {

    }

    public boolean itemsInHistory() {

        return history.size() > 1;
    }

    public void backUpDirectory() {
        Log.d( TAG, "backUpDirectory : enter" );

        String parent = history.get( history.size() - 1 );
        history.remove( parent );
        parent = history.get( history.size() - 1 );
        Log.d( TAG, "backUpDirectory : go to parent=" + parent );

        Bundle args = new Bundle();
        args.putString( PARENT_KEY, parent );
        getLoaderManager().restartLoader( VIDEO_DIR_LOADER_ID, args, VideoDirFragment.this );
        getLoaderManager().restartLoader( VIDEO_LOADER_ID, args, VideoDirFragment.this );

        Log.d( TAG, "backUpDirectory : exit" );
    }

    private View.OnClickListener upClick = new View.OnClickListener() {

        @Override
        public void onClick( View v ) {
            Log.d( TAG, "upClick.onClick : enter" );

            backUpDirectory();

            Log.d( TAG, "upClick.onClick : exit" );
        }

    };

    private AdapterView.OnItemClickListener videoDirItemClick = new AdapterView.OnItemClickListener() {


        @Override
        public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
            Log.d( TAG, "videoDirItemClick.onItemClick : enter" );

            VideoDirItemViewHolder holder = (VideoDirItemViewHolder) view.getTag();
            Log.d( TAG, "videoDirItemClick.onItemClick : directoryName=" + holder.directoryName.getText() + ", currentDirectory=" + holder.currentDirectory + ", parent=" + holder.parent );

            Bundle args = new Bundle();
            args.putString( PARENT_KEY, holder.currentDirectory );
            getLoaderManager().restartLoader( VIDEO_DIR_LOADER_ID, args, VideoDirFragment.this );
            getLoaderManager().restartLoader( VIDEO_LOADER_ID, args, VideoDirFragment.this );

            Log.d( TAG, "videoDirItemClick.onItemClick : exit" );
        }

    };

    private AdapterView.OnItemClickListener videoItemClick = new AdapterView.OnItemClickListener() {


        @Override
        public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
            Log.d( TAG, "videoItemClick.onItemClick : enter" );

            VideoItemViewHolder holder = (VideoItemViewHolder) view.getTag();
            Log.d( TAG, "videoItemClick.onItemClick : filename=" + holder.filename.getText() + ", videoId=" + holder.video.getId() );

            Bundle args = new Bundle();
            args.putSerializable( MovieDetailsFragment.VIDEO_KEY, holder.video );

            Intent videoDetails = new Intent( getActivity(), MovieDetailsActivity.class );
            videoDetails.putExtras( args );
            startActivity( videoDetails );

            Log.d( TAG, "videoItemClick.onItemClick : exit" );
        }

    };

    private class VideoDirItemAdapter extends CursorAdapter {

        private LayoutInflater mLayoutInflater;

        public VideoDirItemAdapter(Context context, Cursor c, int flags) {
            super( context, c, flags );

            mLayoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        }

        @Override
        public View newView( Context context, Cursor cursor, ViewGroup parent ) {

            View v = mLayoutInflater.inflate( R.layout.video_dir_list_item, parent, false );

            VideoDirItemViewHolder holder = new VideoDirItemViewHolder();
            holder.directoryName = (TextView) v.findViewById( R.id.video_item_title );
            v.setTag( holder );

            return v;
        }

        @Override
        public void bindView( View view, Context context, Cursor cursor ) {

            String directoryName = cursor.getString( cursor.getColumnIndex( VideoDirConstants.FIELD_VIDEO_DIR_NAME ) );
            String currentPath = cursor.getString( cursor.getColumnIndex( VideoDirConstants.FIELD_VIDEO_DIR_PATH ) );
            String parent = cursor.getString( cursor.getColumnIndex( VideoDirConstants.FIELD_VIDEO_DIR_PARENT ) );

            VideoDirItemViewHolder holder = (VideoDirItemViewHolder) view.getTag();

            holder.directoryName.setText( directoryName );
            holder.currentDirectory = currentPath;
            holder.parent = parent;

        }

    }

    private class VideoItemAdapter extends CursorAdapter {

        private LayoutInflater mLayoutInflater;

        public VideoItemAdapter( Context context, Cursor c, int flags ) {
            super( context, c, flags );

            mLayoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        }

        @Override
        public View newView( Context context, Cursor cursor, ViewGroup parent ) {

            View v = mLayoutInflater.inflate( R.layout.video_dir_list_item, parent, false );

            VideoItemViewHolder holder = new VideoItemViewHolder();
            holder.filename = (TextView) v.findViewById( R.id.video_item_title );
            v.setTag( holder );

            return v;
        }

        @Override
        public void bindView( View view, Context context, Cursor cursor ) {

            VideoItemViewHolder holder = (VideoItemViewHolder) view.getTag();

            holder.video = convertCursorToVideo( cursor );
            holder.filename.setText( holder.video.getTitle() );

        }

    }

    static class VideoDirItemViewHolder {

        TextView directoryName;
        String currentDirectory;
        String parent;

    }

    static class VideoItemViewHolder {

        TextView filename;
        Video video;

    }

    private void logHistory() {
        Log.d( TAG, "logHistory : enter" );

        if( history.isEmpty() ) {

            Log.d( TAG, "logHistory is empty" );

        } else {

            for( String h : history ) {

                Log.d( TAG, "logHistory : " + h );

            }

        }

        Log.d( TAG, "logHistory : exit" );
    }

    private Video convertCursorToVideo( Cursor cursor ) {

        Video video = new Video();
        video.setId( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_ID ) ) );
        video.setTitle(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_TITLE)));

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SUB_TITLE ) != -1 ) {
            video.setSubTitle( cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SUB_TITLE ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_TAGLINE ) != -1 ) {
            video.setTagline(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_TAGLINE)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_DIRECTOR ) != -1 ) {
            video.setDirector(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_DIRECTOR)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_STUDIO ) != -1 ) {
            video.setStudio(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_STUDIO)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_DESCRIPTION ) != -1 ) {
            video.setDescription(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_DESCRIPTION)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_CERTIFICATION ) != -1 ) {
            video.setCertification(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_CERTIFICATION)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_INETREF ) != -1 ) {
            video.setInetref(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_INETREF)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_COLLECTIONREF ) != -1 ) {
            video.setCollectionref(cursor.getInt(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_COLLECTIONREF)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_HOMEPAGE ) != -1 ) {
            video.setHomePage(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_HOMEPAGE)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_RELEASE_DATE ) != -1 ) {
            video.setReleaseDate( new DateTime( cursor.getLong( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_RELEASE_DATE ) ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_ADD_DATE ) != -1 ) {
            video.setAddDate( new DateTime( cursor.getLong( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_ADD_DATE ) ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_USER_RATING ) != -1 ) {
            video.setUserRating( cursor.getFloat( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_USER_RATING ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_LENGTH ) != -1 ) {
            video.setLength( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_LENGTH ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_PLAY_COUNT ) != -1 ) {
            video.setPlayCount( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_PLAY_COUNT ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SEASON ) != -1 ) {
            video.setSeason(cursor.getInt(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_SEASON)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_EPISODE ) != -1 ) {
            video.setEpisode(cursor.getInt(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_EPISODE)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_PARENTAL_LEVEL ) != -1 ) {
            video.setParentalLevel(cursor.getInt(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_PARENTAL_LEVEL)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_VISIBLE ) != -1 ) {
            video.setVisible( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_VISIBLE ) ) == 0 ? Boolean.FALSE : Boolean.TRUE );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_WATCHED ) != -1 ) {
            video.setWatched(cursor.getInt(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_WATCHED)) == 0 ? Boolean.FALSE : Boolean.TRUE);
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_PROCESSED ) != -1 ) {
            video.setProcessed( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_PROCESSED ) ) == 0 ? Boolean.FALSE : Boolean.TRUE );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_CONTENT_TYPE ) != -1 ) {
            video.setContentType( cursor.getString(cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_CONTENT_TYPE ) ) );
        }

        video.setFilePath( cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_FILEPATH ) ) );
        video.setFileName( cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_FILENAME ) ) );

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_HASH ) != -1 ) {
            video.setHash( cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_HASH ) ) );
        }

        video.setHostName(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_HOSTNAME)));

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_COVERART ) != -1 ) {
            video.setCoverart(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_COVERART)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_FANART ) != -1 ) {
            video.setFanart(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_FANART)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_BANNER ) != -1 ) {
            video.setBanner(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_BANNER)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SCREENSHOT ) != -1 ) {
            video.setScreenshot(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_SCREENSHOT)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SCREENSHOT ) != -1 ) {
            video.setTrailer( cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SCREENSHOT ) ) );
        }

        String castMembers = cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_CAST_MEMBER_NAMES ) );
        if( null != castMembers && !"".equals( castMembers ) ) {

            List<CastMember> castMemberNames = new ArrayList<>();

            StringTokenizer st = new StringTokenizer( castMembers, "|" );
            while( st.hasMoreTokens() ) {

                CastMember member = new CastMember();
                member.setName( st.nextToken() );
                castMemberNames.add( member );

            }
            video.setCastMembers( castMemberNames );

        }

        return video;
    }

}
