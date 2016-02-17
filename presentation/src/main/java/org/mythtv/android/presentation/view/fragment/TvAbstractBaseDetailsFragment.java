package org.mythtv.android.presentation.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v17.leanback.app.DetailsFragment;

import org.mythtv.android.domain.SettingsKeys;

/**
 * Created by dmfrey on 2/14/16.
 */
public abstract class TvAbstractBaseDetailsFragment extends DetailsFragment {

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
