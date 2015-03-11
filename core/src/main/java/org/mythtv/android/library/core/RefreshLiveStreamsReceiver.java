package org.mythtv.android.library.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import org.mythtv.android.library.events.content.RequestAllLiveStreamInfosEvent;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;

/**
 * Created by dmfrey on 1/26/15.
 */
public class RefreshLiveStreamsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive( Context context, Intent intent ) {

        if( MainApplication.getInstance().isConnected() ) {

            new RefreshLiveStreamsTask( context ).execute();

        }

    }

    private class RefreshLiveStreamsTask extends AsyncTask<Void, Void, Void> {

        private final String TAG = RefreshLiveStreamsTask.class.getSimpleName();

        Context mContext;

        public RefreshLiveStreamsTask( Context context ) {

            mContext = context;

        }

        @Override
        protected Void doInBackground( Void... params ) {
            Log.v( TAG, "doInBackground : enter" );

            if( MainApplication.getInstance().isConnected() ) {
                MainApplication.getInstance().getContentService().getLiveStreamInfoList( new RequestAllLiveStreamInfosEvent() );
            }

            Log.v( TAG, "doInBackground : exit" );
            return null;
        }

    }

}
