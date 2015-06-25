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

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.content.LiveStreamInfo;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.utils.AddRecordingLiveStreamAsyncTask;
import org.mythtv.android.player.app.loaders.LiveStreamAsyncTaskLoader;
import org.mythtv.android.player.common.ui.transform.PaletteTransformation;
import org.mythtv.android.player.app.player.RecordingPlayerActivity;
import org.mythtv.android.R;
import org.mythtv.android.player.common.ui.utils.ImageUtils;

import java.util.Locale;

/*
 * Created by dmfrey on 12/8/14.
 */
public class RecordingDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<LiveStreamInfo>, View.OnClickListener {

    private static final String TAG = RecordingDetailsFragment.class.getSimpleName();

    public static final String PROGRAM_KEY = "program";

    CardView cardView;
    RelativeLayout layout;
    ImageView coverart;
    TextView showName, episodeName, callsign, startTime, channelNumber, description;
    Button play, queueHls;
    ProgressBar progress;

    Program mProgram;

    int finalWidth, finalHeight;

    String fullUrl;
    boolean useInternalPlayer;

    @Override
    public Loader onCreateLoader( int id, Bundle args ) {

        LiveStreamAsyncTaskLoader loader = new LiveStreamAsyncTaskLoader( getActivity() );
        loader.setChanId( mProgram.getChannel().getChanId() );
        loader.setStartTime( mProgram.getRecording().getStartTs() );

        return loader;
    }

    @Override
    public void onLoadFinished( Loader<LiveStreamInfo> loader, LiveStreamInfo data ) {

        if( !useInternalPlayer ) {

            queueHls.setVisibility( View.GONE );
            play.setVisibility( View.VISIBLE );

        } else {

            if( null != data ) {

                int percent = data.getPercentComplete();
                if( percent > 0 ) {

                    progress.setIndeterminate( false );
                    progress.setProgress( percent );

                    if( percent > 2 ) {

                        fullUrl = data.getRelativeURL();
                        play.setVisibility( View.VISIBLE );

                    }

                    if( percent == 100 ) {

                        progress.setVisibility( View.GONE );

                    } else {

                        progress.setVisibility( View.VISIBLE );

                    }

                }

                queueHls.setVisibility( View.GONE );

            } else {

                queueHls.setVisibility( View.VISIBLE );
                play.setVisibility( View.GONE );

            }

        }

    }

    @Override
    public void onLoaderReset( Loader<LiveStreamInfo> loader ) { }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        setHasOptionsMenu( true );

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.v( TAG, "onCreateView : enter" );

        View rootView = inflater.inflate( R.layout.fragment_recording_details, container, false );
        cardView = (CardView) rootView.findViewById( R.id.recording_card );
        layout = (RelativeLayout) rootView.findViewById( R.id.recording_layout );
        coverart = (ImageView) rootView.findViewById( R.id.recording_coverart );
        showName = (TextView) rootView.findViewById( R.id.recording_show_name );
        episodeName = (TextView) rootView.findViewById( R.id.recording_episode_name );
        callsign = (TextView) rootView.findViewById( R.id.recording_episode_callsign );
        startTime = (TextView) rootView.findViewById( R.id.recording_start_time );
        channelNumber = (TextView) rootView.findViewById( R.id.recording_episode_channel_number );
        description = (TextView) rootView.findViewById( R.id.recording_description );

        queueHls = (Button) rootView.findViewById( R.id.recording_queue_hls );
        queueHls.setOnClickListener( this );

        play = (Button) rootView.findViewById( R.id.recording_play );
        play.setOnClickListener( this );

        progress = (ProgressBar) rootView.findViewById( R.id.recording_progress );

