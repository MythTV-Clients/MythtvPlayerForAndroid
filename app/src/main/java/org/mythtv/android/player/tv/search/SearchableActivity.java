/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.player.tv.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 3/20/15.
 */
public class SearchableActivity extends Activity {

    private static final String TAG = SearchableActivity.class.getSimpleName();

    private static final int REQUEST_SPEECH = 1;
    private SearchableFragment mSearchableFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_tv_search );

        mSearchableFragment = (SearchableFragment) getFragmentManager().findFragmentById( R.id.search_fragment );

        SpeechRecognitionCallback mSpeechRecognitionCallback = new SpeechRecognitionCallback() {

            @Override
            public void recognizeSpeech() {

                startActivityForResult( mSearchableFragment.getRecognizerIntent(), REQUEST_SPEECH );

            }

        };
        mSearchableFragment.setSpeechRecognitionCallback( mSpeechRecognitionCallback );

    }

}
