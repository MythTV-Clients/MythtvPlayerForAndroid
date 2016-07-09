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

package org.mythtv.android.presentation.view.activity.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import org.mythtv.android.presentation.R;
import org.mythtv.android.presentation.view.fragment.phone.ProgramListFragment;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.internal.di.modules.ProgramsModule;
import org.mythtv.android.presentation.model.ProgramModel;

/**
 * Activity that shows a list of programs.
 *
 * Created by dmfrey on 9/1/15.
 */
public class ProgramListActivity extends AbstractBasePhoneActivity implements HasComponent<DvrComponent>, ProgramListFragment.ProgramListListener {

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

    public static Intent getCallingIntent( Context context, boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup  ) {

        Intent callingIntent = new Intent( context, ProgramListActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_DESCENDING, descending );

        if( startIndex > -1 ) {

            callingIntent.putExtra( INTENT_EXTRA_PARAM_START_INDEX, startIndex );

        }

        if( count > -1 ) {

            callingIntent.putExtra( INTENT_EXTRA_PARAM_COUNT, count );

        }

        if( null != titleRegEx ) {

            callingIntent.putExtra( INTENT_EXTRA_PARAM_TITLE_REG_EX, titleRegEx );

        }

        if( null != recGroup && !"".equals( recGroup ) ) {

            callingIntent.putExtra( INTENT_EXTRA_PARAM_REC_GROUP, recGroup );

        }

        if( null != storageGroup && !"".equals( storageGroup ) ) {

            callingIntent.putExtra( INTENT_EXTRA_PARAM_STORAGE_GROUP, storageGroup );

        }

        return callingIntent;
    }

    private boolean descending = true;
    private int startIndex = -1, count = -1;
    private String titleRegEx = null, recGroup = null, storageGroup = null;
    private DvrComponent dvrComponent;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_program_list;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
        super.onCreate( savedInstanceState );

        this.initializeActivity( savedInstanceState );
        this.initializeInjector();

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            outState.putBoolean( INSTANCE_STATE_PARAM_DESCENDING, this.descending );

            if( this.startIndex > -1 ) {
                Log.d( TAG, "onSaveInstanceState : saving startIndex" );

                outState.putInt( INSTANCE_STATE_PARAM_START_INDEX, this.startIndex );

            }

            if( this.count > -1 ) {
                Log.d( TAG, "onSaveInstanceState : saving count" );

                outState.putInt( INSTANCE_STATE_PARAM_COUNT, this.count );

            }

            if( null != this.titleRegEx && !"".equals( titleRegEx ) ) {
                Log.d( TAG, "onSaveInstanceState : saving titleRegEx" );

                outState.putString( INSTANCE_STATE_PARAM_TITLE_REG_EX, this.titleRegEx );

            }

            if( null != this.recGroup && !"".equals( this.recGroup ) ) {
                Log.d( TAG, "onSaveInstanceState : saving recGroup" );

                outState.putString( INSTANCE_STATE_PARAM_REC_GROUP, this.recGroup );

            }

