/*
 * Copyright (C) 2016 Google Inc. All Rights Reserved.
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

package org.mythtv.android.presentation.view.fragment.phone;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIMediaController;

import org.mythtv.android.R;

/**
 *
 *
 *
 * @author dmfrey
 */
@SuppressWarnings( "PMD" )
public class ExpandedControlsFragment extends Fragment {

    private static final String TAG = ExpandedControlsFragment.class.getSimpleName();

    private TextView mSubtitleTextView;
    private UIMediaController mUIMediaController;
    private final RemoteMediaClient.Listener mPostRemoteMediaClientListener =
            new RemoteMediaClientListener();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CastSession castSession = CastContext.getSharedInstance(getContext()).getSessionManager()
                .getCurrentCastSession();
        if (castSession == null) {
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mUIMediaController = new UIMediaController(getActivity());
        mUIMediaController.setPostRemoteMediaClientListener(mPostRemoteMediaClientListener);
        View fragmentView = inflater.inflate(R.layout.cast_expanded_controls_fragment, container);
        loadAndSetupViews(fragmentView);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateSubtitle();
        updateToolbarTitle();
    }

    @Override
    public void onDestroy() {
        if (mUIMediaController != null) {
            mUIMediaController.setPostRemoteMediaClientListener(null);
            mUIMediaController.dispose();
        }
        super.onDestroy();
    }

    private void loadAndSetupViews(View rootView) {
        setUpBackgroundImage(rootView);
        setUpSeekbarControls(rootView);
        setUpTransportControls(rootView);
    }

    private void setUpBackgroundImage(View rootView) {
        ImageView backgroundImageView = (ImageView) rootView.findViewById(
                R.id.background_imageview);
        ProgressBar loading = (ProgressBar) rootView.findViewById(R.id.loading_indicator);
        mSubtitleTextView = (TextView) rootView.findViewById(R.id.subtitle_textview);

        mUIMediaController.bindImageViewToImageOfCurrentItem( backgroundImageView, -1, R.drawable.ab_transparent_democast );
        mUIMediaController.bindViewToLoadingIndicator(loading);
    }

    private void setUpSeekbarControls(View rootView) {
        TextView start = (TextView) rootView.findViewById(R.id.start_text);
        TextView end = (TextView) rootView.findViewById(R.id.end_text);
        SeekBar seekbar = (SeekBar) rootView.findViewById(R.id.seekbar);
        mUIMediaController.bindTextViewToStreamPosition(start, true);
        mUIMediaController.bindTextViewToStreamDuration(end);
        mUIMediaController.bindSeekBar(seekbar);
    }

    private void setUpTransportControls(View rootView) {
        setUpClosedCaptionButton((ImageButton) rootView.findViewById(R.id.button_image_view_1),
                mUIMediaController);
        setUpSkipPrevButton((ImageButton) rootView.findViewById(R.id.button_image_view_2),
                mUIMediaController);
        setUpPlayPauseToggleButton((ImageButton) rootView.findViewById(R.id.button_image_view_3),
                mUIMediaController);
        setUpSkipNextButton((ImageButton) rootView.findViewById(R.id.button_image_view_4),
                mUIMediaController);
        rootView.findViewById(R.id.button_image_view_5).setVisibility(View.INVISIBLE);
    }

    private void setUpPlayPauseToggleButton(ImageButton button,
                                            UIMediaController uiMediaController) {
        setButtonBackgroundResource(button);
        Drawable pauseDrawable = getResources().getDrawable( R.drawable.ic_pause_circle_white);
        Drawable playDrawable = getResources().getDrawable( R.drawable.ic_play_circle_white);
        button.setImageDrawable(playDrawable);
        uiMediaController.bindImageViewToPlayPauseToggle(button, playDrawable,
                pauseDrawable, null, null, false);
    }

