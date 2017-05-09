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

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.presenter.phone.MediaItemListPresenter;
import org.mythtv.android.presentation.view.MediaItemListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.ennova.zerxconf.ZeRXconf;
import rx.Observable;
import rx.Subscription;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/28/16.
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.GodClass" })
public class SettingsActivity extends AbstractBaseTvActivity implements HasComponent<MediaComponent> {

    private static final String TAG = SettingsActivity.class.getSimpleName();
    private static final String SEPARATOR = " | ";

    private static final int MASTER_BACKEND_SETTINGS = 10;
    private static final int MASTER_BACKEND_SCAN_URL = 11;
    private static final int MASTER_BACKEND_URL = 12;
    private static final int MASTER_BACKEND_PORT = 13;
    private static final int PLAYER_SETTINGS = 20;
    private static final int INTERNAL_PLAYER_SETTINGS = 21;
    private static final int FILTER_SETTINGS = 30;
    private static final int FILTER_HLS_ONLY = 31;
    private static final int RECORDING_SETTINGS = 40;
    private static final int ENABLE_RECORDING_GROUP = 41;
    private static final int RECORDING_GROUPS_FILTER = 42;
    private static final int VIDEO_SETTINGS = 50;
    private static final int ADULT_SETTINGS = 51;
    private static final int PARENTAL_CONTROLS = 52;
    private static final int PARENTAL_CONTROL_LEVEL = 53;
    private static final int CONTENT_RATINGS = 54;
    private static final int CONTENT_RATING_NR = 55;
    private static final int CONTENT_RATING_G = 56;
    private static final int CONTENT_RATING_PG = 57;
    private static final int CONTENT_RATING_PG13 = 58;
    private static final int CONTENT_RATING_R = 59;
    private static final int CONTENT_RATING_NC17 = 60;
    private static final int ANALYTICS_SETTINGS = 70;

    private static final int OPTION_CHECK_SET_ID = 10;

    private static SettingsFragment mSettingsFragment;

    private static MasterBackendFragment mMasterBackendFragment;
    private static MasterBackendScanUrlFragment mMasterBackendScanUrlFragment;
    private static MasterBackendUrlFragment mMasterBackendUrlFragment;
    private static MasterBackendPortFragment mMasterBackendPortFragment;

    private static PlayerFragment mPlayerFragment;
    private static InternalPlayerFragment mInternalPlayerFragment;

    private static FilterSettingsFragment mFilterSettingsFragment;
    private static EnableHlsOnlyFilterFragment mEnableHlsOnlyFilterFragment;

    private static RecordingSettingsFragment mRecordingSettingsFragment;
    private static EnableRecordingGroupFragment mEnableRecordingGroupFragment;
    private static RecordingGroupFragment mRecordingGroupFragment;

    private static VideoSettingsFragment mVideoSettingsFragment;
    private static AdultContentFragment mAdultContentFragment;
    private static ParentalControlsFragment mParentalControlsFragment;
    private static ParentalControlLevelFragment mParentalControlLevelFragment;
    private static ContentRatingFragment mContentRatingFragment;
    private static ContentRatingNrFragment mContentRatingNrFragment;
    private static ContentRatingGFragment mContentRatingGFragment;
    private static ContentRatingPgFragment mContentRatingPgFragment;
    private static ContentRatingPg13Fragment mContentRatingPg13Fragment;
    private static ContentRatingRFragment mContentRatingRFragment;
    private static ContentRatingNc17Fragment mContentRatingNc17Fragment;

    private static AnalyticsFragment mAnalyticsFragment;

