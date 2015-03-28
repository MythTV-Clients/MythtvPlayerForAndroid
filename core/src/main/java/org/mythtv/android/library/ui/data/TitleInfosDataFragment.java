package org.mythtv.android.library.ui.data;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.TitleInfoDetails;
import org.mythtv.android.library.events.dvr.TitleInfosUpdatedEvent;
import org.mythtv.android.library.events.dvr.UpdateTitleInfosEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class TitleInfosDataFragment extends Fragment {

    private List<TitleInfo> titleInfos;

    private TitleInfoDataConsumer consumer;
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

            consumer = (TitleInfoDataConsumer) activity;

        } catch( ClassCastException e ) {
            throw new ClassCastException( activity.toString() + " must implement TitleInfoDataConsumer" );
        }

    }

    public void reset() {

        update();

    }

    public boolean isLoading() {
        return loading;
    }

    private void update() {

        if( null == titleInfos && !isLoading() ) {

            new TitleInfosLoaderAsyncTask().execute();

            loading = true;

        } else {

            if( null != titleInfos ) {

                handleUpdate();

            }

        }

    }

    private void handleUpdate() {

        consumer.setTitleInfos( titleInfos );

    }

    private class TitleInfosLoaderAsyncTask extends AsyncTask<Void, Void, TitleInfosUpdatedEvent> {

        @Override
        protected TitleInfosUpdatedEvent doInBackground( Void... params ) {

            try {
                TitleInfosUpdatedEvent event = MainApplication.getInstance().getDvrService().updateTitleInfos(new UpdateTitleInfosEvent());

                return event;
            } catch( NullPointerException e ) { }

            return null;
        }

        @Override
        protected void onPostExecute( TitleInfosUpdatedEvent event ) {

            if( null != event && event.isEntityFound() ) {

                List<String> titles = new ArrayList<String>();
                titleInfos = new ArrayList<TitleInfo>();

                for( TitleInfoDetails titleInfoDetails : event.getDetails() ) {

                    TitleInfo titleInfo = TitleInfo.fromDetails( titleInfoDetails );
                    if( !titles.contains( titleInfo.getTitle() ) ) {
                        titleInfos.add( titleInfo );
                        titles.add( titleInfo.getTitle() );
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