        Log.v( TAG, "onCreateView : exit" );
        return rootView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.v( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        ViewTreeObserver vto = coverart.getViewTreeObserver();
        vto.addOnPreDrawListener( new ViewTreeObserver.OnPreDrawListener() {

            public boolean onPreDraw() {

                finalWidth = coverart.getMeasuredWidth();
                finalHeight = coverart.getMeasuredHeight();

                return true;
            }

        });

        Log.v( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

        inflater.inflate( R.menu.menu_details, menu );

        super.onCreateOptionsMenu( menu, inflater );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.menu_settings :

                final Intent settings = new Intent( getActivity(), RecordingDetailsSettingsActivity.class );
                startActivity( settings );

                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    public void setProgram( Program program ) {
        Log.v( TAG, "setProgram : enter" );

        useInternalPlayer = MainApplication.getInstance().isInternalPlayerEnabled();

        mProgram = program;

        cardView.setRadius( 2.0f );
        showName.setText(mProgram.getTitle());
        episodeName.setText(mProgram.getSubTitle());
        callsign.setText( mProgram.getChannel().getCallSign() );
        startTime.setText( mProgram.getStartTime().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) );
        channelNumber.setText( mProgram.getChannel().getChanNum() );
        description.setText( mProgram.getDescription() );

        if( null != mProgram.getInetref() && !"".equals( mProgram.getInetref() ) ) {

            final String coverartUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + mProgram.getInetref() + "&Type=coverart&Width=150";

            final PaletteTransformation paletteTransformation = PaletteTransformation.getInstance();
            Picasso.with( getActivity() )
                .load( coverartUrl )
                .fit().centerCrop()
                .transform( paletteTransformation )
                .into(coverart, new Callback.EmptyCallback() {

                    @Override
                    public void onSuccess() {

                        coverart.setVisibility( View.VISIBLE );

//                        Bitmap bitmap = ( (BitmapDrawable) coverart.getDrawable() ).getBitmap(); // Ew!
//                        Palette palette = PaletteTransformation.getPalette( bitmap );
//                        Palette.Swatch swatch = palette.getDarkMutedSwatch();
//
//                        try {
//
//                            if( Color.WHITE == swatch.getTitleTextColor() ) {
//
//                                layout.setBackgroundColor( getActivity().getResources().getColor( R.color.primary_dark ) );
//
//                            } else {
//
//                                layout.setBackgroundColor( palette.getDarkMutedColor( R.color.recording_card_default ) );
//
//                            }
//
//                            showName.setTextColor( swatch.getTitleTextColor() );
//                            episodeName.setTextColor( swatch.getTitleTextColor() );
//                            callsign.setTextColor( swatch.getTitleTextColor() );
//                            startTime.setTextColor( swatch.getTitleTextColor() );
//                            channelNumber.setTextColor( swatch.getTitleTextColor() );
//                            description.setTextColor( swatch.getTitleTextColor() );
//
//                            queueHls.setTextColor( swatch.getTitleTextColor() );
//                            play.setTextColor( swatch.getTitleTextColor() );
//
//                        } catch( Exception e ) {
//
//                            layout.setBackgroundColor( getActivity().getResources().getColor( R.color.primary_dark ) );
//                            showName.setTextColor( getActivity().getResources().getColor( R.color.white ) );
//                            episodeName.setTextColor( getActivity().getResources().getColor( R.color.white ) );
//                            callsign.setTextColor( getActivity().getResources().getColor( R.color.white ) );
//                            startTime.setTextColor( getActivity().getResources().getColor( R.color.white ) );
//                            channelNumber.setTextColor( getActivity().getResources().getColor( R.color.white ) );
//                            description.setTextColor( getActivity().getResources().getColor( R.color.white ) );
//
//                            queueHls.setTextColor( getActivity().getResources().getColor( R.color.white ) );
//                            play.setTextColor( getActivity().getResources().getColor( R.color.white ) );
//
//                        }

                    }

                    @Override
                    public void onError() {
                        super.onError();

                        coverart.setVisibility( View.GONE );

//                        layout.setBackgroundColor(getActivity().getResources().getColor(R.color.primary_dark));
//                        showName.setTextColor(getActivity().getResources().getColor(R.color.white));
//                        episodeName.setTextColor(getActivity().getResources().getColor(R.color.white));
//                        callsign.setTextColor(getActivity().getResources().getColor(R.color.white));
//                        startTime.setTextColor(getActivity().getResources().getColor(R.color.white));
//                        channelNumber.setTextColor(getActivity().getResources().getColor(R.color.white));
//                        description.setTextColor(getActivity().getResources().getColor(R.color.white));
//
//                        queueHls.setTextColor(getActivity().getResources().getColor(R.color.white));
//                        play.setTextColor(getActivity().getResources().getColor(R.color.white));

                    }

                });

        }

        getLoaderManager().initLoader( 0, getArguments(), this );

        Log.v( TAG, "setProgram : exit" );
    }

    @Override
    public void onClick( View v ) {

        switch( v.getId() ) {

            case R.id.recording_play :

                if( useInternalPlayer ) {

                    Intent intent = new Intent( getActivity(), RecordingPlayerActivity.class );
                    intent.putExtra( RecordingPlayerActivity.FULL_URL_TAG, fullUrl );
                    intent.putExtra( RecordingPlayerActivity.PROGRAM_TAG, mProgram );
                    startActivity( intent );

                } else {

                    String externalPlayerUrl = MainApplication.getInstance().getMasterBackendUrl() + "Content/GetFile?FileName=" + mProgram.getFileName();
                    Log.i( TAG, "externalPlayerUrl=" + externalPlayerUrl );

                    final Intent externalPlayer = new Intent( Intent.ACTION_VIEW );
                    externalPlayer.setDataAndType( Uri.parse( externalPlayerUrl ), "video/*" );
                    startActivity( externalPlayer );

                }

                break;

            case R.id.recording_queue_hls :

                new AddRecordingLiveStreamAsyncTask().execute( mProgram );
                queueHls.setVisibility( View.INVISIBLE );
                progress.setVisibility( View.VISIBLE );

                break;

        }

    }

}
