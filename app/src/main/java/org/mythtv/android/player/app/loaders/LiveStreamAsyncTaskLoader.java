package org.mythtv.android.player.app.loaders;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.content.LiveStreamInfo;
import org.mythtv.android.library.events.content.LiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.RequestLiveStreamDetailsEvent;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;

/**
 * Created by dmfrey on 4/5/15.
 */
public class LiveStreamAsyncTaskLoader extends AsyncTaskLoader<LiveStreamInfo> {

    private static final String TAG = LiveStreamAsyncTaskLoader.class.getSimpleName();

    private LiveStreamContentProviderObserver mObserver;
    private LiveStreamInfo mLiveStream;

    private int chanId;
    private DateTime startTime;

    public LiveStreamAsyncTaskLoader( Context context ) {
        super( context );

    }

    @Override
    public LiveStreamInfo loadInBackground() {

        LiveStreamInfo liveStreamInfo = null;

        try {

            if( MainApplication.getInstance().isConnected() ) {

                LiveStreamDetailsEvent event = MainApplication.getInstance().getContentService().requestLiveStream( new RequestLiveStreamDetailsEvent( chanId, startTime ) );
                if( event.isEntityFound() ) {
                    Log.v( TAG, "loadInBackground : liveStream loaded from db" );

                    liveStreamInfo = LiveStreamInfo.fromDetails( event.getDetails() );

                }

            } else {

                Log.w( TAG, "loadInBackground : MasterBackend NOT Connected!!" );

            }

        } catch( NullPointerException e ) {

            Log.e( TAG, "loadInBackground : error", e );

        }

        Log.v( TAG, "loadInBackground : exit" );
        return liveStreamInfo;
    }

    public void setChanId( int chanId ) {

        this.chanId = chanId;

    }

    public void setStartTime( DateTime startTime ) {

        this.startTime = startTime;

    }

    @Override
    public void deliverResult( LiveStreamInfo data ) {

        if( isReset() ) {

            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources( data );

            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        LiveStreamInfo oldData = mLiveStream;
        mLiveStream = data;

        if( isStarted() ) {

            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult( data );
        }

        // Invalidate the old data as we don't need it any more.
        if( oldData != null && oldData != data ) {

            releaseResources( oldData );

        }

    }

    @Override
    protected void onStartLoading() {

        if( null != mLiveStream ) {

            // Deliver any previously loaded data immediately.
            deliverResult( mLiveStream );

        }

        // Begin monitoring the underlying data source.
        if( null == mObserver ) {

            mObserver = new LiveStreamContentProviderObserver( mHandler, this );
            getContext().getContentResolver().registerContentObserver( LiveStreamConstants.CONTENT_URI, true, mObserver );

        }

        if( takeContentChanged() || null == mLiveStream ) {

            // When the observer detects a change, it should call onContentChanged()
            // on the Loader, which will cause the next call to takeContentChanged()
            // to return true. If this is ever the case (or if the current data is
            // null), we force a new load.
            forceLoad();

        }

    }

    @Override
    protected void onStopLoading() {

        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();

        // Note that we leave the observer as is. Loaders in a stopped state
        // should still monitor the data source for changes so that the Loader
        // will know to force a new load if it is ever started again.

    }

    @Override
    protected void onReset() {

        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if( null != mLiveStream ) {

            releaseResources( mLiveStream );
            mLiveStream = null;

        }

        // The Loader is being reset, so we should stop monitoring for changes.
        if( null != mObserver ) {

            getContext().getContentResolver().unregisterContentObserver( mObserver );
            mObserver = null;

        }

    }

    @Override
    public void onCanceled( LiveStreamInfo data ) {

        // Attempt to cancel the current asynchronous load.
        super.onCanceled( data );

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources( data );

    }

    private void releaseResources( LiveStreamInfo data ) {
        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.
    }

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage( msg );

            Log.v( TAG, "handleMessage : LiveStream changed" );
        }

    };

    private class LiveStreamContentProviderObserver extends ContentObserver {

        private LiveStreamAsyncTaskLoader mLoader;

        public LiveStreamContentProviderObserver( Handler handler, LiveStreamAsyncTaskLoader loader ) {
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
