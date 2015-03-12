package org.mythtv.android.library.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.mythtv.android.library.events.dvr.UpdateRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.UpdateTitleInfosEvent;

/**
 * Created by dmfrey on 1/26/15.
 */
public class RefreshTitleInfosReceiver extends BroadcastReceiver {

    private final String TAG = RefreshTitleInfosTask.class.getSimpleName();

    @Override
    public void onReceive( Context context, Intent intent ) {

        new RefreshTitleInfosTask().execute();

    }

    private class RefreshTitleInfosTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground( Void... params ) {
            Log.v( TAG, "doInBackground : enter" );

            try {

                if( MainApplication.getInstance().isConnected() ) {

                    MainApplication.getInstance().getDvrService().updateTitleInfos( new UpdateTitleInfosEvent() );

                }

            } catch( NullPointerException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            Log.v( TAG, "doInBackground : exit" );
            return null;
        }

    }

}
