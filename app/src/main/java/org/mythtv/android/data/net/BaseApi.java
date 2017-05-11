package org.mythtv.android.data.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.mythtv.android.domain.SettingsKeys;

/**
 *
 * @author dmfrey
 *
 * Created on 1/21/17.
 */

abstract class BaseApi {

    private static final String TAG = BaseApi.class.getSimpleName();

    protected final Context context;
    protected final SharedPreferences sharedPreferences;

    BaseApi( final Context context, final SharedPreferences sharedPreferences ) {

        if( null == context || null == sharedPreferences ) {

            throw new IllegalArgumentException( "The constructor parameters cannot be null!!!" );
        }

        this.context = context;
        this.sharedPreferences = sharedPreferences;

    }

    boolean isThereInternetConnection() {

        boolean isConnected;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = ( networkInfo != null && networkInfo.isConnectedOrConnecting() );

        return isConnected;
    }

    protected String getMasterBackendUrl() {

        String host = getFromPreferences( SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getFromPreferences( SettingsKeys.KEY_PREF_BACKEND_PORT );

        String masterBackend = "http://" + host + ":" + port;
        Log.d( TAG, "getMasterBackendUrl : masterBackend=" + masterBackend );

        return masterBackend;
    }

    private String getFromPreferences(String key) {

        return sharedPreferences.getString( key, "" );
    }

}
