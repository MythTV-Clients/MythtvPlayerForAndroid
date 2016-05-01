package org.mythtv.android.app.view.activity;

import android.content.Context;
import android.content.Intent;

import org.mythtv.android.app.R;

/**
 * Created by dmfrey on 9/2/15.
 */
public class SettingsActivity extends AbstractBaseActivity {

    public static Intent getCallingIntent( Context context ) {

        Intent callingIntent = new Intent( context, SettingsActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_settings;
    }

    @Override
    protected void onResume() {
        super.onResume();

        setNavigationMenuItemChecked( 3 );

    }

}
