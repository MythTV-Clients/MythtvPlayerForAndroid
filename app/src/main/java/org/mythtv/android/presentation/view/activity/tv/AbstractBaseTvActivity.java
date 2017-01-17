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

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.mythtv.android.presentation.AndroidApplication;
import org.mythtv.android.presentation.internal.di.components.ApplicationComponent;
import org.mythtv.android.presentation.internal.di.components.NetComponent;
import org.mythtv.android.presentation.internal.di.components.SharedPreferencesComponent;
import org.mythtv.android.presentation.navigation.TvNavigator;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/28/16.
 */
public abstract class AbstractBaseTvActivity extends Activity {

    public abstract int getLayoutResource();

    @Inject
    TvNavigator navigator;

    protected FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.getApplicationComponent().inject( this );
        setContentView( getLayoutResource() );
        ButterKnife.bind( this );

        if( !FirebaseApp.getApps( this ).isEmpty() ) {

            mFirebaseAnalytics = FirebaseAnalytics.getInstance( this );

        }

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
     * @return {@link org.mythtv.android.presentation.internal.di.components.ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {

        return ( (AndroidApplication) getApplication() ).getApplicationComponent();
    }

    /**
     * Get a SharedPreferences component for dependency injection.
     *
     * @return {@link SharedPreferencesComponent}
     */
    protected SharedPreferencesComponent getSharedPreferencesComponent() {

        return ( (AndroidApplication) getApplication() ).getSharedPreferencesComponent();
    }

    /**
     * Get a NetComponent component for dependency injection.
     *
     * @return {@link NetComponent}
     */
    protected NetComponent getNetComponent() {

        return ( (AndroidApplication) getApplication() ).getNetComponent();
    }

}
