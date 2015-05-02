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

package org.mythtv.android.player.tv.recordings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.player.tv.search.SearchableActivity;

public class RecordingsActivity extends Activity {

    private static final String TAG = RecordingsActivity.class.getSimpleName();

    private RecordingsFragment mRecordingsFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.i( TAG, "onCreate : enter" );

        setContentView( R.layout.activity_tv_recordings );

        mRecordingsFragment = (RecordingsFragment) getFragmentManager().findFragmentById( R.id.fragment_recordings );

        update();

    }

    @Override
    public boolean onSearchRequested() {

        startActivity( new Intent( this, SearchableActivity.class ) );

        return true;
    }

    public void update() {

        mRecordingsFragment.reload();

    }

}
