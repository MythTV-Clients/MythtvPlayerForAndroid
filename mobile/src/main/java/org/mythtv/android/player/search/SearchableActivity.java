package org.mythtv.android.player.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import org.mythtv.android.R;
import org.mythtv.android.player.AbstractBaseActionBarActivity;

/**
 * Created by dmfrey on 3/14/15.
 */
public class SearchableActivity extends AbstractBaseActionBarActivity {

    private static final String TAG = SearchableActivity.class.getSimpleName();

    private SearchableRecordingsFragment mSearchableRecordingsFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        mSearchableRecordingsFragment = (SearchableRecordingsFragment) getFragmentManager().findFragmentById( R.id.fragment_search );

        getSupportActionBar().setTitle( getResources().getString( R.string.search_results ) );

        Intent intent = getIntent();
        if( Intent.ACTION_SEARCH.equals( intent.getAction() ) ) {
            Log.v( TAG, "onCreate : sending query to fragment" );

            String query = intent.getStringExtra( SearchManager.QUERY );
            mSearchableRecordingsFragment.setQuery( query );

        }

        Log.v( TAG, "onCreate : exit" );
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

    @Override
    protected void updateData() {

    }

}
