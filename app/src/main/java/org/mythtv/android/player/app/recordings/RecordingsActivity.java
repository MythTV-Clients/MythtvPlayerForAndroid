package org.mythtv.android.player.app.recordings;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;
import org.mythtv.android.R;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingsActivity extends AbstractBaseAppCompatActivity {

    public static final String TITLE_INFO = "title_info";

    private RecordingsFragment mRecordingsFragment;
    TitleInfo mTitleInfo;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_recordings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

//        if( Build.VERSION.SDK_INT >= 21 ) {
//
//            Slide slide = new Slide();
//            slide.setDuration( 5000 );
//            getWindow().setEnterTransition( slide );
//
//            getWindow().setReturnTransition( TransitionInflater.from( this ).inflateTransition( R.transition.transition_title ) );
//
//        }

        super.onCreate( savedInstanceState );

        mRecordingsFragment = (RecordingsFragment) getSupportFragmentManager().findFragmentById( R.id.fragment_recordings );

        if( null != savedInstanceState && savedInstanceState.containsKey( TITLE_INFO ) ) {

            mTitleInfo = (TitleInfo) savedInstanceState.getSerializable( TITLE_INFO );
        }

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( TITLE_INFO ) ) {

            mTitleInfo = (TitleInfo) getIntent().getSerializableExtra( TITLE_INFO );
        }

        if( null != mTitleInfo ) {

            getSupportActionBar().setTitle( mTitleInfo.getTitle() );

        } else {

            getSupportActionBar().setTitle( getResources().getString( R.string.all_recordings ) );

        }

        mRecordingsFragment.setPrograms( ( null != mTitleInfo ? mTitleInfo.getTitle() : null ), ( null != mTitleInfo ? mTitleInfo.getInetref() : null ), null );

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {

        outState.putSerializable(TITLE_INFO, mTitleInfo);

        super.onSaveInstanceState( outState );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);

        if( savedInstanceState.containsKey( TITLE_INFO ) ) {

            mTitleInfo = (TitleInfo) savedInstanceState.getSerializable( TITLE_INFO );

        }

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );

        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ) .getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setIconifiedByDefault( false );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void updateData() {

    }

}
