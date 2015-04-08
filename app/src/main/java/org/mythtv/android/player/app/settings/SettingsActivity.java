package org.mythtv.android.player.app.settings;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import org.mythtv.android.R;
import org.mythtv.android.player.app.AbstractBaseActionBarActivity;
import org.mythtv.android.player.app.NavigationDrawerFragment;

public class SettingsActivity extends AbstractBaseActionBarActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private NavigationDrawerFragment mDrawerFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_settings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        mDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById( R.id.fragment_navigation_drawer );
        mDrawerFragment.setUp( R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar );

        setTitle( getResources().getString( R.string.drawer_item_preferences ) );

    }

    @Override
    protected void onResume() {
        super.onResume();

        mDrawerFragment.setSelectItem( 2 );

    }

    @Override
    protected void updateData() {

    }

}
