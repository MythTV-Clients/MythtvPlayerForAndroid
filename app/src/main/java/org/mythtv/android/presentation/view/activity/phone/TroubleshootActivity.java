package org.mythtv.android.presentation.view.activity.phone;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.crash.FirebaseCrash;

import org.mythtv.android.R;
import org.mythtv.android.data.entity.mapper.StringJsonMapper;

import java.io.IOException;
import java.io.Reader;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.CacheControl;
import okhttp3.Request;

/**
 *  Troubleshooting tasks
 *
 *  1. Check Airplane Mode
 *  2. Check Network Connection
 *  3. Ping Master Backend
 *  4. Verify Services API Connection
 *
 */
public class TroubleshootActivity extends AbstractBasePhoneActivity {

    private static final String TAG = TroubleshootActivity.class.getSimpleName();

    @BindView( R.id.airplane_mode_container )
    LinearLayout airplaneModeContainer;

    @BindView( R.id.airplane_mode_connected_image )
    ImageView airplaneModeConnectedImage;

    @BindView( R.id.network_container )
    LinearLayout networkConnectedContainer;

    @BindView( R.id.network_connected_image )
    ImageView networkConnectedImage;

    @BindView( R.id.ping_container )
    LinearLayout pingContainer;

    @BindView( R.id.ping_connected_image )
    ImageView pingConnectedImage;

    @BindView( R.id.services_container )
    LinearLayout servicesContainer;

    @BindView( R.id.services_connected_image )
    ImageView servicesConnectedImage;

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, TroubleshootActivity.class );
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_troubleshoot;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        super.onCreate( savedInstanceState );

        ButterKnife.bind( this );

        this.initializeActivity( savedInstanceState );

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_troubleshoot, menu );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case android.R.id.home:

                NavUtils.navigateUpFromSameTask( this );

                return true;

            case R.id.menu_settings:

                navigator.navigateToSettings( this );

                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        resetImages();

        new AirplaneModeConnectedAsyncTask().execute();

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void resetImages() {
        Log.v( TAG, "resetImages : enter" );

        airplaneModeConnectedImage.setImageDrawable( getResources().getDrawable( R.drawable.ic_airplanemode_active_black_24dp, null ) );
        networkConnectedImage.setImageDrawable( getResources().getDrawable( R.drawable.ic_signal_wifi_0_bar_black_24dp, null ) );
        pingConnectedImage.setImageDrawable( getResources().getDrawable( R.drawable.ic_signal_wifi_0_bar_black_24dp, null ) );
        servicesConnectedImage.setImageDrawable( getResources().getDrawable( R.drawable.ic_signal_wifi_0_bar_black_24dp, null ) );

        Log.v( TAG, "resetImages : enter" );
    }

    private void setImage( ImageView imageView, int resource ) {
        Log.v( TAG, "setImage : enter" );

        imageView.setImageDrawable( getResources().getDrawable( resource, null ) );

        Log.v( TAG, "setImage : exit" );
    }

    /**
     * @return boolean
     **/
    private boolean isAirplaneModeOn() {
        Log.v( TAG, "isAirplaneModeOn : enter" );

        boolean result = Settings.Global.getInt( getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0 ) != 0;

        Log.v( TAG, "isAirplaneModeOn : exit" );
        return result;
    }

    protected boolean isThereInternetConnection() {
        Log.v( TAG, "isThereInternetConnection : enter" );

        boolean isConnected;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = ( networkInfo != null && networkInfo.isConnectedOrConnecting() );

        Log.v( TAG, "isThereInternetConnection : exit" );
        return isConnected;
    }

    public boolean pingSucceeded() {
        Log.v( TAG, "pingSucceeded : enter" );

        Runtime runtime = Runtime.getRuntime();

        try {

            Process ipProcess = runtime.exec( "/system/bin/ping -c 1 " + getMasterBackendHost() );

            int exitValue = ipProcess.waitFor();

            Log.v( TAG, "pingSucceeded : exit" );
            return ( exitValue == 0 );

        } catch( IOException | InterruptedException e ) {
            Log.e( TAG, "pingSucceeded : error", e );

            FirebaseCrash.report( e );

        }

        Log.v( TAG, "pingSucceeded : exit, ping failed" );
        return false;
    }

    private class AirplaneModeConnectedAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground( Void... params ) {

            resetImages();

            return isAirplaneModeOn();
        }

        @Override
        protected void onPostExecute( Boolean result ) {

            if( result ) {

                setImage( airplaneModeConnectedImage, R.drawable.ic_airplanemode_active_black_24dp );

                showToastMessage( "Device is not in Airplane Model", null, null );

            } else {

                setImage( airplaneModeConnectedImage, R.drawable.ic_airplanemode_inactive_black_24dp );

                showToastMessage( "Device is in Airplane Model", getResources().getString( R.string.retry ), v -> new AirplaneModeConnectedAsyncTask().execute() );

                new NetworkConnectedAsyncTask().execute();

            }

        }

    }

    private void startNetworkConnectionCheck() {

        setImage( networkConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );
        setImage( pingConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );
        setImage( servicesConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

        new NetworkConnectedAsyncTask().execute();

    }

    private class NetworkConnectedAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground( Void... params ) {

            return isThereInternetConnection();
        }

        @Override
        protected void onPostExecute( Boolean result ) {

            if( result ) {

                setImage( networkConnectedImage, R.drawable.ic_network_check_black_24dp );

                showToastMessage( "Network is connected", null, null );

                startPingConnectionCheck();

            } else {

                setImage( networkConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

                showToastMessage( "Network is not connected", getResources().getString( R.string.retry ), v -> new NetworkConnectedAsyncTask().execute() );

            }

        }

    }

    private void startPingConnectionCheck() {

        setImage( pingConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );
        setImage( servicesConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

        new PingConnectedAsyncTask().execute();

    }

    private class PingConnectedAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground( Void... params ) {

            return pingSucceeded();
        }

        @Override
        protected void onPostExecute( Boolean result ) {

            if( result ) {

                setImage( pingConnectedImage, R.drawable.ic_signal_wifi_4_bar_black_24dp );

                showToastMessage( "Master Backend Ping is succeeded", null, null );

                startServicesConnectionCheck();

            } else {

                setImage( pingConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

                showToastMessage( "Master Backend Ping failed", getResources().getString( R.string.retry ), v -> new PingConnectedAsyncTask().execute() );

            }

        }

    }

    private void startServicesConnectionCheck() {

        setImage( servicesConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

        new ServicesConnectedAsyncTask().execute();

    }

    private class ServicesConnectedAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground( Void... params ) {

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + "/Myth/GetHostName" )
                    .addHeader( "Accept", "application/json" )
                    .cacheControl( CacheControl.FORCE_NETWORK )
                    .get()
                    .build();

            try {

                StringJsonMapper mapper = new StringJsonMapper();
                Reader result = okHttpClient.newCall( request ).execute().body().charStream();
                Log.d( TAG, "doInBackground : result=" + result );

                return mapper.transformString( result );

            } catch( IOException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            return null;
        }

        @Override
        protected void onPostExecute( String result ) {

            if( null != result && !"".equals( result ) ) {

                setImage( servicesConnectedImage, R.drawable.ic_computer_black_24dp );

                showToastMessage( "MythTV Services API is connected", null, null );

            } else {

                setImage( servicesConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

                showToastMessage( "MythTV Services API unreachable", getResources().getString( R.string.retry ), v -> new ServicesConnectedAsyncTask().execute() );

            }

        }

    }

}
