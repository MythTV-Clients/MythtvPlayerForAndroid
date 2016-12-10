/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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

package org.mythtv.android.presentation.view.activity.tv;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 1/28/16.
 */
public class SettingsActivity extends Activity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private static final int MASTER_BACKEND_SETTINGS = 10;
    private static final int MASTER_BACKEND_URL = 11;
    private static final int MASTER_BACKEND_PORT = 12;
    private static final int MASTER_BACKEND_READ_TIMEOUT = 13;
    private static final int MASTER_BACKEND_CONNECT_TIMEOUT = 14;
    private static final int PLAYER_SETTINGS = 20;
    private static final int INTERNAL_PLAYER_SETTINGS = 21;
    private static final int EXTERNAL_PLAYER_OVERRIDE_SETTINGS = 20;
    private static final int CONTENT_SETTINGS = 30;
    private static final int ANALYTICS_SETTINGS = 40;

    private static final int OPTION_CHECK_SET_ID = 10;

    private static SettingsFragment mSettingsFragment;
    private static MasterBackendFragment mMasterBackendFragment;
    private static MasterBackendUrlFragment mMasterBackendUrlFragment;
    private static MasterBackendPortFragment mMasterBackendPortFragment;
    private static MasterBackendReadTimeoutFragment mMasterBackendReadTimeoutFragment;
    private static MasterBackendConnectTimeoutFragment mMasterBackendConnectTimeoutFragment;
    private static PlayerFragment mPlayerFragment;
    private static InternalPlayerFragment mInternalPlayerFragment;
    private static ContentFragment mContentFragment;
    private static AnalyticsFragment mAnalyticsFragment;

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, SettingsActivity.class );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate" );
        super.onCreate( savedInstanceState );

        mSettingsFragment = new SettingsFragment();
        mMasterBackendFragment = new MasterBackendFragment();
        mMasterBackendUrlFragment = new MasterBackendUrlFragment();
        mMasterBackendPortFragment = new MasterBackendPortFragment();
        mMasterBackendReadTimeoutFragment = new MasterBackendReadTimeoutFragment();
        mMasterBackendConnectTimeoutFragment = new MasterBackendConnectTimeoutFragment();
        mPlayerFragment = new PlayerFragment();
        mInternalPlayerFragment = new InternalPlayerFragment();
        mContentFragment = new ContentFragment();
        mAnalyticsFragment = new AnalyticsFragment();

        GuidedStepFragment.addAsRoot( this, mSettingsFragment, android.R.id.content );

    }

    @Override
    public void onConfigurationChanged( Configuration newConfig ) {
        Log.v( TAG, "onConfigurationChanged" );
        super.onConfigurationChanged( newConfig );

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.v( TAG, "onSaveInstanceState" );
        super.onSaveInstanceState( outState );

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        Log.v( TAG, "onRestoreInstanceState" );
        super.onRestoreInstanceState( savedInstanceState );

    }

    public static class SettingsFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.tv_settings_title );
            String breadcrumb = "";
            String description = getResources().getString( R.string.tv_settings_title_description );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );
        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {

            FragmentManager fm = getFragmentManager();

            Log.d( TAG, "onGuidedActionClicked : action=" + action );
            switch( (int) action.getId() ) {

                case MASTER_BACKEND_SETTINGS :

                    GuidedStepFragment.add( fm, mMasterBackendFragment, android.R.id.content );

                    break;

                case PLAYER_SETTINGS :

                    GuidedStepFragment.add( fm, mPlayerFragment, android.R.id.content );

                    break;

                case CONTENT_SETTINGS :

                    GuidedStepFragment.add( fm, mContentFragment, android.R.id.content );

                    break;

                case ANALYTICS_SETTINGS :

                    GuidedStepFragment.add( fm, mAnalyticsFragment, android.R.id.content );

                    break;

                default :

            }

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean internalPlayer = getShouldUseInternalPlayer( getActivity() );

            String playback = ( internalPlayer ? getResources().getString( R.string.tv_settings_playback_internal_player ) : getResources().getString( R.string.tv_settings_playback_external_player ) );

            boolean showAdultContent = getShowAdultContent( getActivity() );
            String content = ( showAdultContent ? getResources().getString( R.string.tv_settings_content_adult_shown ) : getResources().getString( R.string.tv_settings_content_adult_hidden ) );

            addAction( getActivity(), actions, MASTER_BACKEND_SETTINGS,
                    getResources().getString( R.string.tv_settings_master_backend_title ),
                    getMasterBackendUrl( getActivity() ),
                    true, true );
            addAction( getActivity(), actions, PLAYER_SETTINGS,
                    getResources().getString( R.string.tv_settings_playback_title ),
                    playback,
                    true, true );
            addAction( getActivity(), actions, CONTENT_SETTINGS,
                    getResources().getString( R.string.tv_settings_content_title ),
                    content,
                    true, true );

            setActions( actions );

        }

    }

    public static class MasterBackendFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.tv_settings_master_backend_title );
            String breadcrumb = getResources().getString( R.string.tv_settings_title );
            String description = getResources().getString( R.string.tv_settings_master_backend_description );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
            super.onGuidedActionClicked(action);

            FragmentManager fm = getFragmentManager();

            Log.d( TAG, "onGuidedActionClicked : action=" + action );
            switch( (int) action.getId() ) {

                case MASTER_BACKEND_URL :

                    GuidedStepFragment.add( fm, mMasterBackendUrlFragment, android.R.id.content );

                    break;

                case MASTER_BACKEND_PORT :

                    GuidedStepFragment.add( fm, mMasterBackendPortFragment, android.R.id.content );

                    break;

                case MASTER_BACKEND_READ_TIMEOUT :

                    GuidedStepFragment.add( fm, mMasterBackendReadTimeoutFragment, android.R.id.content );

                    break;

                case MASTER_BACKEND_CONNECT_TIMEOUT :

                    GuidedStepFragment.add( fm, mMasterBackendConnectTimeoutFragment, android.R.id.content );

                    break;

            }

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String url = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_URL );
            String port = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_PORT );

            String[] entries = getResources().getStringArray( R.array.pref_timeout_entries );
            String[] values = getResources().getStringArray( R.array.pref_timeout_values );

            String readTimeout = entries[ 0 ];
            for( int i = 0; i < values.length; i++ ) {

                String value = values[ i ];
                if( value.equals( getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_READ_TIMEOUT ) ) ) {

                    readTimeout = entries[ i ];

                }

            }

            String connectTimeout = entries[ 0 ];
            for( int i = 0; i < values.length; i++ ) {

                String value = values[ i ];
                if( value.equals( getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_CONNECT_TIMEOUT ) ) ) {

                    connectTimeout = entries[ i ];

                }

            }

            addAction( getActivity(), actions, MASTER_BACKEND_URL,
                    url,
                    getResources().getString( R.string.pref_backend_url_description ),
                    true, true );
            addAction( getActivity(), actions, MASTER_BACKEND_PORT,
                    port,
                    getResources().getString( R.string.pref_backend_port_description ),
                    true, true );
            addAction( getActivity(), actions, MASTER_BACKEND_READ_TIMEOUT,
                    readTimeout,
                    getResources().getString( R.string.pref_read_timeout_title_summary ),
                    true, true );
            addAction( getActivity(), actions, MASTER_BACKEND_CONNECT_TIMEOUT,
                    connectTimeout,
                    getResources().getString( R.string.pref_connect_timeout_title_summary ),
                    true, true );

            setActions( actions );

        }

    }

    public static class MasterBackendUrlFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.tv_settings_master_backend_url );
            String breadcrumb = getResources().getString( R.string.tv_settings_master_backend_title );
            String description = getResources().getString( R.string.pref_backend_url_description );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionEdited( GuidedAction action ) {
            super.onGuidedActionEdited( action );

            Log.d( TAG, "onGuidedActionEdited : action=" + action );

            View view = super.getGuidedActionsStylist().getActionsGridView().findFocus();
            if( null != view ) {

                EditText title = (EditText) view.findViewById( R.id.guidedactions_item_title );
                Log.d( TAG, "onGuidedActionEdited : title=" + title.getText() );
                action.setTitle( title.getText() );
                action.setEditTitle( title.getText() );

                putStringToPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_URL, action.getEditTitle().toString() );

                mSettingsFragment.updateActions( null );
                mMasterBackendFragment.updateActions( null );

            }

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String url = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_URL );

            addEditableAction( getActivity(), actions, MASTER_BACKEND_URL,
                    url,
                    getResources().getString( R.string.pref_backend_url_description ) );

            setActions( actions );

        }

    }

    public static class MasterBackendPortFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.tv_settings_master_backend_port );
            String breadcrumb = getResources().getString( R.string.tv_settings_master_backend_title );
            String description = getResources().getString( R.string.pref_backend_port_description );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionEdited( GuidedAction action ) {
            super.onGuidedActionEdited( action );

            Log.d( TAG, "onGuidedActionEdited : action=" + action );

            View view = super.getGuidedActionsStylist().getActionsGridView().findFocus();
            if( null != view ) {

                EditText title = (EditText) view.findViewById( R.id.guidedactions_item_title );
                Log.d( TAG, "onGuidedActionEdited : title=" + title.getText() );
                action.setTitle( title.getText() );
                action.setEditTitle( title.getText() );

                putStringToPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_PORT, action.getEditTitle().toString() );

                mSettingsFragment.updateActions( null );
                mMasterBackendFragment.updateActions( null );

            }

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String port = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_PORT );

            addEditableAction( getActivity(), actions, MASTER_BACKEND_PORT,
                    port,
                    getResources().getString( R.string.pref_backend_port_description ) );

            setActions( actions );

        }

    }

    public static class MasterBackendReadTimeoutFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.tv_settings_read_timeout );
            String breadcrumb = getResources().getString( R.string.tv_settings_read_timeout_title );
            String description = getResources().getString( R.string.pref_read_timeout_title_summary );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            String[] entries = getResources().getStringArray( R.array.pref_timeout_entries );
            String[] values = getResources().getStringArray( R.array.pref_timeout_values );

            String selectedTimeout = values[ 0 ];
            for( int i = 0; i < entries.length; i++ ) {

                String entry = entries[ i ];
                if( entry.equals( action.getLabel1().toString() ) ) {

                    selectedTimeout = values[ i ];
                    break;

                }

            }

            putStringToPreferences( getActivity(), SettingsKeys.KEY_PREF_READ_TIMEOUT, selectedTimeout );

            mSettingsFragment.updateActions( null );
            mMasterBackendFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String timeout = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_READ_TIMEOUT );
            String[] entries = getResources().getStringArray( R.array.pref_timeout_entries );
            String[] values = getResources().getStringArray( R.array.pref_timeout_values );

            for( int i = 0; i < entries.length; i++ ) {

                addCheckedAction( getActivity(), actions,
                        -1,
                        entries[ i ],
                        null,
                        values[ i ].equals( timeout ) );

            }

            setActions( actions );

        }

    }

    public static class MasterBackendConnectTimeoutFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.tv_settings_connect_timeout );
            String breadcrumb = getResources().getString( R.string.tv_settings_connect_timeout_title );
            String description = getResources().getString( R.string.pref_connect_timeout_title_summary );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            String[] entries = getResources().getStringArray( R.array.pref_timeout_entries );
            String[] values = getResources().getStringArray( R.array.pref_timeout_values );

            String selectedTimeout = values[ 0 ];
            for( int i = 0; i < entries.length; i++ ) {

                String entry = entries[ i ];
                if( entry.equals( action.getLabel1().toString() ) ) {

                    selectedTimeout = values[ i ];
                    break;

                }

            }

            putStringToPreferences( getActivity(), SettingsKeys.KEY_PREF_CONNECT_TIMEOUT, selectedTimeout );

            mSettingsFragment.updateActions( null );
            mMasterBackendFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String timeout = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_CONNECT_TIMEOUT );
            String[] entries = getResources().getStringArray( R.array.pref_timeout_entries );
            String[] values = getResources().getStringArray( R.array.pref_timeout_values );

            for( int i = 0; i < entries.length; i++ ) {

                addCheckedAction( getActivity(), actions,
                        -1,
                        entries[ i ],
                        null,
                        values[ i ].equals( timeout ) );

            }

            setActions( actions );

        }

    }

    public static class PlayerFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.tv_settings_playback_title );
            String breadcrumb = getResources().getString( R.string.tv_settings_playback_title );
            String description = getResources().getString( R.string.tv_settings_playback_title_description );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {

            FragmentManager fm = getFragmentManager();

            Log.d( TAG, "onGuidedActionClicked : action=" + action );
            switch( (int) action.getId() ) {

                case INTERNAL_PLAYER_SETTINGS :

                    GuidedStepFragment.add( fm, mInternalPlayerFragment, android.R.id.content );

                    break;

            }

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String internalPlayer = ( getShouldUseInternalPlayer( getActivity() ) ? getResources().getString( R.string.tv_settings_yes ) : getResources().getString( R.string.tv_settings_no ) );

            addAction( getActivity(), actions, INTERNAL_PLAYER_SETTINGS,
                    getResources().getString( R.string.tv_settings_playback_internal_player ),
                    internalPlayer,
                    true, true );

            setActions( actions );

        }

    }

    public static class InternalPlayerFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.tv_settings_playback_internal_player );
            String breadcrumb = getResources().getString( R.string.tv_settings_playback_title );
            String description = getResources().getString( R.string.tv_settings_playback_internal_player_description );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );
        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_INTERNAL_PLAYER, updated );

            mSettingsFragment.updateActions( null );
            mPlayerFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean internalPlayer = getShouldUseInternalPlayer( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    internalPlayer );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !internalPlayer );

            setActions( actions );

        }

    }

    public static class ContentFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.tv_settings_content_adult_title );
            String breadcrumb = getResources().getString( R.string.tv_settings_content_title );
            String description = getResources().getString( R.string.tv_settings_content_title_description );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_SHOW_ADULT_TAB, updated );

            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean showAdultContent = getShowAdultContent( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    showAdultContent );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !showAdultContent );

            setActions( actions );

        }

    }

    public static class AnalyticsFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_enable_analytics_label );
            String breadcrumb = getResources().getString( R.string.pref_enable_analytics_title );
            String description = getResources().getString( R.string.tv_settings_content_title_description );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_ENABLE_ANALYTICS, updated );

            FirebaseAnalytics.getInstance( getActivity() ).setAnalyticsCollectionEnabled( updated );

            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean showAdultContent = getShowAdultContent( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    showAdultContent );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !showAdultContent );

            setActions( actions );

        }

    }

    private static void addAction( Context context, List<GuidedAction> actions, long id, String title, String desc, boolean enabled, boolean hasNext ) {

        actions.add( new GuidedAction.Builder( context )
                .id( id )
                .title( title )
                .description( desc )
                .editable( false )
                .enabled( enabled )
                .hasNext( hasNext )
                .build()
        );

    }

    private static void addEditableAction( Context context, List<GuidedAction> actions, long id, String title, String desc ) {

        actions.add( new GuidedAction.Builder( context )
                .id( id )
                .title( title )
                .description( desc )
                .editable( true )
                .hasNext( false )
                .build() );

    }

    private static void addEditableAction( Context context, List<GuidedAction> actions, long id, String title, String editTitle, String desc ) {

        actions.add( new GuidedAction.Builder( context )
                .id( id )
                .title( title )
                .editTitle( editTitle )
                .description( desc )
                .editable( true )
                .hasNext( false )
                .build() );

    }

