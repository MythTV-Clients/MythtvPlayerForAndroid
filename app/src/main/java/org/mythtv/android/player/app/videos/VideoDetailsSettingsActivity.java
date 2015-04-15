package org.mythtv.android.player.app.videos;

import android.os.Bundle;

import org.mythtv.android.R;
import org.mythtv.android.player.app.AbstractBaseActionBarActivity;

public class VideoDetailsSettingsActivity extends AbstractBaseActionBarActivity {

    private static final String TAG = VideoDetailsSettingsActivity.class.getSimpleName();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_video_details_settings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setTitle( getResources().getString(R.string.drawer_item_preferences) );

    }

    @Override
    protected void updateData() {

    }

}
