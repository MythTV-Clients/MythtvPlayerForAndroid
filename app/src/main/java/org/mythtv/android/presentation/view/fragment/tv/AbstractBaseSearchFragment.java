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

package org.mythtv.android.presentation.view.fragment.tv;

import android.os.Bundle;
import android.support.v17.leanback.app.SearchFragment;
import android.widget.Toast;

import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;

/**
 * Base {@link SearchFragment} class for every fragment in this application.
 *
 * Created by dmfrey on 2/27/16.
 */
public abstract class AbstractBaseSearchFragment extends SearchFragment {

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setRetainInstance( true );

    }

    /**
     * Shows a {@link Toast} message.
     *
     * @param message A string representing a message to be shown.
     */
    protected void showToastMessage( String message ) {

        Toast
            .makeText( getActivity(), message, Toast.LENGTH_LONG )
            .show();

    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings( "unchecked" )
    protected <C> C getComponent( Class<C> componentType ) {

        return componentType.cast( ( (HasComponent<C>) getActivity() ).getComponent() );
    }

    /**
     * Get a SharedPreferences module for dependency injection.
     *
     * @return {@link org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule}
     */
    protected SharedPreferencesModule getSharedPreferencesModule() {

        return new SharedPreferencesModule( getActivity() );
    }

}