    private void setButtonBackgroundResource(ImageButton button) {
        int[] attrs = new int[]{
                android.support.v7.appcompat.R.attr.selectableItemBackgroundBorderless};

        TypedArray a = getActivity().getTheme().obtainStyledAttributes(attrs);
        int selectable = a.getResourceId(0, 0);
        a.recycle();
        button.setBackgroundResource(selectable);
    }

    private void setUpSkipPrevButton(ImageButton button,
                                     UIMediaController uiMediaController) {
        setButtonBackgroundResource(button);
        Drawable skipPreviousDrawable = getResources().getDrawable(R.drawable.skip_previous_button);
        button.setImageDrawable(skipPreviousDrawable);
        uiMediaController.bindViewToSkipPrev(button, View.VISIBLE);
    }

    private void setUpSkipNextButton(ImageButton button,
                                     UIMediaController uiMediaController) {
        setButtonBackgroundResource(button);
        Drawable skipNextDrawable = getResources().getDrawable(R.drawable.skip_next_button);
        button.setImageDrawable(skipNextDrawable);
        uiMediaController.bindViewToSkipNext(button, View.VISIBLE);
    }

    private void setUpClosedCaptionButton(ImageButton button,
                                          UIMediaController uiMediaController) {
        setButtonBackgroundResource(button);
        Drawable closedCaptionDrawable = getResources().getDrawable(R.drawable.cc);
        button.setImageDrawable(closedCaptionDrawable);
        uiMediaController.bindViewToClosedCaption(button);
    }

    private class RemoteMediaClientListener implements RemoteMediaClient.Listener {

        @Override
        public void onStatusUpdated() {
            RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
            if (remoteMediaClient == null) {
                getActivity().finish();
            }
            updateSubtitle();
        }

        @Override
        public void onMetadataUpdated() {
            updateToolbarTitle();
        }

        @Override
        public void onQueueStatusUpdated() {
            Log.v( TAG, "onQueueStatusUpdated : enter" );

            Log.v( TAG, "onQueueStatusUpdated : exit" );
        }

        @Override
        public void onPreloadStatusUpdated() {
            Log.v( TAG, "onPreloadStatusUpdated : enter" );

            Log.v( TAG, "onPreloadStatusUpdated : exit" );
        }

        @Override
        public void onSendingRemoteMediaRequest() {
            mSubtitleTextView.setText(getResources().getString(R.string.loading));
        }

        @Override
        public void onAdBreakStatusUpdated() {
            Log.v( TAG, "onAdBreakStatusUpdated : enter" );

            Log.v( TAG, "onAdBreakStatusUpdated : exit" );
        }

    }

    @SuppressWarnings( "PMD.AvoidDeeplyNestedIfStmts" )
    private void updateToolbarTitle() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null && remoteMediaClient.hasMediaSession()) {
            MediaInfo mediaInfo = remoteMediaClient.getMediaInfo();
            if (mediaInfo != null) {
                MediaMetadata mediaMetadata = mediaInfo.getMetadata();
                if (mediaMetadata != null) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(
                            mediaMetadata.getString(MediaMetadata.KEY_TITLE));
                }
            }
        }
    }

    private RemoteMediaClient getRemoteMediaClient() {
        CastSession castSession =
                CastContext.getSharedInstance(getContext()).getSessionManager()
                        .getCurrentCastSession();
        return (castSession != null && castSession.isConnected())
                ? castSession.getRemoteMediaClient() : null;
    }

    @SuppressWarnings( "PMD.AvoidDeeplyNestedIfStmts" )
    private void updateSubtitle() {
        CastSession castSession =
                CastContext.getSharedInstance(getContext()).getSessionManager()
                        .getCurrentCastSession();
        if (castSession != null) {
            CastDevice castDevice = castSession.getCastDevice();
            if (castDevice != null) {
                String deviceFriendlyName = castDevice.getFriendlyName();
                if (!TextUtils.isEmpty(deviceFriendlyName)) {
                    mSubtitleTextView.setText(getResources().getString(
                            R.string.cast_casting_to_device, deviceFriendlyName));
                    return;
                }
            }
        }
        mSubtitleTextView.setText("");
    }
}