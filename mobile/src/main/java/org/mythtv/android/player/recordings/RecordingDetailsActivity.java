package org.mythtv.android.player.recordings;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.player.AbstractBaseActionBarActivity;
import org.mythtv.android.R;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingDetailsActivity extends AbstractBaseActionBarActivity {

    private RecordingDetailsFragment mRecordingDetailsFragment;
    Program mProgram;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recording_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( RecordingDetailsFragment.PROGRAM_KEY ) ) {
            mProgram = (Program) getIntent().getSerializableExtra( RecordingDetailsFragment.PROGRAM_KEY );
        }

        mRecordingDetailsFragment = (RecordingDetailsFragment) getFragmentManager().findFragmentById( R.id.fragment_recording_details );
        if( null != mProgram ) {

            mRecordingDetailsFragment.setProgram( mProgram );

            //getSupportActionBar().setTitle( mProgram.getTitle() );
            //getSupportActionBar().setSubtitle( ( null != mProgram.getSubTitle() && !"".equals( mProgram.getSubTitle() ) ) ? mProgram.getSubTitle() : "" );
            getSupportActionBar().setTitle( ( null != mProgram.getSubTitle() && !"".equals( mProgram.getSubTitle() ) ) ? mProgram.getSubTitle() : "" );

        }

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState(outState);

        outState.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, mProgram );

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);

        if( savedInstanceState.containsKey( RecordingDetailsFragment.PROGRAM_KEY ) ) {
            mProgram = (Program) savedInstanceState.getSerializable( RecordingDetailsFragment.PROGRAM_KEY );
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
