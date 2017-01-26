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
import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.ErrorModel;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.presenter.phone.MediaItemListPresenter;
import org.mythtv.android.presentation.view.MediaItemListView;
import org.mythtv.android.presentation.view.activity.phone.TroubleshootClickListener;
import org.mythtv.android.presentation.view.adapter.phone.LayoutManager;
import org.mythtv.android.presentation.view.adapter.phone.MediaItemsAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
public class MediaItemListFragment extends AbstractBaseFragment implements MediaItemListView {

    private static final String TAG = MediaItemListFragment.class.getSimpleName();

    public static final String MEDIA_KEY = "media";
    public static final String DESCENDING_KEY = "descending";
    public static final String START_INDEX_KEY = "start_index";
    public static final String COUNT_KEY = "count";
    public static final String TITLE_REGEX_KEY = "title_regex";
    public static final String REC_GROUP_KEY = "rec_group";
    public static final String STORAGE_GROUP_KEY = "storage_group";
    public static final String INETREF_KEY = "inetref";
    public static final String FOLDER_KEY = "folder";
    public static final String SORT_KEY = "sort";

    /**
     * Interface for listening media item list events.
     */
    public interface MediaItemListListener {

        void onMediaItemClicked( final MediaItemModel mediaItemModel );

    }

    @Inject
    MediaItemListPresenter mediaItemListPresenter;

    @BindView( R.id.rv_mediaItems )
    RecyclerView rv_mediaItems;

    @BindView( R.id.rl_progress )
    RelativeLayout rl_progress;

    private Unbinder unbinder;

    private MediaItemsAdapter mediaItemsAdapter;

    private TroubleshootClickListener troubleshootClickListener;
    private MediaItemListListener mediaItemListListener;

    private Map<String, Object> parameters;

    public MediaItemListFragment() { super(); }

    public static MediaItemListFragment newInstance( Bundle args ) {

        MediaItemListFragment fragment = new MediaItemListFragment();
        fragment.setArguments( args );

        return fragment;
    }

    public static class Builder {

        private Media media;
        private Boolean descending;
        private Integer startIndex;
        private Integer count;
        private String titleRegEx;
        private String recGroup;
        private String storageGroup;
        private String inetref;
        private String folder;
        private String sort;

        public Builder( Media media ) {
            this.media = media;
        }

        public Builder descending( Boolean descending ) {
            this.descending = descending;
            return this;
        }

        public Builder startIndex( Integer startIndex ) {
            this.startIndex = startIndex;
            return this;
        }

        public Builder count( Integer count ) {
            this.count = count;
            return this;
        }

        public Builder titleRegEx( String titleRegEx ) {
            this.titleRegEx = titleRegEx;
            return this;
        }

        public Builder recGroup( String recGroup ) {
            this.recGroup = recGroup;
            return this;
        }

        public Builder storageGroup( String storageGroup ) {
            this.storageGroup = storageGroup;
            return this;
        }

        public Builder inetref( String inetref ) {
            this.inetref = inetref;
            return this;
        }

        public Builder folder( String folder ) {
            this.folder = folder;
            return this;
        }

        public Builder sort( String sort ) {
            this.sort = sort;
            return this;
        }

        public Map<String, Object> build() {

            Map<String, Object> parameters = new HashMap<>();
            parameters.put( MEDIA_KEY, media );

            if( null != descending ) {
                parameters.put( DESCENDING_KEY, descending );
            }

            if( null != startIndex ) {
                parameters.put( START_INDEX_KEY, startIndex );
            }

            if( null != count ) {
                parameters.put( COUNT_KEY, count );
            }

            if( null != titleRegEx ) {
                parameters.put( TITLE_REGEX_KEY, titleRegEx );
            }

            if( null != recGroup ) {
                parameters.put( REC_GROUP_KEY, recGroup );
            }

            if( null != storageGroup ) {
                parameters.put( STORAGE_GROUP_KEY, storageGroup );
            }

            if( null != inetref ) {
                parameters.put( INETREF_KEY, inetref );
            }

            if( null != folder ) {
                parameters.put( FOLDER_KEY, folder );
            }

            if( null != sort ) {
                parameters.put( SORT_KEY, sort );
            }

            return parameters;
        }

        public Bundle toBundle() {

            Bundle args = new Bundle();
            args.putString( MEDIA_KEY, media.name() );

            if( null != descending ) {
                args.putBoolean( DESCENDING_KEY, descending );
            }

            if( null != startIndex ) {
                args.putInt( START_INDEX_KEY, startIndex );
            }

            if( null != count ) {
                args.putInt( COUNT_KEY, count );
            }

            if( null != titleRegEx ) {
                args.putString( TITLE_REGEX_KEY, titleRegEx );
            }

            if( null != recGroup ) {
                args.putString( REC_GROUP_KEY, recGroup );
            }

            if( null != storageGroup ) {
                args.putString( STORAGE_GROUP_KEY, storageGroup );
            }

            if( null != inetref ) {
                args.putString( INETREF_KEY, inetref );
            }

            if( null != folder ) {
                args.putString( FOLDER_KEY, folder );
            }

            if( null != sort ) {
                args.putString( SORT_KEY, sort );
            }

            return args;
        }

