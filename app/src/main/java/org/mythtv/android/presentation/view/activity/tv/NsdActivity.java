package org.mythtv.android.presentation.view.activity.tv;

import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.presentation.view.fragment.NsdFragment;

public class NsdActivity extends AbstractBaseTvActivity implements NsdFragment.BackendDiscoveryListerner {

    private static final String TAG = NsdActivity.class.getSimpleName();

    @Override
    public int getLayoutResource() {

        return R.layout.activity_tv_nsd;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    public void onDiscoveryComplete() {
        Log.d( TAG, "onDiscoveryComplete : enter" );

        navigator.navigateToMain( this );

        Log.d( TAG, "onDiscoveryComplete : exit" );
    }

    @Override
    public void onDiscoveryFailed() {
        Log.d( TAG, "onDiscoveryFailed : enter" );

        navigator.navigateToMain( this );

        Log.d( TAG, "onDiscoveryFailed : exit" );
    }

}
