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
import org.mythtv.android.presentation.model.ErrorModel;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.presenter.phone.MediaItemListPresenter;
import org.mythtv.android.presentation.view.MediaItemListView;
import org.mythtv.android.presentation.view.activity.phone.TroubleshootClickListener;
import org.mythtv.android.presentation.view.adapter.phone.LayoutManager;
import org.mythtv.android.presentation.view.adapter.phone.MediaItemsAdapter;
import org.mythtv.android.presentation.view.component.EmptyRecyclerView;
import org.mythtv.android.presentation.view.listeners.MediaItemListListener;
import org.mythtv.android.presentation.view.listeners.NotifyListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
public class MediaItemListFragment extends AbstractBaseFragment implements MediaItemListView {

    /* private */ static final String TAG = MediaItemListFragment.class.getSimpleName();

    /* private */ static final String MEDIA_KEY = "media";
    /* private */ static final String TITLE_REGEX_KEY = "title_regex";
    /* private */ static final String INETREF_KEY = "inetref";

    @Inject
    /* private */ MediaItemListPresenter presenter;

    @BindView( R.id.rv_items)
    /* private */ EmptyRecyclerView rv_items;

    @BindView( R.id.empty_list_view)
    /* private */ View emptyView;

    /* private */ Unbinder unbinder;

    /* private */ MediaItemsAdapter adapter;

    /* private */ TroubleshootClickListener troubleshootClickListener;
    /* private */ NotifyListener notifyListener;
    /* private */ MediaItemListListener mediaItemListListener;

    /* private */ Media media;
    /* private */ String titleRegEx;
    /* private */ String inetref;

    /* private */ final MediaItemsAdapter.OnItemClickListener onItemClickListener = ( mediaItemModel, sharedElement, sharedElementName ) -> {

        if( null != MediaItemListFragment.this.presenter && null != mediaItemModel ) {

            if( mediaItemModel.isValid() ) {
                Log.i( TAG, "onItemClicked : mediaItemModel=" + mediaItemModel.toString() );

                MediaItemListFragment.this.presenter.onMediaItemClicked( mediaItemModel, sharedElement, sharedElementName );

            } else {
                Log.w( TAG, "onItemClicked : data error - mediaItemModel=" + mediaItemModel.toString() );

                if( null == mediaItemModel.media() ) {

                    String message = getString(R.string.validation_no_media_type);
                    showToastMessage( message, null, null );

                } else {

                    String fields = "";
                    for( ErrorModel errorModel : mediaItemModel.validationErrors() ) {

                        if( !"".equals( fields ) ) {
                            fields += ", ";
                        }

                        fields += errorModel.field();

                    }

                    String message = getResources().getString( R.string.validation_corrupt_data, fields );
                    showToastMessage( message, null, null );

                }

            }

        }

    };

    public MediaItemListFragment() { super(); }

    public static MediaItemListFragment newInstance( final Media media, final String titleRegEx, final String inetref ) {

        Bundle args = new Bundle();
        args.putString( MEDIA_KEY, media.name() );
        args.putString( TITLE_REGEX_KEY, titleRegEx );
        args.putString( INETREF_KEY, inetref );

        MediaItemListFragment fragment = new MediaItemListFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        Log.d( TAG, "onAttach : enter" );

        Activity activity = getActivity();
        if( activity instanceof MediaItemListListener) {
            this.mediaItemListListener = (MediaItemListListener) activity;
        }
        if( activity instanceof NotifyListener) {
            this.notifyListener = (NotifyListener) activity;
        }
        if( activity instanceof TroubleshootClickListener) {
            this.troubleshootClickListener = (TroubleshootClickListener) activity;
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

    /* private */ void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( MediaComponent.class ).inject( this );
        this.presenter.setView( this );

        media = Media.valueOf( getArguments().getString( MEDIA_KEY ) );
        titleRegEx = getArguments().getString( TITLE_REGEX_KEY );
        inetref = getArguments().getString(INETREF_KEY);

        Log.d( TAG, "initialize : exit" );
    }

    /* private */ void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        this.rv_items.setLayoutManager( new LayoutManager( getActivity() ) );

        this.adapter = new MediaItemsAdapter( getActivity(), new ArrayList<>() );
        this.adapter.setOnItemClickListener( onItemClickListener );
        this.rv_items.setAdapter(adapter);
        this.rv_items.setEmptyView( emptyView );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        if( null != notifyListener ) {
            this.notifyListener.showLoading();
        }

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

        if( null != notifyListener ) {
            this.notifyListener.finishLoading();
        }

        Log.d( TAG, "hideLoading : exit" );
    }

    @Override
    public void showRetry() {
        Log.v( TAG, "showRetry : enter" );

        Log.v( TAG, "showRetry : exit" );
    }

    @Override
    public void hideRetry() {
        Log.v( TAG, "hideRetry : enter" );

        Log.v( TAG, "hideRetry : exit" );
    }

    public void reload() {

        initialize();
        loadItems();

    }

    @Override
    public void renderMediaItemList( Collection<MediaItemModel> modelCollection ) {
        Log.d( TAG, "renderMediaItemList : enter - media=" + media.name() + ", titleRegEx=" + titleRegEx + ", inetref=" + inetref );

        if( null == modelCollection ) {

            // this is intentionally left blank

        } else {

            if( null == inetref || "".equals( inetref ) ) {

                this.adapter.setMediaItemsCollection( modelCollection );

            } else {

                List<MediaItemModel> filtered = new ArrayList<>();
                for( MediaItemModel mediaItemModel : modelCollection ) {

                    if( mediaItemModel.inetref().equals( inetref ) ) {

                        filtered.add( mediaItemModel );

                    }

                }

                if( !filtered.isEmpty() ) {

                    this.adapter.setMediaItemsCollection( modelCollection );

                }

            }

        }

        Log.d( TAG, "renderMediaItemList : exit" );
    }

    @Override
    public void viewMediaItem( final MediaItemModel mediaItemModel, final View sharedElement, final String sharedElementName ) {
        Log.d( TAG, "viewMediaItem : enter" );

        if( null != this.mediaItemListListener ) {
            Log.d( TAG, "viewMediaItem : mediaItemModel=" + mediaItemModel.toString() );

            this.mediaItemListListener.onMediaItemClicked( mediaItemModel, sharedElement, sharedElementName );

        }

        Log.d( TAG, "viewProgram : exit" );
    }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

        if( null != notifyListener ) {
            this.notifyListener.hideLoading();
        }

        this.showToastMessage( message, getResources().getString( R.string.troubleshoot ), v -> troubleshootClickListener.onTroubleshootClicked() );

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
     * Loads media items.
     */
    /* private */ void loadItems() {
        Log.d( TAG, "loadItems : enter" );

        this.presenter.initialize( this.media, this.titleRegEx, false );

        Log.d( TAG, "loadItems : exit" );
    }

}
