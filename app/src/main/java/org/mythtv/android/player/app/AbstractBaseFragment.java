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

package org.mythtv.android.player.app;

import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.mythtv.android.library.core.MainApplication;

/**
 * Created by dmfrey on 2/19/15.
 */
public abstract class AbstractBaseFragment extends Fragment {

    private BackendConnectedBroadcastReceiver mBackendConnectedBroadcastReceiver = new BackendConnectedBroadcastReceiver();

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter backendConnectedIntentFilter = new IntentFilter( MainApplication.ACTION_CONNECTED );
        backendConnectedIntentFilter.addAction( MainApplication.ACTION_NOT_CONNECTED );
        getActivity().registerReceiver( mBackendConnectedBroadcastReceiver, backendConnectedIntentFilter );

    }

    @Override
    public void onPause() {
        super.onPause();

        if( null != mBackendConnectedBroadcastReceiver ) {
            getActivity().unregisterReceiver( mBackendConnectedBroadcastReceiver );
        }

//        MainApplication.getInstance().disconnect();
    }

    public abstract void connected();

    public abstract void notConnected();

    private class BackendConnectedBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive( Context context, Intent intent ) {

            if( MainApplication.ACTION_CONNECTED.equals( intent.getAction() ) ) {

                connected();

            }

            if( MainApplication.ACTION_NOT_CONNECTED.equals( intent.getAction() ) ) {

                notConnected();

            }

        }

    }

}
