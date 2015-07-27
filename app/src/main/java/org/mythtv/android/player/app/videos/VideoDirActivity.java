package org.mythtv.android.player.app.videos;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;

/**
 * Created by dmfrey on 7/25/15.
 */
public class VideoDirActivity extends AbstractBaseAppCompatActivity {

    private static final String TAG = VideoDirActivity.class.getSimpleName();

    VideoDirFragment mFragment;

    @Override
    protected void onResume() {
        super.onResume();

        super.setNavigationMenuItemChecked( 1 );

        MainApplication.getInstance().setLastActivity( MainApplication.VIDEOS_ACTIVITY_INTENT_FILTER );

        mFragment = (VideoDirFragment) getSupportFragmentManager().findFragmentById( R.id.video_dir_fragment );

    }

    @Override
    public void onBackPressed() {

        if( !mFragment.itemsInHistory() ) {

            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();

        } else {

            mFragment.backUpDirectory();

        }

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );

        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ) .getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setIconifiedByDefault( false );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_video_dir;
    }

    @Override
    protected void updateData() {

    }

}
