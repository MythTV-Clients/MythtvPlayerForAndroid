package org.mythtv.android.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.presenter.ProgramDetailsPresenter;
import org.mythtv.android.presentation.view.ProgramDetailsView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dmfrey on 8/31/15.
 */
public class ProgramDetailsFragment extends BaseFragment implements ProgramDetailsView {

    private static final String ARGUMENT_KEY_CHAN_ID = "org.mythtv.android.ARGUMENT_CHAN_ID";
    private static final String ARGUMENT_KEY_START_TIME = "org.mythtv.android.ARGUMENT_START_TIME";

    private int chanId;
    private DateTime startTime;

    @Inject
    ProgramDetailsPresenter programDetailsPresenter;

    @Bind( R.id.recording_coverart )
    ImageView iv_coverart;

    @Bind( R.id.recording_show_name )
    TextView tv_showname;

    @Bind( R.id.recording_episode_name )
    TextView tv_episodename;

    @Bind( R.id.recording_episode_callsign )
    TextView tv_callsign;

    @Bind( R.id.recording_start_time )
    TextView tv_starttime;

    @Bind( R.id.recording_episode_channel_number )
    TextView tv_channelnumber;

    @Bind( R.id.recording_progress )
    ProgressBar pb_progress;

    @Bind( R.id.recording_description )
    TextView tv_description;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    @Bind( R.id.rl_retry )
    RelativeLayout rl_retry;

    @Bind( R.id.bt_retry )
    Button bt_retry;

    public ProgramDetailsFragment() { super(); }

    public static ProgramDetailsFragment newInstance( int chanId, DateTime startTime ) {

        ProgramDetailsFragment programDetailsFragment = new ProgramDetailsFragment();

        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putInt( ARGUMENT_KEY_CHAN_ID, chanId );
        argumentsBundle.putLong( ARGUMENT_KEY_START_TIME, startTime.getMillis() );
        programDetailsFragment.setArguments( argumentsBundle );

        return programDetailsFragment;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View fragmentView = inflater.inflate( R.layout.fragment_program_details, container, false );
        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @Override public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);

        this.initialize();

    }

    @Override public void onResume() {
        super.onResume();

        this.programDetailsPresenter.resume();

    }

    @Override public void onPause() {
        super.onPause();

        this.programDetailsPresenter.pause();

    }

    @Override public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind( this );

    }

    @Override public void onDestroy() {
        super.onDestroy();

        this.programDetailsPresenter.destroy();

    }

    private void initialize() {

        this.getComponent( DvrComponent.class ).inject( this );
        this.programDetailsPresenter.setView( this );
        this.chanId = getArguments().getInt( ARGUMENT_KEY_CHAN_ID );
        this.startTime = new DateTime( getArguments().getLong( ARGUMENT_KEY_START_TIME ) );
        this.programDetailsPresenter.initialize( this.chanId, this.startTime );

    }

    @Override public void renderProgram( ProgramModel program ) {

        if( null != program ) {

            this.tv_showname.setText( program.getTitle() );
            this.tv_episodename.setText( program.getSubTitle() );
            //this.tv_callsign.setText( program.getTitle() );
            this.tv_starttime.setText( program.getStartTime().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) );
            //this.tv_channelnumber.setText( program.getTitle() );
            //this.pb_progress;
            this.tv_description.setText( program.getDescription() );

        }

    }

    @Override
    public void showLoading() {

        this.rl_progress.setVisibility( View.VISIBLE );
        this.getActivity().setProgressBarIndeterminateVisibility( true );

    }

    @Override public void hideLoading() {

        this.rl_progress.setVisibility( View.GONE );
        this.getActivity().setProgressBarIndeterminateVisibility( false );

    }

    @Override
    public void showRetry() {

        this.rl_retry.setVisibility( View.VISIBLE );

    }

    @Override
    public void hideRetry() {

        this.rl_retry.setVisibility( View.GONE );

    }

    @Override
    public void showError( String message ) {
        this.showToastMessage( message );
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    /**
     * Loads all users.
     */
    private void loadProgramDetails() {

        if( null != this.programDetailsPresenter ) {

            this.programDetailsPresenter.initialize( this.chanId, this.startTime );

        }

    }

    @OnClick( R.id.bt_retry )
    void onButtonRetryClick() {
        ProgramDetailsFragment.this.loadProgramDetails();
    }

}
