package org.mythtv.android.library.core.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.events.dvr.UpdateRecordedProgramsEvent;

/**
 * Created by dmfrey on 3/14/15.
 */
public class RefreshRecordedProgramsTask extends AsyncTask<String, Void, Void> {

    private final String TAG = RefreshRecordedProgramsTask.class.getSimpleName();

    public interface OnRefreshRecordedProgramTaskListener {

        public void onRefreshComplete();

    }

    OnRefreshRecordedProgramTaskListener mListener;

    public RefreshRecordedProgramsTask( OnRefreshRecordedProgramTaskListener listener ) {

        if( null != listener ) {

            mListener = listener;

        }

    }

    @Override
    protected Void doInBackground( String... params ) {
        Log.v(TAG, "doInBackground : enter");

        String title = null;
        if( null != params && params.length > 0 ) {
            title = params[ 0 ];
        }

        MainApplication.getInstance().getDvrApiService().updateRecordedPrograms(new UpdateRecordedProgramsEvent(true, 0, null, title, null, null));

        Log.v( TAG, "doInBackground : exit" );
        return null;
    }

    @Override
    protected void onPostExecute( Void aVoid ) {

        if( null != mListener ) {

            mListener.onRefreshComplete();

        }

        super.onPostExecute( aVoid );

    }

}