    private MediaComponent mediaComponent;

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, SettingsActivity.class );
    }

    @Override
    public int getLayoutResource() {

        return -1;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate" );
        super.onCreate( savedInstanceState );

        mSettingsFragment = new SettingsFragment();

        mMasterBackendFragment = new MasterBackendFragment();
        mMasterBackendScanUrlFragment = new MasterBackendScanUrlFragment();
        mMasterBackendUrlFragment = new MasterBackendUrlFragment();
        mMasterBackendPortFragment = new MasterBackendPortFragment();

        mPlayerFragment = new PlayerFragment();
        mInternalPlayerFragment = new InternalPlayerFragment();

        mFilterSettingsFragment = new FilterSettingsFragment();
        mEnableHlsOnlyFilterFragment = new EnableHlsOnlyFilterFragment();

        mRecordingSettingsFragment = new RecordingSettingsFragment();
        mEnableRecordingGroupFragment = new EnableRecordingGroupFragment();
        mRecordingGroupFragment = new RecordingGroupFragment();

        mVideoSettingsFragment = new VideoSettingsFragment();
        mAdultContentFragment = new AdultContentFragment();
        mParentalControlsFragment = new ParentalControlsFragment();
        mParentalControlLevelFragment = new ParentalControlLevelFragment();
        mContentRatingFragment = new ContentRatingFragment();
        mContentRatingNrFragment = new ContentRatingNrFragment();
        mContentRatingGFragment = new ContentRatingGFragment();
        mContentRatingPgFragment = new ContentRatingPgFragment();
        mContentRatingPg13Fragment = new ContentRatingPg13Fragment();
        mContentRatingRFragment = new ContentRatingRFragment();
        mContentRatingNc17Fragment = new ContentRatingNc17Fragment();

        mAnalyticsFragment = new AnalyticsFragment();

        GuidedStepFragment.addAsRoot( this, mSettingsFragment, android.R.id.content );

        this.initializeInjector();

    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.mediaComponent = DaggerMediaComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public MediaComponent getComponent() {
        Log.v( TAG, "getComponent : enter" );

        Log.v( TAG, "getComponent : exit" );
        return mediaComponent;
    }

    public static class SettingsFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.title_activity_settings );
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

