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

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.internal.di.modules.SearchResultsModule;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.provider.MythtvSearchSuggestionProvider;
import org.mythtv.android.presentation.view.fragment.tv.TvSearchResultListFragment;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 2/27/16.
 */
public class SearchableActivity extends AbstractBaseTvActivity implements HasComponent<MediaComponent>, TvSearchResultListFragment.MediaItemListListener {

    private static final String TAG = SearchableActivity.class.getSimpleName();

    private static final String INSTANCE_STATE_PARAM_SEARCH_TEXT = "org.mythtv.android.STATE_PARAM_SEARCH_TEXT";

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, SearchableActivity.class );
    }

    private String searchText;
    private MediaComponent mediaComponent;

    private static final int REQUEST_SPEECH = 1;
    TvSearchResultListFragment mSearchableFragment;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_tv_search;
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
        Log.d( TAG, "onSaveInstanceState : enter" );

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            if( null != this.searchText && !"".equals( searchText ) ) {

                outState.putString( INSTANCE_STATE_PARAM_SEARCH_TEXT, this.searchText );

            }

        }

        super.onSaveInstanceState( outState );

        Log.d( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );
        Log.d( TAG, "onRestoreInstanceState : enter" );

        if( null != savedInstanceState ) {
            Log.d( TAG, "onRestoreInstanceState : savedInstanceState != null" );

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_SEARCH_TEXT ) ) {

                this.searchText = savedInstanceState.getString( INSTANCE_STATE_PARAM_SEARCH_TEXT );

            }

        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Intent intent ) {
        Log.d( TAG, "initializeActivity : enter" );

        if( null == intent  ) {
            Log.d( TAG, "initializeActivity : intent == null" );

            mSearchableFragment = TvSearchResultListFragment.newInstance( this.searchText );
            addFragment( R.id.fl_fragment, mSearchableFragment );

        } else {
            Log.d( TAG, "initializeActivity : intent != null" );

            searchText = intent.getStringExtra( SearchManager.QUERY );
            Log.d( TAG, "initializeActivity : searchText = " + searchText );

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions( this, MythtvSearchSuggestionProvider.AUTHORITY, MythtvSearchSuggestionProvider.MODE );
            suggestions.saveRecentQuery( searchText, null );

            mSearchableFragment = TvSearchResultListFragment.newInstance( this.searchText );
            addFragment( R.id.fl_fragment, mSearchableFragment );

        }

        SpeechRecognitionCallback mSpeechRecognitionCallback = () -> startActivityForResult( mSearchableFragment.getRecognizerIntent(), REQUEST_SPEECH );
        mSearchableFragment.setSpeechRecognitionCallback( mSpeechRecognitionCallback );

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.mediaComponent = DaggerMediaComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .searchResultsModule( new SearchResultsModule() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public MediaComponent getComponent() {

        return mediaComponent;
    }

    @Override
    public void onMediaItemClicked( MediaItemModel mediaItemModel ) {
        Log.d( TAG, "onMediaItemClicked : enter" );

        switch( mediaItemModel.getMedia() ) {

            case PROGRAM:
                Log.d( TAG, "onMediaItemClicked : recording clicked" );

                break;

            case VIDEO:
                Log.d( TAG, "onMediaItemClicked : video clicked" );

                break;

        }

        Log.d( TAG, "onMediaItemClicked : exit" );
    }

}
