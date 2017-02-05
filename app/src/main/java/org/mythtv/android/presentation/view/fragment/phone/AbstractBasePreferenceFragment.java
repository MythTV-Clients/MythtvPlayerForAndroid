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

package org.mythtv.android.presentation.view.fragment.phone;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.Gson;

import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.AndroidApplication;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.NetComponent;
import org.mythtv.android.presentation.internal.di.components.SharedPreferencesComponent;

import okhttp3.OkHttpClient;

/**
 *
 * Base {@link PreferenceFragment} class for every fragment in this application.
 *
 * @author dmfrey
 *
 * Created on 1/28/17.
 */
public abstract class AbstractBasePreferenceFragment extends PreferenceFragment {

    protected OkHttpClient okHttpClient;
    protected Gson gson;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setRetainInstance( true );

        okHttpClient = getNetComponent().okHttpClient();
        gson = getNetComponent().gson();

    }

    /**
     * Shows a {@link Snackbar} message.
     *
     * @param message A string representing a message to be shown.
     * @param retryMessage A string representing the retry message to be shown
     * @param retryOnClickListener An onClickListener to handle retries
     */
    protected void showToastMessage( String message, String retryMessage, View.OnClickListener retryOnClickListener ) {

        Snackbar
                .make( getView(), message, Snackbar.LENGTH_LONG )
                .setAction( retryMessage, retryOnClickListener )
                .show();

    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings( "unchecked" )
    protected <C> C getComponent( Class<C> componentType ) {

        return componentType.cast( ( (HasComponent<C>) getActivity() ).getComponent() );
    }

    protected String getMasterBackendComponents() {

        String host = getSharedPreferencesComponent().sharedPreferences().getString( SettingsKeys.KEY_PREF_BACKEND_URL, "" );
        String port = getSharedPreferencesComponent().sharedPreferences().getString( SettingsKeys.KEY_PREF_BACKEND_PORT, "6544" );

        return host + ":" + port;
    }

    protected String getMasterBackendUrl() {

        return "http://" + getMasterBackendComponents();
    }

    /**
     * Get a SharedPreferences module for dependency injection.
     *
     * @return {@link SharedPreferencesComponent}
     */
    protected SharedPreferencesComponent getSharedPreferencesComponent() {

        return ( (AndroidApplication) getActivity().getApplication() ).getSharedPreferencesComponent();
    }

    /**
     * Get a Net module for dependency injection.
     *
     * @return {@link NetComponent}
     */
    protected NetComponent getNetComponent() {

        return ( (AndroidApplication) getActivity().getApplication() ).getNetComponent();
    }

}
