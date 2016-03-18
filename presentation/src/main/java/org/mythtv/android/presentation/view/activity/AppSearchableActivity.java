package org.mythtv.android.presentation.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.MenuItem;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerSearchComponent;
import org.mythtv.android.presentation.internal.di.components.SearchComponent;
import org.mythtv.android.presentation.internal.di.modules.SearchResultsModule;
import org.mythtv.android.presentation.model.SearchResultModel;
import org.mythtv.android.presentation.provider.MythtvSearchSuggestionProvider;
import org.mythtv.android.presentation.view.fragment.AppSearchResultListFragment;

/**
 * Created by dmfrey on 10/14/15.
 */
public class AppSearchableActivity extends AppAbstractBaseActivity implements HasComponent<SearchComponent>, AppSearchResultListFragment.SearchResultListListener {

    private static final String TAG = AppSearchableActivity.class.getSimpleName();

    private static final String INSTANCE_STATE_PARAM_SEARCH_TEXT = "org.mythtv.android.STATE_PARAM_SEARCH_TEXT";

    private String searchText;
    private SearchComponent searchComponent;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_app_search_result_list;
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

            addFragment( R.id.fl_fragment, AppSearchResultListFragment.newInstance( this.searchText ) );

        } else {
            Log.d( TAG, "initializeActivity : intent != null" );

            searchText = intent.getStringExtra( SearchManager.QUERY );

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions( this, MythtvSearchSuggestionProvider.AUTHORITY, MythtvSearchSuggestionProvider.MODE );
            suggestions.saveRecentQuery( searchText, null );

            addFragment( R.id.fl_fragment, AppSearchResultListFragment.newInstance( this.searchText ) );

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

                navigator.navigateToProgram( this, searchResultModel.getChanId(), searchResultModel.getStartTime(), searchResultModel.getStorageGroup(), searchResultModel.getFilename(), searchResultModel.getHostname() );

                break;

            case VIDEO:

                navigator.navigateToVideo( this, searchResultModel.getVideoId(), searchResultModel.getStorageGroup(), searchResultModel.getFilename(), searchResultModel.getHostname() );

                break;

        }

    }

}
