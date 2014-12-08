package org.mythtv.android.library.ui.data;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.TitleInfoDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class TitleInfosDataFragment extends Fragment {

    private static final String TAG = TitleInfosDataFragment.class.getSimpleName();

    private List<TitleInfo> titleInfos;

    private DvrService mDvrService;

    private TitleInfoDataConsumer consumer;
    private boolean loading = false;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.v(TAG, "onCreate : enter" );

        initializeClient( (MainApplication) getActivity().getApplicationContext() );
        update();

        Log.v(TAG, "onCreate : exit");
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i( TAG, "onDestroyView : enter" );

        mDvrService = null;

        Log.i( TAG, "onDestroyView : exit" );
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.i( TAG, "onAttach : enter" );

        if( activity instanceof TitleInfoDataConsumer ) {
            consumer = (TitleInfoDataConsumer) activity;
        }

        Log.i( TAG, "onAttach : exit" );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i( TAG, "onDetach : enter" );

        consumer = null;

        Log.i( TAG, "onDetach : exit" );
    }

    public void setConsumer( TitleInfoDataConsumer consumer ) {

        this.consumer = consumer;

    }

    public boolean isLoading() {
        return loading;
    }

    private void initializeClient( MainApplication mainApplication ) {
        Log.v( TAG, "initializeClient : enter" );

        mDvrService = mainApplication.getDvrService();

        Log.v( TAG, "initializeClient : exit" );
    }

    private void update() {
        Log.v( TAG, "update : enter" );

        if( titleInfos == null && !isLoading() ) {

            new TitleInfosLoaderAsyncTask().execute();

            loading = true;

        } else {

            if( titleInfos != null ) {

                handleUpdate();

            }

        }

        Log.v( TAG, "update : exit" );
    }

    private void handleUpdate() {
        Log.v( TAG, "handleUpdate : enter" );

        consumer.setTitleInfos(titleInfos);

        Log.v(TAG, "handleUpdate : exit");
    }

    private class TitleInfosLoaderAsyncTask extends AsyncTask<Void, Void, AllTitleInfosEvent> {

        private String TAG = TitleInfosLoaderAsyncTask.class.getSimpleName();

        @Override
        protected AllTitleInfosEvent doInBackground( Void... params ) {
            Log.v( TAG, "doInBackground : enter" );

            AllTitleInfosEvent event = mDvrService.getTitleInfos( new RequestAllTitleInfosEvent() );

            Log.v( TAG, "doInBackground : exit" );
            return event;
        }

        @Override
        protected void onPostExecute( AllTitleInfosEvent event ) {
            Log.v(TAG, "onPostExecute : enter");

            if( event.isEntityFound() ) {
                Log.v( TAG, "onPostExecute : received programs" );

                titleInfos = new ArrayList<TitleInfo>();

                for( TitleInfoDetails titleInfoDetails : event.getDetails() ) {
                    Log.v( TAG, "onPostExecute : titleInfoDetails iteration" );

                    TitleInfo titleInfo = TitleInfo.fromDetails( titleInfoDetails );

                    titleInfos.add( titleInfo );
                }

                handleUpdate();

            } else {
                Log.e(TAG, "onPostExecute : error, failed to load recorded programs");

                consumer.handleError( "failed to load recorded programs" );

            }

            loading = false;

            Log.v(TAG, "onPostExecute : exit");
        }

    }

}
