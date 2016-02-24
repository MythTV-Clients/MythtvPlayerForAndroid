package org.mythtv.android.presentation.view.activity;

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
import android.support.v17.leanback.widget.GuidedActionEditText;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 1/28/16.
 */
public class TvSettingsActivity extends Activity {

    private static final String TAG = TvSettingsActivity.class.getSimpleName();

    private static final int MASTER_BACKEND_SETTINGS = 10;
    private static final int MASTER_BACKEND_URL = 11;
    private static final int MASTER_BACKEND_PORT = 12;
    private static final int PLAYER_SETTINGS = 20;
    private static final int INTERNAL_PLAYER_SETTINGS = 21;
    private static final int EXTERNAL_PLAYER_OVERRIDE_SETTINGS = 20;
    private static final int CONTENT_SETTINGS = 30;

    private static final int OPTION_CHECK_SET_ID = 10;

    private static SettingsFragment mSettingsFragment;
    private static MasterBackendFragment mMasterBackendFragment;
    private static MasterBackendUrlFragment mMasterBackendUrlFragment;
    private static MasterBackendPortFragment mMasterBackendPortFragment;
    private static PlayerFragment mPlayerFragment;
    private static InternalPlayerFragment mInternalPlayerFragment;
    private static ExternalPlayerOverrideFragment mExternalPlayerOverrideFragment;
    private static ContentFragment mContentFragment;

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, TvSettingsActivity.class );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate" );
        super.onCreate( savedInstanceState );

        mSettingsFragment = new SettingsFragment();
        mMasterBackendFragment = new MasterBackendFragment();
        mMasterBackendUrlFragment = new MasterBackendUrlFragment();
        mMasterBackendPortFragment = new MasterBackendPortFragment();
        mPlayerFragment = new PlayerFragment();
        mInternalPlayerFragment = new InternalPlayerFragment();
        mExternalPlayerOverrideFragment = new ExternalPlayerOverrideFragment();
        mContentFragment = new ContentFragment();

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

                default :

            }

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean internalPlayer = getShouldUseInternalPlayer( getActivity() );
            boolean externalPlayerOverride = getShouldUseExternalPlayerOverride( getActivity() );
            String playback = ( internalPlayer ? getResources().getString( R.string.tv_settings_playback_internal_player ) : getResources().getString( R.string.tv_settings_playback_external_player ) );
            if( internalPlayer && externalPlayerOverride ) {
                playback = getResources().getString( R.string.tv_settings_playback_external_player_override );
            }

            boolean showAdultContent = getShowAdultContent( getActivity() );
            String content = ( showAdultContent ? getResources().getString( R.string.tv_settings_content_adult_shown ) : getResources().getString( R.string.tv_settings_content_adult_hidden ) );

            addAction( actions, MASTER_BACKEND_SETTINGS,
                    getResources().getString( R.string.tv_settings_master_backend_title ),
                    getMasterBackendUrl( getActivity() ),
                    true, true );
            addAction( actions, PLAYER_SETTINGS,
                    getResources().getString( R.string.tv_settings_playback_title ),
                    playback,
                    true, true );
            addAction( actions, CONTENT_SETTINGS,
                    getResources().getString( R.string.tv_settings_content_title ),
                    content,
                    true, true );

            setActions( actions );

        }

    }

    public static class MasterBackendFragment extends GuidedStepFragment {

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

            }

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String url = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_URL );
            String port = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_PORT );

            addAction( actions, MASTER_BACKEND_URL,
                    url,
                    getResources().getString( R.string.pref_backend_url_description ),
                    true, true );
            addAction( actions, MASTER_BACKEND_PORT,
                    port,
                    getResources().getString( R.string.pref_backend_port_description ),
                    true, true );

            setActions( actions );

        }

    }

    public static class MasterBackendUrlFragment extends GuidedStepFragment {

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

            addEditableAction( actions, MASTER_BACKEND_URL,
                    url,
                    getResources().getString( R.string.pref_backend_url_description ) );

            setActions( actions );

        }

    }

    public static class MasterBackendPortFragment extends GuidedStepFragment {

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

            addEditableAction( actions, MASTER_BACKEND_PORT,
                    port,
                    getResources().getString( R.string.pref_backend_port_description ) );

            setActions( actions );

        }

    }

    public static class PlayerFragment extends GuidedStepFragment {

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

                case EXTERNAL_PLAYER_OVERRIDE_SETTINGS :

                    GuidedStepFragment.add( fm, mExternalPlayerOverrideFragment, android.R.id.content );

                    break;

            }

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String internalPlayer = ( getShouldUseInternalPlayer( getActivity() ) ? getResources().getString( R.string.tv_settings_yes ) : getResources().getString( R.string.tv_settings_no ) );
            String externalPlayer = ( getShouldUseExternalPlayerOverride( getActivity() ) ? getResources().getString( R.string.tv_settings_yes ) : getResources().getString( R.string.tv_settings_no ) );

            addAction( actions, INTERNAL_PLAYER_SETTINGS,
                    getResources().getString( R.string.tv_settings_playback_internal_player ),
                    internalPlayer,
                    true, true );
            addAction( actions, EXTERNAL_PLAYER_OVERRIDE_SETTINGS,
                    getResources().getString( R.string.tv_settings_playback_external_player ),
                    externalPlayer,
                    getShouldUseInternalPlayer( getActivity() ), true );

            setActions( actions );

        }

    }

    public static class InternalPlayerFragment extends GuidedStepFragment {

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

            if( !updated ) {

                putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO, false );

            }

            mSettingsFragment.updateActions( null );
            mPlayerFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean internalPlayer = getShouldUseInternalPlayer( getActivity() );

            addCheckedAction( actions,
                    -1,
                    getActivity(),
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    internalPlayer );
            addCheckedAction( actions,
                    -1,
                    getActivity(),
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !internalPlayer );

            setActions( actions );

        }

    }

    public static class ExternalPlayerOverrideFragment extends GuidedStepFragment {

        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.tv_settings_playback_external_player );
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
            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO, updated );

            mSettingsFragment.updateActions( null );
            mPlayerFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean externalPlayerOverride = getShouldUseExternalPlayerOverride( getActivity() );

            addCheckedAction( actions,
                    -1,
                    getActivity(),
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    externalPlayerOverride );
            addCheckedAction( actions,
                    -1,
                    getActivity(),
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !externalPlayerOverride );

            setActions( actions );

        }

    }

    public static class ContentFragment extends GuidedStepFragment {

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

            addCheckedAction( actions,
                    -1,
                    getActivity(),
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    showAdultContent );
            addCheckedAction( actions,
                    -1,
                    getActivity(),
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !showAdultContent );

            setActions( actions );

        }

    }

    private static void addAction( List<GuidedAction> actions, long id, String title, String desc, boolean enabled, boolean hasNext ) {

        actions.add( new GuidedAction.Builder()
                .id( id )
                .title( title )
                .description( desc )
                .editable( false )
                .enabled( enabled )
                .hasNext( hasNext )
                .build()
        );

    }

    private static void addEditableAction( List<GuidedAction> actions, long id, String title, String desc ) {

        actions.add( new GuidedAction.Builder()
                .id( id )
                .title( title )
                .description( desc )
                .editable( true )
                .hasNext( false )
                .build() );

    }

    private static void addEditableAction( List<GuidedAction> actions, long id, String title, String editTitle, String desc ) {

        actions.add( new GuidedAction.Builder()
                .id( id )
                .title( title )
                .editTitle( editTitle )
                .description( desc )
                .editable( true )
                .hasNext( false )
                .build() );

    }

