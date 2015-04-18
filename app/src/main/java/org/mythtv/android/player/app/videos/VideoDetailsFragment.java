package org.mythtv.android.player.app.videos;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.utils.AddVideoLiveStreamAsyncTask;
import org.mythtv.android.library.events.content.AddVideoLiveStreamEvent;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.player.app.player.VideoPlayerActivity;
import org.mythtv.android.player.common.ui.utils.ImageUtils;
import org.mythtv.android.R;

/**
 * Created by dmfrey on 12/8/14.
 */
public class VideoDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();

    public static final String VIDEO_KEY = "video";

    ImageView coverart;
    TextView description, percentComplete;
    Button play, queueHls;

    Video mVideo;

    int finalWidth, finalHeight;

    String fullUrl;
    boolean useInternalPlayer, useExternalPlayer;

    @Override
    public Loader onCreateLoader( int id, Bundle args ) {

        String[] projection = new String[] { LiveStreamConstants._ID, LiveStreamConstants.FIELD_PERCENT_COMPLETE, LiveStreamConstants.FIELD_FULL_URL, LiveStreamConstants.FIELD_RELATIVE_URL };
        String selection = LiveStreamConstants.FIELD_SOURCE_FILE + " like ?";
        String[] selectionArgs = new String[] { "%" + mVideo.getFileName() };

        return new CursorLoader( getActivity(), LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data ) {

        if( !useInternalPlayer || useExternalPlayer ) {

            queueHls.setVisibility( View.GONE );
            play.setVisibility( View.VISIBLE );

        } else {

            if( null != data && data.moveToNext() ) {

                int percent = data.getInt( data.getColumnIndex(LiveStreamConstants.FIELD_PERCENT_COMPLETE));
                if (percent > 0) {

                    percentComplete.setText("HLS: " + String.valueOf(percent) + "%");

                    if (percent > 2) {
                        fullUrl = data.getString(data.getColumnIndex( LiveStreamConstants.FIELD_FULL_URL ) );
                        play.setVisibility(View.VISIBLE);
                    }
                }

                queueHls.setVisibility(View.INVISIBLE);

            } else {

                queueHls.setVisibility(View.VISIBLE);
                play.setVisibility(View.GONE);

            }

        }

    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader ) { }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setHasOptionsMenu(true);

        useInternalPlayer = MainApplication.getInstance().isInternalPlayerEnabled();
        useExternalPlayer = MainApplication.getInstance().isExternalPlayerVideoOverrideEnabled();

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View rootView = inflater.inflate( R.layout.fragment_video_details, container, false );
        coverart = (ImageView) rootView.findViewById( R.id.video_coverart );
        description = (TextView) rootView.findViewById( R.id.video_description );
        percentComplete = (TextView) rootView.findViewById( R.id.hls_percent_complete );

        queueHls = (Button) rootView.findViewById( R.id.video_queue_hls );
        queueHls.setOnClickListener( this );

        play = (Button) rootView.findViewById( R.id.video_play );
        play.setOnClickListener( this );

        return rootView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);

        ViewTreeObserver vto = coverart.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            public boolean onPreDraw() {

                finalWidth = coverart.getMeasuredWidth();
                finalHeight = coverart.getMeasuredHeight();

                return true;
            }

        });

    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

        inflater.inflate( R.menu.menu_details, menu );

        super.onCreateOptionsMenu( menu, inflater );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.menu_settings :

                final Intent settings = new Intent( getActivity(), VideoDetailsSettingsActivity.class );
                startActivity( settings );

                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    public void setVideo( Video video ) {

        mVideo = video;

        description.setText( mVideo.getDescription() );

        String url = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + mVideo.getId() + "&Type=coverart";
        ImageUtils.updatePreviewImage( getActivity(), coverart, url );

        getLoaderManager().initLoader( 0, getArguments(), this );

    }

    @Override
    public void onClick( View v ) {

        switch( v.getId() ) {

            case R.id.video_play :

                if( useInternalPlayer && !useExternalPlayer ) {

                    Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                    intent.putExtra(VideoPlayerActivity.FULL_URL_TAG, fullUrl);
                    intent.putExtra(VideoPlayerActivity.VIDEO_TAG, mVideo);
                    startActivity(intent);

                } else {

                    String externalPlayerUrl = MainApplication.getInstance().getMasterBackendUrl() + "Content/GetFile?FileName=" + mVideo.getFilePath();
                    Log.i( TAG, "externalPlayerUrl=" + externalPlayerUrl );

                    final Intent externalPlayer = new Intent( Intent.ACTION_VIEW );
                    externalPlayer.setDataAndType( Uri.parse(externalPlayerUrl), "video/*" );
                    startActivity( externalPlayer );

                }

                break;

            case R.id.video_queue_hls :

                new AddVideoLiveStreamAsyncTask().execute( mVideo );
                queueHls.setVisibility( View.INVISIBLE );
                percentComplete.setText( "Queued..." );

                break;

        }

    }

}
