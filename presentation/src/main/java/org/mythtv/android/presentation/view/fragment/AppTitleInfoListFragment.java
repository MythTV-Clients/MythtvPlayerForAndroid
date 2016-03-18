package org.mythtv.android.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.TitleInfoModel;
import org.mythtv.android.presentation.presenter.TitleInfoListPresenter;
import org.mythtv.android.presentation.view.TitleInfoListView;
import org.mythtv.android.presentation.view.adapter.TitleInfosAdapter;
import org.mythtv.android.presentation.view.adapter.TitleInfosLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dmfrey on 8/31/15.
 */
public class AppTitleInfoListFragment extends AppAbstractBaseFragment implements TitleInfoListView {

    private static final String TAG = AppTitleInfoListFragment.class.getSimpleName();

    /**
     * Interface for listening titleInfo list events.
     */
    public interface TitleInfoListListener {

        void onTitleInfoClicked( final TitleInfoModel titleInfoModel );

    }

    @Inject
    TitleInfoListPresenter titleInfoListPresenter;

    @Bind( R.id.rv_titleInfos )
    RecyclerView rv_titleInfos;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    private TitleInfosAdapter titleInfosAdapter;

    private TitleInfoListListener titleInfoListListener;

    public AppTitleInfoListFragment() { super(); }

    public static AppTitleInfoListFragment newInstance() {

        return new AppTitleInfoListFragment();
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.d( TAG, "onAttach : enter" );

        if( activity instanceof TitleInfoListListener ) {
            this.titleInfoListListener = (TitleInfoListListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_app_title_info_list, container, false );
        ButterKnife.bind( this, fragmentView );
        setupUI();

        Log.d( TAG, "onCreateView : exit" );
        return fragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        this.initialize();
        this.loadTitleInfoList();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.titleInfoListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.titleInfoListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.titleInfoListPresenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "onDestroyView : enter" );
        super.onDestroyView();

        ButterKnife.unbind( this );

        Log.d( TAG, "onDestroyView : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( DvrComponent.class ).inject( this );
        this.titleInfoListPresenter.setView( this );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        this.rv_titleInfos.setLayoutManager( new TitleInfosLayoutManager( getActivity() ) );

        this.titleInfosAdapter = new TitleInfosAdapter( getActivity(), new ArrayList<TitleInfoModel>() );
        this.titleInfosAdapter.setOnItemClickListener( onItemClickListener );
        this.rv_titleInfos.setAdapter( titleInfosAdapter );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        this.rl_progress.setVisibility( View.VISIBLE );
        this.getActivity().setProgressBarIndeterminateVisibility( true );

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

        this.rl_progress.setVisibility( View.GONE );
        this.getActivity().setProgressBarIndeterminateVisibility( false );

        Log.d( TAG, "hideLoading : exit" );
    }

    @Override
    public void showRetry() {
        Log.d( TAG, "showRetry : enter" );

        Log.d( TAG, "showRetry : exit" );
    }

    @Override
    public void hideRetry() {
        Log.d( TAG, "hideRetry : enter" );

        Log.d( TAG, "hideRetry : exit" );
    }

    @Override
    public void renderTitleInfoList( Collection<TitleInfoModel> titleInfoModelCollection ) {
        Log.d( TAG, "renderTitleInfoList : enter" );

        if( null != titleInfoModelCollection ) {
            Log.d( TAG, "renderTitleInfoList : titleInfoModelCollection is not null, titleInfoModelCollection=" + titleInfoModelCollection );

            this.titleInfosAdapter.setTitleInfosCollection( titleInfoModelCollection );

        }

        Log.d( TAG, "renderTitleInfoList : exit" );
    }

    @Override
    public void viewTitleInfo( TitleInfoModel titleInfoModel ) {
        Log.d( TAG, "viewTitleInfo : enter" );

        if( null != this.titleInfoListListener ) {

            this.titleInfoListListener.onTitleInfoClicked( titleInfoModel );

        }

        Log.d( TAG, "viewTitleInfo : exit" );
    }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message, getResources().getString( R.string.retry ), new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                AppTitleInfoListFragment.this.loadTitleInfoList();

            }

        });

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public Context getContext() {
        Log.d( TAG, "getContext : enter" );

        Log.d( TAG, "getContext : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all titleInfos.
     */
    private void loadTitleInfoList() {
        Log.d( TAG, "loadTitleInfoList : enter" );

        this.titleInfoListPresenter.initialize();

        Log.d( TAG, "loadTitleInfoList : exit" );
    }

    private TitleInfosAdapter.OnItemClickListener onItemClickListener = new TitleInfosAdapter.OnItemClickListener() {

                @Override
                public void onTitleInfoItemClicked( TitleInfoModel titleInfoModel ) {

                    if( null != AppTitleInfoListFragment.this.titleInfoListPresenter && null != titleInfoModel ) {

                        AppTitleInfoListFragment.this.titleInfoListPresenter.onTitleInfoClicked( titleInfoModel );

                    }

                }

    };

}
