package org.mythtv.android.presentation.view.activity.phone;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.JsonSyntaxException;

import org.mythtv.android.R;
import org.mythtv.android.data.entity.mapper.BackendLangJsonMapper;
import org.mythtv.android.data.entity.mapper.BackendVersionJsonMapper;
import org.mythtv.android.data.entity.mapper.StringJsonMapper;
import org.mythtv.android.presentation.utils.Utils;

import java.io.IOException;
import java.io.Reader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView( R.id.backend_version_container )
    LinearLayout backendVersionContainer;

    @BindView( R.id.backend_version_image )
    ImageView backendVersionImage;

    @BindView( R.id.backend_lang_container )
    LinearLayout backendLangContainer;

    @BindView( R.id.backend_lang_image )
    ImageView backendLangImage;

    @BindView( R.id.fab )
    FloatingActionButton fab;

    private AirplaneModeConnectedAsyncTask airplaneModeConnectedAsyncTask;
    private NetworkConnectedAsyncTask networkConnectedAsyncTask;
    private PingConnectedAsyncTask pingConnectedAsyncTask;
    private ServicesConnectedAsyncTask servicesConnectedAsyncTask;
    private BackendVersionVerificationAsyncTask backendVersionVerificationAsyncTask;
    private BackendLangVerificationAsyncTask backendLangVerificationAsyncTask;

    private Animation pulse;

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

        this.initializeActivity();

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

        int i = item.getItemId();
        if( i == android.R.id.home ) {

            NavUtils.navigateUpFromSameTask( this );

            return true;

        } else if( i == R.id.menu_settings ) {

            navigator.navigateToSettings( this );

            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity() {
        Log.d( TAG, "initializeActivity : enter" );

        pulse = AnimationUtils.loadAnimation( this, R.anim.pulse );

        startAirplaneModeCheckCheck();

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void resetImages() {
        Log.v( TAG, "resetImages : enter" );

        airplaneModeConnectedImage.setImageDrawable( getResources().getDrawable( R.drawable.ic_airplanemode_active_black_24dp, null ) );
        networkConnectedImage.setImageDrawable( getResources().getDrawable( R.drawable.ic_signal_wifi_0_bar_black_24dp, null ) );
        pingConnectedImage.setImageDrawable( getResources().getDrawable( R.drawable.ic_signal_wifi_0_bar_black_24dp, null ) );
        servicesConnectedImage.setImageDrawable( getResources().getDrawable( R.drawable.ic_signal_wifi_0_bar_black_24dp, null ) );
        backendVersionImage.setImageDrawable( getResources().getDrawable( R.drawable.ic_signal_wifi_0_bar_black_24dp, null ) );
        backendLangImage.setImageDrawable( getResources().getDrawable( R.drawable.ic_signal_wifi_0_bar_black_24dp, null ) );

        Log.v( TAG, "resetImages : enter" );
    }

    private void setImage( ImageView imageView, int resource ) {
        Log.v( TAG, "setImage : enter" );

        pulse.cancel();
        pulse.reset();

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

    @OnClick( R.id.fab )
    void onButtonFabPlay() {

        resetImages();

        startAirplaneModeCheckCheck();

    }

    private void startAirplaneModeCheckCheck() {

        resetImages();

        if( null != airplaneModeConnectedAsyncTask && airplaneModeConnectedAsyncTask.getStatus().equals( AsyncTask.Status.RUNNING ) ) {

            airplaneModeConnectedAsyncTask.cancel( true );

        }

        airplaneModeConnectedImage.startAnimation( pulse );

        airplaneModeConnectedAsyncTask = new AirplaneModeConnectedAsyncTask();
        new Handler().postDelayed(() -> airplaneModeConnectedAsyncTask.execute(), 3000 );

    }

    private class AirplaneModeConnectedAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground( Void... params ) {

            return isAirplaneModeOn();
        }

        @Override
        protected void onPostExecute( Boolean result ) {

            if( result ) {

                setImage( airplaneModeConnectedImage, R.drawable.ic_airplanemode_active_black_24dp );

                showToastMessage( fab, getString( R.string.troubleshoot_device_in_airplane_mode ), getResources().getString( R.string.retry ), v -> startAirplaneModeCheckCheck() );

            } else {

                setImage( airplaneModeConnectedImage, R.drawable.ic_airplanemode_inactive_black_24dp );

                showToastMessage( fab, getString( R.string.troubleshoot_device_not_in_airplane_mode ), null, null );

                startNetworkConnectionCheck();

            }

        }

    }

    private void startNetworkConnectionCheck() {

        setImage( networkConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );
        setImage( pingConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );
        setImage( servicesConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

        if( null != networkConnectedAsyncTask && networkConnectedAsyncTask.getStatus().equals( AsyncTask.Status.RUNNING ) ) {

            networkConnectedAsyncTask.cancel( true );

        }

        networkConnectedImage.startAnimation( pulse );

        networkConnectedAsyncTask = new NetworkConnectedAsyncTask();
        new Handler().postDelayed(() -> networkConnectedAsyncTask.execute(), 2000 );

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

                showToastMessage( fab, getString( R.string.troubleshoot_network_connected ), null, null );

                startPingConnectionCheck();

            } else {

                setImage( networkConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

                showToastMessage( fab, getString( R.string.troubleshoot_network_not_connected ), getResources().getString( R.string.retry ), v -> startNetworkConnectionCheck() );

            }

        }

    }

    private void startPingConnectionCheck() {

        setImage( pingConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );
        setImage( servicesConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

        if( null != pingConnectedAsyncTask && pingConnectedAsyncTask.getStatus().equals( AsyncTask.Status.RUNNING ) ) {

            pingConnectedAsyncTask.cancel( true );

        }

        pingConnectedImage.startAnimation( pulse );

        pingConnectedAsyncTask = new PingConnectedAsyncTask();
        new Handler().postDelayed(() -> pingConnectedAsyncTask.execute(), 2000 );

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

                showToastMessage( fab, getString( R.string.troubleshoot_ping_succeeded ), null, null );

                startServicesConnectionCheck();

            } else {

                setImage( pingConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

                showToastMessage( fab, getString( R.string.troubleshoot_ping_failed ), getResources().getString( R.string.retry ), v -> startPingConnectionCheck() );

            }

        }

    }

    private void startServicesConnectionCheck() {

        setImage( servicesConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );
        setImage( backendLangImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

        if( null != servicesConnectedAsyncTask &&  servicesConnectedAsyncTask.getStatus().equals( AsyncTask.Status.RUNNING ) ) {

            servicesConnectedAsyncTask.cancel( true );

        }

        servicesConnectedImage.startAnimation( pulse );

        servicesConnectedAsyncTask = new ServicesConnectedAsyncTask();
        new Handler().postDelayed(() -> servicesConnectedAsyncTask.execute(), 2000 );

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

            if( null == result || "".equals( result ) ) {

                setImage( servicesConnectedImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

                showToastMessage( fab, getString( R.string.troubleshoot_services_not_connected ), getResources().getString( R.string.retry ), v -> startServicesConnectionCheck() );

            } else {

                setImage( servicesConnectedImage, R.drawable.ic_computer_black_24dp );

                showToastMessage( fab, getString( R.string.troubleshoot_services_connected ), null, null );

                startBackendVersionVerificationCheck();

            }

        }

    }

    private void startBackendVersionVerificationCheck() {

        setImage( backendVersionImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );
        setImage( backendLangImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

        if( null != backendVersionVerificationAsyncTask && backendVersionVerificationAsyncTask.getStatus().equals( AsyncTask.Status.RUNNING ) ) {

            backendVersionVerificationAsyncTask.cancel( true );

        }

        backendVersionImage.startAnimation( pulse );

        backendVersionVerificationAsyncTask = new BackendVersionVerificationAsyncTask();
        new Handler().postDelayed(() -> backendVersionVerificationAsyncTask.execute(), 2000 );

    }

    private class BackendVersionVerificationAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground( Void... params ) {

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + "/Myth/GetBackendInfo" )
                    .addHeader( "Accept", "application/json" )
                    .cacheControl( CacheControl.FORCE_NETWORK )
                    .get()
                    .build();

            try {

                BackendVersionJsonMapper mapper = new BackendVersionJsonMapper();
                Reader result = okHttpClient.newCall( request ).execute().body().charStream();
                Log.d( TAG, "doInBackground : result=" + result );

                return mapper.transformString( result );

            } catch( IOException | JsonSyntaxException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            return null;
        }

        @Override
        protected void onPostExecute( String result ) {

            if( null == result ) {

                setImage( backendVersionImage, R.drawable.ic_warning_black_24dp );

                showToastMessage( fab, getString( R.string.backend_version_check_failed, getResources().getString( R.string.minimum_mythtv_version ) ), getResources().getString( R.string.retry ), v -> startBackendVersionVerificationCheck() );

            } else {

                float minimumVersion = Float.parseFloat( getResources().getString( R.string.minimum_mythtv_version ) );
                if( Utils.meetsMinimumVersion( result, minimumVersion ) ) {

                    setImage( backendVersionImage, R.drawable.ic_description_black_24dp );

                    showToastMessage( fab, getString( R.string.backend_version_check_passed ), null, null );

                    startBackendLangVerificationCheck();

                } else {

                    setImage( backendVersionImage, R.drawable.ic_warning_black_24dp );

                    showToastMessage( fab, getString( R.string.backend_version_check_failed, getResources().getString( R.string.minimum_mythtv_version ) ), getResources().getString( R.string.retry ), v -> startBackendVersionVerificationCheck() );

                }

            }

        }

    }

    private void startBackendLangVerificationCheck() {

        setImage( backendLangImage, R.drawable.ic_signal_wifi_0_bar_black_24dp );

        if( null != backendLangVerificationAsyncTask && backendLangVerificationAsyncTask.getStatus().equals( AsyncTask.Status.RUNNING ) ) {

            backendLangVerificationAsyncTask.cancel( true );

        }

        backendLangImage.startAnimation( pulse );

        backendLangVerificationAsyncTask = new BackendLangVerificationAsyncTask();
        new Handler().postDelayed(() -> backendLangVerificationAsyncTask.execute(), 2000 );

    }

    private class BackendLangVerificationAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground( Void... params ) {

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + "/Myth/GetBackendInfo" )
                    .addHeader( "Accept", "application/json" )
                    .cacheControl( CacheControl.FORCE_NETWORK )
                    .get()
                    .build();

            try {

                BackendLangJsonMapper mapper = new BackendLangJsonMapper();
                String result = okHttpClient.newCall( request ).execute().body().string();
                Log.d( TAG, "doInBackground : result=" + result );

                return mapper.transformString( result );

            } catch( IOException | JsonSyntaxException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            return null;
        }

        @Override
        protected void onPostExecute( String result ) {

            if( null != result && result.endsWith( ".UTF-8" ) ) {

                setImage( backendLangImage, R.drawable.ic_description_black_24dp );

                showToastMessage( fab, getString( R.string.backend_lang_is_utf8 ), null, null );

            } else {

                setImage( backendLangImage, R.drawable.ic_warning_black_24dp );

                showToastMessage( fab, getString( R.string.backend_lang_is_not_utf8 ), getResources().getString( R.string.retry ), v -> startBackendLangVerificationCheck() );

            }

        }

    }

}
