package org.mythtv.android.player.common.ui.data;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.domain.video.VideoDirectory;
import org.mythtv.android.library.core.utils.TreeNode;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.UpdateVideosEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.library.events.video.VideosUpdatedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by dmfrey on 11/29/14.
 */
public class VideosDataFragment extends Fragment {

    private List<Video> videos;

    private VideoDataConsumer consumer;
    private boolean loading = false;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        update();

        return null;
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        try {

            consumer = (VideoDataConsumer) activity;

        } catch( ClassCastException e ) {
            throw new ClassCastException( activity.toString() + " must implement VideoDataConsumer" );
        }

    }

    public void reset() {

        update();

    }

    public boolean isLoading() {
        return loading;
    }

    private void update() {

        if( videos == null && !isLoading() ) {

            new VideosLoaderAsyncTask().execute();

            loading = true;

        } else {

            if( videos != null ) {

                handleUpdate();

            }

        }

    }

    private void handleUpdate() {

        consumer.setVideos( videos );

    }

    private class VideosLoaderAsyncTask extends AsyncTask<Void, Void, VideosUpdatedEvent> {

        private String TAG = VideosLoaderAsyncTask.class.getSimpleName();

        @Override
        protected VideosUpdatedEvent doInBackground( Void... params ) {

            try {

                return MainApplication.getInstance().getVideoService().updateVideos( new UpdateVideosEvent( null, null, false, null, null ) );

            } catch( NullPointerException e ) { }

            return null;
        }

        @Override
        protected void onPostExecute( VideosUpdatedEvent event ) {

            if( null != event && event.isEntityFound() ) {

                videos = new ArrayList<Video>();

                for( VideoDetails videoDetails : event.getDetails() ) {

                    Video video = Video.fromDetails( videoDetails );
                    videos.add( video );

                }

                handleUpdate();

            } else {

                consumer.onHandleError( "failed to load videos" );

            }

            loading = false;

        }

    }

    private Map<String, TreeNode<VideoDirectory>> processDirectories( List<Video> videos ) {

        Map<String, TreeNode<VideoDirectory>> directories = new HashMap<String, TreeNode<VideoDirectory>>();
        if( null != videos && !videos.isEmpty() ) {

            for( Video video : videos ) {

                String filename = video.getFileName().substring( 1 );
                StringTokenizer st = new StringTokenizer( video.getFileName(), "/" );
                while( st.hasMoreTokens() ) {



                }

            }

        }

        return directories;
    }

}
