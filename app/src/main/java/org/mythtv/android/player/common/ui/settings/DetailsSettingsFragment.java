package org.mythtv.android.player.common.ui.settings;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;

/**
 * Created by dmfrey on 4/7/15.
 */
public class DetailsSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    private static final String TAG = DetailsSettingsFragment.class.getSimpleName();

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        addPreferencesFromResource( R.xml.preferences_details );

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );

    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );

    }

    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {

        MainApplication.getInstance().resetBackend();

        boolean internalVideoEnabled = sharedPreferences.getBoolean( MainApplication.KEY_PREF_INTERNAL_PLAYER, true );
        if( !internalVideoEnabled ) {

            SwitchPreference external = (SwitchPreference) getPreferenceManager().findPreference( MainApplication.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO );
            external.setChecked( false );

        }

    }

}
