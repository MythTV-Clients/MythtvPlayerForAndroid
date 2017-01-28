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

package org.mythtv.android.presentation.view.activity.phone;

import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;

/**
 *
 *
 *
 * @author dmfrey
 */
public class RecordingSettingsActivity extends AbstractBasePhoneActivity implements HasComponent<MediaComponent>, TroubleshootClickListener {

    private static final String TAG = RecordingSettingsActivity.class.getSimpleName();

    private MediaComponent mediaComponent;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_recording_settings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setTitle( getResources().getString( R.string.recording_preferences ) );

        this.initializeInjector();


    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.mediaComponent = DaggerMediaComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public MediaComponent getComponent() {

        return mediaComponent;
    }

    @Override
    public void onTroubleshootClicked() {

        navigator.navigateToTroubleshoot( RecordingSettingsActivity.this );

    }

}
