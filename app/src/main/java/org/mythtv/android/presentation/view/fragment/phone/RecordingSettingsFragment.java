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

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.R;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 4/7/15.
 */
public class RecordingSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    private static final String TAG = RecordingSettingsFragment.class.getSimpleName();

    ListPreference mDefaultRecordingGroup;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        addPreferencesFromResource( R.xml.preferences_recordings );

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        mDefaultRecordingGroup = (ListPreference) getPreferenceManager().findPreference( "default_recording_group" );

        return super.onCreateView( inflater, container, savedInstanceState );
    }

    @Override
    public void onResume() {
        super.onResume();

//        AllProgramsEvent recordingGroupsEvent = MainApplication.getInstance().getDvrApiService().requestAllRecordingGroups( new RequestAllRecordedProgramsEvent( null, null, null ) );
//        if( recordingGroupsEvent.isEntityFound() ) {
//
//            List<String> recordingGroups = new ArrayList<>();
//            for( ProgramDetails details : recordingGroupsEvent.getDetails() ) {
//
//                Program program = Program.fromDetails( details );
//                recordingGroups.add( program.getRecording().getRecGroup() );
//            }
//
//            String[] groups = recordingGroups.toArray( new String[ recordingGroups.size() ]);
//            mDefaultRecordingGroup.setEntries( groups );
//            mDefaultRecordingGroup.setEntryValues( groups );
//
//        }
//
//        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );

    }

    @Override
    public void onPause() {
        super.onPause();

//        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );

    }

    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {

    }

}
