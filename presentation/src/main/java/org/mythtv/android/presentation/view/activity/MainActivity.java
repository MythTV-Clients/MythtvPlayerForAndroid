package org.mythtv.android.presentation.view.activity;

import android.util.Log;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 8/31/15.
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public int getLayoutResource() {

        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if( getMasterBackendUrl().equals( "http://" + getResources().getString( R.string.pref_backend_url ) + ":" + getResources().getString( R.string.pref_backend_port ) ) ) {
            Log.i( TAG, "onResume : MasterBackend not set, redirecting to Settings" );

            navigator.navigateToSettings( this );

        }

    }

}
