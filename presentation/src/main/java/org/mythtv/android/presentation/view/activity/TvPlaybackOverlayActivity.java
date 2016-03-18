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

package org.mythtv.android.presentation.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import org.mythtv.android.R;

/**
 * PlaybackOverlayActivity for video playback that loads PlaybackOverlayFragment and handles
 * the MediaSession object used to maintain the state of the media playback.
 */
@TargetApi( Build.VERSION_CODES.LOLLIPOP )
public class TvPlaybackOverlayActivity extends Activity {

    private static final String TAG = TvPlaybackOverlayActivity.class.getSimpleName();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.d( TAG, "onCreate" );

        setContentView( R.layout.tv_playback_controls );

    }

    @Override
    protected void onStop() {
        super.onStop();

        getMediaController().getTransportControls().pause();

    }

    @Override
    public void onVisibleBehindCanceled() {
        super.onVisibleBehindCanceled();

        getMediaController().getTransportControls().pause();

    }

    @Override
    public boolean onSearchRequested() {

//        startActivity( new Intent( this, SearchActivity.class ) );

        return true;
    }

    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event ) {

        if( keyCode == KeyEvent.KEYCODE_BUTTON_R1 ) {

            getMediaController().getTransportControls().skipToNext();

            return true;

        } else if( keyCode == KeyEvent.KEYCODE_BUTTON_L1 ) {

            getMediaController().getTransportControls().skipToPrevious();

            return true;
        }

        return super.onKeyDown( keyCode, event );
    }

}
