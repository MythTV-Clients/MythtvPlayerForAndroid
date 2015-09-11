package org.mythtv.android.presentation.view.activity;

import android.os.Bundle;

import org.mythtv.android.R;

import butterknife.ButterKnife;

/**
 * Created by dmfrey on 8/31/15.
 */
public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutResource() {

        return R.layout.activity_main;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

    }

}
