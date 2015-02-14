package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.events.content.AddRecordingLiveStreamEvent;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.library.ui.utils.ImageUtils;
import org.mythtv.android.player.PlayerActivity;
import org.mythtv.android.R;
//import org.mythtv.android.player.widgets.FloatingActionButton;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener { //}, FloatingActionButton.OnCheckedChangeListener  {

    public static final String PROGRAM_KEY = "program";

    ImageView preview;
    TextView startTime, description, percentComplete;
    Button play, queueHls, deleteRecording;
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

                percentComplete.setText( "HLS: " + String.valueOf( percent ) + "%" );

                if( percent > 2 ) {
                    fullUrl = data.getString( data.getColumnIndex( LiveStreamConstants.FIELD_RELATIVE_URL ) );
//                    Log.v( TAG, "onLoaderReset : fullUrl=" + fullUrl );
//                    fab.setVisibility( View.VISIBLE );
                    play.setVisibility( View.VISIBLE );
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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View rootView = inflater.inflate( R.layout.fragment_recording_details, container, false );
        preview = (ImageView) rootView.findViewById( R.id.recording_preview );
        startTime = (TextView) rootView.findViewById( R.id.recording_start_time );
        description = (TextView) rootView.findViewById( R.id.recording_description );
        percentComplete = (TextView) rootView.findViewById( R.id.hls_percent_complete );

        queueHls = (Button) rootView.findViewById( R.id.recording_queue_hls );
        queueHls.setOnClickListener( this );

        play = (Button) rootView.findViewById( R.id.recording_play );
        play.setOnClickListener( this );

        deleteRecording = (Button) rootView.findViewById( R.id.recording_delete );
        deleteRecording.setOnClickListener( this );

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

    public void setProgram( Program program ) {

        mProgram = program;

        startTime.setText( mProgram.getStartTime().withZone( DateTimeZone.getDefault() ).toString( "yyyy-MM-dd hh:mm a" ) );
        description.setText( mProgram.getDescription() );

        DateTime start = new DateTime( mProgram.getRecording().getStartTs() );
        String url = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + mProgram.getChannel().getChanId() + "&StartTime=" + start.toString( "yyyy-MM-dd'T'HH:mm:ss" );
        ImageUtils.updatePreviewImage(getActivity(), preview, url );

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

                new AddRecordingLiveStreamAsyncTask().execute();
                queueHls.setVisibility( View.INVISIBLE );
                percentComplete.setText( "Queued..." );

                break;

            case R.id.recording_delete :

                break;

        }

    }

    private class AddRecordingLiveStreamAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground( Void... params ) {

            MainApplication.getInstance().getContentService().addRecordingLiveStream( new AddRecordingLiveStreamEvent( mProgram.getRecording().getRecordedId(), mProgram.getChannel().getChanId(), mProgram.getRecording().getStartTs(), 0, 1280, 720, null, null, null ) );

            return null;
        }

    }

}
