package org.mythtv.android.player.app.recordings;

import android.os.Bundle;

import org.mythtv.android.R;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;

public class RecordingDetailsSettingsActivity extends AbstractBaseAppCompatActivity {

    private static final String TAG = RecordingDetailsSettingsActivity.class.getSimpleName();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_recording_details_settings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setTitle( getResources().getString( R.string.drawer_item_preferences ) );

    }

    @Override
    protected void updateData() {

    }

}
