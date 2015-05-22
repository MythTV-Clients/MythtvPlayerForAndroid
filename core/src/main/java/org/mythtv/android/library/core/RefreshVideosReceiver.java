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
import android.os.AsyncTask;
import android.util.Log;

import org.mythtv.android.library.events.video.UpdateVideosEvent;

/**
 * Created by dmfrey on 1/26/15.
 */
public class RefreshVideosReceiver extends BroadcastReceiver {

    private final String TAG = RefreshVideosReceiver.class.getSimpleName();

    @Override
    public void onReceive( Context context, Intent intent ) {

        new RefreshVideosTask().execute();

    }

    private class RefreshVideosTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground( Void... params ) {
            Log.v( TAG, "doInBackground : enter" );

            try {

                if( MainApplication.getInstance().isConnected() ) {

                    MainApplication.getInstance().getVideoApiService().updateVideos( new UpdateVideosEvent( null, null, false, null, null ) );

                }

            } catch( NullPointerException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            Log.v( TAG, "doInBackground : exit" );
            return null;
        }

    }

}
