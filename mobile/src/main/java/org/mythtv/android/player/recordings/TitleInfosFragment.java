package org.mythtv.android.player.recordings;

import android.app.Activity;
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

import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.ui.adapters.TitleInfoItemAdapter;
import org.mythtv.android.library.ui.data.TitleInfoDataConsumer;
import org.mythtv.android.library.ui.data.TitleInfosDataFragment;
import org.mythtv.android.R;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class TitleInfosFragment extends Fragment implements TitleInfoDataConsumer, TitleInfoItemAdapter.TitleInfoItemClickListener {

    private static final String TAG = TitleInfosFragment.class.getSimpleName();
    private static final String TITLE_INFOS_DATA_FRAGMENT_TAG = TitleInfosDataFragment.class.getCanonicalName();

    RecyclerView mRecyclerView;
    TitleInfoItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    OnTitleInfoClickListener mListener;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        TitleInfosDataFragment titleInfosDataFragment = (TitleInfosDataFragment) getChildFragmentManager().findFragmentByTag( TITLE_INFOS_DATA_FRAGMENT_TAG );
        if( null == titleInfosDataFragment ) {
            Log.d( TAG, "selectItem : creating new TitleInfosDataFragment");

            titleInfosDataFragment = (TitleInfosDataFragment) Fragment.instantiate( getActivity(), TitleInfosDataFragment.class.getName() );
            titleInfosDataFragment.setRetainInstance( true );
            titleInfosDataFragment.setConsumer( this );

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add( titleInfosDataFragment, TITLE_INFOS_DATA_FRAGMENT_TAG );
            transaction.commit();

        }

        Log.d( TAG, "onCreateView : exit" );
        return inflater.inflate( R.layout.program_list, container, false );
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        Log.d( TAG, "onActivityCreated : enter" );

        mRecyclerView = (RecyclerView) getView().findViewById( R.id.list );

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onAttach( Activity activity ) {
        Log.d( TAG, "onAttach : enter" );
        super.onAttach( activity );

        try {

            mListener = (OnTitleInfoClickListener) activity;

        } catch( ClassCastException e ) {
            throw new ClassCastException( activity.toString() + " must implement OnTitleInfoClickListener" );
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public void setTitleInfos( List<TitleInfo> titleInfos ) {
        Log.d( TAG, "setTitleInfos : enter" );

        mAdapter = new TitleInfoItemAdapter( titleInfos, this );
        mRecyclerView.setAdapter( mAdapter );

        Log.d( TAG, "onSetPrograms : exit" );
    }

    @Override
    public void onHandleError(String message) {
        Log.d( TAG, "onHandleError : enter" );

        Toast.makeText( getActivity(), message, Toast.LENGTH_LONG ).show();

        Log.d( TAG, "onHandleError : exit" );
    }

    public void titleInfoItemClicked( TitleInfo titleInfo ) {
        Log.d( TAG, "titleInfoItemClicked : enter" );

        mListener.onTitleInfoClicked( titleInfo );

//        Bundle args = new Bundle();
//        if( !getActivity().getResources().getString( R.string.all_recordings ).equals( titleInfo.getTitle() ) ) {
//            args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, titleInfo.getTitle() );
//        }
//
//        Intent recordings = new Intent( getActivity(), RecordingsActivity.class );
//        recordings.putExtras( args );
//        startActivity( recordings );

        Log.d( TAG, "titleInfoItemClicked : exit" );
    }

    public interface OnTitleInfoClickListener {

        void onTitleInfoClicked( TitleInfo titleInfo );

    }

}
