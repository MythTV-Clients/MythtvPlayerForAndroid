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

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.SeriesModel;
import org.mythtv.android.presentation.presenter.phone.SeriesListPresenter;
import org.mythtv.android.presentation.view.SeriesListView;
import org.mythtv.android.presentation.view.adapter.phone.LayoutManager;
import org.mythtv.android.presentation.view.adapter.phone.SeriesAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public static final String MEDIA_KEY = "media";
    public static final String TITLE_REGEX_KEY = "title_regex";

    @Inject
    SeriesListPresenter seriesListPresenter;

    @BindView( R.id.rv_seriesList )
    RecyclerView rv_seriesList;

    private Unbinder unbinder;

    private SeriesAdapter seriesAdapter;

    private SeriesListListener seriesListListener;

    private Map<String, Object> parameters;

    private final SeriesAdapter.OnItemClickListener onItemClickListener = seriesModel -> {

        if( null != SeriesListFragment.this.seriesListPresenter && null != seriesModel ) {
            Log.i( TAG, "onItemClicked : seriesModel=" + seriesModel.toString() );

            SeriesListFragment.this.seriesListPresenter.onSeriesClicked( seriesModel );

        }

    };

    /**
     * Interface for listening series list events.
     */
    public interface SeriesListListener {

        void onSeriesClicked( final SeriesModel seriesModel );

    }

    public SeriesListFragment() { super(); }

    public static SeriesListFragment newInstance( Bundle args ) {

        SeriesListFragment fragment = new SeriesListFragment();
        fragment.setArguments( args );

        return fragment;
    }

    public static class Builder {

        private final Media media;
        private String titleRegEx;

        public Builder( Media media ) {
            this.media = media;
        }

        public Builder titleRegEx( String titleRegEx ) {
            this.titleRegEx = titleRegEx;
            return this;
        }

        public Map<String, Object> build() {

            Map<String, Object> parameters = new HashMap<>();
            parameters.put( MEDIA_KEY, media );

            if( null != titleRegEx ) {
                parameters.put( TITLE_REGEX_KEY, titleRegEx );
            }

            return parameters;
        }

        public Bundle toBundle() {

            Bundle args = new Bundle();
            args.putString( MEDIA_KEY, media.name() );

            if( null != titleRegEx ) {
                args.putString( TITLE_REGEX_KEY, titleRegEx );
            }

            return args;
        }

        public static Map<String, Object> fromBundle( Bundle args ) {

            Builder builder = new Builder( Media.valueOf( args.getString( MEDIA_KEY ) ) );

            if( args.containsKey( TITLE_REGEX_KEY ) ) {
                builder.titleRegEx( args.getString( TITLE_REGEX_KEY ) );
            }

            return builder.build();
        }
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.d( TAG, "onAttach : enter" );

//        Activity activity = getActivity();
//        if( activity instanceof NotifyListener) {
//            this.notifyListener = (NotifyListener) activity;
//        }
        if( activity instanceof SeriesListListener) {
            this.seriesListListener = (SeriesListListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_series_list, container, false );
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
        this.loadSeriesList();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.seriesListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.seriesListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.seriesListPresenter.destroy();

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
        this.seriesListPresenter.setView( this );

        parameters = Builder.fromBundle( getArguments() );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        LayoutManager layoutManager = new LayoutManager( getActivity() );
        this.rv_seriesList.setLayoutManager( layoutManager );

        this.seriesAdapter = new SeriesAdapter( getActivity(), new ArrayList<>() );
        this.seriesAdapter.setOnItemClickListener( onItemClickListener );
        this.rv_seriesList.setAdapter( seriesAdapter );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

//        this.notifyListener.showLoading();

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

//        this.notifyListener.finishLoading();

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
        loadSeriesList();

    }

    @Override
    public void renderSeriesList( Collection<SeriesModel> seriesModelCollection ) {
        Log.d( TAG, "renderSeriesList : enter" );

        if( null != seriesModelCollection ) {

            this.seriesAdapter.setSeriesCollection( seriesModelCollection );

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
        this.showToastMessage( message, getResources().getString( R.string.retry ), v -> SeriesListFragment.this.loadSeriesList() );

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
     * Loads series.
     */
    private void loadSeriesList() {
        Log.d( TAG, "loadSeriesList : enter" );

        this.seriesListPresenter.initialize( parameters );

        Log.d( TAG, "loadSeriesList : exit" );
    }

}
