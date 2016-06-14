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

package org.mythtv.android.view.activity.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;

import org.mythtv.android.R;
import org.mythtv.android.internal.di.HasComponent;
import org.mythtv.android.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.internal.di.components.DvrComponent;
import org.mythtv.android.view.fragment.phone.EncoderListFragment;
import org.mythtv.android.view.fragment.phone.RecentListFragment;
import org.mythtv.android.view.fragment.phone.UpcomingListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by dmfrey on 8/31/15.
 */
public class MainPhoneActivity extends AbstractBasePhoneActivity implements HasComponent<DvrComponent> {

    private static final String TAG = MainPhoneActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        Intent callingIntent = new Intent( context, MainPhoneActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );

        return callingIntent;
    }

    private DvrComponent dvrComponent;

    @Bind( R.id.tabs )
    TabLayout mTabLayout;

    @Bind( R.id.pager )
    ViewPager mPager;

    @Bind( R.id.fab )
    FloatingActionButton mFab;

    private MainFragmentPagerAdapter mPagerAdapter;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_main;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        Log.i( TAG, "Branch: " + getResources().getString( R.string.branchName ) + ", Tag: " + getResources().getString( R.string.tagName ) + ", Commit: " + getResources().getString( R.string.shaName ) );


        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
        super.onCreate( savedInstanceState );

        this.initializeInjector();

        mPagerAdapter = new MainFragmentPagerAdapter( getSupportFragmentManager() );

        mTabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );
        mPager.setAdapter( mPagerAdapter );
        mPager.setOffscreenPageLimit( 1 );
        mTabLayout.setupWithViewPager( mPager );
        mPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( mTabLayout ) );

        mFab.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                switch( mPager.getCurrentItem() ) {

                    case 0 :

                        if( null != mPagerAdapter.getItem( 0 ) ) {

                            ( (RecentListFragment) mPagerAdapter.getItem( 0 ) ).reload();

                        }

                        break;

                    case 1 :

                        if( null != mPagerAdapter.getItem( 1 ) ) {

                            ( (EncoderListFragment) mPagerAdapter.getItem( 1 ) ).reload();

                        }

                        break;

                    case 2 :

                        if( null != mPagerAdapter.getItem( 2 ) ) {

                            ( (UpcomingListFragment) mPagerAdapter.getItem( 2 ) ).reload();

                        }

                        break;

                }

            }

        });

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onResume() {
        super.onResume();

        if( getMasterBackendUrl().equals( "http://" + getResources().getString( R.string.pref_backend_url ) + ":" + getResources().getString( R.string.pref_backend_port ) ) ) {
            Log.i( TAG, "onResume : MasterBackend not set, redirecting to Settings" );

            navigator.navigateToSettings( this );

        }

        setNavigationMenuItemChecked( 0 );

    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.dvrComponent = DaggerDvrComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public DvrComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return dvrComponent;
    }

    class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {

        String[] tabs;
        List<Fragment> fragments = new ArrayList<>();

        public MainFragmentPagerAdapter( FragmentManager fm ) {
            super( fm );

            tabs = getResources().getStringArray( R.array.main_tabs );
            fragments.add( Fragment.instantiate( MainPhoneActivity.this, RecentListFragment.class.getName(), null ) );
            fragments.add( Fragment.instantiate( MainPhoneActivity.this, EncoderListFragment.class.getName(), null ) );
            fragments.add( Fragment.instantiate( MainPhoneActivity.this, UpcomingListFragment.class.getName(), null ) );

            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem( int position ) {
            Log.v( TAG, "getItem : position=" + position );

            return fragments.get( position );
        }

        @Override
        public CharSequence getPageTitle( int position ) {

            return tabs[ position ];
        }

        @Override
        public int getCount() {

            return 3;
        }

    }

}
