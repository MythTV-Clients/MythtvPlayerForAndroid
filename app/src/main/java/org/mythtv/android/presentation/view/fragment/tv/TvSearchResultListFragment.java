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

package org.mythtv.android.presentation.view.fragment.tv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v17.leanback.app.SearchFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.presenter.phone.SearchResultListPresenter;
import org.mythtv.android.presentation.presenter.tv.CardPresenter;
import org.mythtv.android.presentation.utils.Utils;
import org.mythtv.android.presentation.view.MediaItemListView;
import org.mythtv.android.presentation.view.activity.tv.MediaItemDetailsActivity;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 2/27/16.
 */
public class TvSearchResultListFragment extends AbstractBaseSearchFragment implements SearchFragment.SearchResultProvider, MediaItemListView {

    private static final String TAG = TvSearchResultListFragment.class.getSimpleName();

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

    private ArrayObjectAdapter mRowsAdapter;

    private MediaItemListListener mediaItemListListener;

    public TvSearchResultListFragment() {
        super();
    }

    public static TvSearchResultListFragment newInstance(String searchText ) {

        TvSearchResultListFragment fragment = new TvSearchResultListFragment();

        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putString( ARGUMENT_KEY_SEARCH_TEXT, searchText );
        fragment.setArguments( argumentsBundle );

        return fragment;
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        Log.d( TAG, "onAttach : enter" );

        Activity activity = getActivity();
        if( activity instanceof MediaItemListListener ) {
            this.mediaItemListListener = (MediaItemListListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        setupUI();

        Log.d( TAG, "onCreate : exit" );
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

        mRowsAdapter = new ArrayObjectAdapter( new ListRowPresenter() );
        setSearchResultProvider( this );
        setOnItemViewClickedListener( new ItemViewClickedListener() );

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

            mRowsAdapter.clear();

            List<MediaItemModel> mediaItems = Utils.filter( PreferenceManager.getDefaultSharedPreferences( getActivity() ), mediaItemModelCollection );

            ArrayObjectAdapter programRowAdapter = new ArrayObjectAdapter( new CardPresenter() );
            ArrayObjectAdapter videoRowAdapter = new ArrayObjectAdapter( new CardPresenter() );

            for( MediaItemModel mediaItemModel : mediaItems ) {
                Log.d( TAG, "renderMediaItemList : mediaItemModel=" + mediaItemModel );

                if( Media.PROGRAM.equals( mediaItemModel.getMedia() ) ) {

                    programRowAdapter.add( mediaItemModel );

                } else {

                    videoRowAdapter.add( mediaItemModel );

                }

            }

            if( programRowAdapter.size() > 0 ) {
                Log.d( TAG, "renderMediaItemList : programRowAdapter has programs" );

                HeaderItem header = new HeaderItem( 0, getResources().getString( R.string.recording ) + " " + getResources().getString( R.string.search_results ) + " '" + this.searchText + "'" );
                mRowsAdapter.add( new ListRow( header, programRowAdapter ) );

            }

            if( videoRowAdapter.size() > 0 ) {
                Log.d( TAG, "renderMediaItemList : videoRowAdapter has videos" );

                HeaderItem header = new HeaderItem( 1, getResources().getString( R.string.video ) + " " + getResources().getString( R.string.search_results ) + " '" + this.searchText + "'" );
                mRowsAdapter.add( new ListRow( header, videoRowAdapter ) );

            }

        }

        Log.d( TAG, "renderMediaItemList : exit" );
    }

    @Override
    public void viewMediaItem( final MediaItemModel mediaItemModel, final View sharedElement, final String sharedElementName ) {
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


        Log.d( TAG, "showError : exit" );
    }

    @Override
    public void showMessage( String message ) {
        Log.d( TAG, "showMessage : enter" );

        this.showToastMessage( message );

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

    @Override
    public ObjectAdapter getResultsAdapter() {

        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange( String newQuery ) {
        Log.d( TAG, "onQueryTextChange : enter" );

        if( !TextUtils.isEmpty( newQuery ) && newQuery.length() > 3 ) {
            Log.d( TAG, "onQueryTextChange : newQuery is not empty and length is greater than 3" );

            this.searchText = newQuery;
            this.loadSearchResultList();

        }

        Log.d( TAG, "onQueryTextChange : exit" );
        return true;
    }

    @Override
    public boolean onQueryTextSubmit( String query ) {
        Log.d( TAG, "onQueryTextSubmit : enter" );

        if( !TextUtils.isEmpty( query ) && query.length() > 3 ) {
            Log.d( TAG, "onQueryTextSubmit : query is not empty and length is greater than 3" );

            this.searchText = query;
            this.loadSearchResultList();

        }

        Log.d( TAG, "onQueryTextSubmit : exit" );
        return true;
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {

            if( item instanceof MediaItemModel ) {

                MediaItemModel mediaItemModel = (MediaItemModel) item;
//                Log.d( TAG, "mediaItemModel: " + mediaItemModel.toString() );

                Intent intent = new Intent( getActivity(), MediaItemDetailsActivity.class );
                intent.putExtra( MediaItemDetailsActivity.MEDIA_ITEM, mediaItemModel );

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ( (ImageCardView) itemViewHolder.view ).getMainImageView(),
                        MediaItemDetailsActivity.SHARED_ELEMENT_NAME ).toBundle();

                getActivity().startActivity( intent, bundle );

            }

        }

    }

}
