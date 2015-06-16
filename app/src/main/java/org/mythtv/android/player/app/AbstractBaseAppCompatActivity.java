/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.player.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.player.app.recordings.ShowsActivity;
import org.mythtv.android.player.app.settings.SettingsActivity;
import org.mythtv.android.player.app.videos.VideosActivity;

/*
 * Created by dmfrey on 12/10/14.
 */
public abstract class AbstractBaseAppCompatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = AbstractBaseAppCompatActivity.class.getSimpleName();

    protected NavigationView navigationView;
    protected Toolbar toolbar;

    protected DrawerLayout drawerLayout;

    private BackendConnectedBroadcastReceiver mBackendConnectedBroadcastReceiver = new BackendConnectedBroadcastReceiver();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( getLayoutResource() );

        navigationView = (NavigationView) findViewById( R.id.navigation_view );

        toolbar = (Toolbar) findViewById( R.id.toolbar );

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if( null != navigationView ) {
            navigationView.setNavigationItemSelectedListener( this );
        }

        if( toolbar != null ) {
            setSupportActionBar( toolbar );

            final ActionBar actionBar = getSupportActionBar();
            if( null != actionBar ) {

                if( null != navigationView ) {

                    actionBar.setHomeAsUpIndicator( R.drawable.ic_menu_white_24dp );
                }

                actionBar.setDisplayHomeAsUpEnabled( true );
            }

        }

        IntentFilter backendConnectedIntentFilter = new IntentFilter( MainApplication.ACTION_CONNECTED );
        backendConnectedIntentFilter.addAction(MainApplication.ACTION_NOT_CONNECTED);
        registerReceiver( mBackendConnectedBroadcastReceiver, backendConnectedIntentFilter );

        if( MainApplication.getInstance().isConnected() ) {
            updateData();
        } else {
            MainApplication.getInstance().resetBackend();
        }

        TextView mythtvVersion = (TextView) findViewById( R.id.mythtv_version );
        if( null != mythtvVersion ) {
            final PackageManager packageManager = getPackageManager();
            if (packageManager != null) {

                String versionName = getResources().getString(R.string.app_version);
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
                    versionName = packageInfo.versionName;
                    mythtvVersion.setText(versionName);
                } catch (PackageManager.NameNotFoundException ignored) {
                }
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if( null != mBackendConnectedBroadcastReceiver ) {
            unregisterReceiver( mBackendConnectedBroadcastReceiver );
        }

//        MainApplication.getInstance().disconnect();
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case android.R.id.home:

                if( null != navigationView ) {

                    drawerLayout.openDrawer( GravityCompat.START );

                    return true;
                }

        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem menuItem ) {
        Log.i( TAG, "onNavigationItemSelected : menuItem=" + menuItem.getItemId() );

        menuItem.setChecked( true );
        drawerLayout.closeDrawers();

        switch( menuItem.getItemId() ) {

            case R.id.navigation_item_watch_recordings :

                Intent shows = new Intent( this, ShowsActivity.class );
                shows.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( shows );

                return true;

            case R.id.navigation_item_watch_videos :

                Intent videos = new Intent( this, VideosActivity.class );
                videos.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( videos );

                return true;

            case R.id.navigation_item_watch_settings :

                startActivity( new Intent( this, SettingsActivity.class ) );

                return true;
        }

        return false;
    }

    public void setNavigationMenuItemChecked( int index) {

        navigationView.getMenu().getItem( index ).setChecked( true );

    }

    protected abstract int getLayoutResource();

    protected abstract void updateData();

    private class BackendConnectedBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive( Context context, Intent intent ) {

            if( MainApplication.ACTION_CONNECTED.equals(intent.getAction()) ) {

                updateData();

            }

            if( MainApplication.ACTION_NOT_CONNECTED.equals( intent.getAction() ) ) {

                Toast.makeText( AbstractBaseAppCompatActivity.this, "Backend not connected", Toast.LENGTH_SHORT ).show();

            }

        }

    }

}
