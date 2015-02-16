package org.mythtv.android.player;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.R;
import org.mythtv.android.library.ui.settings.SettingsActivity;
import org.mythtv.android.player.recordings.ShowsActivity;
import org.mythtv.android.player.videos.VideosActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements NavigationDrawerAdapter.NavigationDrawerCallbacks {

    public static final String PREF_FILE_NAME = "mythtv_preference";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private static final String SELECTED_ITEM_STATE = "selected_item";

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;

    private int mSelectedItem;

    public static List<NavigationItem> getNavigationItems( Context context ) {

        //load only static data inside a drawer
        List<NavigationItem> items = new ArrayList<>();

        items.add( new NavigationItem( R.drawable.ic_watch_recordings, context.getResources().getString( R.string.drawer_item_watch_recordings ) ) );
        items.add( new NavigationItem( R.drawable.ic_watch_videos, context.getResources().getString( R.string.drawer_item_watch_videos ) ) );
        items.add( new NavigationItem( R.drawable.ic_preferences, context.getResources().getString( R.string.drawer_item_preferences ) ) );

        return items;
    }


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        mUserLearnedDrawer = Boolean.valueOf( readFromPreferences( getActivity(), KEY_USER_LEARNED_DRAWER, "false" ) );
        mSelectedItem = Integer.parseInt( readFromPreferences( getActivity(), SELECTED_ITEM_STATE, "0" ) );

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View layout = inflater.inflate( R.layout.fragment_navigation_drawer, container, false );
        recyclerView = (RecyclerView) layout.findViewById( R.id.drawer_list );
        adapter = new NavigationDrawerAdapter( getNavigationItems( getActivity() ) );
        adapter.setNavigationDrawerCallbacks( this );
        recyclerView.setAdapter( adapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        selectItem( mSelectedItem );

        return layout;
    }

    @Override
    public void onViewStateRestored( Bundle savedInstanceState ) {
        super.onViewStateRestored( savedInstanceState );

        if( null != savedInstanceState && savedInstanceState.containsKey( SELECTED_ITEM_STATE ) ) {

            mSelectedItem = savedInstanceState.getInt( SELECTED_ITEM_STATE );

        }

    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );

        saveToPreferences( getActivity(), SELECTED_ITEM_STATE, String.valueOf( mSelectedItem ) );
        outState.putInt( SELECTED_ITEM_STATE, mSelectedItem );

    }

    public void openDrawer() {

        mDrawerLayout.openDrawer( containerView );

    }

    public void closeDrawer() {

        mDrawerLayout.closeDrawer( containerView );

    }


    @Override
    public void onNavigationDrawerItemSelected( int position ) {

        selectItem( position );

    }

    public void setUp( int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar ) {

        containerView = getActivity().findViewById( fragmentId );
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle( getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close ) {

            @Override
            public void onDrawerOpened( View drawerView ) {
                super.onDrawerOpened( drawerView );

                if( !isAdded() ) return;

                if( !mUserLearnedDrawer ) {
                    mUserLearnedDrawer = true;
                    saveToPreferences( getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "" );
                }

                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed( View drawerView ) {
                super.onDrawerClosed( drawerView );

                if( !isAdded() ) return;

                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerSlide( View drawerView, float slideOffset ) {
                super.onDrawerSlide( drawerView, slideOffset );

                toolbar.setAlpha( 1 - slideOffset / 2 );
            }

        };

        if( !mUserLearnedDrawer && !mFromSavedInstanceState ) {
            mDrawerLayout.openDrawer( containerView );
        }

        mDrawerLayout.post( new Runnable() {

            @Override
            public void run() {

                mDrawerToggle.syncState();

            }

        });

        mDrawerLayout.setDrawerListener( mDrawerToggle );

    }

    void selectItem( int position ) {

        mSelectedItem = position;
        saveToPreferences( getActivity(), SELECTED_ITEM_STATE, String.valueOf( mSelectedItem ) );

        if( null != mDrawerLayout ) {
            mDrawerLayout.closeDrawer( containerView );
        }

//        if( null != mCallbacks ) {
//            mCallbacks.onNavigationDrawerItemSelected( position );
//        }

        ((NavigationDrawerAdapter) recyclerView.getAdapter() ).selectPosition( position );

        switch( position ) {

            case 0 :

                if( !( getActivity() instanceof ShowsActivity ) ) {
                    startActivity( new Intent( getActivity(), ShowsActivity.class ) );
                }

                break;

            case 1 :

                if( !( getActivity() instanceof VideosActivity ) ) {
                    startActivity( new Intent( getActivity(), VideosActivity.class ) );
                }

                break;

            case 2 :

                startActivity(new Intent(getActivity(), SettingsActivity.class));

                break;

        }

    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen( containerView );
    }

    @Override
    public void onConfigurationChanged( Configuration newConfig ) {
        super.onConfigurationChanged( newConfig );

        mDrawerToggle.onConfigurationChanged( newConfig );

    }

    public static void saveToPreferences( Context context, String preferenceName, String preferenceValue ) {

        SharedPreferences sharedPreferences = context.getSharedPreferences( PREF_FILE_NAME, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( preferenceName, preferenceValue );
        editor.apply();

    }

    public static String readFromPreferences( Context context, String preferenceName, String defaultValue ) {

        SharedPreferences sharedPreferences = context.getSharedPreferences( PREF_FILE_NAME, Context.MODE_PRIVATE );

        return sharedPreferences.getString( preferenceName, defaultValue );
    }

}
