package org.mythtv.android.player.videos;

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
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.player.PlayerActivity;
import org.mythtv.android.player.R;
import org.mythtv.android.player.widgets.FloatingActionButton;

/**
 * Created by dmfrey on 12/8/14.
 */
public class VideoDetailsFragment extends Fragment implements FloatingActionButton.OnCheckedChangeListener {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();

    public static final String VIDEO_KEY = "video";

    ImageView coverart;
    TextView title, description;
    FloatingActionButton fab;

    Video mVideo;

    int finalWidth, finalHeight;

    public static VideoDetailsFragment newInstance( Video video ) {

        Bundle args = new Bundle();
        args.putSerializable( VIDEO_KEY, video );

        VideoDetailsFragment f = new VideoDetailsFragment();
        f.setArguments( args );

        return f;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View rootView = inflater.inflate( R.layout.video_details, container, false );
        coverart = (ImageView) rootView.findViewById( R.id.video_coverart );
        title = (TextView) rootView.findViewById( R.id.video_title );
        description = (TextView) rootView.findViewById( R.id.video_description );

        fab = (FloatingActionButton) rootView.findViewById( R.id.video_fab );
        fab.setOnCheckedChangeListener( this );

        Log.d( TAG, "onCreateView : exit" );
        return rootView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        Log.d( TAG, "onActivityCreated : enter" );

        ViewTreeObserver vto = coverart.getViewTreeObserver();
        vto.addOnPreDrawListener( new ViewTreeObserver.OnPreDrawListener() {

            public boolean onPreDraw() {

                finalWidth = coverart.getMeasuredWidth();
                finalHeight = coverart.getMeasuredHeight();

                return true;
            }

        });

        mVideo = (Video) getArguments().getSerializable( VIDEO_KEY );

        title.setText( mVideo.getTitle() );
        description.setText( mVideo.getDescription() );

        String url = ( (MainApplication) getActivity().getApplicationContext() ).getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + mVideo.getId() + "&Type=coverart";
        updatePreviewImage( url );

        Log.d( TAG, "onActivityCreated : exit" );
    }

    private void updatePreviewImage( String uri ) {

        Picasso.with( getActivity() )
                .load( uri.toString() )
//                .resize( finalWidth, finalHeight )
//                .centerCrop()
                .into( coverart );

    }

    @Override
    public void onCheckedChanged( FloatingActionButton fabView, boolean isChecked ) {
        Log.d( TAG, "onCheckedChanged : enter" );

        Intent intent = new Intent( getActivity(), PlayerActivity.class );
        intent.putExtra( getResources().getString( R.string.video ), mVideo );
        intent.putExtra( getResources().getString( R.string.should_start ), true );
        startActivity( intent );

        Log.d( TAG, "onCheckedChanged : exit" );
    }

}
