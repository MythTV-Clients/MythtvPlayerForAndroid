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

package org.mythtv.android.app.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.MenuItem;

import org.mythtv.android.app.R;
import org.mythtv.android.app.view.fragment.SearchResultListFragment;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.app.internal.di.components.DaggerSearchComponent;
import org.mythtv.android.app.internal.di.components.SearchComponent;
import org.mythtv.android.app.internal.di.modules.SearchResultsModule;
import org.mythtv.android.presentation.model.SearchResultModel;
import org.mythtv.android.app.provider.MythtvSearchSuggestionProvider;

/**
 * Created by dmfrey on 10/14/15.
 */
public class SearchableActivity extends AbstractBaseActivity implements HasComponent<SearchComponent>, SearchResultListFragment.SearchResultListListener {

    private static final String TAG = SearchableActivity.class.getSimpleName();

    private static final String INSTANCE_STATE_PARAM_SEARCH_TEXT = "org.mythtv.android.STATE_PARAM_SEARCH_TEXT";

    private String searchText;
    private SearchComponent searchComponent;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_search_result_list;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        this.initializeActivity( getIntent() );
        this.initializeInjector();

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d(TAG, "onSaveInstanceState : enter");

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            if( null != this.searchText && !"".equals( searchText ) ) {

                outState.putString( INSTANCE_STATE_PARAM_SEARCH_TEXT, this.searchText );

            }

        }

        super.onSaveInstanceState(outState);

        Log.d( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState : enter");

        if( null != savedInstanceState ) {
            Log.d( TAG, "onRestoreInstanceState : savedInstanceState != null" );

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_SEARCH_TEXT ) ) {

                this.searchText = savedInstanceState.getString( INSTANCE_STATE_PARAM_SEARCH_TEXT );

            }

        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case android.R.id.home:

                finish();

                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Intent intent ) {
        Log.d( TAG, "initializeActivity : enter" );

        if( null == intent  ) {
            Log.d( TAG, "initializeActivity : intent == null" );

            addFragment( R.id.fl_fragment, SearchResultListFragment.newInstance( this.searchText ) );

        } else {
            Log.d( TAG, "initializeActivity : intent != null" );

            searchText = intent.getStringExtra( SearchManager.QUERY );

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions( this, MythtvSearchSuggestionProvider.AUTHORITY, MythtvSearchSuggestionProvider.MODE );
            suggestions.saveRecentQuery( searchText, null );

            addFragment( R.id.fl_fragment, SearchResultListFragment.newInstance( this.searchText ) );

        }

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.searchComponent = DaggerSearchComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .searchResultsModule( new SearchResultsModule( searchText ) )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public SearchComponent getComponent() {

        return searchComponent;
    }

    @Override
    public void onSearchResultClicked( SearchResultModel searchResultModel ) {

        switch( searchResultModel.getType() ) {

            case RECORDING:

                navigator.navigateToProgram( this, searchResultModel.getChanId(), searchResultModel.getStartTime() );

                break;

            case VIDEO:

                navigator.navigateToVideo( this, searchResultModel.getVideoId(), searchResultModel.getStorageGroup(), searchResultModel.getFilename(), searchResultModel.getHostname() );

                break;

        }

    }

}
