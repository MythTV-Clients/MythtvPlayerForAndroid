/*
 * Copyright (c) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mythtv.android.presentation.view.fragment.tv;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.ControlButtonPresenterSelector;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.PlaybackControlsRow.FastForwardAction;
import android.support.v17.leanback.widget.PlaybackControlsRow.PlayPauseAction;
import android.support.v17.leanback.widget.PlaybackControlsRow.RewindAction;
import android.support.v17.leanback.widget.PlaybackControlsRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.mythtv.android.presentation.model.VideoModel;

import java.util.Collections;

/**
 *
 * The PlaybackOverlayFragment class handles the Fragment associated with displaying the UI for the
 * media controls such as play / pause / skip forward / skip backward etc.
 *
 * The UI is updated through events that it receives from its MediaController
 *
 * @author dmfrey
 */
@TargetApi( Build.VERSION_CODES.LOLLIPOP )
public class PlaybackOverlayFragment extends android.support.v17.leanback.app.PlaybackOverlayFragment {

    private static final String TAG = PlaybackOverlayFragment.class.getSimpleName();

    public static final String VIDEO = "org.mythtv.player.Video";
    public static final String SHARED_ELEMENT_NAME = "hero";

    private static final boolean SHOW_DETAIL = true;
    private static final boolean HIDE_MORE_ACTIONS = true;
    private static final int PRIMARY_CONTROLS = 3;
    private static final boolean SHOW_IMAGE = PRIMARY_CONTROLS <= 5;
    private static final int BACKGROUND_TYPE = PlaybackOverlayFragment.BG_LIGHT;
    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 240;
    private static final int DEFAULT_UPDATE_PERIOD = 1000;
    private static final int UPDATE_PERIOD = 16;
    private static final int SIMULATED_BUFFERED_TIME = 10000;

    private ArrayObjectAdapter mRowsAdapter;
    private ArrayObjectAdapter mPrimaryActionsAdapter;
//    private ArrayObjectAdapter mSecondaryActionsAdapter;
    private PlayPauseAction mPlayPauseAction;
//    private PlaybackControlsRow.RepeatAction mRepeatAction;
//    private PlaybackControlsRow.ThumbsUpAction mThumbsUpAction;
//    private PlaybackControlsRow.ThumbsDownAction mThumbsDownAction;
//    private PlaybackControlsRow.ShuffleAction mShuffleAction;
    private FastForwardAction mFastForwardAction;
    private RewindAction mRewindAction;
//    private PlaybackControlsRow.SkipNextAction mSkipNextAction;
//    private PlaybackControlsRow.SkipPreviousAction mSkipPreviousAction;
    private PlaybackControlsRow mPlaybackControlsRow;
    private Handler mHandler;
    private Runnable mRunnable;
    private VideoModel mSelectedVideo; // Video is the currently playing Video and its metadata.
    private int mPosition = 0;
    private long mStartTimeMillis;

    private OnPlayPauseClickedListener mCallback;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        // Initialize instance variables.
        mSelectedVideo = getActivity().getIntent().getParcelableExtra( VIDEO );

        mHandler = new Handler();

        setBackgroundType( BACKGROUND_TYPE );
        setFadingEnabled( false );

        setupRows();