//            Log.d( TAG, "onGuidedActionClicked : action=" + action );
            switch( (int) action.getId() ) {

                case MASTER_BACKEND_SETTINGS :

                    GuidedStepFragment.add( fm, mMasterBackendFragment, android.R.id.content );

                    break;

                case PLAYER_SETTINGS :

                    GuidedStepFragment.add( fm, mPlayerFragment, android.R.id.content );

                    break;

                case FILTER_SETTINGS :

                    GuidedStepFragment.add( fm, mFilterSettingsFragment, android.R.id.content );

                    break;

                case RECORDING_SETTINGS:

                    GuidedStepFragment.add( fm, mRecordingSettingsFragment, android.R.id.content );

                    break;

                case VIDEO_SETTINGS:

                    GuidedStepFragment.add( fm, mVideoSettingsFragment, android.R.id.content );

                    break;

                case ANALYTICS_SETTINGS :

                    GuidedStepFragment.add( fm, mAnalyticsFragment, android.R.id.content );

                    break;

                default :

                    break;

            }

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean internalPlayer = getShouldUseInternalPlayer( getActivity() );

            String playback = ( internalPlayer ? getResources().getString( R.string.pref_internal_player_summary_on ) : getResources().getString( R.string.pref_internal_player_summary_off ) );

            boolean enableHlsOnly = getFilterHlsOnly( getActivity() );
            String hlsOnly = ( enableHlsOnly ? getResources().getString( R.string.pref_filter_hls_only_summary_on ) : getResources().getString( R.string.pref_filter_hls_only_summary_off ) );

            boolean enableAnalytics = getEnableAnalytics( getActivity() );
            String analytics = ( enableAnalytics ? getResources().getString( R.string.pref_enable_analytics_summary_on ) : getResources().getString( R.string.pref_enable_analytics_summary_off ) );

            addAction( getActivity(), actions, MASTER_BACKEND_SETTINGS,
                    getResources().getString( R.string.pref_backend_title ),
                    getMasterBackendUrl( getActivity() ),
                    true, true );
            addAction( getActivity(), actions, PLAYER_SETTINGS,
                    getResources().getString( R.string.pref_default_player ),
                    playback,
                    true, true );
            addAction( getActivity(), actions, FILTER_SETTINGS,
                    getResources().getString( R.string.pref_filter_hls_only_title ),
                    hlsOnly,
                    true, true );
            addAction( getActivity(), actions, RECORDING_SETTINGS,
                    getResources().getString( R.string.recording_preferences ),
                    getResources().getString( R.string.recording_preferences_summary ),
                    true, true );
            addAction( getActivity(), actions, VIDEO_SETTINGS,
                    getResources().getString( R.string.video_preferences ),
                    getResources().getString( R.string.video_preferences_summary ),
                    true, true );
            addAction( getActivity(), actions, ANALYTICS_SETTINGS,
                    getResources().getString( R.string.pref_enable_analytics_title ),
                    analytics,
                    true, true );

            setActions( actions );

        }

    }

    public static class MasterBackendFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_backend_title );
            String breadcrumb = getResources().getString( R.string.title_activity_settings );
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

                case MASTER_BACKEND_SCAN_URL :

                    GuidedStepFragment.add( fm, mMasterBackendScanUrlFragment, android.R.id.content );

                    break;

                case MASTER_BACKEND_URL :

                    GuidedStepFragment.add( fm, mMasterBackendUrlFragment, android.R.id.content );

                    break;

                case MASTER_BACKEND_PORT :

                    GuidedStepFragment.add( fm, mMasterBackendPortFragment, android.R.id.content );

                    break;

                default :

                    break;

            }

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String url = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_URL );
            String port = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_PORT );

            addAction( getActivity(), actions, MASTER_BACKEND_SCAN_URL,
                    getResources().getString( R.string.backend_scan ),
                    getResources().getString( R.string.backend_scan_summary ),
                    true, true );
            addAction( getActivity(), actions, MASTER_BACKEND_URL,
                    url,
                    getResources().getString( R.string.pref_backend_url_description ),
                    true, true );
            addAction( getActivity(), actions, MASTER_BACKEND_PORT,
                    port,
                    getResources().getString( R.string.pref_backend_port_description ),
                    true, true );

            setActions( actions );

        }

    }

    public static class MasterBackendScanUrlFragment extends GuidedStepFragment {

        List<String> detectedBackends = new ArrayList<>();

        Subscription zeroConfSubscription;

        @Override
        public void onCreate( Bundle savedInstanceState ) {
            super.onCreate( savedInstanceState );


        }

        @Override
        public void onActivityCreated( Bundle savedInstanceState ) {
            Log.d( TAG, "onActivityCreated : enter" );
            super.onActivityCreated( savedInstanceState );

            this.initialize();

            Log.d( TAG, "onActivityCreated : exit" );
        }

        @Override
        public void onPause() {
            Log.d( TAG, "onPause : enter" );

            if( null != zeroConfSubscription && !zeroConfSubscription.isUnsubscribed() ) {

                zeroConfSubscription.unsubscribe();

            }

            super.onPause();

            Log.d( TAG, "onPause : exit" );
        }

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_backend_url_title );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + " | " + getResources().getString( R.string.backend_scan );
            String description = getResources().getString( R.string.pref_backend_url_description );
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

            String updated = action.getLabel1().toString();
            String[] updatedComponents = updated.split( ":" );
            if( updatedComponents.length == 2 ) {

                putStringToPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_URL, updatedComponents[ 0 ] );
                putStringToPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_PORT, updatedComponents[ 1 ] );

                mMasterBackendFragment.updateActions( null );
                mSettingsFragment.updateActions( null );

            }

            getFragmentManager().popBackStack();

        }

        private void initialize() {
            Log.d( TAG, "initialize : enter" );

            lookupBackend();

            Log.d( TAG, "initialize : exit" );
        }

        public void updateActions( List<GuidedAction> actions ) {

            String url = getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_URL ) + ":" + getStringFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_PORT );

            List<GuidedAction> guidedActions = new ArrayList<>();
            if( null != actions ) {

                guidedActions = actions;

            }

            if( !detectedBackends.isEmpty() ) {

                for( String backend : detectedBackends ) {

                    addCheckedAction( getActivity(), guidedActions,
                            -1,
                            backend,
                            null,
                            backend.equals( url ) );

                }

                setActions( guidedActions );

            }

        }

        private void lookupBackend() {
            Log.d( TAG, "lookupBackend : enter" );

            zeroConfSubscription = ZeRXconf.startDiscovery( getActivity(), "_mythbackend._tcp." )
                    .subscribe(
                            nsdInfo -> {
                                Log.i( TAG, "lookupBackend : " + nsdInfo.getAddress().getHostAddress() + ":" + nsdInfo.getServicePort() );

                                if( !detectedBackends.contains( nsdInfo.getAddress().getHostAddress() + ":" + nsdInfo.getServicePort() ) ) {

                                    detectedBackends.add( nsdInfo.getAddress().getHostAddress() + ":" + nsdInfo.getServicePort() );

                                    updateActions( null );

                                }


                            },
                            e -> {
                                Log.e( TAG, "lookupBackend : network discovery failed", e );
                            }
                    );

            Log.d( TAG, "lookupBackend : exit" );
        }

    }

    public static class MasterBackendUrlFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_backend_url_title );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.pref_backend_title );
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

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
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

            String title = getResources().getString( R.string.pref_backend_port_title );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.pref_backend_title );
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

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
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

    public static class PlayerFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_default_player );
            String breadcrumb = getResources().getString( R.string.title_activity_settings );
            String description = "";
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

