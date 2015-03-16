package org.mythtv.android.player;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.player.search.SearchableActivity;

/**
 * Created by dmfrey on 12/10/14.
 */
public abstract class AbstractBaseActionBarActivity extends ActionBarActivity {

    private static final String TAG = AbstractBaseActionBarActivity.class.getSimpleName();

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

                Toast.makeText( AbstractBaseActionBarActivity.this, "Backend not connected", Toast.LENGTH_SHORT ).show();

            }

        }

    }

}
