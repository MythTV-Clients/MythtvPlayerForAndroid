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

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.presenter.phone.SearchResultListPresenter;
import org.mythtv.android.presentation.view.MediaItemListView;
import org.mythtv.android.presentation.view.adapter.phone.LayoutManager;
import org.mythtv.android.presentation.view.adapter.phone.MediaItemsAdapter;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dmfrey on 10/12/15.
 */
public class MediaItemSearchResultListFragment extends AbstractBaseFragment implements MediaItemListView {

    private static final String TAG = MediaItemSearchResultListFragment.class.getSimpleName();

    private static final String ARGUMENT_KEY_SEARCH_TEXT = "org.mythtv.android.ARGUMENT_SEARCH_TEXT";

    private String searchText;

    /**
     * Interface for listening media item list events.
     */
    public interface MediaItemListListener {

        void onMediaItemClicked( final MediaItemModel mediaItemModel );

    }

    @Inject
    SearchResultListPresenter searchResultListPresenter;

    @BindView( R.id.rv_mediaItems )
    RecyclerView rv_mediaItems;

    @BindView( R.id.rl_progress )
    RelativeLayout rl_progress;

    private Unbinder unbinder;

    private MediaItemsAdapter mediaItemsAdapter;

    private MediaItemListListener mediaItemListListener;

    public MediaItemSearchResultListFragment() {
        super();
    }

    public static MediaItemSearchResultListFragment newInstance(String searchText ) {

        MediaItemSearchResultListFragment fragment = new MediaItemSearchResultListFragment();

        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putString( ARGUMENT_KEY_SEARCH_TEXT, searchText );
        fragment.setArguments( argumentsBundle );

        return fragment;
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.d( TAG, "onAttach : enter" );

        if( activity instanceof MediaItemListListener ) {
            this.mediaItemListListener = (MediaItemListListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_media_item_list, container, false );
        ButterKnife.bind( this, fragmentView );
        unbinder = ButterKnife.bind( this, fragmentView );

        setupUI();

        Log.d( TAG, "onCreateView : exit" );
        return fragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        this.initialize();
        this.loadSearchResultList();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.searchResultListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.searchResultListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.searchResultListPresenter.destroy();

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

        Log.d( TAG, "initialize : get searchText" );
        this.searchText = getArguments().getString( ARGUMENT_KEY_SEARCH_TEXT );

        Log.d( TAG, "initialize : get component" );
        this.getComponent( MediaComponent.class ).inject( this );

        Log.d( TAG, "initialize : set view" );
        this.searchResultListPresenter.setView( this );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        this.rv_mediaItems.setLayoutManager( new LayoutManager( getActivity() ) );

        this.mediaItemsAdapter = new MediaItemsAdapter( getActivity(), new ArrayList<>() );
        this.mediaItemsAdapter.setOnItemClickListener( onItemClickListener );
        this.rv_mediaItems.setAdapter( mediaItemsAdapter );

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
    public void renderMediaItemList( Collection<MediaItemModel> mediaItemModelCollection ) {
        Log.d( TAG, "renderMediaItemList : enter" );

        if( null != mediaItemModelCollection ) {
            Log.d( TAG, "renderMediaItemList : mediaItemModelCollection is not null" );

            this.mediaItemsAdapter.setMediaItemsCollection( mediaItemModelCollection );

        }

        Log.d( TAG, "renderMediaItemList : exit" );
    }

    @Override
    public void viewMediaItem( MediaItemModel mediaItemModel ) {
        Log.d( TAG, "viewMediaItem : enter" );

        if( null != this.mediaItemListListener ) {
            Log.d( TAG, "viewMediaItem : mediaItemModel=" + mediaItemModel.toString() );

            this.mediaItemListListener.onMediaItemClicked( mediaItemModel );

        }

        Log.d( TAG, "viewMediaItem : exit" );
    }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message, getResources().getString( R.string.retry ), v -> MediaItemSearchResultListFragment.this.loadSearchResultList());

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
     * Loads all search results.
     */
    private void loadSearchResultList() {
        Log.d( TAG, "loadSearchResultList : enter" );

        Log.d( TAG, "loadSearchResultList : searchText=" + searchText );
        this.searchResultListPresenter.initialize( this.searchText );

        Log.d( TAG, "loadSearchResultList : exit" );
    }

    private MediaItemsAdapter.OnItemClickListener onItemClickListener = mediaItemModel -> {

        if( null != MediaItemSearchResultListFragment.this.mediaItemListListener && null != mediaItemModel ) {
            Log.d( TAG, "onProgramItemClicked : mediaItemModel=" + mediaItemModel.toString() );

            MediaItemSearchResultListFragment.this.mediaItemListListener.onMediaItemClicked( mediaItemModel );

        }

    };

}
