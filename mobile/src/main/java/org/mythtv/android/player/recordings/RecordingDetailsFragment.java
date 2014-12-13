package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.content.Intent;
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
import org.mythtv.android.player.PlayerActivity;
import org.mythtv.android.player.R;
import org.mythtv.android.player.widgets.FloatingActionButton;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingDetailsFragment extends Fragment implements FloatingActionButton.OnCheckedChangeListener {

    private static final String TAG = RecordingDetailsFragment.class.getSimpleName();

    public static final String PROGRAM_KEY = "program";

    ImageView preview;
    TextView title, subTitle, startTime, description;
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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View rootView = inflater.inflate( R.layout.recording_details, container, false );
        preview = (ImageView) rootView.findViewById( R.id.recording_preview );
        title = (TextView) rootView.findViewById( R.id.recording_title );
        subTitle = (TextView) rootView.findViewById( R.id.recording_sub_title );
        startTime = (TextView) rootView.findViewById( R.id.recording_start_time );
        description = (TextView) rootView.findViewById( R.id.recording_description );

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