//    private static void addEditableAction( List<GuidedAction> actions, long id, String title, String editTitle, int editInputType, String desc, String editDesc ) {
//
//        actions.add( new GuidedAction.Builder()
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

//    private static void addEditableDescriptionAction( List<GuidedAction> actions, long id, String title, String desc, String editDescription, int descriptionEditInputType ) {
//
//        actions.add( new GuidedAction.Builder()
//                .id( id )
//                .title( title )
//                .description( desc )
//                .editDescription( editDescription )
//                .descriptionEditInputType( descriptionEditInputType )
//                .descriptionEditable( true )
//                .build() );
//
//    }

    private static void addCheckedAction( List<GuidedAction> actions, int iconResId, Context context, String title, String desc, boolean checked ) {

        GuidedAction guidedAction = new GuidedAction.Builder()
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

        String masterBackend = "http://" + host + ":" + port;

        return masterBackend;
    }

    private static boolean getShouldUseInternalPlayer( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_INTERNAL_PLAYER );
    }

    private static boolean getShouldUseExternalPlayerOverride( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO );
    }

    private static boolean getShowAdultContent( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_SHOW_ADULT_TAB );
    }

    private static String getStringFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getString( key, "" );
    }

    private static int getIntFromPreferences( Context context, String key, int defValue ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getInt( key, defValue );
    }

    private static void putStringToPreferences( Context context, String key, String value ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
        sharedPreferences.edit().putString( key, value ).apply();

    }

    private static void putIntToPreferences( Context context, String key, int value ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
        sharedPreferences.edit().putInt( key, value ).apply();

    }

    private static boolean getBooleanFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getBoolean( key, false );
    }

    private static void putBooleanToPreferences( Context context, String key, boolean value ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
        sharedPreferences.edit().putBoolean( key, value ).apply();

    }

}
