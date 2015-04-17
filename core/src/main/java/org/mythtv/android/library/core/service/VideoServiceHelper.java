package org.mythtv.android.library.core.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.UpdateVideosEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.library.events.video.VideosUpdatedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dmfrey on 11/2/14.
 */
public class VideoServiceHelper {

    private static final String TAG = VideoServiceHelper.class.getSimpleName();

    public static final String ACTION_COMPLETE = "org.mythtv.androidtv.core.service.VideoServiceHelper.ACTION_COMPLETE";

    private final Context mContext;

    private Map<String, List<Video>> mVideos = new TreeMap<String, List<Video>>();
    private Map<String, String> mCategories = new TreeMap<String, String>();

    private List<Video> videos = new ArrayList<Video>();

    public VideoServiceHelper( final Context context ) {

        mContext = context;

        new VideosLoaderAsyncTask().execute();

    }

    public Map<String, List<Video>> getVideos() {
        return mVideos;
    }

    public Map<String, String> getCategories() {
        return mCategories;
    }

    private void prepareVideos() {

        for( Video video : videos ) {

            String cleanedTitle = cleanArticles( video.getTitle() );
            String category = cleanedTitle.substring( 0, 1 ).toUpperCase();
//            Log.i( TAG, "prepareVideos : category=" + category + ", cleanedTitle=" + cleanedTitle );
            if( !mVideos.containsKey( category ) ) {
//                Log.i( TAG, "prepareVideos : added video to new category" );

                List<Video> categoryVideos = new ArrayList<Video>();
                categoryVideos.add( video );
                mVideos.put( category, categoryVideos );

                mCategories.put( category, category );

            } else {
//                Log.i( TAG, "prepareVideos : added video to existing category" );

                mVideos.get( category ).add( video );

            }

        }

    }

    private String cleanArticles( String value ) {

        if( null == value || "".equals( value ) ) {
            return value;
        }

        String upper = value.toUpperCase();
        if( upper.startsWith( "THE " ) ) {
            value = value.substring( "THE ".length() );
        }

        if( upper.startsWith( "AN " ) ) {
            value = value.substring( "AN ".length() );
        }

        if( upper.startsWith( "A " ) ) {
            value = value.substring( "A ".length() );
        }

        return value;
    }

    private class VideosLoaderAsyncTask extends AsyncTask<Void, Void, VideosUpdatedEvent> {

        @Override
        protected VideosUpdatedEvent doInBackground( Void... params ) {

            return MainApplication.getInstance().getVideoService().updateVideos( new UpdateVideosEvent( null, null, false, null, null ) );
        }

        @Override
        protected void onPostExecute( VideosUpdatedEvent event ) {
            Log.i( TAG, "onPostExecute : enter" );

            if( event.isEntityFound() ) {

                for( VideoDetails video : event.getDetails() ) {

                    videos.add( Video.fromDetails( video ) );

                }

                prepareVideos();

            }

            Intent complete = new Intent( ACTION_COMPLETE );
            mContext.sendBroadcast( complete );

            Log.i( TAG, "onPostExecute : exit" );
        }

    }

}
