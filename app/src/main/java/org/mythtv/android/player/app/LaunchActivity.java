package org.mythtv.android.player.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;

public class LaunchActivity extends AppCompatActivity {

    private static final String TAG = LaunchActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_launch );

        final String backendUrl = MainApplication.getInstance().getBackendUrl();
        final String lastActivity = ( null != backendUrl ) ? MainApplication.getInstance().getLastActivity() : MainApplication.SETTINGS_ACTIVITY_INTENT_FILTER;

        Thread timerThread = new Thread(){
            public void run() {

                try{

                    sleep( 3000 );

                } catch( InterruptedException e ) {

                    Log.e( TAG, "thread interrupted", e );

                } finally {

                    Intent i = new Intent( lastActivity );
                    startActivity( i );

                }

            }

        };
        timerThread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();

    }

}
