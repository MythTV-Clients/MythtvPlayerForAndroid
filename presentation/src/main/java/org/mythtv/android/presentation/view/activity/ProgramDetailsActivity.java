package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.internal.di.modules.ProgramModule;
import org.mythtv.android.presentation.view.fragment.ProgramDetailsFragment;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by dmfrey on 9/30/15.
 */
public class ProgramDetailsActivity extends BaseActivity implements HasComponent<DvrComponent> {

    private static final String TAG = ProgramDetailsActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_CHAN_ID = "org.mythtv.android.INTENT_PARAM_CHAN_ID";
    private static final String INTENT_EXTRA_PARAM_START_TIME = "org.mythtv.android.INTENT_PARAM_START_TIME";
    private static final String INSTANCE_STATE_PARAM_CHAN_ID = "org.mythtv.android.STATE_PARAM_CHAN_ID";
    private static final String INSTANCE_STATE_PARAM_START_TIME = "org.mythtv.android.STATE_PARAM_START_TIME";

    private int chanId;
    private DateTime startTime;
    private DvrComponent dvrComponent;

    @Bind( R.id.backdrop )
    ImageView backdrop;

    @BindDimen( R.dimen.detail_backdrop_height )
    int backdropHeight;

    public static Intent getCallingIntent( Context context, int chanId, DateTime startTime ) {

        Intent callingIntent = new Intent( context, ProgramDetailsActivity.class );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_CHAN_ID, chanId );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_START_TIME, startTime.getMillis() );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_program_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        ButterKnife.bind( this );

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    public boolean onNavigateUp() {
        Log.d(TAG, "onNavigateUp : enter");

        onBackPressed();

        Log.d( TAG, "onNavigateUp : exit" );
        return true;
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d(TAG, "onSaveInstanceState : enter");

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            outState.putInt( INSTANCE_STATE_PARAM_CHAN_ID, this.chanId );
            outState.putLong( INSTANCE_STATE_PARAM_START_TIME, this.startTime.getMillis() );

        }

        super.onSaveInstanceState(outState);

        Log.d( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        Log.d( TAG, "onRestoreInstanceState : enter" );
        super.onRestoreInstanceState(savedInstanceState);

        if( null != savedInstanceState ) {

            this.chanId = savedInstanceState.getInt( INSTANCE_STATE_PARAM_CHAN_ID );
            this.startTime = new DateTime( savedInstanceState.getLong( INSTANCE_STATE_PARAM_START_TIME ) );

        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d(TAG, "initializeActivity : enter");

        if( null == savedInstanceState  ) {
            Log.d( TAG, "initializeActivity : savedInstanceState is null" );

            this.chanId = getIntent().getIntExtra( INTENT_EXTRA_PARAM_CHAN_ID, -1 );
            this.startTime = new DateTime( getIntent().getLongExtra( INTENT_EXTRA_PARAM_START_TIME, -1 ) );
            addFragment( R.id.fl_fragment, ProgramDetailsFragment.newInstance( this.chanId, this.startTime ) );

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.chanId = savedInstanceState.getInt( INSTANCE_STATE_PARAM_CHAN_ID );
            this.startTime = new DateTime( getIntent().getLongExtra(INSTANCE_STATE_PARAM_START_TIME, -1) );

        }

        loadBackdrop();

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {

        this.dvrComponent = DaggerDvrComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .programModule( new ProgramModule( this.chanId, this.startTime ) )
                .build();

    }

    @Override
    public DvrComponent getComponent() {

        return dvrComponent;
    }

    private void loadBackdrop() {

        String previewUrl = getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + this.chanId + "&StartTime=" + this.startTime.withZone(DateTimeZone.UTC).toString("yyyy-MM-dd'T'HH:mm:ss") + "&Height=" + backdropHeight;
        Log.i( TAG, "loadBackdrop : previewUrl=" + previewUrl );
        final ImageView imageView = (ImageView) findViewById( R.id.backdrop );
        Picasso.with( this )
                .load( previewUrl )
                .fit().centerCrop()
                .into( imageView );

    }

}
