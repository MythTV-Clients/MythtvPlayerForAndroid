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
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
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

import java.net.InetAddress;
import java.net.UnknownHostException;

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

                InetAddress address = null;

                // 1st try to verify an IPv4 or IPv6 address, not a hostname...
                try {

                    Log.d( TAG, "Verify an IPv[46] address");
                    if ( null != InetAddress.getAllByName( backendUrl ) )
                        return true;

                  // Typos in a address cause an attemmpt to resolve it as a *hostname
                  // e.g 192.168.1.123x.
                  // The 2nd exception on the next line fires because we shouldn't be
                  // doing that in this thread.
                } catch( UnknownHostException | NetworkOnMainThreadException e ) {
                    Log.i( TAG, "mBackendUrl.onPreferenceChange : an invalid IPv[46] address was passed: " + backendUrl );
                }

                // FIXME: This must be removed, but is here to test DNS lookup:
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork ().build();
                StrictMode.setThreadPolicy(policy);

                // If it wasn't an address, then ^^^ will allow hostname resolution...
                try {

                    address = InetAddress.getByName( backendUrl );

                } catch (UnknownHostException  e) {
                    Log.i( TAG, "Unable to resolve hostname " + backendUrl );
                }

                // FIXME: This must be removed, turn off permission so another attempt to change the
                // url will work the same way...

                policy = new StrictMode.ThreadPolicy.Builder().detectNetwork ().build();
                StrictMode.setThreadPolicy(policy);

                if ( null != address ) {
                    Log.d( TAG, backendUrl + " resolved to " + address.getHostAddress());
                    return true;
                }

                return false;

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
