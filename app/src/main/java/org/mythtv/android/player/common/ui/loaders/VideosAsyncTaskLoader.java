package org.mythtv.android.player.common.ui.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.library.persistence.domain.video.VideoConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dmfrey on 3/10/15.
 */
public class VideosAsyncTaskLoader extends AsyncTaskLoader<List<Video>> {

    private static final String TAG = VideosAsyncTaskLoader.class.getSimpleName();

    private VideosObserver mObserver;
    private List<Video> mVideos;

    public VideosAsyncTaskLoader(Context context) {
        super( context );

    }

    @Override
    public List<Video> loadInBackground() {
        Log.v( TAG, "loadInBackground : enter" );

        List<Video> videos = new ArrayList<>();

        try {

            if( ( (MainApplication) getContext().getApplicationContext() ).isConnected() ) {

                AllVideosEvent event = ( (MainApplication) getContext().getApplicationContext() ).getVideoService().requestAllVideos( new RequestAllVideosEvent( null ) );
                if( event.isEntityFound() ) {
                    Log.v( TAG, "loadInBackground : titleInfos loaded from db" );

                    for( VideoDetails details : event.getDetails() ) {
                        Log.v( TAG, "loadInBackground : video iteration" );

                        Video video = Video.fromDetails( details );

                        videos.add( video );

                    }

//                    Collections.sort( videos );

                }

            } else {

                Log.w( TAG, "loadInBackground : MasterBackend NOT Connected!!" );

            }

        } catch( NullPointerException e ) {

            Log.e( TAG, "loadInBackground : error", e );

        }

        Log.v( TAG, "loadInBackground : exit" );
        return videos;
    }

    @Override
    public void deliverResult( List<Video> data ) {
        Log.v( TAG, "deliverResult : enter" );

        if( isReset() ) {
            Log.v( TAG, "deliverResult : isReset" );

            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources( data );

            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        List<Video> oldData = mVideos;
        mVideos = data;

        if( isStarted() ) {
            Log.v( TAG, "deliverResult : isStarted" );

            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult( data );
        }

        // Invalidate the old data as we don't need it any more.
        if( oldData != null && oldData != data ) {
            Log.v( TAG, "deliverResult : oldDate != null && oldData != data" );

            releaseResources( oldData );

        }

    }

    @Override
    protected void onStartLoading() {
        Log.v( TAG, "onStartLoading : enter" );

        if( null != mVideos ) {

            // Deliver any previously loaded data immediately.
            deliverResult( mVideos );

        }

        // Begin monitoring the underlying data source.
        if( null == mObserver ) {

            mObserver = new VideosObserver( mHandler, this );
            getContext().getContentResolver().registerContentObserver( VideoConstants.CONTENT_URI, true, mObserver );

        }

        if( takeContentChanged() || null == mVideos ) {

            // When the observer detects a change, it should call onContentChanged()
            // on the Loader, which will cause the next call to takeContentChanged()
            // to return true. If this is ever the case (or if the current data is
            // null), we force a new load.
            forceLoad();

        }

        Log.v( TAG, "onStartLoading : exit" );
    }

    @Override
    protected void onStopLoading() {
        Log.v( TAG, "onStopLoading : enter" );

        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();

        // Note that we leave the observer as is. Loaders in a stopped state
        // should still monitor the data source for changes so that the Loader
        // will know to force a new load if it is ever started again.

        Log.v( TAG, "onStopLoading : exit" );
    }

    @Override
    protected void onReset() {
        Log.v( TAG, "onReset : enter" );

        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if( null != mVideos ) {
            Log.v( TAG, "onReset : null != mVideos" );

            releaseResources( mVideos );
            mVideos = null;

        }

        // The Loader is being reset, so we should stop monitoring for changes.
        if( null != mObserver ) {

            getContext().getContentResolver().unregisterContentObserver( mObserver );
            mObserver = null;

        }

        Log.v(TAG, "onReset : exit");
    }

    @Override
    public void onCanceled( List<Video> data ) {
        Log.v( TAG, "onCanceled : enter" );

        // Attempt to cancel the current asynchronous load.
        super.onCanceled( data );

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources( data );

        Log.v( TAG, "onCanceled : exit" );
    }

    private void releaseResources( List<Video> data ) {
        Log.v( TAG, "releaseResources : enter" );

        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.

        Log.v( TAG, "releaseResources : exit" );
    }

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage( msg );

            Log.v( TAG, "handleMessage : Videos changed" );
        }

    };

    private class VideosObserver extends ContentObserver {

        private VideosAsyncTaskLoader mLoader;

        public VideosObserver( Handler handler, VideosAsyncTaskLoader loader ) {
            super( handler );

            mLoader = loader;
        }

        @Override
        public boolean deliverSelfNotifications() {

            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange( boolean selfChange ) {
            super.onChange( selfChange );

            mLoader.onContentChanged();

        }

        @Override
        public void onChange( boolean selfChange, Uri uri ) {
            super.onChange( selfChange, uri );

            mLoader.onContentChanged();

        }

    }

}