//    private static void addEditableAction( Context context, List<GuidedAction> actions, long id, String title, String editTitle, int editInputType, String desc, String editDesc ) {
//
//        actions.add( new GuidedAction.Builder( context )
//                .id( id )
//                .title( title )
//                .editTitle( editTitle )
//                .editInputType( editInputType )
//                .description( desc )
//                .editDescription( editDesc )
//                .editable( true )
//                .build() );
//
//    }

//    private static void addEditableDescriptionAction( Context context, List<GuidedAction> actions, long id, String title, String desc, String editDescription, int descriptionEditInputType ) {
//
//        actions.add( new GuidedAction.Builder( context )
//                .id( id )
//                .title( title )
//                .description( desc )
//                .editDescription( editDescription )
//                .descriptionEditInputType( descriptionEditInputType )
//                .descriptionEditable( true )
//                .build() );
//
//    }

    private static void addCheckedAction( Context context, List<GuidedAction> actions, int iconResId, String title, String desc, boolean checked ) {

        GuidedAction guidedAction = new GuidedAction.Builder( context )
                .title( title )
                .description( desc )
                .checkSetId( OPTION_CHECK_SET_ID )
//                .iconResourceId( iconResId, context )
                .build();

        guidedAction.setChecked( checked );
        actions.add( guidedAction );

    }

    private static String getMasterBackendUrl( Context context ) {

        String host = getStringFromPreferences( context, SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getStringFromPreferences( context, SettingsKeys.KEY_PREF_BACKEND_PORT );

        return "http://" + host + ":" + port;
    }

    private static boolean getShouldUseInternalPlayer( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_INTERNAL_PLAYER );
    }

    private static boolean getShowAdultContent( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_SHOW_ADULT_TAB );
    }

    private static String getStringFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getString( key, "" );
    }

    private static void putStringToPreferences( Context context, String key, String value ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
        sharedPreferences.edit().putString( key, value ).apply();

    }

    private static boolean getBooleanFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getBoolean( key, false );
    }

    private static void putBooleanToPreferences( Context context, String key, boolean value ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
        sharedPreferences.edit().putBoolean( key, value ).apply();

    }

}
