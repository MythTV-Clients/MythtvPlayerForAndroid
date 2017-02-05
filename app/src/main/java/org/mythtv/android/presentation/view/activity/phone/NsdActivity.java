package org.mythtv.android.presentation.view.activity.phone;

import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.presentation.view.fragment.NsdFragment;

public class NsdActivity extends AbstractBasePhoneActivity implements NsdFragment.BackendDiscoveryListerner, TroubleshootClickListener {

    private static final String TAG = NsdActivity.class.getSimpleName();

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_nsd;
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

//        navigator.navigateToHome( this );

        Log.d( TAG, "onDiscoveryComplete : exit" );
    }

    @Override
    public void onDiscoveryFailed() {
        Log.d( TAG, "onDiscoveryFailed : enter" );

//        navigator.navigateToSettings( this );

        Log.d( TAG, "onDiscoveryFailed : exit" );
    }

    @Override
    public void onTroubleshootClicked() {

        navigator.navigateToTroubleshoot( NsdActivity.this );

    }

}
