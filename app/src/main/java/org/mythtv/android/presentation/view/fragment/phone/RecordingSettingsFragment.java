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

package org.mythtv.android.presentation.view.fragment.phone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.presenter.phone.MediaItemListPresenter;
import org.mythtv.android.presentation.view.MediaItemListView;
import org.mythtv.android.presentation.view.activity.phone.TroubleshootClickListener;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import rx.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 4/7/15.
 */
public class RecordingSettingsFragment extends AbstractBasePreferenceFragment implements MediaItemListView, OnSharedPreferenceChangeListener {

    private static final String TAG = RecordingSettingsFragment.class.getSimpleName();

    public static final String MEDIA_KEY = "media";

    @Inject
    MediaItemListPresenter mediaItemListPresenter;

    private TroubleshootClickListener troubleshootClickListener;

    ListPreference defaultRecordingGroup;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        addPreferencesFromResource( R.xml.preferences_recordings );

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        defaultRecordingGroup = (ListPreference) getPreferenceManager().findPreference( SettingsKeys.KEY_PREF_RECORDING_GROUP_FILTER);

        setupUI();

        return super.onCreateView( inflater, container, savedInstanceState );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        Log.d( TAG, "onAttach : enter" );

        Activity activity = getActivity();
        if( activity instanceof TroubleshootClickListener) {
            this.troubleshootClickListener = (TroubleshootClickListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        this.initialize();

    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );

        this.loadMediaItemList();

        this.mediaItemListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );

        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

        super.onPause();

        this.mediaItemListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.mediaItemListPresenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( MediaComponent.class ).inject( this );
        this.mediaItemListPresenter.setView( this );

        boolean filterGroupEnabled = getPreferenceManager().getSharedPreferences().getBoolean( SettingsKeys.KEY_PREF_ENABLE_RECORDING_GROUP_FILTER, false );
        String filterGroup = getPreferenceManager().getSharedPreferences().getString( SettingsKeys.KEY_PREF_RECORDING_GROUP_FILTER, "Default" );
        if( filterGroupEnabled ) {

            updateDefaultRecordingGroupSummary( R.string.pref_recording_group_filter_summary_on, filterGroup );

        } else {

            updateDefaultRecordingGroupSummary( R.string.pref_recording_group_filter_summary_off, null );

        }

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.v( TAG, "showLoading : enter" );

        Log.v( TAG, "showLoading : enter" );
    }

    @Override
    public void hideLoading() {
        Log.v( TAG, "hideLoading : enter" );

        Log.v( TAG, "hideLoading : enter" );
    }

    @Override
    public void showRetry() {
        Log.v( TAG, "showRetry : enter" );

        Log.v( TAG, "showRetry : enter" );
    }

    @Override
    public void hideRetry() {
        Log.v( TAG, "hideRetry : enter" );

        Log.v( TAG, "hideRetry : enter" );
    }

    @Override
    public void renderMediaItemList( Collection<MediaItemModel> mediaItemModelCollection ) {
        Log.d( TAG, "renderMediaItemList : enter" );

        Observable.from( mediaItemModelCollection )
                .map( MediaItemModel::getRecordingGroup )
                .distinct()
                .toList()
                .subscribe( groups -> {
                    String[] groupsArray = groups.toArray( new String[ groups.size() ] );
                    defaultRecordingGroup.setEntries( groupsArray );
                    defaultRecordingGroup.setEntryValues( groupsArray );
                });

        Log.d( TAG, "renderMediaItemList : exit" );
    }

    @Override
    public void viewMediaItem( final MediaItemModel mediaItemModel, final View sharedElement, final String sharedElementName ) {
        Log.v( TAG, "viewMediaItem : enter" );

        Log.v( TAG, "viewMediaItem : enter" );
    }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message, getResources().getString( R.string.troubleshoot ), v -> troubleshootClickListener.onTroubleshootClicked() );

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public void showMessage( String message ) {
        Log.d( TAG, "showMessage : enter" );

        this.showToastMessage( message, null, null );

        Log.d( TAG, "showMessage : exit" );
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

    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {
        Log.d( TAG, "onSharedPreferenceChanged : enter" );

        boolean filterGroupEnabled = sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_ENABLE_RECORDING_GROUP_FILTER, false );
        String filterGroup = sharedPreferences.getString( SettingsKeys.KEY_PREF_RECORDING_GROUP_FILTER, "Default" );

        if( key.equals( SettingsKeys.KEY_PREF_ENABLE_RECORDING_GROUP_FILTER) ) {

            if( filterGroupEnabled ) {

                updateDefaultRecordingGroupSummary( R.string.pref_recording_group_filter_summary_on, filterGroup );

            } else {

                updateDefaultRecordingGroupSummary( R.string.pref_recording_group_filter_summary_off, null );

            }

        }

        if( key.equals( SettingsKeys.KEY_PREF_RECORDING_GROUP_FILTER) ) {

            updateDefaultRecordingGroupSummary( R.string.pref_recording_group_filter_summary_on, filterGroup );

        }

        Log.d( TAG, "onSharedPreferenceChanged : exit" );
    }

    private void updateDefaultRecordingGroupSummary( final int resourceId, final String filterGroup ) {

        if( null == filterGroup ) {

            defaultRecordingGroup.setSummary(getResources().getString( resourceId ) );

        } else {

            defaultRecordingGroup.setSummary(getResources().getString( resourceId, filterGroup ) );

        }

    }

}
