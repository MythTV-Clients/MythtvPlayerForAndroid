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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.interactor.GetMediaItemList;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.presenter.phone.MediaItemListPresenter;
import org.mythtv.android.presentation.presenter.tv.CardPresenter;
import org.mythtv.android.presentation.utils.ArticleCleaner;
import org.mythtv.android.presentation.utils.MediaItemFilter;
import org.mythtv.android.presentation.view.MediaItemListView;
import org.mythtv.android.presentation.view.activity.tv.MediaItemDetailsActivity;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/29/16.
 */
public class MediaItemListFragment extends AbstractBaseBrowseFragment implements MediaItemListView {

    private static final String TAG = MediaItemListFragment.class.getSimpleName();

    public static final String MEDIA_KEY = "media";
    public static final String TV_KEY = "tv";

    private static final int BACKGROUND_UPDATE_DELAY = 300;

    private Media media;
    private boolean tv;

    private final Handler mHandler = new Handler();
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private URI mBackgroundURI;
    private BackgroundManager mBackgroundManager;

    @Inject
    MediaItemListPresenter mediaItemListPresenter;

    private MediaItemListListener listener;

    private List<MediaItemModel> mediaItems;

    public MediaItemListFragment() {
        super();
    }

    /**
     * Interface for listening program list events.
     */
    public interface MediaItemListListener {

        void onSearchClicked();

    }

