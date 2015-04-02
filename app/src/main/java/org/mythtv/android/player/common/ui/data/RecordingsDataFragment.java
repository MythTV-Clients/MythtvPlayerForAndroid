package org.mythtv.android.player.common.ui.data;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.ProgramsUpdatedEvent;
import org.mythtv.android.library.events.dvr.UpdateRecordedProgramsEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class RecordingsDataFragment extends Fragment {

    public static final String TITLE_INFO_TITLE = "title_info_title";

    private List<Program> programs;

    private RecordingDataConsumer consumer;
    private boolean loading = false;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        update();

        return null;
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        try {

            consumer = (RecordingDataConsumer) activity;

        } catch( ClassCastException e ) {
            throw new ClassCastException( activity.toString() + " must implement RecordingDataConsumer" );
        }

    }

    public void reset() {

        update();

    }

    public boolean isLoading() {
        return loading;
    }

    private void update() {

        if( programs == null && !isLoading() ) {

            String title = null;
            if( getArguments().containsKey( TITLE_INFO_TITLE ) ) {
                title = getArguments().getString( TITLE_INFO_TITLE );
            }

            new ProgramsLoaderAsyncTask().execute( title );

            loading = true;

        } else {

            if( programs != null ) {

                handleUpdate();

            }

        }

    }

    private void handleUpdate() {

        consumer.onSetPrograms( programs );

    }

    private class ProgramsLoaderAsyncTask extends AsyncTask<String, Void, ProgramsUpdatedEvent> {

        private String TAG = ProgramsLoaderAsyncTask.class.getSimpleName();

        @Override
        protected ProgramsUpdatedEvent doInBackground( String... params ) {

            String title = params[ 0 ];

            try {

                ProgramsUpdatedEvent event = MainApplication.getInstance().getDvrService().updateRecordedPrograms( new UpdateRecordedProgramsEvent( true, 0, null, title, null, null ) );

                return event;

            } catch( NullPointerException e ) { }

            return null;
        }

        @Override
        protected void onPostExecute( ProgramsUpdatedEvent event ) {

            if( null != event && event.isEntityFound() ) {

                programs = new ArrayList<Program>();

                for( ProgramDetails programDetails : event.getDetails() ) {

                    Program program = Program.fromDetails( programDetails );

                    if( !"LiveTV".equalsIgnoreCase( program.getRecording().getRecGroup() ) ) {
                        programs.add( program );
                    }
                }

                handleUpdate();

            } else {

                consumer.onHandleError( "failed to load recorded programs" );

            }

            loading = false;

        }

    }

}
