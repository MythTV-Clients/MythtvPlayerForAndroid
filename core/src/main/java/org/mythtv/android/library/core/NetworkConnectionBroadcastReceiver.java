package org.mythtv.android.library.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by dmfrey on 2/19/15.
 */
public class NetworkConnectionBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkConnectionBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if( intent.getAction().equals( "android.net.conn.CONNECTIVITY_CHANGE" ) ) {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if( isConnected && !MainApplication.getInstance().isConnected() ) {
                Log.i( TAG, "Reconnecting backend!" );

                MainApplication.getInstance().resetBackend();

            } else if( !isConnected ) {
                Log.i( TAG, "Disconnecting backend!" );

                MainApplication.getInstance().disconnect();

            }

        }

    }

}
