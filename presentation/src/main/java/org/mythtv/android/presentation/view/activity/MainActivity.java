package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.view.fragment.EncoderListFragment;
import org.mythtv.android.presentation.view.fragment.RecentListFragment;
import org.mythtv.android.presentation.view.fragment.UpcomingListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by dmfrey on 8/31/15.
 */
public class MainActivity extends BaseActivity implements HasComponent<DvrComponent> {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static Intent getCallingIntent(Context context ) {

        return new Intent( context, MainActivity.class );
    }

    private DvrComponent dvrComponent;

    @Bind( R.id.tabs )
    TabLayout mTabLayout;

    @Bind( R.id.pager )
    ViewPager mPager;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_main;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
        super.onCreate( savedInstanceState );

        this.initializeInjector();

        setNavigationMenuItemChecked( 1 );

        mTabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );
        mPager.setAdapter( new MainFragmentPagerAdapter( getSupportFragmentManager() ) );
        mPager.setOffscreenPageLimit( 1 );
        mTabLayout.setupWithViewPager( mPager );
        mPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( mTabLayout ) );

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    public void onBackPressed() {

        if( mPager.getCurrentItem() == 0 ) {

            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();

        } else {

            // Otherwise, select the previous step.
            mPager.setCurrentItem( mPager.getCurrentItem() - 1 );

        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        if( getMasterBackendUrl().equals( "http://" + getResources().getString( R.string.pref_backend_url ) + ":" + getResources().getString( R.string.pref_backend_port ) ) ) {
            Log.i( TAG, "onResume : MasterBackend not set, redirecting to Settings" );

            navigator.navigateToSettings( this );

        }

    }

    private void initializeInjector() {
        Log.d(TAG, "initializeInjector : enter");

        this.dvrComponent = DaggerDvrComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
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
            fragments.add( Fragment.instantiate( MainActivity.this, RecentListFragment.class.getName(), null ) );
            fragments.add( Fragment.instantiate( MainActivity.this, EncoderListFragment.class.getName(), null ) );
            fragments.add( Fragment.instantiate( MainActivity.this, UpcomingListFragment.class.getName(), null ) );

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
