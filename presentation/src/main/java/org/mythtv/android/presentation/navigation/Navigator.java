package org.mythtv.android.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.presentation.view.activity.ProgramListActivity;
import org.mythtv.android.presentation.view.activity.SettingsActivity;
import org.mythtv.android.presentation.view.activity.TitleInfoListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 *
 * Created by dmfrey on 8/30/15.
 */
@Singleton
public class Navigator {

    private static final String TAG = Navigator.class.getSimpleName();

    @Inject
    public void Navigator() {
        //empty
    }

    public void navigateToTitleInfos( Context context ) {
        Log.i( TAG, "navigateToTitleInfos : enter" );

        if( null != context ) {
            Log.i( TAG, "navigateToTitleInfos : context != null" );

            Intent intentToLaunch = TitleInfoListActivity.getCallingIntent(context);
            context.startActivity( intentToLaunch );

        }

        Log.i( TAG, "navigateToTitleInfos : exit" );
    }

    public void navigateToPrograms( Context context, boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {
        Log.i( TAG, "navigateToPrograms : enter" );

        if( null != context ) {
            Log.i( TAG, "navigateToPrograms : context != null" );

            Intent intentToLaunch = ProgramListActivity.getCallingIntent( context, descending, startIndex, count, titleRegEx, recGroup, storageGroup );
            context.startActivity( intentToLaunch );

        }

        Log.i( TAG, "navigateToPrograms : exit" );
    }

    public void navigateToProgram( Context context, int chanId, DateTime startTime ) {
        Log.i( TAG, "navigateToProgram : enter" );

        if( null != context ) {
            Log.i(TAG, "navigateToProgram : context != null");

        }

        Log.i( TAG, "navigateToProgram : exit" );
    }

    public void navigateToSettings( Context context ) {
        Log.i( TAG, "navigateToSettings : enter" );

        if( null != context ) {
            Log.i( TAG, "navigateToSettings : context != null" );

            Intent intentToLaunch = SettingsActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);

        }

        Log.i( TAG, "navigateToSettings : exit" );
    }

}
