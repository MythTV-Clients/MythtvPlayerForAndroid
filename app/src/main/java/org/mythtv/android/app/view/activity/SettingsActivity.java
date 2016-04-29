package org.mythtv.android.app.view.activity;

import android.content.Context;
import android.content.Intent;

import org.mythtv.android.app.R;

/**
 * Created by dmfrey on 9/2/15.
 */
public class SettingsActivity extends AbstractBaseActivity {

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, SettingsActivity.class );
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
