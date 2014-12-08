package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.ui.adapters.ProgramAdapter;
import org.mythtv.android.library.ui.adapters.TitleInfoAdapter;
import org.mythtv.android.library.ui.data.RecordingDataConsumer;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.library.ui.data.TitleInfoDataConsumer;
import org.mythtv.android.library.ui.data.TitleInfosDataFragment;
import org.mythtv.android.player.R;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class TitleInfosFragment extends Fragment implements TitleInfoDataConsumer, TitleInfoAdapter.TitleInfoClickListener {

    private static final String TAG = TitleInfosFragment.class.getSimpleName();
    private static final String TITLE_INFOS_DATA_FRAGMENT_TAG = TitleInfosDataFragment.class.getCanonicalName();
    private static final String RECORDINGS_FRAGMENT_TAG = RecordingsDataFragment.class.getCanonicalName();

    RecyclerView mRecyclerView;
    TitleInfoAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.v( TAG, "onCreateView : enter" );

        TitleInfosDataFragment titleInfosDataFragment = (TitleInfosDataFragment) getChildFragmentManager().findFragmentByTag( TITLE_INFOS_DATA_FRAGMENT_TAG );
        if( null == titleInfosDataFragment ) {
            Log.d( TAG, "selectItem : creating new TitleInfosDataFragment");

            titleInfosDataFragment = (TitleInfosDataFragment) Fragment.instantiate( getActivity(), TitleInfosDataFragment.class.getName() );
            titleInfosDataFragment.setRetainInstance( true );
            titleInfosDataFragment.setConsumer( this );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add( titleInfosDataFragment, TITLE_INFOS_DATA_FRAGMENT_TAG );
            transaction.commit();

        }

        Log.v( TAG, "onCreateView : exit" );
        return inflater.inflate( R.layout.program_list, container, false );
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        Log.v( TAG, "onActivityCreated : enter" );

        mRecyclerView = (RecyclerView) getView().findViewById( R.id.list );

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        Log.v( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void setTitleInfos( List<TitleInfo> titleInfos ) {
        Log.v( TAG, "setTitleInfos : enter" );

        mAdapter = new TitleInfoAdapter( titleInfos, this );
        mRecyclerView.setAdapter( mAdapter );

        Log.v( TAG, "setPrograms : exit" );
    }

    @Override
    public void handleError( String message ) {
        Log.v(TAG, "handleError : enter");

        Toast.makeText( getActivity(), message, Toast.LENGTH_LONG ).show();

        Log.v( TAG, "handleError : exit" );
    }

    public void titleInfoClicked( TitleInfo titleInfo ) {
        Log.v( TAG, "titleInfoClicked : enter" );

        Bundle args = new Bundle();
        if( !getActivity().getResources().getString( R.string.all_recordings ).equals( titleInfo.getTitle() ) ) {
            args.putString(RecordingsDataFragment.TITLE_INFO_TITLE, titleInfo.getTitle());
        }

        RecordingsFragment recordingsFragment = (RecordingsFragment) getChildFragmentManager().findFragmentByTag( RECORDINGS_FRAGMENT_TAG );
        if( null == recordingsFragment ) {
            Log.d( TAG, "titleInfoSelected : creating new RecordingsFragment" );

            recordingsFragment = (RecordingsFragment) Fragment.instantiate( getActivity(), RecordingsFragment.class.getName(), args );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace( R.id.content_frame, recordingsFragment, RECORDINGS_FRAGMENT_TAG );
            transaction.commit();
        }

        Log.v( TAG, "titleInfoClicked : exit" );
    }

}
