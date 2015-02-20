package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.ui.data.RecordingDataConsumer;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.player.AbstractBaseActionBarActivity;
import org.mythtv.android.R;

import java.util.List;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingsActivity extends AbstractBaseActionBarActivity implements RecordingDataConsumer {

    private static final String RECORDINGS_DATA_FRAGMENT_TAG = RecordingsDataFragment.class.getCanonicalName();
    public static final String TITLE_INFO = "title_info";

    private RecordingsFragment mRecordingsFragment;
    TitleInfo mTitleInfo;

//    ImageView mRecordingsBanner;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recordings;
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

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {

        outState.putSerializable( TITLE_INFO, mTitleInfo );

        super.onSaveInstanceState( outState );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );

        if( savedInstanceState.containsKey( TITLE_INFO ) ) {

            mTitleInfo = (TitleInfo) savedInstanceState.getSerializable( TITLE_INFO );

        }

    }

    @Override
    public void onSetPrograms( List<Program> programs ) {

        mRecordingsFragment.setPrograms( ( null != mTitleInfo ? mTitleInfo.getTitle() : null ), programs );

    }

    @Override
    public void onHandleError( String message ) {

        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();

    }

    @Override
    protected void updateData() {

        RecordingsDataFragment recordingsDataFragment = (RecordingsDataFragment) getFragmentManager().findFragmentByTag( RECORDINGS_DATA_FRAGMENT_TAG );
        if( null == recordingsDataFragment ) {

            Bundle args = new Bundle();
            if( null != mTitleInfo ) {
                args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitleInfo.getTitle() );
            }

            recordingsDataFragment = (RecordingsDataFragment) Fragment.instantiate( this, RecordingsDataFragment.class.getName(), args );
            recordingsDataFragment.setRetainInstance( true );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add( recordingsDataFragment, RECORDINGS_DATA_FRAGMENT_TAG );
            transaction.commit();

        } else {

            recordingsDataFragment.reset();

        }

    }

}
