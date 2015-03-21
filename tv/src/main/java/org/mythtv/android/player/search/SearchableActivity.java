package org.mythtv.android.player.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.util.Log;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 3/20/15.
 */
public class SearchableActivity extends Activity {

    private static final String TAG = SearchableActivity.class.getSimpleName();

    private static final int REQUEST_SPEECH = 1;
    private SearchableFragment mSearchableFragment;
    private SpeechRecognitionCallback mSpeechRecognitionCallback;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_search );

        mSearchableFragment = (SearchableFragment) getFragmentManager().findFragmentById( R.id.search_fragment );

        mSpeechRecognitionCallback = new SpeechRecognitionCallback() {

            @Override
            public void recognizeSpeech() {

                startActivityForResult( mSearchableFragment.getRecognizerIntent(), REQUEST_SPEECH );

            }

        };
        mSearchableFragment.setSpeechRecognitionCallback( mSpeechRecognitionCallback );

    }

}
