package org.mythtv.android.player.app.recordings;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTimeZone;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.utils.AddRecordingLiveStreamAsyncTask;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.player.common.ui.transform.PaletteTransformation;
import org.mythtv.android.player.app.player.PlayerActivity;
import org.mythtv.android.R;
//import FloatingActionButton;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener { //}, FloatingActionButton.OnCheckedChangeListener  {

    private static final String TAG = RecordingDetailsFragment.class.getSimpleName();

    public static final String PROGRAM_KEY = "program";

    CardView cardView;
    RelativeLayout layout;
    ImageView preview, coverart;
    TextView showName, episodeName, startTime, description;
    Button play, queueHls;
    ProgressBar progress;
//    FloatingActionButton fab;

    Program mProgram;

    int finalWidth, finalHeight;

    String fullUrl;

    @Override
    public Loader onCreateLoader( int id, Bundle args ) {

        String[] projection = new String[] { LiveStreamConstants._ID, LiveStreamConstants.FIELD_PERCENT_COMPLETE, LiveStreamConstants.FIELD_FULL_URL, LiveStreamConstants.FIELD_RELATIVE_URL };
        String selection = LiveStreamConstants.FIELD_SOURCE_FILE + " like ?";
        String[] selectionArgs = new String[] { "%" + mProgram.getFileName() };

        return new CursorLoader( getActivity(), LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data ) {

        if( null != data && data.moveToNext() ) {

            int percent = data.getInt( data.getColumnIndex( LiveStreamConstants.FIELD_PERCENT_COMPLETE ) );
            if( percent > 0 ) {

                if( percent > 1 ) {

                    progress.setIndeterminate( false );
                    progress.setProgress( percent );

                }

                if( percent > 2 ) {
                    fullUrl = data.getString( data.getColumnIndex( LiveStreamConstants.FIELD_RELATIVE_URL ) );
//                    Log.v( TAG, "onLoaderReset : fullUrl=" + fullUrl );
//                    fab.setVisibility( View.VISIBLE );
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
//            fab.setVisibility( View.GONE );
            play.setVisibility( View.GONE );
        }

    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader ) { }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setHasOptionsMenu( true );

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View rootView = inflater.inflate( R.layout.fragment_recording_details, container, false );
        cardView = (CardView) rootView.findViewById( R.id.recording_card );
        layout = (RelativeLayout) rootView.findViewById( R.id.recording_layout );
        preview = (ImageView) rootView.findViewById( R.id.recording_preview );
        coverart = (ImageView) rootView.findViewById( R.id.recording_coverart );
        showName = (TextView) rootView.findViewById( R.id.recording_show_name );
        episodeName = (TextView) rootView.findViewById( R.id.recording_episode_name );
        startTime = (TextView) rootView.findViewById( R.id.recording_start_time );
        description = (TextView) rootView.findViewById( R.id.recording_description );

        queueHls = (Button) rootView.findViewById( R.id.recording_queue_hls );
        queueHls.setOnClickListener( this );

        play = (Button) rootView.findViewById( R.id.recording_play );
        play.setOnClickListener( this );

        progress = (ProgressBar) rootView.findViewById( R.id.recording_progress );

//        fab = (FloatingActionButton) rootView.findViewById( R.id.recording_fab );
//        fab.setOnCheckedChangeListener( this );

        return rootView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);

        ViewTreeObserver vto = preview.getViewTreeObserver();
        vto.addOnPreDrawListener( new ViewTreeObserver.OnPreDrawListener() {

            public boolean onPreDraw() {

                finalWidth = preview.getMeasuredWidth();
                finalHeight = preview.getMeasuredHeight();

                return true;
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();

        getLoaderManager().restartLoader( 0, getArguments(), this );

    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

        inflater.inflate( R.menu.menu_details, menu );

        super.onCreateOptionsMenu( menu, inflater );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.play_external :

                String externalPlayerUrl = MainApplication.getInstance().getMasterBackendUrl() + "Content/GetFile?FileName=" + mProgram.getFileName();
                Log.i( TAG, "externalPlayerUrl=" + externalPlayerUrl );

                final Intent externalPlayer = new Intent( Intent.ACTION_VIEW );
                externalPlayer.setDataAndType( Uri.parse( externalPlayerUrl ), "video/*" );
                startActivity( externalPlayer );

                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    public void setProgram( Program program ) {

        mProgram = program;

        cardView.setRadius( 2.0f );
        showName.setText( mProgram.getTitle() );
        episodeName.setText( mProgram.getSubTitle() );
        startTime.setText( mProgram.getStartTime().withZone( DateTimeZone.getDefault() ).toString( "yyyy-MM-dd hh:mm a" ) );
        description.setText( mProgram.getDescription() );

        String previewUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + mProgram.getChannel().getChanId() + "&StartTime=" + mProgram.getRecording().getStartTs().withZone( DateTimeZone.UTC ).toString( "yyyy-MM-dd'T'HH:mm:ss" );

        final PaletteTransformation paletteTransformation = PaletteTransformation.getInstance();
        Picasso.with( getActivity() )
                .load( previewUrl )
                .fit().centerCrop()
                .transform( paletteTransformation )
                .into( preview, new Callback.EmptyCallback() {

                    @Override
                    public void onSuccess() {

                        Bitmap bitmap = ( (BitmapDrawable) preview.getDrawable() ).getBitmap(); // Ew!
                        Palette palette = PaletteTransformation.getPalette(bitmap);
                        Palette.Swatch swatch = palette.getDarkMutedSwatch();

                        layout.setBackgroundColor( palette.getDarkMutedColor( R.color.recording_card_default ) );
                        showName.setTextColor(swatch.getTitleTextColor());
                        episodeName.setTextColor( swatch.getTitleTextColor() );
                        startTime.setTextColor( swatch.getTitleTextColor() );
                        description.setTextColor(swatch.getTitleTextColor());

                        queueHls.setTextColor( swatch.getTitleTextColor() );
                        play.setTextColor( swatch.getTitleTextColor() );

                    }
                });
//        ImageUtils.updatePreviewImage( getActivity(), preview, url );

        if( null != mProgram.getInetref() && !"".equals( mProgram.getInetref() ) ) {

            String coverartUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + mProgram.getInetref() + "&Type=coverart&Width=150";
            Picasso.with( getActivity() )
                    .load(coverartUrl)
                    .fit().centerCrop()
                    .into( coverart );

        }

        getLoaderManager().initLoader( 0, getArguments(), this );

    }

//    @Override
//    public void onCheckedChanged( FloatingActionButton fabView, boolean isChecked ) {
//
//        Intent intent = new Intent( getActivity(), PlayerActivity.class );
//        intent.putExtra( PlayerActivity.FULL_URL_TAG, fullUrl );
//        intent.putExtra( getResources().getString( R.string.should_start ), true );
//        startActivity( intent );
//
//    }

    @Override
    public void onClick( View v ) {

        switch( v.getId() ) {

            case R.id.recording_play :

                Intent intent = new Intent( getActivity(), PlayerActivity.class );
                intent.putExtra( PlayerActivity.FULL_URL_TAG, fullUrl );
                intent.putExtra( getResources().getString( R.string.should_start ), true );
                startActivity( intent );

                break;

            case R.id.recording_queue_hls :

                new AddRecordingLiveStreamAsyncTask().execute(mProgram);
                queueHls.setVisibility(View.INVISIBLE);

                break;

        }

    }

}
