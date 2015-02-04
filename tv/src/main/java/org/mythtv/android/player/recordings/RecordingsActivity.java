package org.mythtv.android.player.recordings;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.library.ui.settings.SettingsActivity;

public class RecordingsActivity extends Activity {

    private static final String TAG = RecordingsActivity.class.getSimpleName();
    private static final String RECORDINGS_FRAGMENT_TAG = RecordingsFragment.class.getCanonicalName();

    String mTitle = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.i( TAG, "onCreate : enter" );

        setContentView( R.layout.activity_recordings );

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            Log.d( TAG, "onCreate : retrieved title from intent extras" );

            mTitle = getIntent().getStringExtra( RecordingsDataFragment.TITLE_INFO_TITLE );
        }

        RecordingsFragment recordingsFragment = (RecordingsFragment) getFragmentManager().findFragmentByTag( RECORDINGS_FRAGMENT_TAG );
        if( null == recordingsFragment ) {
            Log.d( TAG, "onCreate : creating new RecordingsFragment" );

            Bundle args = new Bundle();
            if( null != mTitle ) {
                args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitle );
            }

            recordingsFragment = (RecordingsFragment) Fragment.instantiate(this, RecordingsFragment.class.getName(), args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace( R.id.content_frame, recordingsFragment, RECORDINGS_FRAGMENT_TAG );
            transaction.commit();

        }

    }

}
