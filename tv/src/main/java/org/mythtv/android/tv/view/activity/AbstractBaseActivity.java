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

package org.mythtv.android.tv.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.tv.AndroidApplication;
import org.mythtv.android.tv.internal.di.components.ApplicationComponent;
import org.mythtv.android.tv.internal.di.modules.ActivityModule;
import org.mythtv.android.tv.navigation.Navigator;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by dmfrey on 1/28/16.
 */
public abstract class AbstractBaseActivity extends Activity {

    public abstract int getLayoutResource();

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.getApplicationComponent().inject( this );
        setContentView( getLayoutResource() );
        ButterKnife.bind( this );

    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void addFragment( int containerViewId, Fragment fragment ) {

        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add( containerViewId, fragment );
        fragmentTransaction.commit();

    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link org.mythtv.android.tv.internal.di.components.ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {

        return ( (AndroidApplication) getApplication() ).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link org.mythtv.android.tv.internal.di.modules.ActivityModule}
     */
    protected ActivityModule getActivityModule() {

        return new ActivityModule( this );
    }

    /**
     * Get a SharedPreferences module for dependency injection.
     *
     * @return {@link org.mythtv.android.tv.internal.di.modules.SharedPreferencesModule}
     */
    protected SharedPreferencesModule getSharedPreferencesModule() {

        return new SharedPreferencesModule( this );
    }

}