//            Log.d( TAG, "onGuidedActionClicked : action=" + action );
            if( (int) action.getId() == INTERNAL_PLAYER_SETTINGS) {

                GuidedStepFragment.add( fm, mInternalPlayerFragment, android.R.id.content );

            }

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String internalPlayer = ( getShouldUseInternalPlayer( getActivity() ) ? getResources().getString( R.string.pref_internal_player_summary_on ) : getResources().getString( R.string.pref_internal_player_summary_off ) );

            addAction( getActivity(), actions, INTERNAL_PLAYER_SETTINGS,
                    getResources().getString( R.string.pref_internal_player ),
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
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.pref_default_player );
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
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_INTERNAL_PLAYER, updated );

            mSettingsFragment.updateActions( null );
            mPlayerFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
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

    public static class FilterSettingsFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_filter_hls_only_title );
            String breadcrumb = getResources().getString( R.string.title_activity_settings );
            String description = "";
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

//            Log.d( TAG, "onGuidedActionClicked : action=" + action );
            if( (int) action.getId() == FILTER_HLS_ONLY ) {

                GuidedStepFragment.add( fm, mEnableHlsOnlyFilterFragment, android.R.id.content );

            }

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String hlsOnly = ( getFilterHlsOnly( getActivity() ) ? getResources().getString( R.string.pref_filter_hls_only_summary_on ) : getResources().getString( R.string.pref_filter_hls_only_summary_off ) );

            addAction( getActivity(), actions, FILTER_HLS_ONLY,
                    getResources().getString( R.string.pref_filter_hls_only ),
                    hlsOnly,
                    true, true );

            setActions( actions );

        }

    }

    public static class EnableHlsOnlyFilterFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_filter_hls_only );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.pref_filter_hls_only_title );
            String description = ""; //getResources().getString( R.string.tv_settings_playback_internal_player_description );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );
        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_FILTER_HLS_ONLY, updated );

            mSettingsFragment.updateActions( null );
            mFilterSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean hlsOnly = getFilterHlsOnly( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    hlsOnly );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !hlsOnly );

            setActions( actions );

        }

    }

    public static class RecordingSettingsFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.recording_preferences );
            String breadcrumb = getResources().getString( R.string.title_activity_settings );
            String description = getResources().getString( R.string.recording_preferences_summary );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            FragmentManager fm = getFragmentManager();

            if( (int) action.getId() == ENABLE_RECORDING_GROUP ) {

                GuidedStepFragment.add(fm, mEnableRecordingGroupFragment, android.R.id.content );

            } else if( (int) action.getId() == RECORDING_GROUPS_FILTER ) {

                GuidedStepFragment.add( fm, mRecordingGroupFragment, android.R.id.content );

            }

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean enableRecordingGroupFilter = getEnableRecordingGroupFilter( getActivity() );
            String enableRecordingGroupFilterMessage = ( enableRecordingGroupFilter ? getResources().getString( R.string.pref_enable_recording_group_summary_on ) : getResources().getString( R.string.pref_enable_recording_group_summary_off ) );

            addAction( getActivity(), actions, ENABLE_RECORDING_GROUP,
                    getResources().getString( R.string.pref_enable_recording_group_filter ),
                    enableRecordingGroupFilterMessage,
                    true, true );

            if( enableRecordingGroupFilter ) {

                String recordingGroupFilter = getRecordingGroupFilter( getActivity() );

                addAction( getActivity(), actions, RECORDING_GROUPS_FILTER,
                        getResources().getString( R.string.pref_recording_group_filter ),
                        recordingGroupFilter,
                        true, true );

            }

            setActions( actions );

        }

    }

    public static class EnableRecordingGroupFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_enable_recording_group_filter );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.recording_preferences );
            String description = "";
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_ENABLE_RECORDING_GROUP_FILTER, updated );

            mRecordingSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean enableRecordingGroupFilter = getEnableRecordingGroupFilter( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    enableRecordingGroupFilter );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !enableRecordingGroupFilter );

            setActions( actions );

        }

    }

    public static abstract class AbstractBaseGuidedStepFragment extends GuidedStepFragment {

        /**
         * Gets a component for dependency injection by its type.
         */
        @SuppressWarnings( "unchecked" )
        protected <C> C getComponent( Class<C> componentType ) {

            return componentType.cast( ( (HasComponent<C>) getActivity() ).getComponent() );
        }


    }

    public static class RecordingGroupFragment extends AbstractBaseGuidedStepFragment implements MediaItemListView {

        public static final String MEDIA_KEY = "media";

        @Inject
        MediaItemListPresenter mediaItemListPresenter;

        List<String> availableRecordingGroups;

        @Override
        public void onCreate( Bundle savedInstanceState ) {
            super.onCreate( savedInstanceState );

            availableRecordingGroups = Collections.singletonList( "Default" );

        }

        @Override
        public void onActivityCreated( Bundle savedInstanceState ) {
            super.onActivityCreated( savedInstanceState );

            this.initialize();

            this.loadMediaItemList();

        }

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_recording_group_filter );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.recording_preferences );
            String description = "";
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            String updated = action.getLabel1().toString();
            putStringToPreferences( getActivity(), SettingsKeys.KEY_PREF_RECORDING_GROUP_FILTER, updated );

            mRecordingSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        private void initialize() {
            Log.d( TAG, "initialize : enter" );

            this.getComponent( MediaComponent.class ).inject( this );
            this.mediaItemListPresenter.setView( this );

            Log.d( TAG, "initialize : exit" );
        }

        @Override
        public void showLoading() {
            Log.v( TAG, "showLoading : enter" );

            Log.v( TAG, "showLoading : exit" );
        }

        @Override
        public void hideLoading() {
            Log.v( TAG, "hideLoading : enter" );

            Log.v( TAG, "shideLoading : exit" );
        }

        @Override
        public void showRetry() {
            Log.v( TAG, "showRetry : enter" );

            Log.v( TAG, "showRetry : exit" );
        }

        @Override
        public void hideRetry() {
            Log.v( TAG, "hideLoading : enter" );

            Log.v( TAG, "hideLoading : exit" );
        }

        @Override
        public void showError(String message) {
            Log.v( TAG, "showError : enter" );

            Log.v( TAG, "showError : exit" );
        }

        @Override
        public void showMessage(String message) {
            Log.v( TAG, "showMessage : enter" );

            Log.v( TAG, "showMessage : exit" );
        }

        @Override
        public void renderMediaItemList( Collection<MediaItemModel> mediaItemModelCollection ) {

            Observable.from( mediaItemModelCollection )
                    .map( MediaItemModel::recordingGroup )
                    .distinct()
                    .toList()
                    .subscribe( groups -> {
                        availableRecordingGroups = groups;
                    });

        }

        @Override
        public void viewMediaItem( final MediaItemModel mediaItemModel, final View sharedElement, final String sharedElementName ) {
            Log.v( TAG, "viewMediaItem : enter" );

            Log.v( TAG, "viewMediaItem : exit" );
        }

        @Override
        public Context getContext() {
            Log.d( TAG, "getContext : enter" );

            Log.d( TAG, "getContext : exit" );
            return this.getActivity().getApplicationContext();
        }

        /**
         * Loads media items.
         */
        private void loadMediaItemList() {
            Log.d( TAG, "loadMediaItemList : enter" );

            this.mediaItemListPresenter.initialize( Collections.singletonMap( MEDIA_KEY, Media.PROGRAM ) );

            Log.d( TAG, "loadMediaItemList : exit" );
        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            if( null == availableRecordingGroups ) {

                availableRecordingGroups = Collections.singletonList( "Default" );

            }

            String recordingGroupFilter = getRecordingGroupFilter( getActivity() );

            for( String recordingGroup : availableRecordingGroups ) {

                addCheckedAction( getActivity(), actions,
                        -1,
                        recordingGroup,
                        null,
                        recordingGroup.equals( recordingGroupFilter ) );

            }

            setActions( actions );

        }

    }

    public static class VideoSettingsFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.video_preferences );
            String breadcrumb = getResources().getString( R.string.title_activity_settings );
            String description = getResources().getString( R.string.video_preferences_summary );
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

