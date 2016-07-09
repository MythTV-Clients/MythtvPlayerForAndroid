/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.presentation.view.fragment.phone;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.mythtv.android.presentation.R;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.TitleInfoModel;
import org.mythtv.android.presentation.presenter.phone.TitleInfoListPresenter;
import org.mythtv.android.presentation.view.TitleInfoListView;
import org.mythtv.android.presentation.view.adapter.phone.TitleInfosAdapter;
import org.mythtv.android.presentation.view.adapter.phone.TitleInfosLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

/**
 * Created by dmfrey on 8/31/15.
 */
public class TitleInfoListFragment extends AbstractBaseFragment implements TitleInfoListView {

    private static final String TAG = TitleInfoListFragment.class.getSimpleName();

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

    @Bind( R.id.fast_scroller )
    VerticalRecyclerViewFastScroller fastScroller;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    private TitleInfosAdapter titleInfosAdapter;

    private TitleInfoListListener titleInfoListListener;

    public TitleInfoListFragment() { super(); }

    public static TitleInfoListFragment newInstance() {

        return new TitleInfoListFragment();
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

        View fragmentView = inflater.inflate( R.layout.fragment_phone_title_info_list, container, false );
        ButterKnife.bind( this, fragmentView );
        setupUI();

        fastScroller.setRecyclerView( rv_titleInfos );
        rv_titleInfos.addOnScrollListener( fastScroller.getOnScrollListener() );

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

                TitleInfoListFragment.this.loadTitleInfoList();

            }

        });

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public void showMessage( String message ) {
        Log.d( TAG, "showMessage : enter" );

        this.showToastMessage( message, null, null );

        Log.d( TAG, "showMessage : exit" );
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

                    if( null != TitleInfoListFragment.this.titleInfoListPresenter && null != titleInfoModel ) {

                        TitleInfoListFragment.this.titleInfoListPresenter.onTitleInfoClicked( titleInfoModel );

                    }

                }

    };

}
