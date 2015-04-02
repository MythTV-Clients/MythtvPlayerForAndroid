package org.mythtv.android.player.common.ui.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.TitleInfoDetails;
import org.mythtv.android.library.persistence.domain.dvr.TitleInfoConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 3/10/15.
 */
public class TitleInfosAsyncTaskLoader extends AsyncTaskLoader<List<TitleInfo>> {

    private static final String TAG = TitleInfosAsyncTaskLoader.class.getSimpleName();

    private TitleInfosObserver mObserver;
    private List<TitleInfo> mTitleInfos;

    public TitleInfosAsyncTaskLoader( Context context ) {
        super( context );

    }

    @Override
    public List<TitleInfo> loadInBackground() {
        Log.v( TAG, "loadInBackground : enter" );

        List<TitleInfo> titleInfos = new ArrayList<>();

        try {

            if( ( (MainApplication) getContext().getApplicationContext() ).isConnected() ) {

                AllTitleInfosEvent event = ( (MainApplication) getContext().getApplicationContext() ).getDvrService().requestAllTitleInfos( new RequestAllTitleInfosEvent() );
                if( event.isEntityFound() ) {
                    Log.v( TAG, "loadInBackground : titleInfos loaded from db" );

                    for( TitleInfoDetails details : event.getDetails() ) {
                        Log.v( TAG, "loadInBackground : titleInfo iteration" );

                        titleInfos.add( TitleInfo.fromDetails( details ) );

                    }

                }

            } else {

                Log.w( TAG, "loadInBackground : MasterBackend NOT Connected!!" );

            }

        } catch( NullPointerException e ) {

            Log.e( TAG, "loadInBackground : error", e );

        }

        Log.v( TAG, "loadInBackground : exit" );
        return titleInfos;
    }

    @Override
    public void deliverResult( List<TitleInfo> data ) {
        Log.v( TAG, "deliverResult : enter" );

        if( isReset() ) {
            Log.v( TAG, "deliverResult : isReset" );

            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources( data );

            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        List<TitleInfo> oldData = mTitleInfos;
        mTitleInfos = data;

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

        if( null != mTitleInfos ) {

            // Deliver any previously loaded data immediately.
            deliverResult( mTitleInfos );

        }

        // Begin monitoring the underlying data source.
        if( null == mObserver ) {

            mObserver = new TitleInfosObserver( mHandler, this );
            getContext().getContentResolver().registerContentObserver( TitleInfoConstants.CONTENT_URI, true, mObserver );

        }

        if( takeContentChanged() || null == mTitleInfos ) {

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
        if( null != mTitleInfos ) {
            Log.v( TAG, "onReset : null != mTitleInfos" );

            releaseResources( mTitleInfos );
            mTitleInfos = null;

        }

        // The Loader is being reset, so we should stop monitoring for changes.
        if( null != mObserver ) {

            getContext().getContentResolver().unregisterContentObserver( mObserver );
            mObserver = null;

        }

        Log.v( TAG, "onReset : exit" );
    }

    @Override
    public void onCanceled( List<TitleInfo> data ) {
        Log.v( TAG, "onCanceled : enter" );

        // Attempt to cancel the current asynchronous load.
        super.onCanceled( data );

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources( data );

        Log.v( TAG, "onCanceled : exit" );
    }

    private void releaseResources( List<TitleInfo> data ) {
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

            Log.v( TAG, "handleMessage : TitleInfos changed" );
        }

    };

    private class TitleInfosObserver extends ContentObserver {

        private TitleInfosAsyncTaskLoader mLoader;

        public TitleInfosObserver( Handler handler, TitleInfosAsyncTaskLoader loader ) {
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
