package org.mythtv.android.player.app.videos;

import android.os.Bundle;
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
import java.util.Collections;
import java.util.List;

/**
 * Created by dmfrey on 4/22/15.
 */
public class TelevisionSeasonsActivity extends AbstractBaseAppCompatActivity implements AdapterView.OnItemSelectedListener {

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

            getSupportActionBar().setTitle( mVideo.getTitle() );

            mTelevisionSeasonsFragment.setShow( mVideo.getTitle(), mSelectedSeason );

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
        outState.putInt(SELECTED_SEASON, mSelectedSeason);
        outState.putInt( SELECTED_POSITION, mSelectedPosition );

        super.onSaveInstanceState( outState );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);

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

        AllVideosEvent event = MainApplication.getInstance().getVideoService().requestAllVideos( new RequestAllVideosEvent( VideosAsyncTaskLoader.Type.TELEVISION.name(), mVideo.getTitle(), null ) );
        if( event.isEntityFound() ) {

            List<Video> videos = new ArrayList<>();
            for( VideoDetails details : event.getDetails() ) {

                Video video = Video.fromDetails( details );
                videos.add( video );

            }

            mSeasons = new ArrayList<>();
            for( Video video : videos ) {

                if( !mSeasons.contains( video.getSeason() ) ) {
                    mSeasons.add( video.getSeason() );
                }

            }

            String seasonLabel = getResources().getString( R.string.season );

            Collections.sort( mSeasons );
            String[] seasonLabels = new String[ mSeasons.size() ];
            int index = 0;
            for( Integer season : mSeasons ) {

                seasonLabels[ index ] = seasonLabel + " " + season;

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
