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

import org.mythtv.android.presentation.R;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.presenter.tv.CardPresenter;
import org.mythtv.android.presentation.presenter.phone.ProgramListPresenter;
import org.mythtv.android.presentation.utils.ArticleCleaner;
import org.mythtv.android.presentation.view.ProgramListView;
import org.mythtv.android.presentation.view.activity.tv.RecordingsDetailsActivity;

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
public class RecordingsFragment extends AbstractBaseBrowseFragment implements ProgramListView {

    private static final String TAG = RecordingsFragment.class.getSimpleName();

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

    /**
     * Interface for listening program list events.
     */
    public interface ProgramListListener {

        void onSearchClicked();

    }

    @Inject
    ProgramListPresenter programListPresenter;

    private ProgramListListener listener;

    public RecordingsFragment() {
        super();
    }

    public static RecordingsFragment newInstance() {

        return new RecordingsFragment();
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.d( TAG, "onAttach : enter" );

        if( activity instanceof ProgramListListener ) {
            this.listener = (ProgramListListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
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
        this.loadProgramList();

        setupEventListeners();

        Log.i( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.programListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.programListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if( null != mBackgroundTimer ) {
            Log.d( TAG, "onDestroy: " + mBackgroundTimer.toString() );

            mBackgroundTimer.cancel();

        }

        this.programListPresenter.destroy();

    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( DvrComponent.class ).inject( this );
        this.programListPresenter.setView( this );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        setTitle( getString( R.string.recordings_browse_title ) ); // Badge, when set, takes precedent

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
    public void renderProgramList( Collection<ProgramModel> programModelCollection ) {
        Log.d( TAG, "renderProgramList : enter" );

        if( null != programModelCollection ) {

            ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
            CardPresenter cardPresenter = new CardPresenter();

            Map<Category, List<ProgramModel>> recordings = new TreeMap<>( new Comparator<Category>() {

                @Override
                public int compare( Category lhs, Category rhs ) {

                    return lhs.key.compareTo( rhs.key );
                }

            });

            for( ProgramModel programModel : programModelCollection ) {

//                if( "LiveTV".equalsIgnoreCase( programModel.getRecording().getStorageGroup() ) ) {
//
//                    break;
//                }

                Category category = new Category( programModel.getTitle() );
                if( !recordings.containsKey( category ) ) {

                    List<ProgramModel> programModels = new ArrayList<>();
                    programModels.add( programModel );
                    recordings.put( category, programModels );

                } else {

                    recordings.get( category ).add( programModel );

                }

            }

            int i = 0;
            for( Category category : recordings.keySet() ) {

                Collections.sort( recordings.get( category ), new Comparator<ProgramModel>() {

                    @Override
                    public int compare( ProgramModel lhs, ProgramModel rhs ) {

                        int i = lhs.getEndTime().compareTo( rhs.getEndTime() );
                        if( i != 0 ) {

                            return i;
                        }

                        i = lhs.getSeason().compareTo( rhs.getSeason() );
                        if( i != 0 ) {

                            return i;
                        }

                        return lhs.getEpisode().compareTo( rhs.getEpisode() );
                    }

                });

                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter( cardPresenter );
                for( ProgramModel programModel : recordings.get( category ) ) {

                    listRowAdapter.add( programModel );

                }

                HeaderItem header = new HeaderItem( i, category.getTitle() );
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

        Log.d( TAG, "renderProgramList : exit" );
    }

    @Override
    public void viewProgram( ProgramModel programModel ) {

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
     * Loads all programs.
     */
    private void loadProgramList() {
        Log.d( TAG, "loadProgramList : enter" );

        this.programListPresenter.initialize();

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

                RecordingsFragment.this.listener.onSearchClicked();

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

            if( item instanceof ProgramModel ) {

                ProgramModel programModel = (ProgramModel) item;
                Log.d(TAG, "onItemClicked : programModel=" + programModel.toString() );

                Intent intent = new Intent( getActivity(), RecordingsDetailsActivity.class );
                intent.putExtra( RecordingsDetailsActivity.PROGRAM, programModel );

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        RecordingsDetailsActivity.SHARED_ELEMENT_NAME ).toBundle();

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

            if( item instanceof ProgramModel ) {

                ProgramModel programModel = (ProgramModel) item;
                mBackgroundURI = URI.create( getSharedPreferencesModule().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + programModel.getInetref() + "&Type=fanart" );
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
        private final String title;

        public Category( final String title ) {

            this.key = ArticleCleaner.clean( getActivity(), title );
            this.title = title;

        }

        public String getKey() {
            return key;
        }

        public String getTitle() {
            return title;
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
                    '}';
        }

    }

}
