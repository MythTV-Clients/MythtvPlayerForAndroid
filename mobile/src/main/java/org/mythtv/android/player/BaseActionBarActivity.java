package org.mythtv.android.player;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;

/**
 * Created by dmfrey on 12/10/14.
 */
public abstract class BaseActionBarActivity extends ActionBarActivity {

    private static final String TAG = BaseActionBarActivity.class.getSimpleName();

    protected Toolbar toolbar;

    private BackendConnectedBroadcastReceiver mBackendConnectedBroadcastReceiver = new BackendConnectedBroadcastReceiver();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

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
        backendConnectedIntentFilter.addAction( MainApplication.ACTION_NOT_CONNECTED );
        registerReceiver( mBackendConnectedBroadcastReceiver, backendConnectedIntentFilter );

        if( MainApplication.getInstance().isConnected() ) {
            updateData();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if( null != mBackendConnectedBroadcastReceiver ) {
            unregisterReceiver( mBackendConnectedBroadcastReceiver );
        }

    }

    protected abstract int getLayoutResource();

    protected abstract void updateData();

    protected void setActionBarIcon( int iconRes ) {
        toolbar.setNavigationIcon( iconRes );
    }

    protected void addFragment( Fragment fragment, String tag, boolean addToBackStack ) {

        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.add(R.id.content_frame, fragment, tag);

        if( addToBackStack ) {
            tx.addToBackStack( null );
        }

        tx.commit();

    }

    protected void replaceFragment( Fragment fragment, String tag, boolean addToBackStack ) {

        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.replace( R.id.content_frame, fragment, tag );

        if( addToBackStack ) {
            tx.addToBackStack( null );
        }

        tx.commit();

    }

    protected void removeFragment( Fragment fragment ) {

        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.remove( fragment );
        tx.commit();

    }

    private class BackendConnectedBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive( Context context, Intent intent ) {

            if( MainApplication.ACTION_CONNECTED.equals(intent.getAction()) ) {

                updateData();

            }

//            if( MainApplication.ACTION_NOT_CONNECTED.equals( intent.getAction() ) ) {
//
//                Bundle args = new Bundle();
//                args.putBoolean( ConnectingFragment.CONNECTED_KEY, false );
//
//                ConnectingFragment connectingFragment = (ConnectingFragment) getFragmentManager().findFragmentByTag( CONTENT_FRAGMENT_TAG );
//                if( null == connectingFragment ) {
//
//                    connectingFragment = (ConnectingFragment) Fragment.instantiate( ShowsActivity.this, ConnectingFragment.class.getName(), args );
//
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace( R.id.content_frame, connectingFragment, CONTENT_FRAGMENT_TAG );
//                    transaction.commit();
//
//                }
//
//                Toast.makeText( ShowsActivity.this, "Backend not connected", Toast.LENGTH_SHORT ).show();
//
//            }

        }

    }


}
