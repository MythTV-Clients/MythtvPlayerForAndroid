package org.mythtv.android.library.ui.data;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class RecordingsDataFragment extends Fragment {

    private static final String TAG = RecordingsDataFragment.class.getSimpleName();

    public static final String TITLE_INFO_TITLE = "title_info_title";

    private List<Program> programs;

    private RecordingDataConsumer consumer;
    private boolean loading = false;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );

        update();

        Log.v( TAG, "onCreate : exit" );
        return null;
    }

    public void setConsumer( RecordingDataConsumer consumer ) {

        this.consumer = consumer;

    }

    public boolean isLoading() {
        return loading;
    }

    private void update() {
        Log.v( TAG, "update : enter" );

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

        Log.v( TAG, "update : exit" );
    }

    private void handleUpdate() {
        Log.v( TAG, "handleUpdate : enter" );

        consumer.onSetPrograms( programs );

        Log.v(TAG, "handleUpdate : exit");
    }

    private class ProgramsLoaderAsyncTask extends AsyncTask<String, Void, AllProgramsEvent> {

        private String TAG = ProgramsLoaderAsyncTask.class.getSimpleName();

        @Override
        protected AllProgramsEvent doInBackground( String... params ) {
            Log.v( TAG, "doInBackground : enter" );

            String title = params[ 0 ];
            Log.v( TAG, "doInBackground : title=" + title );

            AllProgramsEvent event = MainApplication.getInstance().getDvrService().getRecordedPrograms( new RequestAllRecordedProgramsEvent( true, 0, null, title, null, null ) );

            Log.v( TAG, "doInBackground : exit" );
            return event;
        }

        @Override
        protected void onPostExecute( AllProgramsEvent event ) {
            Log.v(TAG, "onPostExecute : enter");

            if( event.isEntityFound() ) {
                Log.v( TAG, "onPostExecute : received programs" );

                programs = new ArrayList<Program>();

                for( ProgramDetails programDetails : event.getDetails() ) {
                    Log.v( TAG, "onPostExecute : programDetails iteration" );

                    Program program = Program.fromDetails( programDetails );

                    if( !"LiveTV".equalsIgnoreCase( program.getRecording().getRecGroup() ) ) {
                        Log.v( TAG, "onPostExecute : program added" );
                        programs.add( program );
                    }
                }

                handleUpdate();

            } else {
                Log.e(TAG, "onPostExecute : error, failed to load recorded programs");

                consumer.onHandleError( "failed to load recorded programs" );

            }

            loading = false;

            Log.v(TAG, "onPostExecute : exit");
        }

    }

}
