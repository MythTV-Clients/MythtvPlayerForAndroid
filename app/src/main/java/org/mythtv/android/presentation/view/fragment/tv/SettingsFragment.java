/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.presentation.view.fragment.tv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.util.Log;

import org.mythtv.android.presentation.R;
import org.mythtv.android.domain.SettingsKeys;

import java.util.List;

/*
 * Created by dmfrey on 1/28/16.
 */
public class SettingsFragment extends AbstractBaseGuidedStepFragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private static final int MASTER_BACKEND_URL = 1;
    private static final int MASTER_BACKEND_PORT = 2;
    private static final int INTERNAL_PLAYER = 3;
    private static final int EXTERNAL_PLAYER_OVERRIDE = 4;
    private static final int SHOW_ADULT = 5;

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

        return new GuidanceStylist.Guidance( getResources().getString( R.string.title_activity_settings ), getResources().getString( R.string.settings_description ), null, null );
    }

    @Override
    public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

        String masterBackendUrl = getSharedPreferencesModule().getStringFromPreferences( SettingsKeys.KEY_PREF_BACKEND_URL );
        actions.add( new GuidedAction.Builder( getActivity() )
                .id( MASTER_BACKEND_URL )
                .title( masterBackendUrl )
                .editTitle( masterBackendUrl )
                .description( getResources().getString( R.string.pref_backend_url_description ) )
                .multilineDescription( true )
                .editable( true )
                .infoOnly( false )
                .build()
        );

        String masterBackendPort = getSharedPreferencesModule().getStringFromPreferences( SettingsKeys.KEY_PREF_BACKEND_PORT );
        actions.add( new GuidedAction.Builder( getActivity() )
                .id( MASTER_BACKEND_PORT )
                .title( masterBackendPort )
                .editTitle( masterBackendPort )
                .description( getResources().getString( R.string.pref_backend_port_description ) )
                .multilineDescription( true )
                .editable( true )
                .infoOnly( false )
                .build()
        );

    }

    @Override
    public void onGuidedActionEdited( GuidedAction action ) {
        super.onGuidedActionEdited( action );

        Log.i( TAG, "onGuidedActionEdited : action=" + action );
        switch( (int) action.getId() ) {

            case MASTER_BACKEND_URL :
                Log.i( TAG, "onGuidedActionEdited : saving master backend url - " + action.getEditTitle() );

                getSharedPreferencesModule().putStringToPreferences( SettingsKeys.KEY_PREF_BACKEND_URL, action.getEditTitle().toString() );

                break;

            case MASTER_BACKEND_PORT :
                Log.i( TAG, "onGuidedActionEdited : saving master backend port - " + action.getEditTitle() );

                try {

                    int port = Integer.parseInt( action.getEditTitle().toString() );
                    getSharedPreferencesModule().putIntToPreferences( SettingsKeys.KEY_PREF_BACKEND_PORT, port );

                } catch( NumberFormatException e ) {

                    Log.e( TAG, "onGuidedActionEdited : error saving master backend port", e );

                }


                break;

            case INTERNAL_PLAYER :

                break;

            case EXTERNAL_PLAYER_OVERRIDE :

                break;

            case SHOW_ADULT :

                break;

            default:

                break;

        }

    }

    @Override
    public void onGuidedActionClicked( GuidedAction action ) {
        super.onGuidedActionClicked( action );

        Log.i( TAG, "onGuidedActionClicked : action=" + action );
        switch( (int) action.getId() ) {

            case MASTER_BACKEND_URL :


                break;

            case MASTER_BACKEND_PORT :

                break;

            case INTERNAL_PLAYER :

                break;

            case EXTERNAL_PLAYER_OVERRIDE :

                break;

            case SHOW_ADULT :

                break;

            default:

                break;

        }

    }


//    private EditTextPreference mBackendUrl;
//    private CheckBoxPreference internalPlayer;
//    private SwitchPreference externalPlayerOverride;
//
//    @Override
//    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
//
//        mBackendUrl = (EditTextPreference) getPreferenceManager().findPreference( SettingsKeys.KEY_PREF_BACKEND_URL );
//        mBackendUrl.setOnPreferenceChangeListener( new Preference.OnPreferenceChangeListener() {
//
//            @Override
//            public boolean onPreferenceChange( Preference preference, Object newValue ) {
//
//                String backendUrl = ( (String) newValue ).toLowerCase();
//
//                boolean isIPv6 = backendUrl.matches("(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))" );
//                if( isIPv6 ) {
//                    Log.i( TAG, "onPreferenceChange : validated IPv6" );
//
//                    return true;
//                }
//
//                boolean isIPv4 = backendUrl.matches( "^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}" );
//                if( isIPv4 ) {
//
//                    return true;
//                }
//
//                boolean isFQDN = backendUrl.matches( "(?=^.{1,253}$)(^(((?!-)[a-zA-Z0-9-]{1,63}(?<!-))|((?!-)[a-zA-Z0-9-]{1,63}(?<!-)\\.)+[a-zA-Z]{2,63})$)" );
//                return isFQDN;
//
//            }
//
//        });
//
//        externalPlayerOverride = (SwitchPreference) getPreferenceManager().findPreference( SettingsKeys.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO );
//        internalPlayer = (CheckBoxPreference) getPreferenceManager().findPreference( SettingsKeys.KEY_PREF_INTERNAL_PLAYER );
//        internalPlayer.setOnPreferenceChangeListener( new Preference.OnPreferenceChangeListener() {
//
//            @Override
//            public boolean onPreferenceChange( Preference preference, Object newValue ) {
//
//                if( !( (boolean) newValue ) ) {
//
//                    externalPlayerOverride.setChecked( false );
//
//                }
//
//                return true;
//            }
//
//        });
//
//        return super.onCreateView( inflater, container, savedInstanceState );
//    }

}
