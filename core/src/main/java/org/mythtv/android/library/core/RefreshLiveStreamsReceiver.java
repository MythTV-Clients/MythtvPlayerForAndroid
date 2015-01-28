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

    private static final String TAG = RefreshLiveStreamsReceiver.class.getSimpleName();
    
    @Override
    public void onReceive( Context context, Intent intent ) {
        Log.v( TAG, "onReceive : enter" );

        new RefreshLiveStreamsTask( context ).execute();

        Log.v( TAG, "onReceive : exit" );
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

            String[] projection = null;
            String selection = LiveStreamConstants.FIELD_PERCENT_COMPLETE + " < ?";
            String[] selectionArgs = new String[] { "100" };

            Cursor cursor = mContext.getContentResolver().query( LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
            if( cursor.moveToNext() ) {
                Log.v( TAG, "doInBackground : live streams need refreshing" );

                if( null != mContext ) {

                    if( null != mContext.getApplicationContext() ) {

                        MainApplication application = (MainApplication) mContext.getApplicationContext();

                        if( null != application.getContentService() ) {
                            application.getContentService().getLiveStreamInfoList(new RequestAllLiveStreamInfosEvent());
                        }

                    }

                }

            }
            cursor.close();

            Log.v( TAG, "doInBackground : exit" );
            return null;
        }

    }

}
