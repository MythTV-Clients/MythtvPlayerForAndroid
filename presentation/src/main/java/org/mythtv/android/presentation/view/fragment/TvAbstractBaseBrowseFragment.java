package org.mythtv.android.presentation.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.widget.Toast;

import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.HasComponent;

/**
 * Base {@link Fragment} class for every fragment in this application.
 *
 * Created by dmfrey on 1/28/16.
 */
public abstract class TvAbstractBaseBrowseFragment extends BrowseFragment {

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

    protected String getMasterBackendUrl() {

        String host = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_PORT );

        String masterBackend = "http://" + host + ":" + port;

        return masterBackend;
    }

    protected boolean getShouldPlayVideoInExternalPlayer() {

        boolean playInternalPlayer = getBooleanFromPreferences( getActivity(), SettingsKeys.KEY_PREF_INTERNAL_PLAYER );
        boolean playExternalPlayerOverride = getBooleanFromPreferences( getActivity(), SettingsKeys.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO );

        return ( !playInternalPlayer || playExternalPlayerOverride );
    }

    protected String getStringFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getString( key, "" );
    }

    protected boolean getBooleanFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getBoolean( key, false );
    }

}
