/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
