package org.mythtv.android.player.app.recordings;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.player.app.AbstractBaseActionBarActivity;
import org.mythtv.android.R;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingDetailsActivity extends AbstractBaseActionBarActivity {

    private static final String TAG = RecordingDetailsActivity.class.getSimpleName();

    private RecordingDetailsFragment mRecordingDetailsFragment;
    Program mProgram;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_recording_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        if( null != savedInstanceState ) {
            Log.v( TAG, "onCreate : program loaded from savedInstanceState" );

            mProgram = (Program) savedInstanceState.getSerializable( RecordingDetailsFragment.PROGRAM_KEY );

        } else {

            if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( RecordingDetailsFragment.PROGRAM_KEY ) ) {
                Log.v( TAG, "onCreate : program loaded from intent" );

                mProgram = (Program) getIntent().getSerializableExtra( RecordingDetailsFragment.PROGRAM_KEY );

            }

        }

        mRecordingDetailsFragment = (RecordingDetailsFragment) getSupportFragmentManager().findFragmentById( R.id.fragment_recording_details );

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    protected void onResume() {
        Log.v( TAG, "onResume : enter" );
        super.onResume();

        if( null != mProgram ) {
            Log.v( TAG, "onResume : sending program to fragment" );

            mRecordingDetailsFragment.setProgram( mProgram );

            //getSupportActionBar().setTitle( mProgram.getTitle() );
            //getSupportActionBar().setSubtitle( ( null != mProgram.getSubTitle() && !"".equals( mProgram.getSubTitle() ) ) ? mProgram.getSubTitle() : "" );
            getSupportActionBar().setTitle( ( null != mProgram.getSubTitle() && !"".equals( mProgram.getSubTitle() ) ) ? mProgram.getSubTitle() : mProgram.getTitle() );

        }

        Log.v( TAG, "onResume : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.v( TAG, "onSaveInstanceState : enter" );
        super.onSaveInstanceState( outState );

        outState.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, mProgram );

        Log.v( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        Log.v( TAG, "onRestoreInstanceState : enter" );
        super.onRestoreInstanceState( savedInstanceState );

        if( savedInstanceState.containsKey( RecordingDetailsFragment.PROGRAM_KEY ) ) {
            Log.v( TAG, "onRestoreInstanceState : program loaded from savedInstanceState" );
            mProgram = (Program) savedInstanceState.getSerializable( RecordingDetailsFragment.PROGRAM_KEY );
        }

        Log.v( TAG, "onRestoreInstanceState : exit" );
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
