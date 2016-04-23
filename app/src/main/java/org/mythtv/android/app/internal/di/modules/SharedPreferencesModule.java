package org.mythtv.android.app.internal.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.mythtv.android.domain.SettingsKeys;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmfrey on 3/1/16.
 */
@Module
public class SharedPreferencesModule {

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesModule( Context context ) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {

        return sharedPreferences;
    }

    public String getMasterBackendUrl() {

        String host = getStringFromPreferences( SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getStringFromPreferences( SettingsKeys.KEY_PREF_BACKEND_PORT );

        return "http://" + host + ":" + port;
    }

    public boolean getInternalPlayerPreferenceFromPreferences() {

        return getBooleanFromPreferences( SettingsKeys.KEY_PREF_INTERNAL_PLAYER );
    }

    public boolean getShowAdultContent() {

        return getBooleanFromPreferences( SettingsKeys.KEY_PREF_SHOW_ADULT_TAB );
    }

    public String getStringFromPreferences( String key ) {

        return sharedPreferences.getString( key, "" );
    }

    public void putStringToPreferences( String key, String value ) {

        sharedPreferences.edit().putString( key, value ).apply();

    }

    public int getIntFromPreferences( String key, int defaultValue ) {

        return sharedPreferences.getInt( key, defaultValue );
    }

    public void putIntToPreferences( String key, int value ) {

        sharedPreferences.edit().putInt( key, value ).apply();

    }

    public boolean getBooleanFromPreferences( String key ) {

        return sharedPreferences.getBoolean( key, false );
    }

    public void putBooleanToPreferences( String key, boolean value ) {

        sharedPreferences.edit().putBoolean( key, value ).apply();

    }

}
