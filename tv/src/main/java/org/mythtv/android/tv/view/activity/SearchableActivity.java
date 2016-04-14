package org.mythtv.android.tv.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.tv.internal.di.components.DaggerSearchComponent;
import org.mythtv.android.tv.internal.di.components.SearchComponent;
import org.mythtv.android.tv.internal.di.modules.SearchResultsModule;
import org.mythtv.android.presentation.model.SearchResultModel;
import org.mythtv.android.tv.provider.MythtvSearchSuggestionProvider;
import org.mythtv.android.tv.view.fragment.SearchResultListFragment;

/**
 * Created by dmfrey on 2/27/16.
 */
public class SearchableActivity extends AbstractBaseActivity implements HasComponent<SearchComponent>, SearchResultListFragment.SearchResultListListener {

    private static final String TAG = SearchableActivity.class.getSimpleName();

    private static final String INSTANCE_STATE_PARAM_SEARCH_TEXT = "org.mythtv.android.STATE_PARAM_SEARCH_TEXT";

    private String searchText;
    private SearchComponent searchComponent;

    private static final int REQUEST_SPEECH = 1;
    SearchResultListFragment mSearchableFragment;

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
        Log.d(TAG, "onSaveInstanceState : enter");

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

        mSearchableFragment = SearchResultListFragment.newInstance( this.searchText );
        if( null == intent  ) {
            Log.d( TAG, "initializeActivity : intent == null" );

            addFragment( R.id.fl_fragment, mSearchableFragment );

        } else {
            Log.d( TAG, "initializeActivity : intent != null" );

            searchText = intent.getStringExtra( SearchManager.QUERY );
            Log.d( TAG, "initializeActivity : searchText = " + searchText );

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions( this, MythtvSearchSuggestionProvider.AUTHORITY, MythtvSearchSuggestionProvider.MODE );
            suggestions.saveRecentQuery( searchText, null );

            addFragment( R.id.fl_fragment, mSearchableFragment );

        }

        SpeechRecognitionCallback mSpeechRecognitionCallback = new SpeechRecognitionCallback() {

            @Override
            public void recognizeSpeech() {

                startActivityForResult( mSearchableFragment.getRecognizerIntent(), REQUEST_SPEECH );

            }

        };
        mSearchableFragment.setSpeechRecognitionCallback( mSpeechRecognitionCallback );

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
        Log.d( TAG, "onSearchResultClicked : enter" );

        switch( searchResultModel.getType() ) {

            case RECORDING:
                Log.d( TAG, "onSearchResultClicked : recording clicked" );

                break;

            case VIDEO:
                Log.d( TAG, "onSearchResultClicked : video clicked" );

                break;

        }

        Log.d( TAG, "onSearchResultClicked : exit" );
    }

}
