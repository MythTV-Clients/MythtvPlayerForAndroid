package org.mythtv.android.library.ui.data;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.service.VideoService;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.VideoDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class VideosDataFragment extends Fragment {

    private static final String TAG = VideosDataFragment.class.getSimpleName();

    public static final String VIDEO_TITLE = "video_title";

    private List<Video> videos;

    private VideoService mVideoService;

    private VideoDataConsumer consumer;
    private boolean loading = false;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );

        initializeClient( (MainApplication) getActivity().getApplicationContext() );
        update();

        Log.v( TAG, "onCreate : exit" );
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i( TAG, "onDestroyView : enter" );

        mVideoService = null;

        Log.i( TAG, "onDestroyView : exit" );
    }

    public void setConsumer( VideoDataConsumer consumer ) {

        this.consumer = consumer;

    }

    public boolean isLoading() {
        return loading;
    }

    private void initializeClient( MainApplication mainApplication ) {
        Log.v( TAG, "initializeClient : enter" );

        mVideoService = mainApplication.getVideoService();

        Log.v( TAG, "initializeClient : exit" );
    }

    private void update() {
        Log.v( TAG, "update : enter" );

        if( videos == null && !isLoading() ) {

            new VideosLoaderAsyncTask().execute();

            loading = true;

        } else {

            if( videos != null ) {

                handleUpdate();

            }

        }

        Log.v( TAG, "update : exit" );
    }

    private void handleUpdate() {
        Log.v( TAG, "handleUpdate : enter" );

        consumer.setVideos(videos);

        Log.v(TAG, "handleUpdate : exit");
    }

    private class VideosLoaderAsyncTask extends AsyncTask<Void, Void, AllVideosEvent> {

        private String TAG = VideosLoaderAsyncTask.class.getSimpleName();

        @Override
        protected AllVideosEvent doInBackground( Void... params ) {
            Log.v( TAG, "doInBackground : enter" );

            AllVideosEvent event = mVideoService.getVideoList( new RequestAllVideosEvent( null, null, false, null, null ) );

            Log.v( TAG, "doInBackground : exit" );
            return event;
        }

        @Override
        protected void onPostExecute( AllVideosEvent event ) {
            Log.v(TAG, "onPostExecute : enter");

            if( event.isEntityFound() ) {
                Log.v( TAG, "onPostExecute : received videos" );

                videos = new ArrayList<Video>();

                for( VideoDetails videoDetails : event.getDetails() ) {
                    Log.v( TAG, "onPostExecute : videoDetails iteration" );

                    Video video = Video.fromDetails( videoDetails );

                    videos.add( video );
                }

                handleUpdate();

            } else {
                Log.e( TAG, "onPostExecute : error, failed to load videos" );

                consumer.handleError( "failed to load videos" );

            }

            loading = false;

            Log.v( TAG, "onPostExecute : exit" );
        }

    }

}
