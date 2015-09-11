package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.internal.di.modules.ProgramsModule;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.view.fragment.ProgramListFragment;

/**
 * Activity that shows a list of programs.
 *
 * Created by dmfrey on 9/1/15.
 */
public class ProgramListActivity extends BaseActivity implements HasComponent<DvrComponent>, ProgramListFragment.ProgramListListener {

    private static final String TAG = ProgramListActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_DESCENDING = "org.mythtv.android.INTENT_PARAM_DESCENDING";
    private static final String INSTANCE_STATE_PARAM_DESCENDING = "org.mythtv.android.STATE_PARAM_DESCENDING";

    private static final String INTENT_EXTRA_PARAM_START_INDEX = "org.mythtv.android.INTENT_PARAM_START_INDEX";
    private static final String INSTANCE_STATE_PARAM_START_INDEX = "org.mythtv.android.STATE_PARAM_START_INDEX";

    private static final String INTENT_EXTRA_PARAM_COUNT = "org.mythtv.android.INTENT_PARAM_COUNT";
    private static final String INSTANCE_STATE_PARAM_COUNT = "org.mythtv.android.STATE_PARAM_COUNT";

    private static final String INTENT_EXTRA_PARAM_TITLE_REG_EX = "org.mythtv.android.INTENT_PARAM_TITLE_REG_EX";
    private static final String INSTANCE_STATE_PARAM_TITLE_REG_EX = "org.mythtv.android.STATE_PARAM_TITLE_REG_EX";

    private static final String INTENT_EXTRA_PARAM_REC_GROUP = "org.mythtv.android.INTENT_PARAM_REC_GROUP";
    private static final String INSTANCE_STATE_PARAM_REC_GROUP = "org.mythtv.android.STATE_PARAM_REC_GROUP";

    private static final String INTENT_EXTRA_PARAM_STORAGE_GROUP = "org.mythtv.android.INTENT_PARAM_STORAGE_GROUP";
    private static final String INSTANCE_STATE_PARAM_STORAGE_GROUP = "org.mythtv.android.STATE_PARAM_STORAGE_GROUP";

    public static Intent getCallingIntent( Context context, String title ) {

        Intent callingIntent = new Intent( context, ProgramListActivity.class );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_DESCENDING, true );
//        callingIntent.putExtra( INTENT_EXTRA_PARAM_START_INDEX, -1 );
//        callingIntent.putExtra( INTENT_EXTRA_PARAM_COUNT, -1 );

        if( null != title ) {

            callingIntent.putExtra( INTENT_EXTRA_PARAM_TITLE_REG_EX, title );

        }

//        callingIntent.putExtra( INTENT_EXTRA_PARAM_REC_GROUP, "" );
//        callingIntent.putExtra( INTENT_EXTRA_PARAM_STORAGE_GROUP, "" );

        return callingIntent;
    }

    private boolean descending = true;
    private int startIndex = -1, count = -1;
    private String titleRegEx = null, recGroup = null, storageGroup = null;
    private DvrComponent dvrComponent;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_program_list;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
        super.onCreate( savedInstanceState );

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();

        Log.d(TAG, "onCreate : exit");
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        if( null != outState ) {

            outState.putBoolean( INSTANCE_STATE_PARAM_DESCENDING, this.descending );
//            outState.putInt( INSTANCE_STATE_PARAM_START_INDEX, this.startIndex );
//            outState.putInt( INSTANCE_STATE_PARAM_COUNT, this.count );

            if( null != this.titleRegEx ) {

                outState.putString( INSTANCE_STATE_PARAM_TITLE_REG_EX, this.titleRegEx );

            }

//            outState.putString( INSTANCE_STATE_PARAM_REC_GROUP, this.recGroup );
//            outState.putString( INSTANCE_STATE_PARAM_STORAGE_GROUP, this.storageGroup );

        }

        super.onSaveInstanceState( outState );

        Log.d(TAG, "onSaveInstanceState : exit");
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        if( null == savedInstanceState ) {

            this.descending = getIntent().getBooleanExtra( INTENT_EXTRA_PARAM_DESCENDING, true  );
            this.titleRegEx = getIntent().getStringExtra( INSTANCE_STATE_PARAM_TITLE_REG_EX );
            addFragment(R.id.fl_fragment, ProgramListFragment.newInstance( this.descending, this.startIndex, this.count, this.titleRegEx, this.recGroup, this.storageGroup ) );

        } else {

            this.descending = savedInstanceState.getBoolean( INSTANCE_STATE_PARAM_DESCENDING );

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_TITLE_REG_EX ) ) {

                this.titleRegEx = savedInstanceState.getString( INSTANCE_STATE_PARAM_TITLE_REG_EX );

            }

        }

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.dvrComponent = DaggerDvrComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .programsModule( new ProgramsModule( descending, startIndex, count, titleRegEx, recGroup, storageGroup ) )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public DvrComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return dvrComponent;
    }

    @Override
    public void onProgramClicked( ProgramModel programModel ) {
        Log.d( TAG, "onProgramClicked : enter" );

    //    navigator.navigateToProgram( this, programModel.getChannel().getChanId(), programModel.getRecording().getStartTs() );

        Log.d( TAG, "onProgramClicked : exit" );
    }

}
