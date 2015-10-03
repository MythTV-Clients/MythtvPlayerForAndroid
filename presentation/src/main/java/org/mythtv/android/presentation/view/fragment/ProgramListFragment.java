package org.mythtv.android.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.presenter.ProgramListPresenter;
import org.mythtv.android.presentation.view.ProgramListView;
import org.mythtv.android.presentation.view.adapter.ProgramsAdapter;
import org.mythtv.android.presentation.view.adapter.ProgramsLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dmfrey on 8/31/15.
 */
public class ProgramListFragment extends BaseFragment implements ProgramListView {

    private static final String TAG = ProgramListFragment.class.getSimpleName();

    private static final String ARGUMENT_KEY_DESCENDING = "org.mythtv.android.ARGUMENT_DESCENDING";
    private static final String ARGUMENT_KEY_START_INDEX = "org.mythtv.android.ARGUMENT_START_INDEX";
    private static final String ARGUMENT_KEY_COUNT = "org.mythtv.android.ARGUMENT_COUNT";
    private static final String ARGUMENT_KEY_TITLE_REG_EX = "org.mythtv.android.ARGUMENT_TITLE_REG_EX";
    private static final String ARGUMENT_KEY_REC_GROUP = "org.mythtv.android.ARGUMENT_REC_GROUP";
    private static final String ARGUMENT_KEY_STORAGE_GROUP = "org.mythtv.android.ARGUMENT_STORAGE_GROUP";

    private boolean descending;
    private int startIndex, count;
    private String titleRegEx, recGroup, storageGroup;

    /**
     * Interface for listening program list events.
     */
    public interface ProgramListListener {

        void onProgramClicked( final ProgramModel programModel );

    }

    @Inject
    ProgramListPresenter programListPresenter;

    @Bind( R.id.rv_programs )
    RecyclerView rv_programs;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    @Bind( R.id.rl_retry )
    RelativeLayout rl_retry;

    @Bind( R.id.bt_retry )
    Button bt_retry;

    private ProgramsAdapter programsAdapter;
    private ProgramsLayoutManager programsLayoutManager;

    private ProgramListListener programListListener;

    public ProgramListFragment() { super(); }

    public static ProgramListFragment newInstance( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {

        ProgramListFragment programListFragment = new ProgramListFragment();

        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putBoolean( ARGUMENT_KEY_DESCENDING, descending );
        argumentsBundle.putInt( ARGUMENT_KEY_START_INDEX, startIndex );
        argumentsBundle.putInt( ARGUMENT_KEY_COUNT, count );
        argumentsBundle.putString( ARGUMENT_KEY_TITLE_REG_EX, titleRegEx );
        argumentsBundle.putString( ARGUMENT_KEY_REC_GROUP, recGroup );
        argumentsBundle.putString( ARGUMENT_KEY_STORAGE_GROUP, storageGroup );
        programListFragment.setArguments( argumentsBundle );

        return programListFragment;
    }

    @Override public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.d(TAG, "onAttach : enter");

        if( activity instanceof ProgramListListener ) {
            this.programListListener = (ProgramListListener) activity;
        }

        Log.d(TAG, "onAttach : exit");
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d(TAG, "onCreateView : enter");

        View fragmentView = inflater.inflate( R.layout.fragment_program_list, container, false );
        ButterKnife.bind( this, fragmentView );
        setupUI();

        Log.d(TAG, "onCreateView : exit");
        return fragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d(TAG, "onActivityCreated : enter");
        super.onActivityCreated(savedInstanceState);

        this.initialize();
        this.loadProgramList();

        Log.d(TAG, "onActivityCreated : exit");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume : enter");
        super.onResume();

        this.programListPresenter.resume();

        Log.d(TAG, "onResume : exit");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause : enter");
        super.onPause();

        this.programListPresenter.pause();

        Log.d(TAG, "onPause : exit");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy : enter");
        super.onDestroy();

        this.programListPresenter.destroy();

        Log.d(TAG, "onDestroy : exit");
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView : enter");
        super.onDestroyView();

        ButterKnife.unbind(this);

        Log.d(TAG, "onDestroyView : exit");
    }

    private void initialize() {
        Log.d(TAG, "initialize : enter");

        this.getComponent( DvrComponent.class ).inject(this);
        this.programListPresenter.setView(this);

        Log.d(TAG, "initialize : exit");
    }

    private void setupUI() {
        Log.d(TAG, "setupUI : enter");

        this.programsLayoutManager = new ProgramsLayoutManager( getActivity() );
        this.rv_programs.setLayoutManager( programsLayoutManager );

        this.programsAdapter = new ProgramsAdapter( getActivity(), new ArrayList<ProgramModel>() );
        this.programsAdapter.setOnItemClickListener( onItemClickListener );
        this.rv_programs.setAdapter( programsAdapter );

        Log.d(TAG, "setupUI : exit");
    }

    @Override
    public void showLoading() {
        Log.d(TAG, "showLoading : enter");

        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);

        Log.d(TAG, "showLoading : exit");
    }

    @Override
    public void hideLoading() {
        Log.d(TAG, "hideLoading : enter");

        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);

        Log.d(TAG, "hideLoading : exit");
    }

    @Override
    public void showRetry() {
        Log.d(TAG, "showRetry : enter");

        this.rl_retry.setVisibility(View.VISIBLE);

        Log.d(TAG, "showRetry : exit");
    }

    @Override
    public void hideRetry() {
        Log.d(TAG, "hideRetry : enter");

        this.rl_retry.setVisibility(View.GONE);

        Log.d(TAG, "hideRetry : exit");
    }

    @Override
    public void renderProgramList( Collection<ProgramModel> programModelCollection ) {
        Log.d(TAG, "renderProgramList : enter");

        if( null != programModelCollection ) {

            this.programsAdapter.setProgramsCollection( programModelCollection );

        }

        Log.d(TAG, "renderProgramList : exit");
    }

    @Override
    public void viewProgram( ProgramModel programModel ) {
        Log.d( TAG, "viewProgram : enter" );

        if( null != this.programListListener ) {
            Log.d( TAG, "viewProgram : programModel=" + programModel.toString() );

            this.programListListener.onProgramClicked( programModel );

        }

        Log.d(TAG, "viewProgram : exit");
    }

    @Override
    public void showError( String message ) {
        Log.d(TAG, "showError : enter");

        this.showToastMessage(message);

        Log.d(TAG, "showError : exit");
    }

    @Override
    public Context getContext() {
        Log.d(TAG, "getContext : enter");

        Log.d(TAG, "getContext : exit");
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all programs.
     */
    private void loadProgramList() {
        Log.d(TAG, "loadProgramList : enter");

        this.programListPresenter.initialize(descending, startIndex, count, titleRegEx, recGroup, storageGroup);

        Log.d(TAG, "loadProgramList : exit");
    }

    @OnClick( R.id.bt_retry )
    void onButtonRetryClick() {
        Log.d( TAG, "onButtonRetryClick : enter" );

        ProgramListFragment.this.loadProgramList();

        Log.d( TAG, "onButtonRetryClick : exit" );
    }

    private ProgramsAdapter.OnItemClickListener onItemClickListener = new ProgramsAdapter.OnItemClickListener() {

        @Override
        public void onProgramItemClicked( ProgramModel programModel ) {

            if( null != ProgramListFragment.this.programListPresenter && null != programModel ) {
                Log.i( TAG, "onProgramItemClicked : programModel=" + programModel.toString() );

                ProgramListFragment.this.programListPresenter.onProgramClicked( programModel );

            }

        }

    };

}
