package org.mythtv.android.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.view.fragment.phone.AbstractBaseFragment;

import it.ennova.zerxconf.ZeRXconf;
import rx.Subscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class NsdFragment extends AbstractBaseFragment {

    private static final String TAG = NsdFragment.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    public interface BackendDiscoveryListerner {

        public void onDiscoveryComplete();

        public void onDiscoveryFailed();
    }

    private BackendDiscoveryListerner listener;

    public NsdFragment() {
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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        sharedPreferences = getSharedPreferencesComponent().sharedPreferences();

        return inflater.inflate( R.layout.fragment_nsd, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        lookupBackend();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    private void lookupBackend() {
        Log.d( TAG, "lookupBackend : enter" );

        Subscription s = ZeRXconf.startDiscovery( getActivity(), "_mythbackend._tcp." )
                            .subscribe(
                                    nsdInfo -> {
                                        Log.i( TAG, "lookupBackend : " + nsdInfo.getAddress().getHostAddress() + ":" + nsdInfo.getServicePort() );
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString( SettingsKeys.KEY_PREF_BACKEND_URL, nsdInfo.getAddress().getHostAddress() );
                                        editor.putString( SettingsKeys.KEY_PREF_BACKEND_PORT, String.valueOf( nsdInfo.getServicePort() ) );
                                        editor.apply();

                                        listener.onDiscoveryComplete();
                                    },
                                    e -> {
                                        Log.e( TAG, "lookupBackend : network discovery failed", e );

                                        listener.onDiscoveryFailed();
                                    }
                            );

        Log.d( TAG, "lookupBackend : exit" );
    }

}
