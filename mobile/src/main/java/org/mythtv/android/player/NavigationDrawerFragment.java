package org.mythtv.android.player;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
public class NavigationDrawerFragment extends Fragment {

    private static final String TAG = NavigationDrawerFragment.class.getSimpleName();

    public static final String PREF_FILE_NAME = "mythtv_preference";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private static final String SELECTED_ITEM_STATE = "selected_item";

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavAdapter adapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;
    private boolean isDrawerOpened = false;

    private int mSelectedItem;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static List<NavigationItem> getNavigationItems( Context context ) {

        //load only static data inside a drawer
        List<NavigationItem> items = new ArrayList<>();

        items.add( new NavigationItem( R.drawable.ic_watch_recordings, context.getResources().getString( R.string.drawer_item_watch_recordings ) ) );
        items.add( new NavigationItem( R.drawable.ic_watch_videos, context.getResources().getString( R.string.drawer_item_watch_videos ) ) );
        items.add( new NavigationItem( R.drawable.ic_preferences, context.getResources().getString( R.string.drawer_item_preferences ) ) );

        return items;
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

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf( readFromPreferences( getActivity(), KEY_USER_LEARNED_DRAWER, "false" ) );
        mSelectedItem = Integer.parseInt( readFromPreferences( getActivity(), SELECTED_ITEM_STATE, "0" ) );

        if( savedInstanceState == null ) {

            mSelectedItem = 0;

        } else {

            mFromSavedInstanceState = true;

            if( savedInstanceState.containsKey( SELECTED_ITEM_STATE ) ) {

                mSelectedItem = savedInstanceState.getInt( SELECTED_ITEM_STATE );

            }

        }

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View layout = inflater.inflate( R.layout.fragment_navigation_drawer, container, false );
        recyclerView = (RecyclerView) layout.findViewById( R.id.drawer_list );
        adapter = new NavAdapter( getActivity(), getNavigationItems(getActivity()) );
        recyclerView.setAdapter( adapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        recyclerView.addOnItemTouchListener( new RecyclerTouchListener( getActivity(), recyclerView, new ClickListener() {

            @Override
            public void onClick( View view, int position ) {
                Log.v( TAG, "onClick : enter" );


                switch( position ) {

                    case 0 :

                        if( !( getActivity() instanceof ShowsActivity ) ) {
                            startActivity( new Intent( getActivity(), ShowsActivity.class ) );
                        }

                        mDrawerLayout.closeDrawer( containerView );

                        break;

                    case 1 :

                        if( !( getActivity() instanceof VideosActivity ) ) {
                            startActivity( new Intent( getActivity(), VideosActivity.class ) );
                        }

                        mDrawerLayout.closeDrawer( containerView );

                        break;

                    case 2 :

                        startActivity( new Intent( getActivity(), SettingsActivity.class ) );

                        mDrawerLayout.closeDrawer( containerView );

                        break;

                }

                Log.v( TAG, "onClick : exit" );
            }

            @Override
            public void onLongClick( View view, int position ) {
                Log.v( TAG, "onLongClick : enter" );

                switch( position ) {

                    case 0 :

                        if( !( getActivity() instanceof ShowsActivity ) ) {
                            startActivity( new Intent( getActivity(), ShowsActivity.class ) );
                        }

                        mDrawerLayout.closeDrawer( containerView );

                        break;

                    case 1 :

                        if( !( getActivity() instanceof VideosActivity ) ) {
                            startActivity( new Intent( getActivity(), VideosActivity.class ) );
                        }

                        mDrawerLayout.closeDrawer( containerView );

                        break;

                    case 2 :

                        startActivity( new Intent( getActivity(), SettingsActivity.class ) );

                        mDrawerLayout.closeDrawer( containerView );

                        break;

                }

                Log.v( TAG, "onLongClick : exit" );
            }

        }));

        return layout;
    }

    @Override
    public void onViewStateRestored( Bundle savedInstanceState ) {
        super.onViewStateRestored( savedInstanceState );

        if( null != savedInstanceState && savedInstanceState.containsKey( SELECTED_ITEM_STATE ) ) {

            mSelectedItem = savedInstanceState.getInt( SELECTED_ITEM_STATE );

        } else {

            mSelectedItem = 0;

        }

    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );

        outState.putInt( SELECTED_ITEM_STATE, mSelectedItem );

    }

    public void setUp( int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar ) {

        containerView = getActivity().findViewById( fragmentId );
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle( getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close ) {

            @Override
            public void onDrawerOpened( View drawerView ) {
                super.onDrawerOpened( drawerView );

                if( !mUserLearnedDrawer ) {
                    mUserLearnedDrawer = true;
                    saveToPreferences( getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "" );
                }

                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed( View drawerView ) {
                super.onDrawerClosed( drawerView );

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

        mDrawerLayout.setDrawerListener( mDrawerToggle );
        mDrawerLayout.post( new Runnable() {

            @Override
            public void run() {

                mDrawerToggle.syncState();

            }

        });

    }

    public static interface ClickListener {

        public void onClick( View view, int position );

        public void onLongClick( View view, int position );

    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener( Context context, final RecyclerView recyclerView, final ClickListener clickListener ) {

            this.clickListener = clickListener;

            gestureDetector = new GestureDetector( context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp( MotionEvent e ) {
                    return true;
                }

                @Override
                public void onLongPress( MotionEvent e ) {

                    View child = recyclerView.findChildViewUnder( e.getX(), e.getY() );
                    if( null != child && null != clickListener ) {

                        clickListener.onLongClick( child, recyclerView.getChildPosition( child ) );

                    }

                }

            });

        }

        @Override
        public boolean onInterceptTouchEvent( RecyclerView rv, MotionEvent e ) {

            View child = rv.findChildViewUnder( e.getX(), e.getY() );
            if( null != child && null != clickListener && gestureDetector.onTouchEvent( e ) ) {

                clickListener.onClick( child, rv.getChildPosition( child ) );

            }

            return false;
        }

        @Override
        public void onTouchEvent( RecyclerView rv, MotionEvent e ) {
        }

    }

}
