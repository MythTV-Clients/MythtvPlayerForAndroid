package org.mythtv.android.library.ui.data;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.core.service.DvrServiceHelper;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dmfrey on 11/29/14.
 */
public class RecordingsDataFragment extends Fragment {

    private static final String TAG = RecordingsDataFragment.class.getSimpleName();

    private Map<String, List<Program>> mPrograms = new TreeMap<String, List<Program>>();
    private Map<String, String> mCategories = new TreeMap<String, String>();

    private List<Program> programs = new ArrayList<Program>();

    private DvrService mDvrService;

    private RecordingDataConsumer consumer;
    private boolean loading = false;

    private BackendConnectedBroadcastReceiver mBackendConnectedBroadcastReceiver = new BackendConnectedBroadcastReceiver();

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.v( TAG, "onCreate : enter" );

        Log.v( TAG, "onCreate : exit" );
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i( TAG, "onResume : enter" );

        IntentFilter backendConnectedIntentFilter = new IntentFilter( MainApplication.ACTION_CONNECTED );
        backendConnectedIntentFilter.addAction( MainApplication.ACTION_NOT_CONNECTED );
        getActivity().registerReceiver( mBackendConnectedBroadcastReceiver, backendConnectedIntentFilter );

        Log.i( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i( TAG, "onPause : enter" );

        if( null != mBackendConnectedBroadcastReceiver ) {
            getActivity().unregisterReceiver( mBackendConnectedBroadcastReceiver );
        }

        Log.i( TAG, "onPause : exit" );
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.v(TAG, "onAttach : enter");

        if( activity instanceof RecordingDataConsumer ) {
            consumer = (RecordingDataConsumer) activity;
        }

        Log.v( TAG, "onAttach : exit" );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v( TAG, "onDestroyView : enter" );

        Log.v( TAG, "onDestroyView : exit" );
    }

    public boolean isLoading() {
        return loading;
    }

    private void initializeClient( MainApplication mainApplication ) {
        Log.v( TAG, "initializeClient : enter" );

        mDvrService = mainApplication.getDvrService();

        Log.v( TAG, "initializeClient : exit" );
    }

    private void preparePrograms() {
        Log.d( TAG, "preparePrograms : enter" );

        for( Program program : programs ) {

            String cleanedTitle = program.getTitle(); //cleanArticles( program.getTitle() );
            if( !mPrograms.containsKey( cleanedTitle ) ) {

                List<Program> categoryPrograms = new ArrayList<Program>();
                categoryPrograms.add( program );
                mPrograms.put( cleanedTitle, categoryPrograms );

                mCategories.put( cleanedTitle, program.getTitle() );

            } else {

                mPrograms.get( cleanedTitle ).add( program );

            }

        }

        if( null != consumer ) {
            Log.i( TAG, "preparePrograms : programs loaded" );

            consumer.setPrograms( mCategories, mPrograms );
        }

        Log.d( TAG, "preparePrograms : exit" );
    }

    private class ProgramsLoaderAsyncTask extends AsyncTask<Void, Void, AllProgramsEvent> {

        private String TAG = ProgramsLoaderAsyncTask.class.getSimpleName();

        @Override
        protected AllProgramsEvent doInBackground( Void... params ) {
            Log.v( TAG, "doInBackground : enter" );

            loading = true;

            AllProgramsEvent event = mDvrService.getRecordedPrograms( new RequestAllRecordedProgramsEvent( true, 1, -1, null, null, null ) );

            Log.v( TAG, "doInBackground : exit" );
            return event;
        }

        @Override
        protected void onPostExecute( AllProgramsEvent event ) {
            Log.v( TAG, "onPostExecute : enter" );

            if( event.isEntityFound() ) {

                for( ProgramDetails programDetails : event.getDetails() ) {

                    Program program = Program.fromDetails( programDetails );

                    if( !"LiveTV".equals( program.getRecording().getStorageGroup() ) ) {
                        programs.add( program );
                    }
                }

                preparePrograms();
                consumer.setPrograms( programs );

            } else {
                Log.e( TAG, "onPostExecute : error, failed to load recorded programs" );

                consumer.handleError( "failed to load recorded programs");

            }
            loading = false;

            Log.v( TAG, "onPostExecute : exit" );
        }

    }

    private class BackendConnectedBroadcastReceiver extends BroadcastReceiver {

        private final String TAG = BackendConnectedBroadcastReceiver.class.getSimpleName();

        @Override
        public void onReceive( Context context, Intent intent ) {
            Log.d( TAG, "onReceive : enter" );

            if( MainApplication.ACTION_CONNECTED.equals(intent.getAction()) ) {
                Log.v(TAG, "onReceive : backend is connected");

                initializeClient( (MainApplication) getActivity().getApplicationContext() );
                new ProgramsLoaderAsyncTask().execute();


            }

            if( MainApplication.ACTION_NOT_CONNECTED.equals( intent.getAction() ) ) {
                Log.v( TAG, "onReceive : backend is NOT connected" );

                Toast.makeText(getActivity(), "Backend not connected", Toast.LENGTH_SHORT).show();
            }

            Log.d( TAG, "onReceive : exit" );
        }

    }


}
