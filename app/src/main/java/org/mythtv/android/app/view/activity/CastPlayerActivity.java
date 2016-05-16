package org.mythtv.android.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.mythtv.android.app.R;
import org.mythtv.android.presentation.model.ProgramModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dmfrey on 9/30/15.
 */
public class CastPlayerActivity extends AbstractBaseActivity {

    private static final String TAG = CastPlayerActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_PROGRAM_MODEL = "org.mythtv.android.INTENT_PARAM_PROGRAM_MODEL";
    private static final String INSTANCE_STATE_PARAM_PROGRAM_MODEL = "org.mythtv.android.STATE_PARAM_PROGRAM_MODEL";

    private ProgramModel programModel;

    @Bind( R.id.backdrop )
    ImageView backdrop;

    @Bind( R.id.fab )
    FloatingActionButton fab;

    public static Intent getCallingIntent( Context context, ProgramModel programModel ) {

        Intent callingIntent = new Intent( context, CastPlayerActivity.class );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_PROGRAM_MODEL, programModel );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_cast_player;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        ButterKnife.bind( this );

        this.initializeActivity( savedInstanceState );

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    public boolean onNavigateUp() {
        Log.d( TAG, "onNavigateUp : enter" );

        onBackPressed();

        Log.d( TAG, "onNavigateUp : exit" );
        return true;
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            outState.putSerializable( INSTANCE_STATE_PARAM_PROGRAM_MODEL, this.programModel );

        }

        super.onSaveInstanceState( outState );

        Log.d( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        Log.d( TAG, "onRestoreInstanceState : enter" );
        super.onRestoreInstanceState( savedInstanceState );

        if( null != savedInstanceState ) {
            Log.d( TAG, "onRestoreInstanceState : savedInstanceState != null" );

            this.programModel = (ProgramModel) savedInstanceState.getSerializable( INSTANCE_STATE_PARAM_PROGRAM_MODEL );

            Log.d( TAG, "onRestoreInstanceState : programModel=" + programModel );
        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        if( null == savedInstanceState  ) {
            Log.d( TAG, "initializeActivity : savedInstanceState is null" );

            Bundle extras = getIntent().getExtras();
            if( null != extras ) {
                Log.d( TAG, "initializeActivity : extras != null" );

                if( extras.containsKey( INTENT_EXTRA_PARAM_PROGRAM_MODEL ) ) {

                    this.programModel = (ProgramModel) getIntent().getSerializableExtra( INTENT_EXTRA_PARAM_PROGRAM_MODEL );

                }

            }

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.programModel = (ProgramModel) savedInstanceState.getSerializable( INSTANCE_STATE_PARAM_PROGRAM_MODEL );

        }

        loadBackdrop();

        fab.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

            }

        });

        Log.d( TAG, "initializeActivity : exit" );
    }

//    public void onPlayRecording( ProgramModel programModel ) {
//        Log.d( TAG, "onPlayRecording : enter" );
//
//            try {
//
//                String recordingUrl = getSharedPreferencesModule().getMasterBackendUrl() + URLEncoder.encode( programModel.getLiveStreamInfo().getRelativeUrl(), "UTF-8" );
//                recordingUrl = recordingUrl.replaceAll( "%2F", "/" );
//                recordingUrl = recordingUrl.replaceAll( "\\+", "%20" );
//
//                navigator.navigateToVideoPlayer( this, recordingUrl );
//
//            } catch( UnsupportedEncodingException e ) { }
//
//        Log.d( TAG, "onPlayRecording : exit" );
//    }

    private void loadBackdrop() {
        Log.d( TAG, "loadBackdrop : enter" );

        String previewUrl = getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + programModel.getInetref() + "&Type=banner";
        Log.i( TAG, "loadBackdrop : previewUrl=" + previewUrl );
        Picasso.with( this )
                .load( previewUrl )
                .fit().centerCrop()
                .into( backdrop );

        Log.d( TAG, "loadBackdrop : exit" );
    }

}
