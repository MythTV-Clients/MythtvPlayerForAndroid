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

package org.mythtv.android.presentation.view.fragment.phone;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 4/7/15.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource( R.xml.preferences );

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        EditTextPreference mBackendUrl = (EditTextPreference) getPreferenceManager().findPreference( SettingsKeys.KEY_PREF_BACKEND_URL );
        mBackendUrl.setOnPreferenceChangeListener( ( preference, newValue ) -> {

            String backendUrl = ((String) newValue).toLowerCase();

            boolean isIPv6 = backendUrl.matches( "^\\[(([0-9a-f]{1,4}:){7,7}[0-9a-f]{1,4}|([0-9a-f]{1,4}:){1,7}:|([0-9a-f]{1,4}:){1,6}:[0-9a-f]{1,4}|([0-9a-f]{1,4}:){1,5}(:[0-9a-f]{1,4}){1,2}|([0-9a-f]{1,4}:){1,4}(:[0-9a-f]{1,4}){1,3}|([0-9a-f]{1,4}:){1,3}(:[0-9a-f]{1,4}){1,4}|([0-9a-f]{1,4}:){1,2}(:[0-9a-f]{1,4}){1,5}|[0-9a-f]{1,4}:((:[0-9a-f]{1,4}){1,6})|:((:[0-9a-f]{1,4}){1,7}|:)|fe80:(:[0-9a-f]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-f]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))\\]$" );
            if( isIPv6 ) {
                return true;
            }

            boolean isIPv4 = backendUrl.matches( "^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}" );

            return isIPv4 || backendUrl.matches( "(?=^.{1,253}$)(^(((?!-)[a-z0-9-]{1,63}(?<!-))|((?!-)[a-z0-9-]{1,63}(?<!-)\\.)+[a-z]{2,63})$)" );

        });

        CheckBoxPreference mEnableAnalytics = (CheckBoxPreference) getPreferenceManager().findPreference( SettingsKeys.KEY_PREF_ENABLE_ANALYTICS );
        mEnableAnalytics.setOnPreferenceChangeListener( ( preference, newValue ) -> {

            Boolean enableAnalytics = (Boolean) newValue;
            FirebaseAnalytics.getInstance( getActivity() ).setAnalyticsCollectionEnabled( enableAnalytics );

            return true;
        });

        return super.onCreateView( inflater, container, savedInstanceState );
    }

}
