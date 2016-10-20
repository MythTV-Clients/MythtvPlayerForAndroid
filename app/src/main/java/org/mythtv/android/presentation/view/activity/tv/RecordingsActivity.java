/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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

package org.mythtv.android.presentation.view.activity.tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.view.fragment.tv.MediaItemListFragment;

public class RecordingsActivity extends AbstractBaseTvActivity implements HasComponent<MediaComponent>, MediaItemListFragment.MediaItemListListener {

    private static final String TAG = RecordingsActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, RecordingsActivity.class );
    }

    private MediaComponent mediaComponent;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_tv_recordings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.initializeActivity( savedInstanceState );
        this.initializeInjector();

    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        MediaItemListFragment.Builder recordingsParameters = new MediaItemListFragment.Builder( Media.PROGRAM );
        MediaItemListFragment mediaItemListFragment = MediaItemListFragment.newInstance( recordingsParameters.toBundle() );

        addFragment( R.id.fl_fragment, mediaItemListFragment);

        Log.d( TAG, "initializeActivity : exit" );
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
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return mediaComponent;
    }

    @Override
    public void onSearchClicked() {
        Log.d( TAG, "onSearchClicked : enter" );

        navigator.navigateToSearch( this );

        Log.d( TAG, "onSearchClicked : exit" );
    }

}
