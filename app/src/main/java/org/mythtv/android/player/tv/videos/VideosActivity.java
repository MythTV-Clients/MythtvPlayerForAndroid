package org.mythtv.android.player.tv.videos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.player.app.settings.SettingsActivity;

public class VideosActivity extends Activity {

    private static final String TAG = VideosActivity.class.getSimpleName();

    private VideosFragment mVideosFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.i( TAG, "onCreate : enter" );

        setContentView( R.layout.activity_tv_videos );

        mVideosFragment = (VideosFragment) getFragmentManager().findFragmentById( R.id.videos_browse_fragment );

        update();

    }

    public void update() {

        mVideosFragment.reload();

    }


}
