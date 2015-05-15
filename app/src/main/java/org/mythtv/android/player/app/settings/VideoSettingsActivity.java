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

package org.mythtv.android.player.app.settings;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import org.mythtv.android.R;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;
import org.mythtv.android.player.app.NavigationDrawerFragment;

public class VideoSettingsActivity extends AbstractBaseAppCompatActivity {

    private static final String TAG = VideoSettingsActivity.class.getSimpleName();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_video_settings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setTitle( getResources().getString( R.string.video_preferences ) );

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void updateData() {

    }

}
