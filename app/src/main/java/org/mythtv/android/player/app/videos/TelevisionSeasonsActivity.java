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

package org.mythtv.android.player.app.videos;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.player.app.AbstractBaseAppCompatActivity;
import org.mythtv.android.player.app.loaders.VideosAsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 4/22/15.
 */
public class TelevisionSeasonsActivity extends AbstractBaseAppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = TelevisionSeasonsActivity.class.getSimpleName();

    public static final String VIDEO_KEY = "video";
    public static final String SELECTED_SEASON = "selected_season";
    public static final String SELECTED_POSITION = "selected_position";

    private TelevisionSeasonsFragment mTelevisionSeasonsFragment;

    private Video mVideo;
    private Spinner mSeasonSpinner;

    List<Integer> mSeasons;
    Integer mSelectedSeason, mSelectedPosition;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_app_tv_seasons;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        mSeasonSpinner = (Spinner) findViewById( R.id.season_spinner );
        mTelevisionSeasonsFragment = (TelevisionSeasonsFragment) getSupportFragmentManager().findFragmentById( R.id.fragment_tv_seasons );

        if( null != savedInstanceState ) {

            mVideo = (Video) savedInstanceState.getSerializable( VIDEO_KEY );
            mSelectedSeason = savedInstanceState.getInt( SELECTED_SEASON );
            mSelectedPosition = savedInstanceState.getInt( SELECTED_POSITION );

        } else {

            mVideo = (Video) getIntent().getSerializableExtra( VIDEO_KEY );
            mSelectedSeason = mVideo.getSeason();
            mSelectedPosition = 0;

        }

        if( null != mVideo ) {

            setTitle( mVideo.getTitle() );

//            mTelevisionSeasonsFragment.setShow( mVideo.getTitle(), mSelectedSeason );

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadSeasons();

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {

        outState.putSerializable( VIDEO_KEY, mVideo );
        outState.putInt( SELECTED_SEASON, mSelectedSeason );
        outState.putInt( SELECTED_POSITION, mSelectedPosition );

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );

        if( savedInstanceState.containsKey( VIDEO_KEY ) ) {

            mVideo = (Video) savedInstanceState.getSerializable( VIDEO_KEY );

        }

        if( savedInstanceState.containsKey( SELECTED_SEASON ) ) {

            mSelectedSeason = savedInstanceState.getInt( SELECTED_SEASON );

        }

        if( savedInstanceState.containsKey( SELECTED_POSITION ) ) {

            mSelectedPosition = savedInstanceState.getInt( SELECTED_POSITION );

        }

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );

        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ) .getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {

        if( null != mSeasons ) {

            mSelectedPosition = position;
            mSelectedSeason = mSeasons.get( position );
            mTelevisionSeasonsFragment.setShow( mVideo.getTitle(), mSelectedSeason );

        }

    }

    @Override
    public void onNothingSelected( AdapterView<?> parent ) {

    }

    @Override
    protected void updateData() {

    }

    private void loadSeasons() {

        AllVideosEvent event = MainApplication.getInstance().getVideoService().requestAllVideoTvTitleSeasons( new RequestAllVideosEvent( VideosAsyncTaskLoader.Type.TELEVISION.name(), mVideo.getTitle(), null ) );
        if( event.isEntityFound() ) {

            List<Video> videos = new ArrayList<>();
            for( VideoDetails details : event.getDetails() ) {

                Video video = Video.fromDetails( details );
                videos.add( video );

            }

            mSeasons = new ArrayList<>();
            for( Video video : videos ) {
                if( !mSeasons.contains( video.getSeason() ) && null != video.getSeason() ) {

                    mSeasons.add( video.getSeason() );

                }

            }

            String seasonLabel = getResources().getString( R.string.season );

            String[] seasonLabels = new String[ mSeasons.size() ];
            int index = 0;
            for( Integer season : mSeasons ) {

                seasonLabels[ index ] = seasonLabel + " " + ( season == 0 ? "Specials" : season );

                index++;

            }

            mSeasonSpinner.setOnItemSelectedListener( this );

            ArrayAdapter<CharSequence> adapter = new ArrayAdapter( this, android.R.layout.simple_spinner_item, seasonLabels );
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            mSeasonSpinner.setAdapter( adapter );
            mSeasonSpinner.setVisibility( View.VISIBLE );

            mSeasonSpinner.setSelection( mSelectedPosition );

        } else {

            mSeasonSpinner.setVisibility( View.GONE );

        }

    }

}
