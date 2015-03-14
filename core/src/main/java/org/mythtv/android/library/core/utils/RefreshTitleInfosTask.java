package org.mythtv.android.library.core.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.events.dvr.UpdateTitleInfosEvent;

/**
 * Created by dmfrey on 3/14/15.
 */
public class RefreshTitleInfosTask extends AsyncTask<Void, Void, Void> {

    private final String TAG = RefreshTitleInfosTask.class.getSimpleName();

    public interface OnRefreshRecordedProgramTaskListener {

        public void onRefreshComplete();

    }

    OnRefreshRecordedProgramTaskListener mListener;

    public RefreshTitleInfosTask( OnRefreshRecordedProgramTaskListener listener ) {

        if( null != listener ) {

            mListener = listener;

        }

    }

    @Override
    protected Void doInBackground( Void... params ) {
        Log.v(TAG, "doInBackground : enter");

        MainApplication.getInstance().getDvrService().updateTitleInfos(new UpdateTitleInfosEvent());

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
