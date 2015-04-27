package org.mythtv.android.player.tv.loaders;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.content.AsyncTaskLoader;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.SearchRecordedProgramsEvent;
import org.mythtv.android.library.persistence.domain.dvr.ProgramConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 3/10/15.
 */
public class SearchProgramsAsyncTaskLoader extends AsyncTaskLoader<List<Program>> {

    private static final String TAG = SearchProgramsAsyncTaskLoader.class.getSimpleName();

    private ProgramsContentProviderObserver mObserver;
    private List<Program> mPrograms;

    private String query;

    public SearchProgramsAsyncTaskLoader(Context context) {
        super( context );

    }

    @Override
    public List<Program> loadInBackground() {
        Log.v( TAG, "loadInBackground : enter" );

        List<Program> programs = new ArrayList<>();

        try {

            if( MainApplication.getInstance().isConnected() ) {

                AllProgramsEvent event = MainApplication.getInstance().getDvrService().searchRecordedPrograms( new SearchRecordedProgramsEvent( query ) );
                if( event.isEntityFound() ) {
                    Log.v( TAG, "loadInBackground : programs loaded from db" );

                    for( ProgramDetails details : event.getDetails() ) {
                        Log.v( TAG, "loadInBackground : program iteration" );

                        programs.add( Program.fromDetails( details ) );

                    }

                }

            } else {

                Log.w( TAG, "loadInBackground : MasterBackend NOT Connected!!" );

            }

        } catch( NullPointerException e ) {

            Log.e( TAG, "loadInBackground : error", e );

        }

        Log.v( TAG, "loadInBackground : exit" );
        return programs;
    }

    public void setQuery( String query ) {

        this.query = query;

    }

    @Override
    public void deliverResult( List<Program> data ) {

        if( isReset() ) {

            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources( data );

            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        List<Program> oldData = mPrograms;
        mPrograms = data;

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

        if( null != mPrograms ) {

            // Deliver any previously loaded data immediately.
            deliverResult( mPrograms );

        }

        // Begin monitoring the underlying data source.
        if( null == mObserver ) {

            mObserver = new ProgramsContentProviderObserver( mHandler, this );
            getContext().getContentResolver().registerContentObserver( ProgramConstants.CONTENT_URI, true, mObserver );

        }

        if( takeContentChanged() || null == mPrograms ) {

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
        if( null != mPrograms ) {

            releaseResources( mPrograms );
            mPrograms = null;

        }

        // The Loader is being reset, so we should stop monitoring for changes.
        if( null != mObserver ) {

            getContext().getContentResolver().unregisterContentObserver( mObserver );
            mObserver = null;

        }

    }

    @Override
    public void onCanceled( List<Program> data ) {

        // Attempt to cancel the current asynchronous load.
        super.onCanceled( data );

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources( data );

    }

    private void releaseResources( List<Program> data ) {
        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.
    }

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage( msg );

            Log.v( TAG, "handleMessage : Programs changed" );
        }

    };

    private class ProgramsContentProviderObserver extends ContentObserver {

        private SearchProgramsAsyncTaskLoader mLoader;

        public ProgramsContentProviderObserver( Handler handler, SearchProgramsAsyncTaskLoader loader ) {
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
