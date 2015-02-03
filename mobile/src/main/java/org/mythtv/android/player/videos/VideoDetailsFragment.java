package org.mythtv.android.player.videos;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.service.ContentService;
import org.mythtv.android.library.events.content.AddVideoLiveStreamEvent;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.player.PlayerActivity;
import org.mythtv.android.R;
import org.mythtv.android.player.widgets.FloatingActionButton;

/**
 * Created by dmfrey on 12/8/14.
 */
public class VideoDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, FloatingActionButton.OnCheckedChangeListener, View.OnClickListener {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();

    public static final String VIDEO_KEY = "video";

    ImageView coverart;
    TextView title, description, percentComplete;
    Button queueHls;
    FloatingActionButton fab;

    private ContentService mContentService;

    Video mVideo;

    int finalWidth, finalHeight;

    String fullUrl;

    public static VideoDetailsFragment newInstance( Video video ) {

        Bundle args = new Bundle();
        args.putSerializable( VIDEO_KEY, video );

        VideoDetailsFragment f = new VideoDetailsFragment();
        f.setArguments( args );

        return f;
    }

    @Override
    public Loader onCreateLoader( int id, Bundle args ) {
        Log.v( TAG, "onCreateLoader : enter" );

        Video video = (Video) args.getSerializable( VIDEO_KEY );

        String[] projection = new String[] { LiveStreamConstants._ID, LiveStreamConstants.FIELD_PERCENT_COMPLETE, LiveStreamConstants.FIELD_FULL_URL, LiveStreamConstants.FIELD_RELATIVE_URL };
        String selection = LiveStreamConstants.FIELD_SOURCE_FILE + " like ?";
        String[] selectionArgs = new String[] { "%" + video.getFileName() };

        Log.v( TAG, "onCreateLoader : exit" );
        return new CursorLoader( getActivity(), LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data ) {
        Log.v( TAG, "onLoaderFinished : enter" );

        if( null != data && data.moveToNext() ) {
            Log.v( TAG, "onLoaderReset : cursor found live stream" );

            int percent = data.getInt( data.getColumnIndex( LiveStreamConstants.FIELD_PERCENT_COMPLETE ) );
            if( percent > 0 ) {
                Log.v( TAG, "onLoaderReset : updating percent complete" );

                percentComplete.setText( "HLS: " + String.valueOf( percent ) + "%" );

                if( percent > 2 ) {
                    fullUrl = data.getString( data.getColumnIndex( LiveStreamConstants.FIELD_RELATIVE_URL ) );
                    fab.setVisibility( View.VISIBLE );
                }
            }

            queueHls.setVisibility( View.INVISIBLE );

        } else {
            queueHls.setVisibility( View.VISIBLE );
            fab.setVisibility( View.GONE );
        }

        Log.v( TAG, "onLoaderReset : exit" );
    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader ) {
        Log.v( TAG, "onLoaderReset : enter" );

        Log.v( TAG, "onLoaderReset : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d(TAG, "onCreateView : enter");

        View rootView = inflater.inflate( R.layout.video_details, container, false );
        coverart = (ImageView) rootView.findViewById( R.id.video_coverart );
        title = (TextView) rootView.findViewById( R.id.video_title );
        description = (TextView) rootView.findViewById( R.id.video_description );
        percentComplete = (TextView) rootView.findViewById( R.id.hls_percent_complete );

        queueHls = (Button) rootView.findViewById( R.id.video_queue_hls );
        queueHls.setOnClickListener( this );

        fab = (FloatingActionButton) rootView.findViewById( R.id.video_fab );
        fab.setOnCheckedChangeListener( this );

        Log.d( TAG, "onCreateView : exit" );
        return rootView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);
        Log.d( TAG, "onActivityCreated : enter" );

        ViewTreeObserver vto = coverart.getViewTreeObserver();
        vto.addOnPreDrawListener( new ViewTreeObserver.OnPreDrawListener() {

            public boolean onPreDraw() {

                finalWidth = coverart.getMeasuredWidth();
                finalHeight = coverart.getMeasuredHeight();

                return true;
            }

        });

        mVideo = (Video) getArguments().getSerializable(VIDEO_KEY);

        title.setText( mVideo.getTitle() );
        description.setText( mVideo.getDescription() );

        String url = ( (MainApplication) getActivity().getApplicationContext() ).getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + mVideo.getId() + "&Type=coverart";
        updatePreviewImage( url );

        getLoaderManager().initLoader( 0, getArguments(), this );

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume : enter");
        super.onResume();

        mContentService = ( (MainApplication) getActivity().getApplicationContext() ).getContentService();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onCheckedChanged( FloatingActionButton fabView, boolean isChecked ) {
        Log.d(TAG, "onCheckedChanged : enter");

        Intent intent = new Intent( getActivity(), PlayerActivity.class );
        intent.putExtra( PlayerActivity.FULL_URL_TAG, fullUrl );
        intent.putExtra( getResources().getString( R.string.should_start ), true );
        startActivity( intent );

        Log.d( TAG, "onCheckedChanged : exit" );
    }

    @Override
    public void onClick( View v ) {
        Log.d( TAG, "onClick : enter" );

        switch( v.getId() ) {

            case R.id.video_queue_hls :
                Log.d( TAG, "onClick : queue hls click" );

                new AddVideoLiveStreamAsyncTask().execute();
                queueHls.setVisibility( View.INVISIBLE );
                percentComplete.setText( "Queued..." );

                break;

        }

        Log.d( TAG, "onClick : exit" );
    }

    private void updatePreviewImage( String uri ) {

        Picasso.with( getActivity() )
                .load( uri.toString() )
//                .resize( finalWidth, finalHeight )
//                .centerCrop()
                .into( coverart );

    }

    private class AddVideoLiveStreamAsyncTask extends AsyncTask<Void, Void, Void> {

        private String TAG = AddVideoLiveStreamAsyncTask.class.getSimpleName();

        @Override
        protected Void doInBackground( Void... params ) {
            Log.d( TAG, "doInBackground : adding video hls" );

            mContentService.addVideoLiveStream( new AddVideoLiveStreamEvent( mVideo.getId(), null, 1280, 720, null, null, null ) );

            return null;
        }

    }

}
