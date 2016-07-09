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

import org.mythtv.android.presentation.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerVideoComponent;
import org.mythtv.android.presentation.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.view.fragment.tv.VideosFragment;

public class VideosActivity extends AbstractBaseTvActivity implements HasComponent<VideoComponent>, VideosFragment.CategoryListener {

    private static final String TAG = VideosActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, VideosActivity.class );
    }

    private VideoComponent videoComponent;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_tv_videos;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.initializeInjector();

    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.videoComponent = DaggerVideoComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public VideoComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return videoComponent;
    }

    @Override
    public void onCategoryClicked( String category ) {

        navigator.navigateToVideoCategory( this, category );

    }

}
