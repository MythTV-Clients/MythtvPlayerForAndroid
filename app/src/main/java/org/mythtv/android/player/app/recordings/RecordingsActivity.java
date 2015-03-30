package org.mythtv.android.player.app.recordings;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.ui.data.RecordingDataConsumer;
import org.mythtv.android.player.app.AbstractBaseActionBarActivity;
import org.mythtv.android.R;

import java.util.List;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingsActivity extends AbstractBaseActionBarActivity implements RecordingDataConsumer {

    public static final String TITLE_INFO = "title_info";

    private RecordingsFragment mRecordingsFragment;
    TitleInfo mTitleInfo;

//    ImageView mRecordingsBanner;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_recordings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        mRecordingsFragment = (RecordingsFragment) getFragmentManager().findFragmentById( R.id.fragment_recordings );
//        mRecordingsBanner = (ImageView) findViewById( R.id.recordings_banner );

        if( null != savedInstanceState && savedInstanceState.containsKey( TITLE_INFO ) ) {

            mTitleInfo = (TitleInfo) savedInstanceState.getSerializable( TITLE_INFO );
        }

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( TITLE_INFO ) ) {

            mTitleInfo = (TitleInfo) getIntent().getSerializableExtra( TITLE_INFO );
        }

        if( null != mTitleInfo ) {

            getSupportActionBar().setTitle( mTitleInfo.getTitle() );

//            String bannerUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + mTitleInfo.getInetref() + "&Type=Banner&Height=" + String.valueOf(getResources().getDimension(R.dimen.recordings_banner));
//            ImageUtils.updatePreviewImage( this, mRecordingsBanner, bannerUrl );

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
    public void onSetPrograms( List<Program> programs ) {

        mRecordingsFragment.setPrograms( ( null != mTitleInfo ? mTitleInfo.getTitle() : null ), ( null != mTitleInfo ? mTitleInfo.getInetref() : null ), programs );

    }

    @Override
    public void onHandleError( String message ) {

        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();

    }

    @Override
    protected void updateData() {

//        RecordingsDataFragment recordingsDataFragment = (RecordingsDataFragment) getFragmentManager().findFragmentByTag( RECORDINGS_DATA_FRAGMENT_TAG );
//        if( null == recordingsDataFragment ) {
//
//            Bundle args = new Bundle();
//            if( null != mTitleInfo ) {
//                args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitleInfo.getTitle() );
//            }
//
//            recordingsDataFragment = (RecordingsDataFragment) Fragment.instantiate( this, RecordingsDataFragment.class.getName(), args );
//            recordingsDataFragment.setRetainInstance( true );
//
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.add( recordingsDataFragment, RECORDINGS_DATA_FRAGMENT_TAG );
//            transaction.commit();
//
//        } else {
//
//            recordingsDataFragment.reset();
//
//        }

    }

}