//            Log.d( TAG, "onGuidedActionClicked : action=" + action );
            switch( (int) action.getId() ) {

                case ADULT_SETTINGS :

                    GuidedStepFragment.add( fm, mAdultContentFragment, android.R.id.content );

                    break;

                case PARENTAL_CONTROLS :

                    GuidedStepFragment.add( fm, mParentalControlsFragment, android.R.id.content );

                    break;

                case PARENTAL_CONTROL_LEVEL :

                    GuidedStepFragment.add( fm, mParentalControlLevelFragment, android.R.id.content );

                    break;

                case CONTENT_RATINGS :

                    GuidedStepFragment.add( fm, mContentRatingFragment, android.R.id.content );

                    break;

                case CONTENT_RATING_NR :

                    GuidedStepFragment.add( fm, mContentRatingNrFragment, android.R.id.content );

                    break;

                case CONTENT_RATING_G :

                    GuidedStepFragment.add( fm, mContentRatingGFragment, android.R.id.content );

                    break;

                case CONTENT_RATING_PG :

                    GuidedStepFragment.add( fm, mContentRatingPgFragment, android.R.id.content );

                    break;

                case CONTENT_RATING_PG13 :

                    GuidedStepFragment.add( fm, mContentRatingPg13Fragment, android.R.id.content );

                    break;

                case CONTENT_RATING_R :

                    GuidedStepFragment.add( fm, mContentRatingRFragment, android.R.id.content );

                    break;

                case CONTENT_RATING_NC17 :

                    GuidedStepFragment.add( fm, mContentRatingNc17Fragment, android.R.id.content );

                    break;

                default :

                    break;

            }

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean showAdultContent = getShowAdultContent( getActivity() );
            String content = ( showAdultContent ? getResources().getString( R.string.pref_show_adult_tab_summary_on ) : getResources().getString( R.string.pref_show_adult_tab_summary_off ) );

            boolean enableParentalControls = getEnableParentalControls( getActivity() );
            String parentalControls = ( enableParentalControls ? getResources().getString( R.string.pref_enable_parental_controls_summary_on ) : getResources().getString( R.string.pref_enable_parental_controls_summary_off ) );

            String parentalControlLevel = getParentalControlLevel( getActivity() );

            boolean restrictContentType = getRestrictContentType( getActivity() );
            String contentType = ( restrictContentType ? getResources().getString( R.string.pref_restrict_content_types_summary_on ) : getResources().getString( R.string.pref_restrict_content_types_summary_off ) );

            addAction( getActivity(), actions, ADULT_SETTINGS,
                    getResources().getString( R.string.pref_show_adult_tab ),
                    content,
                    true, true );

            addAction( getActivity(), actions, PARENTAL_CONTROLS,
                    getResources().getString( R.string.pref_enable_parental_controls ),
                    parentalControls,
                    true, true );

            if( enableParentalControls ) {

                addAction( getActivity(), actions, PARENTAL_CONTROL_LEVEL,
                        getResources().getString( R.string.pref_parental_control_level ),
                        parentalControlLevel,
                        true, true );

            }

            addAction( getActivity(), actions, CONTENT_RATINGS,
                    getResources().getString( R.string.pref_restrict_content_types ),
                    contentType,
                    true, true );

            if( restrictContentType ) {

                boolean contentTypeNr = getContentTypeNR( getActivity() );
                String nr = ( contentTypeNr ? getResources().getString( R.string.tv_settings_yes ) : getResources().getString( R.string.tv_settings_no ) );

                addAction( getActivity(), actions, CONTENT_RATING_NR,
                        getResources().getString( R.string.pref_rating_nr ),
                        nr,
                        true, true );

                boolean contentTypeG = getContentTypeG( getActivity() );
                String g = ( contentTypeG ? getResources().getString( R.string.tv_settings_yes ) : getResources().getString( R.string.tv_settings_no ) );

                addAction( getActivity(), actions, CONTENT_RATING_G,
                        getResources().getString( R.string.pref_rating_g ),
                        g,
                        true, true );

                boolean contentTypePg = getContentTypePG( getActivity() );
                String pg = ( contentTypePg ? getResources().getString( R.string.tv_settings_yes ) : getResources().getString( R.string.tv_settings_no ) );

                addAction( getActivity(), actions, CONTENT_RATING_PG,
                        getResources().getString( R.string.pref_rating_pg ),
                        pg,
                        true, true );

                boolean contentTypePg13 = getContentTypePG13( getActivity() );
                String pg13 = ( contentTypePg13 ? getResources().getString( R.string.tv_settings_yes ) : getResources().getString( R.string.tv_settings_no ) );

                addAction( getActivity(), actions, CONTENT_RATING_PG13,
                        getResources().getString( R.string.pref_rating_pg13 ),
                        pg13,
                        true, true );

                boolean contentTypeR = getContentTypeR( getActivity() );
                String r = ( contentTypeR ? getResources().getString( R.string.tv_settings_yes ) : getResources().getString( R.string.tv_settings_no ) );

                addAction( getActivity(), actions, CONTENT_RATING_R,
                        getResources().getString( R.string.pref_rating_r ),
                        r,
                        true, true );

                boolean contentTypeNc17 = getContentTypeNC17( getActivity() );
                String nc17 = ( contentTypeNc17 ? getResources().getString( R.string.tv_settings_yes ) : getResources().getString( R.string.tv_settings_no ) );

                addAction( getActivity(), actions, CONTENT_RATING_NC17,
                        getResources().getString( R.string.pref_rating_nc17 ),
                        nc17,
                        true, true );

            }

            setActions( actions );

        }

    }

    public static class AdultContentFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_show_adult_tab );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.video_preferences );
            String description = "";
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_SHOW_ADULT_TAB, updated );

            mVideoSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
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

    public static class ParentalControlsFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_enable_parental_controls );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.video_preferences );
            String description = "";
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_ENABLE_PARENTAL_CONTROLS, updated );

            mVideoSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean enableParentalControls = getEnableParentalControls( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    enableParentalControls );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !enableParentalControls );

            setActions( actions );

        }

    }

    public static class ParentalControlLevelFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_parental_controls );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.video_preferences );
            String description = "";
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            String updated = action.getLabel1().toString();
            putStringToPreferences( getActivity(), SettingsKeys.KEY_PREF_PARENTAL_CONTROL_LEVEL, updated );

            mVideoSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            String parentalControlLevel = getParentalControlLevel( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    "1",
                    null,
                    "1".equals( parentalControlLevel ) );
            addCheckedAction( getActivity(), actions,
                    -1,
                    "2",
                    null,
                    "2".equals( parentalControlLevel ) );
            addCheckedAction( getActivity(), actions,
                    -1,
                    "3",
                    null,
                    "3".equals( parentalControlLevel ) );
            addCheckedAction( getActivity(), actions,
                    -1,
                    "4",
                    null,
                    "4".equals( parentalControlLevel ) );

            setActions( actions );

        }

    }

    public static class ContentRatingFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_restrict_content_types );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.video_preferences );
            String description = "";
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_RESTRICT_CONTENT_TYPES, updated );

            mVideoSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean enableContentRating = getRestrictContentType( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    enableContentRating );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !enableContentRating );

            setActions( actions );

        }

    }

    public static class ContentRatingNrFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_rating_nr );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.video_preferences );
            String description = getResources().getString( R.string.pref_rating_nr_summary );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_RATING_NR, updated );

            mVideoSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean showContentType = getContentTypeNR( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    showContentType );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !showContentType );

            setActions( actions );

        }

    }

    public static class ContentRatingGFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_rating_g );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.video_preferences );
            String description = getResources().getString( R.string.pref_rating_g_summary );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_RATING_G, updated );

            mVideoSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean showContentType = getContentTypeG( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    showContentType );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !showContentType );

            setActions( actions );

        }

    }

    public static class ContentRatingPgFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_rating_pg );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.video_preferences );
            String description = getResources().getString( R.string.pref_rating_pg_summary );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_RATING_PG, updated );

            mVideoSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean showContentType = getContentTypePG( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    showContentType );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !showContentType );

            setActions( actions );

        }

    }

    public static class ContentRatingPg13Fragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_rating_pg13 );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.video_preferences );
            String description = getResources().getString( R.string.pref_rating_pg13_summary );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_RATING_PG13, updated );

            mVideoSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean showContentType = getContentTypePG13( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    showContentType );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !showContentType );

            setActions( actions );

        }

    }

    public static class ContentRatingRFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_rating_r );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.video_preferences );
            String description = getResources().getString( R.string.pref_rating_r_summary );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_RATING_R, updated );

            mVideoSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean showContentType = getContentTypeR( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    showContentType );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !showContentType );

            setActions( actions );

        }

    }

    public static class ContentRatingNc17Fragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_rating_nc17 );
            String breadcrumb = getResources().getString( R.string.title_activity_settings ) + SEPARATOR + getResources().getString( R.string.video_preferences );
            String description = getResources().getString( R.string.pref_rating_nc17_summary );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_RATING_NC17, updated );

            mVideoSettingsFragment.updateActions( null );
            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean showContentType = getContentTypeNC17( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    showContentType );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !showContentType );

            setActions( actions );

        }

    }

    public static class AnalyticsFragment extends GuidedStepFragment {

        @NonNull
        @Override
        public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

            String title = getResources().getString( R.string.pref_enable_analytics_label );
            String breadcrumb = getResources().getString( R.string.pref_enable_analytics_title );
            String description = getResources().getString( R.string.tv_settings_analytics_title_description );
            Drawable icon = null;

            return new GuidanceStylist.Guidance( title, description, breadcrumb, icon );
        }

        @Override
        public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

            updateActions( actions );

        }

        @Override
        public void onGuidedActionClicked( GuidedAction action ) {
//            Log.d( TAG, "onGuidedActionClicked : action=" + action );

            boolean updated = ( action.getLabel1().equals( getResources().getString( R.string.tv_settings_yes ) ) );
            putBooleanToPreferences( getActivity(), SettingsKeys.KEY_PREF_ENABLE_ANALYTICS, updated );

            FirebaseAnalytics.getInstance( getActivity() ).setAnalyticsCollectionEnabled( updated );

            mSettingsFragment.updateActions( null );

            getFragmentManager().popBackStack();

        }

        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public void updateActions( List<GuidedAction> actions ) {

            if( null == actions ) {

                actions = new ArrayList<>();

            }

            boolean enableAnalytics = getEnableAnalytics( getActivity() );

            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_yes ),
                    null,
                    enableAnalytics );
            addCheckedAction( getActivity(), actions,
                    -1,
                    getResources().getString( R.string.tv_settings_no ),
                    null,
                    !enableAnalytics );

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

