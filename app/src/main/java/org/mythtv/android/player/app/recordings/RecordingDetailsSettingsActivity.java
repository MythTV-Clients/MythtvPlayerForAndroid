package org.mythtv.android.player.app.recordings;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import org.mythtv.android.R;
import org.mythtv.android.player.app.AbstractBaseActionBarActivity;
import org.mythtv.android.player.app.NavigationDrawerFragment;

public class RecordingDetailsSettingsActivity extends AbstractBaseActionBarActivity {

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
