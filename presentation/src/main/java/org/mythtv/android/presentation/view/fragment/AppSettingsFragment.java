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

package org.mythtv.android.presentation.view.fragment;

import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;


/*
 * Created by dmfrey on 4/7/15.
 */
public class AppSettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = AppSettingsFragment.class.getSimpleName();

    private SwitchPreference externalPlayerOverride;

    @Override
    public void onCreatePreferences( Bundle bundle, String s ) {

        addPreferencesFromResource( R.xml.preferences );

    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );


    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        EditTextPreference mBackendUrl = (EditTextPreference) getPreferenceManager().findPreference(SettingsKeys.KEY_PREF_BACKEND_URL);
        mBackendUrl.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange( Preference preference, Object newValue ) {

                String backendUrl = ((String) newValue).toLowerCase();

                boolean isIPv6 = backendUrl.matches("^\\[(([0-9a-f]{1,4}:){7,7}[0-9a-f]{1,4}|([0-9a-f]{1,4}:){1,7}:|([0-9a-f]{1,4}:){1,6}:[0-9a-f]{1,4}|([0-9a-f]{1,4}:){1,5}(:[0-9a-f]{1,4}){1,2}|([0-9a-f]{1,4}:){1,4}(:[0-9a-f]{1,4}){1,3}|([0-9a-f]{1,4}:){1,3}(:[0-9a-f]{1,4}){1,4}|([0-9a-f]{1,4}:){1,2}(:[0-9a-f]{1,4}){1,5}|[0-9a-f]{1,4}:((:[0-9a-f]{1,4}){1,6})|:((:[0-9a-f]{1,4}){1,7}|:)|fe80:(:[0-9a-f]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-f]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))\\]$" );
                if( isIPv6 ) {
                    Log.i( TAG, "onPreferenceChange : validated IPv6" );

                    return true;
                }

                boolean isIPv4 = backendUrl.matches("^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}");

                return isIPv4 || backendUrl.matches("(?=^.{1,253}$)(^(((?!-)[a-z0-9-]{1,63}(?<!-))|((?!-)[a-z0-9-]{1,63}(?<!-)\\.)+[a-z]{2,63})$)");

            }

        });

        externalPlayerOverride = (SwitchPreference) getPreferenceManager().findPreference( SettingsKeys.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO );
        CheckBoxPreference internalPlayer = (CheckBoxPreference) getPreferenceManager().findPreference(SettingsKeys.KEY_PREF_INTERNAL_PLAYER);
        internalPlayer.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange( Preference preference, Object newValue ) {

                if( !( (boolean) newValue ) ) {

                    externalPlayerOverride.setChecked( false );

                }

                return true;
            }

        });

        return super.onCreateView( inflater, container, savedInstanceState );
    }

}
