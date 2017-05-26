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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.SeriesModel;
import org.mythtv.android.presentation.presenter.phone.SeriesListPresenter;
import org.mythtv.android.presentation.view.SeriesListView;
import org.mythtv.android.presentation.view.adapter.phone.LayoutManager;
import org.mythtv.android.presentation.view.adapter.phone.SeriesAdapter;
import org.mythtv.android.presentation.view.component.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/20/16.
 */
public class SeriesListFragment extends AbstractBaseFragment implements SeriesListView {

    private static final String TAG = SeriesListFragment.class.getSimpleName();

    private static final String MEDIA_KEY = "media";

    @Inject
    SeriesListPresenter presenter;

    @BindView( R.id.rv_items )
    EmptyRecyclerView rv_items;

    @BindView( R.id.empty_list_view)
    View emptyView;

    private Unbinder unbinder;

    private SeriesAdapter adapter;

    private SeriesListListener seriesListListener;

    private Media media;

    private final SeriesAdapter.OnItemClickListener onItemClickListener = seriesModel -> {

        if( null != SeriesListFragment.this.presenter && null != seriesModel ) {
            Log.i( TAG, "onItemClicked : seriesModel=" + seriesModel.toString() );

            SeriesListFragment.this.presenter.onSeriesClicked( seriesModel );

        }

    };

    /**
     * Interface for listening series list events.
     */
    public interface SeriesListListener {

        void onSeriesClicked( final SeriesModel seriesModel );

    }

    public SeriesListFragment() { super(); }

    public static SeriesListFragment newInstance( final Media media ) {

        Bundle args = new Bundle();
        args.putString( MEDIA_KEY, media.name() );

        SeriesListFragment fragment = new SeriesListFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        Log.d( TAG, "onAttach : enter" );

        Activity activity = getActivity();
        if( activity instanceof SeriesListListener) {
            this.seriesListListener = (SeriesListListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_empty_item_list, container, false );
        ButterKnife.bind( this, fragmentView );
        unbinder = ButterKnife.bind( this, fragmentView );

        Log.d( TAG, "onCreateView : exit" );
        return fragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        setupUI();

        this.initialize();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.presenter.resume();
        this.loadItems();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.presenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.presenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "onDestroyView : enter" );
        super.onDestroyView();

        unbinder.unbind();

        Log.d( TAG, "onDestroyView : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( MediaComponent.class ).inject( this );
        this.presenter.setView( this );

        this.media = Media.valueOf( getArguments().getString( MEDIA_KEY ) );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        LayoutManager layoutManager = new LayoutManager( getActivity() );
        this.rv_items.setLayoutManager( layoutManager );

        this.adapter = new SeriesAdapter( getActivity(), new ArrayList<>() );
        this.adapter.setOnItemClickListener( onItemClickListener );
        this.rv_items.setAdapter( adapter );
        this.rv_items.setEmptyView( emptyView );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

        Log.d( TAG, "hideLoading : exit" );
    }

    @Override
    public void showRetry() {
        Log.v( TAG, "showRetry : enter" );

        Log.v( TAG, "showRetry : enter" );
    }

    @Override
    public void hideRetry() {
        Log.v( TAG, "hideRetry : enter" );

        Log.v( TAG, "hideRetry : enter" );
    }

    public void reload() {

        initialize();
        loadItems();

    }

    @Override
    public void renderSeriesList( Collection<SeriesModel> seriesModelCollection ) {
        Log.d( TAG, "renderSeriesList : enter" );

        if( null != seriesModelCollection ) {

            this.adapter.setSeriesCollection( seriesModelCollection );

        }

        Log.d( TAG, "renderSeriesList : exit" );
    }

    @Override
    public void viewSeries( SeriesModel seriesModel ) {
        Log.d( TAG, "viewSeries : enter" );

        if( null != this.seriesListListener) {
            Log.d( TAG, "viewSeries : seriesModel=" + seriesModel.toString() );

            this.seriesListListener.onSeriesClicked( seriesModel );

        }

        Log.d( TAG, "viewSeries : exit" );
    }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

//        this.notifyListener.hideLoading();

        Log.e( TAG, "showError : error=" + message );
        this.showToastMessage( message, getResources().getString( R.string.retry ), v -> SeriesListFragment.this.loadItems() );

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public void showMessage( String message ) {
        Log.d( TAG, "showMessage : enter" );

        this.showToastMessage( message, null, null );

        Log.d( TAG, "showMessage : exit" );
    }

    @Override
    public Context context() {
        Log.d( TAG, "context : enter" );

        Log.d( TAG, "context : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads series.
     */
    private void loadItems() {
        Log.d( TAG, "loadItems : enter" );

        this.presenter.initialize( media );

        Log.d( TAG, "loadItems : exit" );
    }

}
