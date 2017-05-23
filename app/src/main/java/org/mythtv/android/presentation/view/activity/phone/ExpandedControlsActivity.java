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

package org.mythtv.android.presentation.view.activity.phone;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionManagerListener;

import org.mythtv.android.R;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

/**
 *
 *
 * @author dmfrey
 */
public class ExpandedControlsActivity extends AppCompatActivity {

    private static final String TAG = "ExpandedControlsActvty";

    private final SessionManagerListener mSessionManagerListener = new SessionManagerListenerImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CastContext.getSharedInstance(this).registerLifecycleCallbacksBeforeIceCreamSandwich(this,
                savedInstanceState);
        setContentView(R.layout.cast_expanded_controls);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        CastContext.getSharedInstance(this).getSessionManager().addSessionManagerListener(
                mSessionManagerListener);
        CastSession castSession =
                CastContext.getSharedInstance(this).getSessionManager().getCurrentCastSession();
        if ((castSession == null) || (!castSession.isConnected() && !castSession.isConnecting())) {
            finish();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        CastContext.getSharedInstance(this).getSessionManager().removeSessionManagerListener(
                mSessionManagerListener);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        CastButtonFactory.setUpMediaRouteButton(this, menu, R.id.media_route_menu_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
//        Intent intent;
//        if (item.getItemId() == R.id.action_settings) {
//            intent = new Intent(ExpandedControlsActivity.this, CastPreference.class);
//            startActivity(intent);
//        }
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setImmersive();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setImmersive() {

        int newUiOptions = getWindow().getDecorView().getSystemUiVisibility();

        // Navigation bar hiding:  Backwards compatible to ICS.
        newUiOptions ^= SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        // Status bar hiding: Backwards compatible to Jellybean
        newUiOptions ^= SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        getWindow().getDecorView().setSystemUiVisibility( newUiOptions );
        setImmersive( true );

    }

    private class SessionManagerListenerImpl implements SessionManagerListener {

        @Override
        public void onSessionEnded(Session session, int error) {
            Log.v( TAG, "onSessionEnded : enter" );

            finish();

            Log.v( TAG, "onSessionEnded : exit" );
        }

        @Override
        public void onSessionStarting(Session session) {
            Log.v( TAG, "onSessionStarting : enter" );

            Log.v( TAG, "onSessionStarting : exit" );
        }

        @Override
        public void onSessionStarted(Session session, String sessionId) {
            Log.v( TAG, "onSessionStarted : enter" );

            Log.v( TAG, "onSessionStarted : exit" );
        }

        @Override
        public void onSessionStartFailed(Session session, int error) {
            Log.v( TAG, "onSessionStartFailed : enter" );

            Log.v( TAG, "onSessionStartFailed : exit" );
        }

        @Override
        public void onSessionEnding(Session session) {
            Log.v( TAG, "onSessionEnding : enter" );

            Log.v( TAG, "onSessionEnding : exit" );
        }

        @Override
        public void onSessionResuming(Session session, String sessionId) {
            Log.v( TAG, "onSessionResuming : enter" );

            Log.v( TAG, "onSessionResuming : exit" );
        }

        @Override
        public void onSessionResumed(Session session, boolean wasSuspended) {
            Log.v( TAG, "onSessionResumed : enter" );

            Log.v( TAG, "onSessionResumed : exit" );
        }

        @Override
        public void onSessionResumeFailed(Session session, int error) {
            Log.v( TAG, "onSessionResumeFailed : enter" );

            Log.v( TAG, "onSessionResumeFailed : exit" );
        }

        @Override
        public void onSessionSuspended(Session session, int reason) {
            Log.v( TAG, "onSessionSuspended : enter" );

            Log.v( TAG, "onSessionSuspended : exit" );
        }
    }

}