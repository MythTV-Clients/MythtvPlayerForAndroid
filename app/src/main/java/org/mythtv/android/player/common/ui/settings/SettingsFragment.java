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
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;

/*
 * Created by dmfrey on 4/7/15.
 */
public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private EditTextPreference mBackendUrl;
    private CheckBoxPreference internalPlayer;
    private SwitchPreference externalPlayerOverride;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        addPreferencesFromResource( R.xml.preferences );

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBackendUrl = (EditTextPreference) getPreferenceManager().findPreference( MainApplication.KEY_PREF_BACKEND_URL );
        mBackendUrl.setOnPreferenceChangeListener( new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange( Preference preference, Object newValue ) {

                String backendUrl = ( (String) newValue ).toLowerCase();

//                boolean isIPv6 = backendUrl.matches("(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))" );
//                if( isIPv6 ) {
//                    Log.i( TAG, "onPreferenceChange : validated IPv6" );
//
//                    return true;
//                }

                boolean isIPv4 = backendUrl.matches( "^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}" );
                if( isIPv4 ) {
                    Log.i( TAG, "onPreferenceChange : validated IPv4" );

                    return true;
                }

                boolean isFQDN = backendUrl.matches( "(?=^.{1,253}$)(^(((?!-)[a-zA-Z0-9-]{1,63}(?<!-))|((?!-)[a-zA-Z0-9-]{1,63}(?<!-)\\.)+[a-zA-Z]{2,63})$)" );
                if( isFQDN ) {
                    Log.i( TAG, "onPreferenceChange : validated FQDN" );

                    return true;
                }

                Log.i( TAG, "onPreferenceChange : validation failed!" );
                return false;
            }

        });

        externalPlayerOverride = (SwitchPreference) getPreferenceManager().findPreference( MainApplication.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO );
        internalPlayer = (CheckBoxPreference) getPreferenceManager().findPreference( MainApplication.KEY_PREF_INTERNAL_PLAYER );
        internalPlayer.setOnPreferenceChangeListener( new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange( Preference preference, Object newValue ) {
            Log.i( TAG, "onPreferenceChanged : " + MainApplication.KEY_PREF_INTERNAL_PLAYER + " value=" + (boolean) newValue );

                if( !( (boolean) newValue ) ) {

                    externalPlayerOverride.setChecked( false );

                }

                return true;
            }

        });

        return super.onCreateView( inflater, container, savedInstanceState );
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

        if( key.equals( MainApplication.KEY_PREF_BACKEND_URL ) || key.equals( MainApplication.KEY_PREF_BACKEND_PORT ) ) {

            MainApplication.getInstance().resetBackend();

        }

//        if( key.equals( MainApplication.KEY_PREF_INTERNAL_PLAYER ) ) {
//            Log.i( TAG, "onSharedPreferenceChanged : key=" + key + ", value=" + sharedPreferences.getBoolean( MainApplication.KEY_PREF_INTERNAL_PLAYER, true ) );
//            Log.i( TAG, "onSharedPreferenceChanged : key=" + MainApplication.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO + ", value=" + sharedPreferences.getBoolean( MainApplication.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO, true ) );
//
//            boolean internalVideoEnabled = sharedPreferences.getBoolean( MainApplication.KEY_PREF_INTERNAL_PLAYER, true );
//            if( !internalVideoEnabled ) {
//
//                SwitchPreference external = (SwitchPreference) getPreferenceManager().findPreference( MainApplication.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO );
//                external.setChecked(false);
//
//            }
//
//        }

    }

}