        public static Map<String, Object> fromBundle( Bundle args ) {

            Builder builder = new Builder( Media.valueOf( args.getString( MEDIA_KEY ) ) );

            if( args.containsKey( DESCENDING_KEY ) ) {
                builder.descending( args.getBoolean( DESCENDING_KEY ) );
            }

            if( args.containsKey( START_INDEX_KEY ) ) {
                builder.startIndex( args.getInt( START_INDEX_KEY ) );
            }

            if( args.containsKey( COUNT_KEY ) ) {
                builder.count( args.getInt( COUNT_KEY ) );
            }

            if( args.containsKey( TITLE_REGEX_KEY ) ) {
                builder.titleRegEx( args.getString( TITLE_REGEX_KEY ) );
            }

            if( args.containsKey( REC_GROUP_KEY ) ) {
                builder.recGroup( args.getString( REC_GROUP_KEY ) );
            }

            if( args.containsKey( STORAGE_GROUP_KEY ) ) {
                builder.storageGroup( args.getString( STORAGE_GROUP_KEY ) );
            }

            if( args.containsKey( INETREF_KEY ) ) {
                builder.inetref( args.getString( INETREF_KEY ) );
            }

            if( args.containsKey( FOLDER_KEY ) ) {
                builder.folder( args.getString( FOLDER_KEY ) );
            }

            if( args.containsKey( SORT_KEY ) ) {
                builder.sort( args.getString( SORT_KEY ) );
            }

            return builder.build();
        }
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        Log.d( TAG, "onAttach : enter" );

        Activity activity = getActivity();
        if( activity instanceof MediaItemListFragment.MediaItemListListener) {
            this.mediaItemListListener = (MediaItemListFragment.MediaItemListListener) activity;
        }
        if( activity instanceof TroubleshootClickListener) {
            this.troubleshootClickListener = (TroubleshootClickListener) activity;
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

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.loadMediaItemList();

        this.mediaItemListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.mediaItemListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.mediaItemListPresenter.destroy();

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
        this.mediaItemListPresenter.setView( this );

        parameters = Builder.fromBundle( getArguments() );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        LayoutManager layoutManager = new LayoutManager( getActivity() );
        this.rv_mediaItems.setLayoutManager( layoutManager );

        this.mediaItemsAdapter = new MediaItemsAdapter( getActivity(), new ArrayList<>() );
        this.mediaItemsAdapter.setOnItemClickListener( onItemClickListener );
        this.rv_mediaItems.setAdapter( mediaItemsAdapter );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        if( null != this.rl_progress ) {
            this.rl_progress.setVisibility( View.VISIBLE );
        }

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

        if( null != this.rl_progress ) {
            this.rl_progress.setVisibility( View.GONE );
        }

        Log.d( TAG, "hideLoading : exit" );
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    public void reload() {

        initialize();
        loadMediaItemList();

    }

    @Override
    public void renderMediaItemList( Collection<MediaItemModel> mediaItemModelCollection ) {
        Log.d( TAG, "renderMediaItemList : enter" );

        if( null != mediaItemModelCollection ) {

            if( parameters.containsKey( INETREF_KEY ) ) {

                String inetref = (String) parameters.get( INETREF_KEY );
                List<MediaItemModel> filtered = new ArrayList<>();
                for( MediaItemModel mediaItemModel : mediaItemModelCollection ) {

                    if( mediaItemModel.getInetref().equals( inetref ) ) {

                        filtered.add( mediaItemModel );

                    }

                }

                if( !filtered.isEmpty() ) {

                    mediaItemModelCollection = filtered;

                }

            }

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

        Log.d( TAG, "viewProgram : exit" );
    }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

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
    public Context getContext() {
        Log.d( TAG, "getContext : enter" );

        Log.d( TAG, "getContext : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads media items.
     */
    private void loadMediaItemList() {
        Log.d( TAG, "loadMediaItemList : enter" );

        this.mediaItemListPresenter.initialize( parameters );

        Log.d( TAG, "loadMediaItemList : exit" );
    }

    private MediaItemsAdapter.OnItemClickListener onItemClickListener = mediaItemModel -> {

        if( null != MediaItemListFragment.this.mediaItemListPresenter && null != mediaItemModel ) {

            if( mediaItemModel.isValid() ) {
                Log.i( TAG, "onItemClicked : mediaItemModel=" + mediaItemModel.toString() );

                MediaItemListFragment.this.mediaItemListPresenter.onMediaItemClicked( mediaItemModel );

            } else {
                Log.w( TAG, "onItemClicked : data error - mediaItemModel=" + mediaItemModel.toString() );

                if( null == mediaItemModel.getMedia() ) {

                    String message = getString(R.string.validation_no_media_type);
                    showToastMessage( message, null, null );

                } else {

                    String fields = "";
                    for( ErrorModel errorModel : mediaItemModel.getValidationErrors() ) {

                        if( !"".equals( fields ) ) {
                            fields += ", ";
                        }

                        fields += errorModel.getField();

                    }

                    String message = getResources().getString( R.string.validation_corrupt_data, fields );
                    showToastMessage( message, null, null );

                }

            }

        }

    };

}