    public static MediaItemListFragment newInstance( Bundle args ) {

        MediaItemListFragment mediaItemListFragment = new MediaItemListFragment();
        mediaItemListFragment.setArguments( args );

        return mediaItemListFragment;
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        Log.d( TAG, "onAttach : enter" );

        Activity activity = getActivity();
        if( activity instanceof MediaItemListListener) {
            this.listener = (MediaItemListListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        media = Media.valueOf( getArguments().getString( MEDIA_KEY ) );
        tv = getArguments().getBoolean( TV_KEY );

        setupUI();

        Log.d( TAG, "onCreateView : exit" );
        return super.onCreateView( inflater, container, savedInstanceState );
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        prepareBackgroundManager();

        this.initialize();

        this.loadMediaItemList();

        setupEventListeners();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.mediaItemListPresenter.resume();

        Log.d( TAG, "onActivityCreated : exit" );
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
        super.onDestroy();

        if( null != mBackgroundTimer ) {
            Log.d( TAG, "onDestroy: " + mBackgroundTimer.toString() );

            mBackgroundTimer.cancel();

        }

        this.mediaItemListPresenter.destroy();

    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( MediaComponent.class ).inject( this );
        this.mediaItemListPresenter.setView( this );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        switch( media ) {

            case PROGRAM :

                setTitle( getString( R.string.recordings_browse_title ) ); // Badge, when set, takes precedent

                break;

            case MOVIE :

                setTitle( getResources().getStringArray( R.array.watch_videos_tabs )[ 0 ] ); // Badge, when set, takes precedent

                break;

            case TELEVISION :

                setTitle( getResources().getStringArray( R.array.watch_videos_tabs )[ 1 ] ); // Badge, when set, takes precedent

                break;

            case HOMEVIDEO :

                setTitle( getResources().getStringArray( R.array.watch_videos_tabs )[ 2 ] ); // Badge, when set, takes precedent

                break;

            case MUSICVIDEO :

                setTitle( getResources().getStringArray( R.array.watch_videos_tabs )[ 3 ] ); // Badge, when set, takes precedent

                break;

            case ADULT :

                setTitle( getResources().getStringArray( R.array.watch_videos_tabs )[ 4 ] ); // Badge, when set, takes precedent

                break;

            default :

                break;

        }

        // over title
        setHeadersState( HEADERS_ENABLED );
        setHeadersTransitionOnBackEnabled( true );

        // set fastLane (or headers) background color
        setBrandColor( getResources().getColor( R.color.primary ) );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.v( TAG, "showLoading : enter" );

        Log.v( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.v( TAG, "hideLoading : enter" );

        Log.v( TAG, "hideLoading : exit" );
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

    @Override
    public void renderMediaItemList( Collection<MediaItemModel> mediaItemModelCollection ) {
        Log.d( TAG, "renderMediaItemList : enter" );

        if( null != mediaItemModelCollection ) {
            Log.d( TAG, "renderMediaItemList : mediaItemModelCollection is not null" );

            Observable.fromIterable( mediaItemModelCollection )
                    .filter( mediaItemModel -> MediaItemFilter.filter( mediaItemModel, getActivity() ) )
                    .toList()
                    .subscribe( items -> this.mediaItems = items );

            ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter( new ListRowPresenter() );
            CardPresenter cardPresenter = new CardPresenter();

            Map<Category, List<MediaItemModel>> categories = new TreeMap<>( new Comparator<Category>() {

                @Override
                public int compare( Category lhs, Category rhs ) {

                    return lhs.key.compareTo( rhs.key );
                }

            });

            for( MediaItemModel mediaItemModel : mediaItems ) {
                Log.d( TAG, "renderMediaItemList : mediaItemModel=" + mediaItemModel.toString() );

                Category category = new Category( mediaItemModel.title(), mediaItemModel.media() );
                if( categories.containsKey( category ) ) {
                    Log.d( TAG, "renderMediaItemList : adding to existing category" );

                    categories.get( category ).add( mediaItemModel );

                } else {
                    Log.d( TAG, "renderMediaItemList : new category=" + category );

                    List<MediaItemModel> mediaItemModels = new ArrayList<>();
                    mediaItemModels.add( mediaItemModel );
                    categories.put( category, mediaItemModels );

                }

            }

            Log.d( TAG, "renderMediaItemList : build the list row adapters, catgory size=" + categories.size() );
            int i = 0;
            for( Map.Entry<Category, List<MediaItemModel>> entry : categories.entrySet() ) {
                Log.d( TAG, "renderMediaItemList : category=" + entry.getKey() );

                Collections.sort( entry.getValue(), ( lhs, rhs ) -> {

                    if( entry.getKey().media == Media.PROGRAM ) {

                        // Requires min API 19
//                        int i1 = Integer.compare( lhs.season(), rhs.season() );
                        int i1 = Integer.valueOf( lhs.season() ).compareTo( rhs.season() );
                        if( i1 != 0 ) {

                            return i1;
                        }

                        // Requires min API 19
//                        return Integer.compare( lhs.episode(), rhs.episode() );
                        return Integer.valueOf( lhs.episode() ).compareTo( rhs.episode() );

                    } else {

                        String lhsTitle = ArticleCleaner.clean( getActivity(), lhs.title() );
                        String rhsTitle = ArticleCleaner.clean( getActivity(), rhs.title() );

                        return lhsTitle.compareTo(rhsTitle);
                    }

                });

                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter( cardPresenter );
                for( MediaItemModel mediaItemModel : entry.getValue() ) {
                    Log.d( TAG, "renderMediaItemList : adding mediaItem '" + mediaItemModel.title() + "' to category '" + entry.getKey().getKey() + "' list row adapter" );

                    listRowAdapter.add( mediaItemModel );

                }

                HeaderItem header = new HeaderItem( i, entry.getKey().getTitle() );
                mRowsAdapter.add( new ListRow( header, listRowAdapter ) );

                i++;

            }

            setAdapter( mRowsAdapter );

        }

        Log.d( TAG, "renderMediaItemList : exit" );
    }

    @Override
    public void viewMediaItem( final MediaItemModel mediaItemModel, final View sharedElement, final String sharedElementName ) {
        Log.v( TAG, "viewMediaItem : enter" );

        Log.v( TAG, "viewMediaItem : exit" );
    }

    @Override
    public void showError( String message ) {
        Log.v( TAG, "showError : enter" );

        Log.v( TAG, "showError : exit" );
    }

    @Override
    public void showMessage( String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message );

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public Context context() {
        Log.d( TAG, "context : enter" );

        Log.d( TAG, "context : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all media items.
     */
    private void loadMediaItemList() {
        Log.d( TAG, "loadMediaItemList : enter" );

        this.mediaItemListPresenter.initialize( media, null, tv );

        Log.d( TAG, "loadMediaItemList : exit" );
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

        setOnSearchClickedListener(view -> MediaItemListFragment.this.listener.onSearchClicked());

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
                .diskCacheStrategy( DiskCacheStrategy.RESULT )
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

            if( item instanceof MediaItemModel ) {

                MediaItemModel mediaItemModel = (MediaItemModel) item;
                Log.d( TAG, "onItemClicked : mediaItemModel=" + mediaItemModel.toString() );

                Intent intent = new Intent( getActivity(), MediaItemDetailsActivity.class );
                intent.putExtra( MediaItemDetailsActivity.MEDIA_ITEM, mediaItemModel );

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        MediaItemDetailsActivity.SHARED_ELEMENT_NAME ).toBundle();

                getActivity().startActivity( intent, bundle );

            } else if( item instanceof String ) {

                if( ( (String) item ).contains( getString( R.string.error_fragment ) ) ) {
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

            if( item instanceof MediaItemModel ) {

                try {

                    MediaItemModel mediaItemModel = (MediaItemModel) item;

                    String cleanedUrl = mediaItemModel.fanartUrl();
                    cleanedUrl = cleanedUrl.replaceAll(" ", "%20");
                    cleanedUrl = cleanedUrl.replaceAll(":", "&#58;");

                    mBackgroundURI = URI.create(getSharedPreferencesModule().getMasterBackendUrl() + cleanedUrl);
                    startBackgroundTimer();

                } catch( NullPointerException e ) {
                    Log.w( TAG, e.getLocalizedMessage(), e );
                }

            }

        }

    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(() -> {

                if( null != mBackgroundURI ) {

                    updateBackground( mBackgroundURI.toString() );

                }
            });

        }
    }

    private class Category {

        private final String key;
        private final String title;
        private final Media media;

        public Category( final String title, final Media media ) {

            if( media == Media.PROGRAM || media == Media.TELEVISION ) {

                this.key = ArticleCleaner.clean( getActivity(), title ).toUpperCase( Locale.getDefault() );
                this.title = title;

            } else {

                this.key = ArticleCleaner.clean( getActivity(), title ).substring( 0, 1 ).toUpperCase( Locale.getDefault() );
                this.title = key;

            }

            this.media = media;

        }

        public String getKey() {

            return key;
        }

        public String getTitle() {

            return title;
        }

        public Media getMedia() {

            return media;
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
                    ", title='" + title + '\'' +
                    ", media=" + media.name() +
                    '}';
        }

    }

}
