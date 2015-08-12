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

package org.mythtv.android.player.app.recordings;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;
import org.mythtv.android.R;
import org.mythtv.android.player.common.ui.transform.PaletteTransformation;
import org.mythtv.android.player.common.ui.utils.ToolbarColorizeHelper;

/*
 * Created by dmfrey on 12/8/14.
 */
public class RecordingsActivity extends AbstractBaseAppCompatActivity {

    private static final String TAG = RecordingsActivity.class.getSimpleName();

    public static final String TITLE_INFO = "title_info";

    CollapsingToolbarLayout collapsingToolbar;
    MenuItem mSearchMenuItem, mUpMenuItem;

    TitleInfo mTitleInfo;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_recordings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        RecordingsFragment mRecordingsFragment = (RecordingsFragment) getSupportFragmentManager().findFragmentById( R.id.fragment_recordings );

        if( null != savedInstanceState && savedInstanceState.containsKey( TITLE_INFO ) ) {

            mTitleInfo = (TitleInfo) savedInstanceState.getSerializable( TITLE_INFO );
        }

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( TITLE_INFO ) ) {

            mTitleInfo = (TitleInfo) getIntent().getSerializableExtra( TITLE_INFO );
        }

        collapsingToolbar = (CollapsingToolbarLayout) findViewById( R.id.collapsing_toolbar );

        if( null != mTitleInfo ) {
            Log.i( TAG, "mTitleInfo=" + mTitleInfo.toString() );

            collapsingToolbar.setTitle( mTitleInfo.getTitle() );

            if( null != mTitleInfo.getInetref() && !"".equals( mTitleInfo.getInetref() ) ) {

                loadBackdrop( mTitleInfo.getInetref() );

            }

        } else {

            collapsingToolbar.setTitle( getResources().getString( R.string.all_recordings ) );

        }

        mRecordingsFragment.setPrograms( ( null != mTitleInfo ? mTitleInfo.getTitle() : null ), ( null != mTitleInfo ? mTitleInfo.getInetref() : null ) );

    }

    @Override
    protected void onResume() {
        super.onResume();

//        ToolbarColorizeHelper.colorizeToolbar( toolbar, R.color.white, this );

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {

        outState.putSerializable(TITLE_INFO, mTitleInfo);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);

        if( savedInstanceState.containsKey( TITLE_INFO ) ) {

            mTitleInfo = (TitleInfo) savedInstanceState.getSerializable( TITLE_INFO );

        }

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );

        mSearchMenuItem = menu.findItem( R.id.search_action );
        mUpMenuItem = menu.findItem( android.R.id.home );

        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setIconifiedByDefault(false);

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void updateData() {

    }

    private void loadBackdrop( String inetref ) {
        Log.i( TAG, "loadBackdrop : inetref=" + inetref );

        String bannerUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + inetref + "&Type=banner&Height=256";
        Log.i(TAG, "loadBackdrop : bannerUrl=" + bannerUrl);
        final ImageView imageView = (ImageView) findViewById( R.id.backdrop );
        final PaletteTransformation paletteTransformation = PaletteTransformation.getInstance();
        Picasso.with( this )
                .load( bannerUrl )
                .fit().centerCrop()
                .transform( paletteTransformation )
                .into(imageView, new Callback.EmptyCallback() {

                    @Override
                    public void onSuccess() {

                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap(); // Ew!
                        Palette palette = PaletteTransformation.getPalette(bitmap);

                        try {

                            int inverseColor = getComplementaryColor(palette.getVibrantColor(R.color.white));
                            Log.i( TAG, "loadBackdrop : inverseColor=" + inverseColor + ", Color.WHITE=" + Color.WHITE + ", Color.BLACK=" + Color.BLACK + ", Color." + ( inverseColor > ( Color.BLACK / 2 ) ? "WHITE" : "BLACK" ) );

                            int color = ( inverseColor > ( Color.BLACK / 2 ) ? Color.WHITE : Color.BLACK );

                            collapsingToolbar.setCollapsedTitleTextColor( color );
                            collapsingToolbar.setExpandedTitleColor( color );

                            Drawable newSearchMenuItem = mSearchMenuItem.getIcon();
                            newSearchMenuItem.mutate().setColorFilter( color, PorterDuff.Mode.SRC_IN );
                            mSearchMenuItem.setIcon( newSearchMenuItem );

                            Drawable newUpMenuItem = getResources().getDrawable( R.drawable.ic_arrow_back_white_24dp );
                            newUpMenuItem.mutate().setColorFilter( color, PorterDuff.Mode.SRC_IN );
                            getSupportActionBar().setHomeAsUpIndicator( newUpMenuItem );

                        } catch( Exception e ) {
                            Log.e( TAG, "error decoding palette from imageView", e );
                        }
                    }

                    @Override
                    public void onError() {

                    }

                });

    }

    public static int getComplementaryColor( int colorToInvert ) {

        float[] hsv = new float[ 3 ];
        Color.RGBToHSV( Color.red( colorToInvert ), Color.green( colorToInvert ), Color.blue( colorToInvert ), hsv );

        hsv[ 0 ] = ( hsv[ 0 ] + 180 ) % 360;

        return Color.HSVToColor( hsv );
    }

}
