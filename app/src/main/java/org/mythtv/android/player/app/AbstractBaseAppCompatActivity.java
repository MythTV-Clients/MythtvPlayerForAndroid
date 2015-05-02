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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;

/**
 * Created by dmfrey on 12/10/14.
 */
public abstract class AbstractBaseAppCompatActivity extends AppCompatActivity {

    private static final String TAG = AbstractBaseAppCompatActivity.class.getSimpleName();

    protected Toolbar toolbar;

    private BackendConnectedBroadcastReceiver mBackendConnectedBroadcastReceiver = new BackendConnectedBroadcastReceiver();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        setContentView( getLayoutResource() );

        toolbar = (Toolbar) findViewById( R.id.toolbar );
        if( toolbar != null ) {
            setSupportActionBar( toolbar );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter backendConnectedIntentFilter = new IntentFilter( MainApplication.ACTION_CONNECTED );
        backendConnectedIntentFilter.addAction(MainApplication.ACTION_NOT_CONNECTED);
        registerReceiver( mBackendConnectedBroadcastReceiver, backendConnectedIntentFilter );

        if( MainApplication.getInstance().isConnected() ) {
            updateData();
        } else {
            MainApplication.getInstance().resetBackend();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if( null != mBackendConnectedBroadcastReceiver ) {
            unregisterReceiver( mBackendConnectedBroadcastReceiver );
        }

//        MainApplication.getInstance().disconnect();
    }

    protected abstract int getLayoutResource();

    protected abstract void updateData();

    private class BackendConnectedBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive( Context context, Intent intent ) {

            if( MainApplication.ACTION_CONNECTED.equals(intent.getAction()) ) {

                updateData();

            }

            if( MainApplication.ACTION_NOT_CONNECTED.equals( intent.getAction() ) ) {

                Toast.makeText( AbstractBaseAppCompatActivity.this, "Backend not connected", Toast.LENGTH_SHORT ).show();

            }

        }

    }

}
