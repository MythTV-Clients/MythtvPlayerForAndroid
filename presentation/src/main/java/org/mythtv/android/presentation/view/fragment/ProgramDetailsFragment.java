package org.mythtv.android.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.CastMemberModel;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.presenter.ProgramDetailsPresenter;
import org.mythtv.android.presentation.view.ProgramDetailsView;
import org.mythtv.android.presentation.view.component.AutoLoadImageView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dmfrey on 8/31/15.
 */
public class ProgramDetailsFragment extends BaseFragment implements ProgramDetailsView {

    private static final String TAG = ProgramDetailsFragment.class.getSimpleName();

    private static final String ARGUMENT_KEY_CHAN_ID = "org.mythtv.android.ARGUMENT_CHAN_ID";
    private static final String ARGUMENT_KEY_START_TIME = "org.mythtv.android.ARGUMENT_START_TIME";

    private int chanId;
    private DateTime startTime;

    @Inject
    ProgramDetailsPresenter programDetailsPresenter;

    @Bind( R.id.recording_coverart )
    AutoLoadImageView iv_coverart;

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

    @Bind( R.id.recording_cast )
    TableLayout tl_cast;

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
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_program_details, container, false );
        ButterKnife.bind( this, fragmentView );

        Log.d( TAG, "onCreateView : exit" );
        return fragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        this.initialize();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.programDetailsPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.programDetailsPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "onDestroyView : enter" );
        super.onDestroyView();

        ButterKnife.unbind( this );

        Log.d(TAG, "onDestroyView : exit");
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.programDetailsPresenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( DvrComponent.class ).inject( this );
        this.programDetailsPresenter.setView( this );
        this.chanId = getArguments().getInt( ARGUMENT_KEY_CHAN_ID );
        this.startTime = new DateTime( getArguments().getLong( ARGUMENT_KEY_START_TIME ) );
        Log.d( TAG, "initialize : chanId=" + this.chanId + ", startTime=" + this.startTime );

        this.programDetailsPresenter.initialize(this.chanId, this.startTime);

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public void renderProgram( ProgramModel program ) {
        Log.d(TAG, "renderProgram : enter");

        if( null != program ) {
            Log.d( TAG, "renderProgram : program is not null" );

            ActionBar actionBar = ( (AppCompatActivity) getActivity() ).getSupportActionBar();
            actionBar.setTitle( program.getSubTitle() );
            actionBar.setSubtitle( program.getTitle() );

            if( null == program.getSubTitle() || "".equals( program.getSubTitle() ) ) {

                actionBar.setTitle( program.getTitle() );
                actionBar.setSubtitle( "" );

            }

            this.iv_coverart.setImageUrl( getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + program.getInetref() + "&Type=coverart&Width=150" );
            this.tv_showname.setText( program.getTitle() );
            this.tv_episodename.setText( program.getSubTitle() );
            this.tv_callsign.setText( program.getChannel().getCallSign() );
            this.tv_starttime.setText( program.getStartTime().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) );
            this.tv_channelnumber.setText( program.getChannel().getChanNum() );
            //this.pb_progress;
            this.tv_description.setText( program.getDescription() );

            if( null != program.getCastMembers() && !program.getCastMembers().isEmpty() ) {

                for( CastMemberModel castMember : program.getCastMembers() ) {
                    Log.d( TAG, "renderProgram : castMember=" + castMember );

                    TableRow row = (TableRow)LayoutInflater.from( getActivity() ).inflate( R.layout.cast_member_row, null );
                    ( (TextView) row.findViewById( R.id.cast_member_name ) ).setText( castMember.getName() );
                    ( (TextView) row.findViewById( R.id.cast_member_character_name ) ).setText( castMember.getCharacterName() );
                    ( (TextView) row.findViewById( R.id.cast_member_role ) ).setText( castMember.getTranslatedRole() );
                    tl_cast.addView( row );

                }

            }

        }

        Log.d( TAG, "renderProgram : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        this.rl_progress.setVisibility( View.VISIBLE );
        this.getActivity().setProgressBarIndeterminateVisibility( true );

        Log.d( TAG, "showLoading : exit" );
    }

    @Override public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);

        Log.d( TAG, "hideLoading : exit" );
    }

    @Override
    public void showRetry() {
        Log.d( TAG, "showRetry : enter" );

        this.rl_retry.setVisibility( View.VISIBLE );

        Log.d( TAG, "showRetry : exit" );
    }

    @Override
    public void hideRetry() {
        Log.d( TAG, "hideRetry : enter" );

        this.rl_retry.setVisibility( View.GONE );

        Log.d( TAG, "hideRetry : exit" );
    }

    @Override
    public void showError(String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message );

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public Context getContext()
    {
        return getActivity().getApplicationContext();
    }

    /**
     * Loads all users.
     */
    private void loadProgramDetails() {
        Log.d( TAG, "loadProgramDetails : enter" );

        if( null != this.programDetailsPresenter ) {
            Log.d( TAG, "loadProgramDetails : programDetailsPresenter is not null" );

            this.programDetailsPresenter.initialize( this.chanId, this.startTime );

        }

        Log.d( TAG, "loadProgramDetails : exit" );
    }

    @OnClick( R.id.bt_retry )
    void onButtonRetryClick() {
        Log.d( TAG, "onButtonRetryClick : enter" );

        ProgramDetailsFragment.this.loadProgramDetails();

        Log.d( TAG, "onButtonRetryClick : exit" );
    }

}
