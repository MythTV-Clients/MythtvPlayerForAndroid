/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        addPreferencesFromResource( R.xml.preferences );

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
