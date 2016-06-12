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

package org.mythtv.android.view.fragment.tv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.mythtv.android.R;
import org.mythtv.android.internal.di.components.VideoComponent;
import org.mythtv.android.model.VideoMetadataInfoModel;
import org.mythtv.android.presenter.tv.CardPresenter;
import org.mythtv.android.presenter.phone.HomeVideoListPresenter;
import org.mythtv.android.utils.ArticleCleaner;
import org.mythtv.android.view.VideoListView;
import org.mythtv.android.view.activity.tv.VideoDetailsActivity;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.inject.Inject;

/**
 * Created by dmfrey on 1/29/16.
 */
public class VideoHomeMovieFragment extends AbstractBaseVideoFragment implements VideoListView {

    private static final String TAG = VideoHomeMovieFragment.class.getSimpleName();

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int NUM_ROWS = 6;
    private static final int NUM_COLS = 15;

    private final Handler mHandler = new Handler();
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private URI mBackgroundURI;
    private BackgroundManager mBackgroundManager;

    @Inject
    HomeVideoListPresenter movieListPresenter;

    public VideoHomeMovieFragment() {
        super();
    }

    public static VideoHomeMovieFragment newInstance() {

        return new VideoHomeMovieFragment();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.i( TAG, "onCreateView : enter" );

        setupUI();

        Log.i( TAG, "onCreateView : exit" );
        return super.onCreateView( inflater, container, savedInstanceState );
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.i( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        prepareBackgroundManager();

        this.initialize();
        this.loadVideoList();

        setupEventListeners();

        Log.i( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.movieListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.movieListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if( null != mBackgroundTimer ) {
            Log.d( TAG, "onDestroy: " + mBackgroundTimer.toString() );

            mBackgroundTimer.cancel();

        }

        this.movieListPresenter.destroy();

    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( VideoComponent.class ).inject( this );
        this.movieListPresenter.setView( this );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        setTitle( getResources().getStringArray( R.array.watch_videos_tabs )[ 2 ] ); // Badge, when set, takes precedent

        // over title
        setHeadersState( HEADERS_ENABLED );
        setHeadersTransitionOnBackEnabled( true );

        // set fastLane (or headers) background color
        setBrandColor( getResources().getColor( R.color.primary ) );

        // set search icon color
//        setSearchAffordanceColor( getResources().getColor( R.color.primary_dark ) );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void renderVideoList( Collection<VideoMetadataInfoModel> videoModelCollection ) {
        Log.d( TAG, "renderVideoList : enter" );

        if( null != videoModelCollection ) {
            Log.d( TAG, "renderVideoList : videoModelCollection is not null" );

            ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
            CardPresenter cardPresenter = new CardPresenter();

            Map<Category, List<VideoMetadataInfoModel>> videos = new TreeMap<>( new Comparator<Category>() {

                @Override
                public int compare( Category lhs, Category rhs ) {

                    return lhs.key.compareTo( rhs.key );
                }

            });

            for( VideoMetadataInfoModel videoModel : videoModelCollection ) {
                Log.d( TAG, "renderVideoList : videoModel=" + videoModel );

                Category category = new Category( videoModel.getTitle() );
                if( !videos.containsKey( category ) ) {
                    Log.d( TAG, "renderVideoList : new category " + category );

                    List<VideoMetadataInfoModel> videoMetadataInfoModels = new ArrayList<>();
                    videoMetadataInfoModels.add( videoModel );
                    videos.put( category, videoMetadataInfoModels );

                } else {
                    Log.d( TAG, "renderVideoList : adding to existing category" );

                    videos.get( category ).add( videoModel );

                }

            }

            Log.d( TAG, "renderVideoList : build the list row adapters, catgory size=" + videos.size() );
            int i = 0;
            for( Category category : videos.keySet() ) {
                Log.d( TAG, "renderVideoList : category=" + category );

                Collections.sort( videos.get( category ), new Comparator<VideoMetadataInfoModel>() {

                    @Override
                    public int compare( VideoMetadataInfoModel lhs, VideoMetadataInfoModel rhs ) {

                        String lhsTitle = ArticleCleaner.clean( lhs.getTitle() );
                        String rhsTitle = ArticleCleaner.clean( rhs.getTitle() );

                        return lhsTitle.compareTo( rhsTitle );
                    }

                });

                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter( cardPresenter );
                for( VideoMetadataInfoModel videoMetadataInfoModel : videos.get( category ) ) {
                    Log.d( TAG, "renderVideoList : adding video '" + videoMetadataInfoModel.getTitle() + "' to category '" + category.getKey() + "' list row adapter" );

                    listRowAdapter.add( videoMetadataInfoModel );

                }

                HeaderItem header = new HeaderItem( i, category.getKey() );
                mRowsAdapter.add( new ListRow( header, listRowAdapter ) );

                i++;

            }

//            HeaderItem gridHeader = new HeaderItem( i, "PREFERENCES" );
//
//            GridItemPresenter mGridPresenter = new GridItemPresenter();
//            ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter( mGridPresenter );
//            gridRowAdapter.add( getResources().getString( R.string.personal_settings ) );
//            mRowsAdapter.add( new ListRow( gridHeader, gridRowAdapter ) );

            setAdapter(mRowsAdapter);

        }

        Log.d( TAG, "renderVideoList : exit" );
    }

    @Override
    public void viewVideo( VideoMetadataInfoModel videoMetadataInfoModel ) {

    }

    @Override
    public void showError( String message ) {

    }

    @Override
    public void showMessage( String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message );

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public Context getContext() {
        Log.d( TAG, "getContext : enter" );

        Log.d( TAG, "getContext : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all videos.
     */
    private void loadVideoList() {
        Log.d( TAG, "loadVideoList : enter" );

        this.movieListPresenter.initialize();

        Log.d( TAG, "loadProgramList : exit" );
    }

    private void prepareBackgroundManager() {
        Log.d( TAG, "prepareBackgroundManager : enter" );

        mBackgroundManager = BackgroundManager.getInstance( getActivity() );
        mBackgroundManager.attach( getActivity().getWindow() );
        mDefaultBackground = getResources().getDrawable( R.drawable.default_background );
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( mMetrics );

        Log.d( TAG, "prepareBackgroundManager : exit" );
    }

    private void setupEventListeners() {
        Log.d( TAG, "setupEventListeners : enter" );

        setOnSearchClickedListener( new View.OnClickListener() {

            @Override
            public void onClick( View view ) {

                VideoHomeMovieFragment.this.listener.onSearchClicked();

            }

        });

        setOnItemViewClickedListener( new ItemViewClickedListener() );
        setOnItemViewSelectedListener( new ItemViewSelectedListener() );

        Log.d( TAG, "setupEventListeners : exit" );
    }

    protected void updateBackground( String uri ) {
        Log.d( TAG, "updateBackground : enter" );

        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with( getActivity() )
                .load( uri )
//                .centerCrop()
                .error( mDefaultBackground )
                .into( new SimpleTarget<GlideDrawable>( width, height ) {

                    @Override
                    public void onResourceReady( GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation ) {

                        mBackgroundManager.setDrawable( resource );

                    }

                });

        mBackgroundTimer.cancel();

        Log.d( TAG, "updateBackground : exit" );
    }

    private void startBackgroundTimer() {
        Log.d( TAG, "startBackgroundTimer : enter" );

        if( mBackgroundTimer != null ) {

            mBackgroundTimer.cancel();

        }

        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule( new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY );

        Log.d( TAG, "startBackgroundTimer : exit" );
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {

            if( item instanceof VideoMetadataInfoModel ) {

                VideoMetadataInfoModel videoMetadataInfoModel = (VideoMetadataInfoModel) item;
                Log.d(TAG, "onItemClicked : videoMetadataInfoModel=" + videoMetadataInfoModel.toString() );

                Intent intent = new Intent( getActivity(), VideoDetailsActivity.class );
                intent.putExtra( VideoDetailsActivity.VIDEO, videoMetadataInfoModel );

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        VideoDetailsActivity.SHARED_ELEMENT_NAME ).toBundle();

                getActivity().startActivity( intent, bundle );

            } else if( item instanceof String ) {

                if (((String) item).contains(getString(R.string.error_fragment))) {
//                    Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
//                    startActivity(intent);

                } else {

                    Toast.makeText( getActivity(), ((String) item), Toast.LENGTH_SHORT ).show();

                }

            }

        }

    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {

        @Override
        public void onItemSelected( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {

            if( item instanceof VideoMetadataInfoModel ) {

                VideoMetadataInfoModel videoMetadataInfoModel = (VideoMetadataInfoModel) item;
                mBackgroundURI = URI.create( getSharedPreferencesModule().getMasterBackendUrl() + "/Content/GetImageFile?StorageGroup=Fanart&FileName=" + videoMetadataInfoModel.getFanart() );
                startBackgroundTimer();

            }

        }

    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    if( null != mBackgroundURI ) {

                        updateBackground( mBackgroundURI.toString() );

                    }
                }
            });

        }
    }

    private class GridItemPresenter extends Presenter {

        @Override
        public ViewHolder onCreateViewHolder( ViewGroup parent ) {

            TextView view = new TextView( parent.getContext() );
            view.setLayoutParams( new ViewGroup.LayoutParams( GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT ) );
            view.setFocusable( true );
            view.setFocusableInTouchMode( true );
            view.setBackgroundColor( getResources().getColor( R.color.default_background ) );
            view.setTextColor( Color.WHITE );
            view.setGravity( Gravity.CENTER );

            return new ViewHolder( view );
        }

        @Override
        public void onBindViewHolder( ViewHolder viewHolder, Object item ) {
            ( (TextView) viewHolder.view ).setText( (String) item );
        }

        @Override
        public void onUnbindViewHolder( ViewHolder viewHolder ) {
        }

    }

    private class Category {

        private final String key;

        public Category( final String title ) {

            this.key = ArticleCleaner.clean( title ).substring( 0, 1 ).toUpperCase();

        }

        public String getKey() {
            return key;
        }

        @Override
        public boolean equals( Object o ) {

            if( this == o ) {

                return true;
            }

            if( o == null || getClass() != o.getClass() ) {

                return false;
            }

            Category category = (Category) o;

            return key.equals( category.key );

        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public String toString() {
            return "Category{" +
                    "key='" + key + '\'' +
                    '}';
        }

    }

}
