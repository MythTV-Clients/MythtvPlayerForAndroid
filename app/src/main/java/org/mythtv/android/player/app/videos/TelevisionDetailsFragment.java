package org.mythtv.android.player.app.videos;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.utils.AddVideoLiveStreamAsyncTask;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.player.app.player.VideoPlayerActivity;
import org.mythtv.android.player.common.ui.transform.PaletteTransformation;

/**
 * Created by dmfrey on 12/8/14.
 */
public class TelevisionDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private static final String TAG = TelevisionDetailsFragment.class.getSimpleName();

    public static final String VIDEO_KEY = "video";

    CardView cardView;
    RelativeLayout layout;
    ImageView coverart;
    TextView title, season, subTitle, rating, description;
    Button play, queueHls;
    ProgressBar progress;

    Video mVideo;

    int finalWidth, finalHeight;

    String fullUrl;
    boolean useInternalPlayer, useExternalPlayer;

    @Override
    public Loader onCreateLoader( int id, Bundle args ) {

        String[] projection = new String[] { LiveStreamConstants._ID, LiveStreamConstants.FIELD_PERCENT_COMPLETE, LiveStreamConstants.FIELD_FULL_URL, LiveStreamConstants.FIELD_RELATIVE_URL };
        String selection = LiveStreamConstants.FIELD_SOURCE_FILE + " like ?";
        String[] selectionArgs = new String[] { "%" + mVideo.getFileName() };

        return new CursorLoader( getActivity(), LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data ) {

        if( !useInternalPlayer || useExternalPlayer ) {

            queueHls.setVisibility( View.GONE );
            play.setVisibility( View.VISIBLE );

        } else {

            if( null != data && data.moveToNext() ) {

                int percent = data.getInt( data.getColumnIndex(LiveStreamConstants.FIELD_PERCENT_COMPLETE));
                if (percent > 0) {

                    if( percent > 1 ) {

                        progress.setIndeterminate( false );
                        progress.setProgress(percent);

                    }

                    if( percent > 2 ) {

                        fullUrl = data.getString( data.getColumnIndex(LiveStreamConstants.FIELD_FULL_URL ) );
                        play.setVisibility( View.VISIBLE );

                    }

                    if( percent == 100 ) {

                        progress.setVisibility( View.GONE );

                    } else {

                        progress.setVisibility( View.VISIBLE );

                    }

                }

                queueHls.setVisibility( View.INVISIBLE );

            } else {

                queueHls.setVisibility( View.VISIBLE );
                play.setVisibility( View.GONE );

            }

        }

    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader ) { }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View rootView = inflater.inflate( R.layout.fragment_television_details, container, false );
        cardView = (CardView) rootView.findViewById( R.id.video_card );
        layout = (RelativeLayout) rootView.findViewById( R.id.video_layout );
        coverart = (ImageView) rootView.findViewById( R.id.video_coverart );
        title = (TextView) rootView.findViewById( R.id.video_title );
        season = (TextView) rootView.findViewById( R.id.video_season );
        subTitle = (TextView) rootView.findViewById( R.id.video_sub_title );
        rating = (TextView) rootView.findViewById( R.id.video_rating );
        description = (TextView) rootView.findViewById( R.id.video_description );

        queueHls = (Button) rootView.findViewById( R.id.video_queue_hls );
        queueHls.setOnClickListener( this );

        play = (Button) rootView.findViewById( R.id.video_play );
        play.setOnClickListener( this );

        progress = (ProgressBar) rootView.findViewById( R.id.video_progress );

        return rootView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);

        ViewTreeObserver vto = coverart.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            public boolean onPreDraw() {

                finalWidth = coverart.getMeasuredWidth();
                finalHeight = coverart.getMeasuredHeight();

                return true;
            }

        });

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

                final Intent settings = new Intent( getActivity(), VideoDetailsSettingsActivity.class );
                startActivity( settings );

                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    public void setVideo( Video video ) {

        useInternalPlayer = MainApplication.getInstance().isInternalPlayerEnabled();
        useExternalPlayer = MainApplication.getInstance().isExternalPlayerVideoOverrideEnabled();

        mVideo = video;

        title.setText( video.getTitle() );
        season.setText( getResources().getString( R.string.season ) + " " + video.getSeason() + " " + getResources().getString( R.string.episode ) + " " + video.getEpisode() );
        subTitle.setText( video.getSubTitle() );
        rating.setText( video.getCertification() );
        description.setText( mVideo.getDescription() );

        String coverartUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + mVideo.getId() + "&Type=coverart&Width=150";

        final PaletteTransformation paletteTransformation = PaletteTransformation.getInstance();
        Picasso.with( getActivity() )
                .load( coverartUrl )
                .fit().centerCrop()
                .transform( paletteTransformation )
                .into( coverart, new Callback.EmptyCallback() {

                    @Override
                    public void onSuccess() {

                        coverart.setVisibility( View.VISIBLE );

                        Bitmap bitmap = ((BitmapDrawable) coverart.getDrawable()).getBitmap(); // Ew!
                        Palette palette = PaletteTransformation.getPalette(bitmap);
                        Palette.Swatch swatch = palette.getDarkMutedSwatch();

                        try {

                            layout.setBackgroundColor( palette.getDarkMutedColor( R.color.recording_card_default ) );
                            title.setTextColor( swatch.getTitleTextColor() );
                            season.setTextColor( swatch.getTitleTextColor() );
                            subTitle.setTextColor( swatch.getTitleTextColor() );
                            description.setTextColor( swatch.getTitleTextColor() );

                            queueHls.setTextColor( swatch.getTitleTextColor() );
                            play.setTextColor( swatch.getTitleTextColor() );

                        } catch( Exception e ) {

                            layout.setBackgroundColor( getActivity().getResources().getColor( R.color.primary_dark ) );
                            title.setTextColor( getActivity().getResources().getColor( R.color.white ) );
                            season.setTextColor( getActivity().getResources().getColor( R.color.white ) );
                            subTitle.setTextColor( getActivity().getResources().getColor( R.color.white ) );
                            description.setTextColor( getActivity().getResources().getColor( R.color.white ) );

                            queueHls.setTextColor( getActivity().getResources().getColor( R.color.white ) );
                            play.setTextColor( getActivity().getResources().getColor( R.color.white ) );

                        }

                    }

                    @Override
                    public void onError() {
                        super.onError();

                        coverart.setVisibility( View.GONE );

                        layout.setBackgroundColor( getActivity().getResources().getColor( R.color.primary_dark ) );
                        title.setTextColor( getActivity().getResources().getColor( R.color.white ) );
                        season.setTextColor( getActivity().getResources().getColor( R.color.white ) );
                        subTitle.setTextColor( getActivity().getResources().getColor( R.color.white ) );
                        description.setTextColor( getActivity().getResources().getColor( R.color.white ) );

                        queueHls.setTextColor( getActivity().getResources().getColor( R.color.white ) );
                        play.setTextColor(getActivity().getResources().getColor(R.color.white));
                    }

                });

        getLoaderManager().initLoader( 0, getArguments(), this );

    }

    @Override
    public void onClick( View v ) {

        switch( v.getId() ) {

            case R.id.video_play :

                if( useInternalPlayer && !useExternalPlayer ) {

                    Intent intent = new Intent( getActivity(), VideoPlayerActivity.class );
                    intent.putExtra( VideoPlayerActivity.FULL_URL_TAG, fullUrl );
                    intent.putExtra( VideoPlayerActivity.VIDEO_TAG, mVideo );
                    startActivity( intent );

                } else {

                    String externalPlayerUrl = MainApplication.getInstance().getMasterBackendUrl() + "Content/GetFile?FileName=" + mVideo.getFilePath();
                    Log.i( TAG, "externalPlayerUrl=" + externalPlayerUrl );

                    final Intent externalPlayer = new Intent( Intent.ACTION_VIEW );
                    externalPlayer.setDataAndType( Uri.parse(externalPlayerUrl), "video/*" );
                    startActivity( externalPlayer );

                }

                break;

            case R.id.video_queue_hls :

                new AddVideoLiveStreamAsyncTask().execute( mVideo );
                queueHls.setVisibility(View.INVISIBLE);
                progress.setVisibility( View.VISIBLE );

                break;

        }

    }

}
