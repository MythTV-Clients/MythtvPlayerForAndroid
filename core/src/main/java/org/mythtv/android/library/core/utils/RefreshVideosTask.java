package org.mythtv.android.library.core.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.events.dvr.UpdateTitleInfosEvent;
import org.mythtv.android.library.events.video.UpdateVideosEvent;

/**
 * Created by dmfrey on 3/14/15.
 */
public class RefreshVideosTask extends AsyncTask<Void, Void, Void> {

    private final String TAG = RefreshVideosTask.class.getSimpleName();

    public interface OnRefreshVideosTaskListener {

        public void onRefreshComplete();

    }

    OnRefreshVideosTaskListener mListener;

    public RefreshVideosTask( OnRefreshVideosTaskListener listener ) {

        if( null != listener ) {

            mListener = listener;

        }

    }

    @Override
    protected Void doInBackground( Void... params ) {
        Log.v( TAG, "doInBackground : enter" );

        if( MainApplication.getInstance().isConnected() ) {

            MainApplication.getInstance().getVideoService().updateVideos( new UpdateVideosEvent( null, null,false, null, null ) );

        }

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
