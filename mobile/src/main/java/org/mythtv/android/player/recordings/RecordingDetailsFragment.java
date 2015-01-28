package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.player.PlayerActivity;
import org.mythtv.android.R;
import org.mythtv.android.player.widgets.FloatingActionButton;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, FloatingActionButton.OnCheckedChangeListener {

    private static final String TAG = RecordingDetailsFragment.class.getSimpleName();

    public static final String PROGRAM_KEY = "program";

    ImageView preview;
    TextView title, subTitle, startTime, description, percentComplete;
    FloatingActionButton fab;

    Program mProgram;

    int finalWidth, finalHeight;

    public static RecordingDetailsFragment newInstance( Program program ) {

        Bundle args = new Bundle();
        args.putSerializable(PROGRAM_KEY, program);

        RecordingDetailsFragment f = new RecordingDetailsFragment();
        f.setArguments( args );

        return f;
    }

    @Override
    public Loader onCreateLoader( int id, Bundle args ) {
        Log.v( TAG, "onCreateLoader : enter" );

        Program program = (Program) args.getSerializable( PROGRAM_KEY );

        String[] projection = new String[] { LiveStreamConstants._ID, LiveStreamConstants.FIELD_PERCENT_COMPLETE };
        String selection = LiveStreamConstants.FIELD_SOURCE_FILE + " like ?";
        String[] selectionArgs = new String[] { "%" + program.getFileName() };

        Log.v( TAG, "onCreateLoader : exit" );
        return new CursorLoader( getActivity(), LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data ) {
        Log.v( TAG, "onLoaderFinished : enter" );

        if( null != data && data.moveToNext() ) {
            Log.v( TAG, "onLoaderReset : cursor found live stream" );

            int percent = data.getInt( data.getColumnIndex( LiveStreamConstants.FIELD_PERCENT_COMPLETE ) );
            if( percent > 0 ) {
                Log.v( TAG, "onLoaderReset : updating percent complete" );

                percentComplete.setText( "HLS: " + String.valueOf( percent ) + "%" );
            }

        }

        Log.v( TAG, "onLoaderReset : exit" );
    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader ) {
        Log.v( TAG, "onLoaderReset : enter" );

        Log.v( TAG, "onLoaderReset : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View rootView = inflater.inflate( R.layout.recording_details, container, false );
        preview = (ImageView) rootView.findViewById( R.id.recording_preview );
        title = (TextView) rootView.findViewById( R.id.recording_title );
        subTitle = (TextView) rootView.findViewById( R.id.recording_sub_title );
        startTime = (TextView) rootView.findViewById( R.id.recording_start_time );
        description = (TextView) rootView.findViewById( R.id.recording_description );
        percentComplete = (TextView) rootView.findViewById( R.id.hls_percent_complete );

        fab = (FloatingActionButton) rootView.findViewById( R.id.recording_fab );
        fab.setOnCheckedChangeListener( this );

        Log.d( TAG, "onCreateView : exit" );
        return rootView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        Log.d( TAG, "onActivityCreated : enter" );

        ViewTreeObserver vto = preview.getViewTreeObserver();
        vto.addOnPreDrawListener( new ViewTreeObserver.OnPreDrawListener() {

            public boolean onPreDraw() {

                finalWidth = preview.getMeasuredWidth();
                finalHeight = preview.getMeasuredHeight();

                return true;
            }

        });

        mProgram = (Program) getArguments().getSerializable( PROGRAM_KEY );

        title.setText( mProgram.getTitle() );
        subTitle.setText( mProgram.getSubTitle() );
        startTime.setText( mProgram.getStartTime().withZone( DateTimeZone.getDefault() ).toString( "yyyy-MM-dd hh:mm a" ) );
        description.setText( mProgram.getDescription() );

        DateTime start = new DateTime( mProgram.getRecording().getStartTs() );
        String url = ( (MainApplication) getActivity().getApplicationContext() ).getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + mProgram.getChannel().getChanId() + "&StartTime=" + start.toString( "yyyy-MM-dd'T'HH:mm:ss" );
        updatePreviewImage( url );

        getLoaderManager().initLoader( 0, getArguments(), this );

        Log.d( TAG, "onActivityCreated : exit" );
    }

    private void updatePreviewImage( String uri ) {

        Picasso.with( getActivity() )
                .load( uri.toString() )
//                .resize( finalWidth, finalHeight )
//                .centerCrop()
                .into( preview );

    }

    @Override
    public void onCheckedChanged( FloatingActionButton fabView, boolean isChecked ) {
        Log.d( TAG, "onCheckedChanged : enter" );

        Intent intent = new Intent( getActivity(), PlayerActivity.class );
        intent.putExtra( getResources().getString( R.string.recording ), mProgram );
        intent.putExtra( getResources().getString( R.string.should_start ), true );
        startActivity( intent );

        Log.d( TAG, "onCheckedChanged : exit" );
    }

}
