package org.mythtv.android.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.view.fragment.phone.AbstractBasePreferenceFragment;

import java.util.ArrayList;
import java.util.List;

import it.ennova.zerxconf.ZeRXconf;
import rx.Subscription;

/**
 * Fragment that looks up MythTV Backend servers on the network with ZeroConf
 *
 * @author dmfrey
 *
 */
public class NsdFragment extends AbstractBasePreferenceFragment implements OnSharedPreferenceChangeListener {

    private static final String TAG = NsdFragment.class.getSimpleName();

    public interface BackendDiscoveryListerner {

        void onDiscoveryComplete();

        void onDiscoveryFailed();
    }

    private BackendDiscoveryListerner listener;

    ListPreference backendScanUrl;
    List<String> detectedBackends = new ArrayList<>();

    Subscription zeroConfSubscription;

    public NsdFragment() {
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        addPreferencesFromResource( R.xml.preferences_backend_scan );

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        backendScanUrl = (ListPreference) getPreferenceManager().findPreference( SettingsKeys.KEY_PREF_BACKEND_SCAN_URL );

        setupUI();

        return super.onCreateView( inflater, container, savedInstanceState );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        Log.d( TAG, "onAttach : enter" );

        Activity activity = getActivity();
        if( activity instanceof BackendDiscoveryListerner) {
            this.listener = (BackendDiscoveryListerner) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        this.initialize();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );

        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );

        if( null != zeroConfSubscription && !zeroConfSubscription.isUnsubscribed() ) {

            zeroConfSubscription.unsubscribe();

        }

        super.onPause();

        Log.d( TAG, "onPause : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        String[] backends = detectedBackends.toArray( new String[ detectedBackends.size() ] );
        backendScanUrl.setEntries( backends );
        backendScanUrl.setEntryValues( backends );

        String scannedMasterBackend = getPreferenceManager().getSharedPreferences().getString( SettingsKeys.KEY_PREF_BACKEND_SCAN_URL, "Empty" );
        String masterBackend = getMasterBackendComponents();
        Log.d( TAG, "initialize : scannedMasterBackend=" + scannedMasterBackend + ", masterBackend=" + masterBackend );

        if( !"Empty".equals( scannedMasterBackend ) ) {

            if( scannedMasterBackend.equals( masterBackend ) ) {

                backendScanUrl.setSummary( scannedMasterBackend );

            }

        } else {


        }

        lookupBackend();

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        Log.d( TAG, "setupUI : exit" );
    }

    private void lookupBackend() {
        Log.d( TAG, "lookupBackend : enter" );

        zeroConfSubscription = ZeRXconf.startDiscovery( getActivity(), "_mythbackend._tcp." )
                            .subscribe(
                                    nsdInfo -> {
                                        Log.i( TAG, "lookupBackend : " + nsdInfo.getAddress().getHostAddress() + ":" + nsdInfo.getServicePort() );

                                        if( !detectedBackends.contains( nsdInfo.getAddress().getHostAddress() + ":" + nsdInfo.getServicePort() ) ) {
                                            detectedBackends.add(nsdInfo.getAddress().getHostAddress() + ":" + nsdInfo.getServicePort());
                                        }

                                        String[] backends = detectedBackends.toArray( new String[ detectedBackends.size() ] );
                                        backendScanUrl.setEntries( backends );
                                        backendScanUrl.setEntryValues( backends );

                                        listener.onDiscoveryComplete();
                                    },
                                    e -> {
                                        Log.e( TAG, "lookupBackend : network discovery failed", e );

                                        listener.onDiscoveryFailed();
                                    }
                            );

        Log.d( TAG, "lookupBackend : exit" );
    }

    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {
        Log.d( TAG, "onSharedPreferenceChanged : enter" );

        if( key.equals( SettingsKeys.KEY_PREF_BACKEND_SCAN_URL ) ) {

            String masterBackend = sharedPreferences.getString( SettingsKeys.KEY_PREF_BACKEND_SCAN_URL, "Empty" );
            backendScanUrl.setSummary( masterBackend );

            String[] masterBackendComponents = masterBackend.split( ":" );

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString( SettingsKeys.KEY_PREF_BACKEND_URL, masterBackendComponents[ 0 ] );
            editor.putString( SettingsKeys.KEY_PREF_BACKEND_PORT, masterBackendComponents[ 1 ] );
            editor.apply();

        }

        Log.d( TAG, "onSharedPreferenceChanged : exit" );
    }

}
