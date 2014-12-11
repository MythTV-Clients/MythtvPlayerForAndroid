package org.mythtv.android.player;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by dmfrey on 12/10/14.
 */
public abstract class BaseActionBarActivity extends ActionBarActivity {

    private static final String TAG = BaseActionBarActivity.class.getSimpleName();

    private Toolbar toolbar;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( getLayoutResource() );
        toolbar = (Toolbar) findViewById( R.id.toolbar );
        if( toolbar != null ) {
            setSupportActionBar( toolbar );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }
    }

    protected abstract int getLayoutResource();

    protected void setActionBarIcon( int iconRes ) {
        toolbar.setNavigationIcon( iconRes );
    }

}