        setOnItemViewSelectedListener( new OnItemViewSelectedListener() {

            @Override
            public void onItemSelected( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {
                Log.i( TAG, "onItemSelected: " + item + " row " + row );
            }

        });

        setOnItemViewClickedListener( new OnItemViewClickedListener() {

            @Override
            public void onItemClicked( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {
                Log.i( TAG, "onItemClicked: " + item + " row " + row );
            }

        });

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    public void onAttach( Context context ) {
        Log.d( TAG, "onAttach : enter" );
        super.onAttach( context );

        if( context instanceof OnPlayPauseClickedListener ) {

            mCallback = (OnPlayPauseClickedListener) context;

        } else {

            throw new RuntimeException( context.toString() + " must implement OnPlayPauseClickedListener" );

        }

        Log.d( TAG, "onAttach : exit" );
    }

    private void setupRows() {
        Log.d( TAG, "setupRows : enter" );

        ClassPresenterSelector ps = new ClassPresenterSelector();

        PlaybackControlsRowPresenter playbackControlsRowPresenter;
        if( SHOW_DETAIL ) {

            playbackControlsRowPresenter = new PlaybackControlsRowPresenter( new DescriptionPresenter() );

        } else {

            playbackControlsRowPresenter = new PlaybackControlsRowPresenter();

        }

        playbackControlsRowPresenter.setOnActionClickedListener( new OnActionClickedListener() {

            public void onActionClicked( Action action ) {

                if( action.getId() == mPlayPauseAction.getId() ) {

                    togglePlayback( mPlayPauseAction.getIndex() == PlayPauseAction.PLAY );

//                } else if( action.getId() == mSkipNextAction.getId() ) {
//
//                    next();
//
//                } else if( action.getId() == mSkipPreviousAction.getId() ) {
//
//                    prev();

                } else if( action.getId() == mFastForwardAction.getId() ) {

                    Toast.makeText( getActivity(), "TODO: Fast Forward", Toast.LENGTH_SHORT ).show();

                } else if( action.getId() == mRewindAction.getId() ) {

                    Toast.makeText( getActivity(), "TODO: Rewind", Toast.LENGTH_SHORT ).show();

                }

                if( action instanceof PlaybackControlsRow.MultiAction ) {

                    ( ( PlaybackControlsRow.MultiAction ) action ).nextIndex();
                    notifyChanged( action );

                }

            }
        });
        playbackControlsRowPresenter.setSecondaryActionsHidden( HIDE_MORE_ACTIONS );

        ps.addClassPresenter( PlaybackControlsRow.class, playbackControlsRowPresenter );
        ps.addClassPresenter( ListRow.class, new ListRowPresenter() );
        mRowsAdapter = new ArrayObjectAdapter( ps );

        addPlaybackControlsRow();
        addOtherRows();

        setAdapter( mRowsAdapter );

        Log.d( TAG, "setupRows : exit" );
    }

    public void togglePlayback( boolean playPause ) {
        Log.d( TAG, "togglePlayback : enter" );

        if( playPause ) {

            startProgressAutomation();
            setFadingEnabled( true );
            mCallback.onFragmentPlayPause( mSelectedVideo, mPlaybackControlsRow.getCurrentTime(), true );
            mPlayPauseAction.setIcon( mPlayPauseAction.getDrawable( PlayPauseAction.PAUSE ) );

        } else {

            stopProgressAutomation();
            setFadingEnabled( false );
            mCallback.onFragmentPlayPause( mSelectedVideo, mPlaybackControlsRow.getCurrentTime(), false );
            mPlayPauseAction.setIcon( mPlayPauseAction.getDrawable( PlayPauseAction.PLAY ) );

        }

        notifyChanged( mPlayPauseAction );

        Log.d( TAG, "togglePlayback : exit" );
    }

    private int getDuration() {
        Log.d( TAG, "getDuration : enter" );

        Log.i( TAG, "getDuration : mSelectedVideo=" + mSelectedVideo.toString() );

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource( mSelectedVideo.videoUrl, Collections.emptyMap() );

        String time = mmr.extractMetadata( MediaMetadataRetriever.METADATA_KEY_DURATION );
        long duration = Long.parseLong( time );
        Log.d( TAG, "getDuration : duration=" + duration );

        Log.d( TAG, "getDuration : exit" );
        return (int) duration;
    }

    private void addPlaybackControlsRow() {
        Log.d( TAG, "addPlaybackControlsRow : enter" );

        if( SHOW_DETAIL ) {

            mPlaybackControlsRow = new PlaybackControlsRow( mSelectedVideo );

        } else {

            mPlaybackControlsRow = new PlaybackControlsRow();

        }

        mRowsAdapter.add( mPlaybackControlsRow );

        updatePlaybackRow();

        ControlButtonPresenterSelector presenterSelector = new ControlButtonPresenterSelector();
        mPrimaryActionsAdapter = new ArrayObjectAdapter( presenterSelector );
//        mSecondaryActionsAdapter = new ArrayObjectAdapter( presenterSelector );
        mPlaybackControlsRow.setPrimaryActionsAdapter( mPrimaryActionsAdapter );
//        mPlaybackControlsRow.setSecondaryActionsAdapter( mSecondaryActionsAdapter );

        mPlayPauseAction = new PlayPauseAction( getActivity() );
//        mRepeatAction = new PlaybackControlsRow.RepeatAction( getActivity() );
//        mThumbsUpAction = new PlaybackControlsRow.ThumbsUpAction( getActivity() );
//        mThumbsDownAction = new PlaybackControlsRow.ThumbsDownAction( getActivity() );
//        mShuffleAction = new PlaybackControlsRow.ShuffleAction( getActivity() );
//        mSkipNextAction = new PlaybackControlsRow.SkipNextAction( getActivity() );
//        mSkipPreviousAction = new PlaybackControlsRow.SkipPreviousAction( getActivity() );
        mFastForwardAction = new PlaybackControlsRow.FastForwardAction( getActivity() );
        mRewindAction = new PlaybackControlsRow.RewindAction( getActivity() );

//        if( PRIMARY_CONTROLS > 5 ) {
//
//            mPrimaryActionsAdapter.add( mThumbsUpAction );
//
//        } else {
//
//            mSecondaryActionsAdapter.add( mThumbsUpAction );
//
//        }

//        mPrimaryActionsAdapter.add( mSkipPreviousAction );

        if( PRIMARY_CONTROLS > 3 ) {

            mPrimaryActionsAdapter.add( new PlaybackControlsRow.RewindAction( getActivity() ) );

        }

        mPrimaryActionsAdapter.add( mPlayPauseAction );

        if( PRIMARY_CONTROLS > 3 ) {

            mPrimaryActionsAdapter.add( new PlaybackControlsRow.FastForwardAction( getActivity() ) );

        }

//        mPrimaryActionsAdapter.add( mSkipNextAction );

//        mSecondaryActionsAdapter.add( mRepeatAction );
//        mSecondaryActionsAdapter.add( mShuffleAction );
//        if( PRIMARY_CONTROLS > 5 ) {
//
//            mPrimaryActionsAdapter.add( mThumbsDownAction );
//
//        } else {
//
//            mSecondaryActionsAdapter.add( mThumbsDownAction );
//
//        }

//        mSecondaryActionsAdapter.add( new PlaybackControlsRow.HighQualityAction( getActivity() ) );
//        mSecondaryActionsAdapter.add( new PlaybackControlsRow.ClosedCaptioningAction( getActivity() ) );

        Log.d( TAG, "addPlaybackControlsRow : exit" );
    }

    private void notifyChanged( Action action ) {
        Log.d( TAG, "notifyChanged : enter" );

        ArrayObjectAdapter adapter = mPrimaryActionsAdapter;
        if( adapter.indexOf(action) >= 0 ) {

            adapter.notifyArrayItemRangeChanged( adapter.indexOf( action ), 1 );

            Log.d( TAG, "notifyChanged : exit" );
            return;
        }

//        adapter = mSecondaryActionsAdapter;
//        if( adapter.indexOf( action ) >= 0 ) {
//
//            adapter.notifyArrayItemRangeChanged( adapter.indexOf( action ), 1 );
//
//            return;
//        }

        Log.d( TAG, "notifyChanged : exit" );
    }

    private void updatePlaybackRow() {
        Log.d( TAG, "updatePlaybackRow : enter" );

        if( null != mPlaybackControlsRow.getItem() ) {
            VideoModel item = (VideoModel) mPlaybackControlsRow.getItem();
            item = mSelectedVideo;
        }

        if( SHOW_IMAGE ) {

            updateVideoImage( mSelectedVideo.cardImageUrl );

        }

        mRowsAdapter.notifyArrayItemRangeChanged( 0, 1 );
        mPlaybackControlsRow.setTotalTime( getDuration() );
        mPlaybackControlsRow.setCurrentTime( 0 );
        mPlaybackControlsRow.setBufferedProgress( 0 );

        Log.d( TAG, "updatePlaybackRow : exit" );
    }

    private void addOtherRows() {
        Log.d( TAG, "addOtherRows : enter" );

//        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardPresenter());
//        for (Movie movie : mItems) {
//            listRowAdapter.add(movie);
//        }
//
//        HeaderItem header = new HeaderItem(0, getString(R.string.related_movies));
//        mRowsAdapter.add(new ListRow(header, listRowAdapter));

        Log.d( TAG, "addOtherRows : exit" );
    }

    private int getUpdatePeriod() {
        Log.d( TAG, "getUpdatePeriod : enter" );

        if( null == getView() || mPlaybackControlsRow.getTotalTime() <= 0 ) {

            Log.d( TAG, "getUpdatePeriod : exit, default period" );
            return DEFAULT_UPDATE_PERIOD;
        }

        Log.d( TAG, "getUpdatePeriod : exit" );
        return Math.max( UPDATE_PERIOD, mPlaybackControlsRow.getTotalTime() / getView().getWidth() );

    }

    private void startProgressAutomation() {
        Log.d( TAG, "startProgressAutomation : enter" );

        mRunnable = new Runnable() {

            @Override
            public void run() {

                int updatePeriod = getUpdatePeriod();
                int currentTime = mPlaybackControlsRow.getCurrentTime() + updatePeriod;
                int totalTime = mPlaybackControlsRow.getTotalTime();
                mPlaybackControlsRow.setCurrentTime( currentTime );
                mPlaybackControlsRow.setBufferedProgress( currentTime + SIMULATED_BUFFERED_TIME );

                if( totalTime > 0 && totalTime <= currentTime ) {

                    next();

                }

                mHandler.postDelayed( this, updatePeriod );

            }

        };

        mHandler.postDelayed( mRunnable, getUpdatePeriod() );

        Log.d( TAG, "startProgressAutomation : exit" );
    }

    private void next() {
        Log.d( TAG, "next : enter" );

//        if( ++mCurrentItem >= mItems.size() ) {
//
//            mCurrentItem = 0;
//
//        }
//
//        if( mPlayPauseAction.getIndex() == PlayPauseAction.PLAY ) {
//
//            mCallback.onFragmentPlayPause( mItems.get( mCurrentItem ), 0, false );
//
//        } else {
//
//            mCallback.onFragmentPlayPause( mItems.get( mCurrentItem ), 0, true );
//
//        }
//
//        updatePlaybackRow( mCurrentItem );

        Log.d( TAG, "next : exit" );
    }

    private void prev() {
        Log.d( TAG, "prev : enter" );

//        if( --mCurrentItem < 0 ) {
//
//            mCurrentItem = mItems.size() - 1;
//
//        }
//
//        if( mPlayPauseAction.getIndex() == PlayPauseAction.PLAY ) {
//
//            mCallback.onFragmentPlayPause( mItems.get( mCurrentItem), 0, false );
//
//        } else {
//
//            mCallback.onFragmentPlayPause( mItems.get( mCurrentItem ), 0, true );
//
//        }
//
//        updatePlaybackRow( mCurrentItem );

        Log.d( TAG, "prev : exit" );
    }

//    private void setPosition( int position ) {
//        if( position > mDuration ) {
//
//            mPosition = (int) mDuration;
//
//        } else if( position < 0 ) {
//
//            mPosition = 0;
//            mStartTimeMillis = System.currentTimeMillis();
//
//        } else {
//
//            mPosition = position;
//
//        }
//
//        mStartTimeMillis = System.currentTimeMillis();
//        Log.d( TAG, "position set to " + mPosition );
//
//    }

//    private void setVideoPath( String videoUrl ) {
//        Log.i( TAG, "setVideoPath : videoUrl=" + videoUrl );
//
//        setPosition( 0 );
//        mVideoView.setVideoPath( videoUrl );
//        mStartTimeMillis = 0;
//        mDuration = Utils.getDuration( videoUrl );
//
//    }

    private void stopProgressAutomation() {
        Log.d( TAG, "stopProgressAutomation : enter" );

        if( null != mHandler && null != mRunnable ) {

            mHandler.removeCallbacks( mRunnable);

        }

        Log.d( TAG, "stopProgressAutomation : exit" );
    }

    @Override
    public void onStop() {
        Log.d( TAG, "onStop : enter" );

        stopProgressAutomation();

        super.onStop();

        Log.d( TAG, "onStop : exit" );
    }

    protected void updateVideoImage( String uri ) {
        Log.d( TAG, "updateVideoImage : enter" );

        Glide.with( getActivity() )
                .load( uri )
                .centerCrop()
                .into( new SimpleTarget<GlideDrawable>( CARD_WIDTH, CARD_HEIGHT ) {

                    @Override
                    public void onResourceReady( GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation ) {

                        mPlaybackControlsRow.setImageDrawable( resource );
                        mRowsAdapter.notifyArrayItemRangeChanged( 0, mRowsAdapter.size() );

                    }

                });

        Log.d( TAG, "updateVideoImage : exit" );
    }

    // Container Activity must implement this interface
    public interface OnPlayPauseClickedListener {

        void onFragmentPlayPause( VideoModel movie, int position, Boolean playPause );

    }

    static class DescriptionPresenter extends AbstractDetailsDescriptionPresenter {

        @Override
        protected void onBindDescription( ViewHolder viewHolder, Object item ) {
            Log.d( TAG, "onBindDescription : enter" );

            viewHolder.getTitle().setText( ( (VideoModel) item ).title );
            viewHolder.getSubtitle().setText( ( (VideoModel) item ).studio );

            Log.d( TAG, "onBindDescription : exit" );
        }

    }

}
