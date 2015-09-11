package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 9/2/15.
 */
public class SettingsActivity extends BaseActivity {

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, SettingsActivity.class );
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setNavigationMenuItemChecked( 2 );

    }

}
