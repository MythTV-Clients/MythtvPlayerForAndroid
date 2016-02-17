package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 1/28/16.
 */
public class TvSettingsActivity extends TvAbstractBaseActivity {

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, TvSettingsActivity.class );
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_tv_settings;
    }

}
