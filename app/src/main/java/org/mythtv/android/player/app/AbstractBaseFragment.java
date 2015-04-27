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