//    private static void addEditableAction( Context context, List<GuidedAction> actions, long id, String title, String editTitle, String desc ) {
//
//        actions.add( new GuidedAction.Builder( context )
//                .id( id )
//                .title( title )
//                .editTitle( editTitle )
//                .description( desc )
//                .editable( true )
//                .hasNext( false )
//                .build() );
//
//    }

    private static void addCheckedAction( Context context, List<GuidedAction> actions, int iconResId, String title, String desc, boolean checked ) {

        GuidedAction guidedAction = new GuidedAction.Builder( context )
                .title( title )
                .description( desc )
                .checkSetId( OPTION_CHECK_SET_ID )
                .iconResourceId( iconResId, context )
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

    private static boolean getFilterHlsOnly( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_FILTER_HLS_ONLY );
    }

    private static boolean getEnableRecordingGroupFilter( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_ENABLE_RECORDING_GROUP_FILTER );
    }

    private static String getRecordingGroupFilter( Context context ) {

        return getStringFromPreferences( context, SettingsKeys.KEY_PREF_RECORDING_GROUP_FILTER );
    }

    private static boolean getShowAdultContent( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_SHOW_ADULT_TAB );
    }

    private static boolean getEnableParentalControls( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_ENABLE_PARENTAL_CONTROLS );
    }

    private static String getParentalControlLevel( Context context ) {

        return getStringFromPreferences( context, SettingsKeys.KEY_PREF_PARENTAL_CONTROL_LEVEL );
    }

    private static boolean getRestrictContentType( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_RESTRICT_CONTENT_TYPES );
    }

    private static boolean getContentTypeNR( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_RATING_NR );
    }

    private static boolean getContentTypeG( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_RATING_G );
    }

    private static boolean getContentTypePG( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_RATING_PG );
    }

    private static boolean getContentTypePG13(Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_RATING_PG13 );
    }

    private static boolean getContentTypeR( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_RATING_R );
    }

    private static boolean getContentTypeNC17( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_RATING_NC17 );
    }

    private static boolean getEnableAnalytics( Context context ) {

        return getBooleanFromPreferences( context, SettingsKeys.KEY_PREF_ENABLE_ANALYTICS );
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
