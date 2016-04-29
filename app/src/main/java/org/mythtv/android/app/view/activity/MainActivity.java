package org.mythtv.android.app.view.activity;

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

import org.mythtv.android.app.R;
import org.mythtv.android.app.view.fragment.UpcomingListFragment;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.app.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.app.internal.di.components.DvrComponent;
import org.mythtv.android.app.view.fragment.EncoderListFragment;
import org.mythtv.android.app.view.fragment.RecentListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import org.mythtv.android.app.BuildConfig;

/**
 * Created by dmfrey on 8/31/15.
 */
public class MainActivity extends AbstractBaseActivity implements HasComponent<DvrComponent> {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static Intent getCallingIntent(Context context ) {

        return new Intent( context, MainActivity.class );
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

        return R.layout.activity_main;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        Log.i( TAG, "Current tag: " + BuildConfig.APPLICATION_TAG + ", Commit: " + BuildConfig.APPLICATION_SHA1 );

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

                        ( (RecentListFragment) mPagerAdapter.getItem( 0 ) ).reload();

                        break;

                    case 1 :

                        ( (EncoderListFragment) mPagerAdapter.getItem( 1 ) ).reload();

                        break;

                    case 2 :

                        ( (UpcomingListFragment) mPagerAdapter.getItem( 2 ) ).reload();

                        break;

                }

            }

        });

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onResume() {
        super.onResume();

        if( getSharedPreferencesModule().getMasterBackendUrl().equals( "http://" + getResources().getString( R.string.pref_backend_url ) + ":" + getResources().getString( R.string.pref_backend_port ) ) ) {
            Log.i( TAG, "onResume : MasterBackend not set, redirecting to Settings" );

            navigator.navigateToSettings( this );

        }

        setNavigationMenuItemChecked( 0 );

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