            if( null != this.storageGroup && !"".equals( this.storageGroup ) ) {
                Log.d( TAG, "onSaveInstanceState : saving storageGroup" );

                outState.putString( INSTANCE_STATE_PARAM_STORAGE_GROUP, this.storageGroup );

            }

        }

        super.onSaveInstanceState( outState );

        Log.d( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );
        Log.d( TAG, "onRestoreInstanceState : enter" );

        if( null != savedInstanceState ) {
            Log.d( TAG, "onRestoreInstanceState : savedInstanceState != null" );

            this.descending = savedInstanceState.getBoolean( INSTANCE_STATE_PARAM_DESCENDING, true );
            Log.d( TAG, "onRestoreInstanceState : restored descending=" + this.descending );

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_START_INDEX ) ) {
                Log.d( TAG, "onRestoreInstanceState : restoring startIndex" );

                this.startIndex = savedInstanceState.getInt( INSTANCE_STATE_PARAM_START_INDEX );
                Log.d( TAG, "onRestoreInstanceState : restored startIndex=" + this.startIndex );

            }

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_COUNT ) ) {
                Log.d( TAG, "onRestoreInstanceState : restoring count" );

                this.count = savedInstanceState.getInt( INSTANCE_STATE_PARAM_COUNT );
                Log.d( TAG, "onRestoreInstanceState : restored count=" + this.count );

            }

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_TITLE_REG_EX ) ) {
                Log.d( TAG, "onRestoreInstanceState : restoring titleRegEx" );

                this.titleRegEx = savedInstanceState.getString( INSTANCE_STATE_PARAM_TITLE_REG_EX );
                Log.d( TAG, "onRestoreInstanceState : restored titleRegEx=" + this.titleRegEx );

            }

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_REC_GROUP ) ) {
                Log.d( TAG, "onRestoreInstanceState : restoring recGroup" );

                this.recGroup = savedInstanceState.getString( INSTANCE_STATE_PARAM_REC_GROUP );
                Log.d( TAG, "onRestoreInstanceState : restored recGroup=" + this.recGroup );

            }

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_STORAGE_GROUP ) ) {
                Log.d( TAG, "onRestoreInstanceState : restoring storageGroup" );

                this.storageGroup = savedInstanceState.getString( INSTANCE_STATE_PARAM_STORAGE_GROUP );
                Log.d( TAG, "onRestoreInstanceState : restored storageGroup=" + this.storageGroup );

            }

        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        if( null == savedInstanceState ) {
            Log.d( TAG, "initializeActivity : savedInstanceState == null" );

            Bundle extras = getIntent().getExtras();
            if( null != extras ) {

                this.descending = extras.getBoolean( INTENT_EXTRA_PARAM_DESCENDING, true );
                Log.d( TAG, "initializeActivity : restored descending=" + this.descending + ", from extras" );

                if( extras.containsKey( INTENT_EXTRA_PARAM_START_INDEX ) ) {

                    this.startIndex = extras.getInt( INTENT_EXTRA_PARAM_START_INDEX );
                    Log.d( TAG, "initializeActivity : restored startIndex=" + this.startIndex + ", from extras" );

                }

                if( extras.containsKey( INTENT_EXTRA_PARAM_COUNT ) ) {

                    this.count = extras.getInt( INTENT_EXTRA_PARAM_COUNT );
                    Log.d( TAG, "initializeActivity : restored count=" + this.count + ", from extras" );

                }

                if( extras.containsKey( INTENT_EXTRA_PARAM_TITLE_REG_EX ) ) {

                    this.titleRegEx = extras.getString( INTENT_EXTRA_PARAM_TITLE_REG_EX );
                    Log.d( TAG, "initializeActivity : restored titleRegEx=" + this.titleRegEx + ", from extras" );

                }

                if( extras.containsKey( INTENT_EXTRA_PARAM_REC_GROUP ) ) {

                    this.recGroup = extras.getString( INTENT_EXTRA_PARAM_REC_GROUP );
                    Log.d( TAG, "initializeActivity : restored recGroup=" + this.recGroup + ", from extras" );

                }

                if( extras.containsKey( INTENT_EXTRA_PARAM_STORAGE_GROUP ) ) {

                    this.storageGroup = extras.getString( INTENT_EXTRA_PARAM_STORAGE_GROUP );
                    Log.d( TAG, "initializeActivity : restored storageGroup=" + this.storageGroup + ", from extras" );

                }

            }

            Log.d( TAG, "initializeActivity : descending=" + descending + ", startIndex=" + startIndex + ", count=" + count + ", titleRegEx=" + titleRegEx + ", recGroup=" + recGroup + ", storageGroup=" + storageGroup );
            addFragment( R.id.fl_fragment, ProgramListFragment.newInstance() );

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState != null" );

            this.descending = savedInstanceState.getBoolean( INSTANCE_STATE_PARAM_DESCENDING );
            Log.d( TAG, "initializeActivity : restored descending=" + this.descending + ", from savedInstanceState" );

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_START_INDEX ) ) {

                this.startIndex = savedInstanceState.getInt( INSTANCE_STATE_PARAM_START_INDEX );
                Log.d( TAG, "initializeActivity : restored startIndex=" + this.startIndex + ", from savedInstanceState" );

            }

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_COUNT ) ) {

                this.count = savedInstanceState.getInt( INSTANCE_STATE_PARAM_COUNT );
                Log.d( TAG, "initializeActivity : restored count=" + this.count + ", from savedInstanceState" );

            }

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_TITLE_REG_EX ) ) {

                this.titleRegEx = savedInstanceState.getString( INSTANCE_STATE_PARAM_TITLE_REG_EX );
                Log.d( TAG, "initializeActivity : restored titleRegEx=" + this.titleRegEx + ", from savedInstanceState" );

            }

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_REC_GROUP ) ) {

                this.recGroup = savedInstanceState.getString( INSTANCE_STATE_PARAM_REC_GROUP );
                Log.d( TAG, "initializeActivity : restored recGroup=" + this.recGroup + ", from savedInstanceState" );

            }

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_STORAGE_GROUP ) ) {

                this.storageGroup = savedInstanceState.getString( INSTANCE_STATE_PARAM_STORAGE_GROUP );
                Log.d( TAG, "initializeActivity : restored storageGroup=" + this.storageGroup + ", from savedInstanceState" );

            }

        }

        if( null != titleRegEx && !"".equals( titleRegEx ) ) {
            Log.d( TAG, "initializeActivity : setting toolbar title to '" + titleRegEx + "'" );

            getSupportActionBar().setTitle( titleRegEx );

        }

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.dvrComponent = DaggerDvrComponent.builder()
                .applicationComponent( getApplicationComponent() )
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

        Log.i( TAG, "onProgramClicked : programModel=" + programModel.toString() );
        navigator.navigateToProgram( this, programModel.getChannel().getChanId(), programModel.getRecording().getStartTs() );

        Log.d( TAG, "onProgramClicked : exit" );
    }

}
