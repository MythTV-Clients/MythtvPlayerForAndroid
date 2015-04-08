package org.mythtv.android.player.tv.settings;

import android.app.Activity;
import android.os.Bundle;

import org.mythtv.android.R;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_tv_settings);
    }

}
