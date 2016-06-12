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

package org.mythtv.android.view.activity.phone;

import android.content.Context;
import android.content.Intent;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 9/2/15.
 */
public class SettingsActivity extends AbstractBasePhoneActivity {

    public static Intent getCallingIntent( Context context ) {

        Intent callingIntent = new Intent( context, SettingsActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_settings;
    }

    @Override
    protected void onResume() {
        super.onResume();

        setNavigationMenuItemChecked( 3 );

    }

}
