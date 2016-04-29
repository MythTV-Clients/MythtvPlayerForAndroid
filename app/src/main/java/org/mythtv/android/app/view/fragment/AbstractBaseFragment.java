package org.mythtv.android.app.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;

import org.mythtv.android.app.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.HasComponent;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 *
 * Created by dmfrey on 8/30/15.
 */
public abstract class AbstractBaseFragment extends Fragment {

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setRetainInstance( true );

    }

    /**
     * Shows a {@link android.widget.Toast} message.
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

    protected String getMasterBackendUrl() {

        String host = getSharedPreferencesModule().getStringFromPreferences( SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getSharedPreferencesModule().getStringFromPreferences( SettingsKeys.KEY_PREF_BACKEND_PORT );

        return "http://" + host + ":" + port;
    }

    /**
     * Get a SharedPreferences module for dependency injection.
     *
     * @return {@link org.mythtv.android.app.internal.di.modules.SharedPreferencesModule}
     */
    protected SharedPreferencesModule getSharedPreferencesModule() {

        return new SharedPreferencesModule( getActivity() );
    }

}
