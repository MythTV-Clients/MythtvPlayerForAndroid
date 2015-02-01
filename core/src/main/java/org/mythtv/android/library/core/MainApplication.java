package org.mythtv.android.library.core;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mythtv.android.library.core.service.ContentService;
import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.core.service.MythService;
import org.mythtv.android.library.core.service.VideoService;
import org.mythtv.android.library.core.service.v027.content.ContentServiceV27EventHandler;
import org.mythtv.android.library.core.service.v027.dvr.DvrServiceV27EventHandler;
import org.mythtv.android.library.core.service.v027.myth.MythServiceV27EventHandler;
import org.mythtv.android.library.core.service.v027.video.VideoServiceV27EventHandler;
import org.mythtv.android.library.core.service.v028.content.ContentServiceV28EventHandler;
import org.mythtv.android.library.core.service.v028.dvr.DvrServiceV28EventHandler;
import org.mythtv.android.library.core.service.v028.myth.MythServiceV28EventHandler;
import org.mythtv.android.library.core.service.v028.video.VideoServiceV28EventHandler;
import org.mythtv.android.library.events.DeleteEvent;
import org.mythtv.android.library.events.content.RequestAllLiveStreamInfosEvent;
import org.mythtv.android.library.events.myth.HostNameDetailsEvent;
import org.mythtv.android.library.events.myth.RequestHostNameEvent;
import org.mythtv.android.library.ui.settings.SettingsActivity;
import org.mythtv.services.api.ApiVersion;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.ServerVersionQuery;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;

/**
 * Created by dmfrey on 11/15/14.
 */
public class MainApplication extends Application {

    private static final String TAG = MainApplication.class.getSimpleName();

    public static final String ACTION_CONNECTED = "org.mythtv.androidtv.core.service.ACTION_CONNECTED";
    public static final String ACTION_NOT_CONNECTED = "org.mythtv.androidtv.core.service.ACTION_NOT_CONNECTED";

    boolean mConnected = false;
    String mBackendUrl, mBackendHostname;
    int mBackendPort;

    private ApiVersion mApiVersion;

    private MythTvApiContext mMythTvApiContext;

    private ContentService mContentService;
    private DvrService mDvrService;
    private MythService mMythService;
    private VideoService mVideoService;

    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;

    public void disconnect() {

        mConnected = false;

        mDvrService.cleanup( new DeleteEvent() );

    }

    public void scheduleAlarms() {

        mAlarmManager = (AlarmManager) getSystemService( Context.ALARM_SERVICE );
        Intent intent = new Intent( MainApplication.this, RefreshLiveStreamsReceiver.class );
        mPendingIntent = PendingIntent.getBroadcast( MainApplication.this, 0, intent, 0 );

        mAlarmManager.setInexactRepeating( AlarmManager.RTC, System.currentTimeMillis(), 5000, mPendingIntent );

    }

    public void cancelAlarms() {

        if( mAlarmManager != null ) {
            Log.v( TAG, "onPause : cancelling live stream refresh" );

            mAlarmManager.cancel( mPendingIntent );
        }

    }

    public void resetBackend() {

        initializeApi();

    }

    public MythTvApiContext getMythTvApiContext() {
        return mMythTvApiContext;
    }

    public ContentService getContentService() {
        return mContentService;
    }

    public DvrService getDvrService() {
        return mDvrService;
    }

    public MythService getMythService() { return mMythService; }

    public VideoService getVideoService() {
        return mVideoService;
    }

    public boolean isConnected() { return mConnected; }

    public String getMasterBackendUrl() {
        return "http://" + mBackendUrl + ":" + mBackendPort + "/";
    }

    public String getMasterBackendHostName() { return mBackendHostname; }

    private void initializeApi() {
        Log.d( TAG, "initializeApi : enter" );

        new ServerVersionAsyncTask().execute();

        Log.d( TAG, "initializeApi : exit" );
    }

    private class ServerVersionAsyncTask extends AsyncTask<Void, Void, ApiVersion> {

        private final String TAG = ServerVersionAsyncTask.class.getSimpleName();

        @Override
        protected ApiVersion doInBackground( Void... params ) {
            Log.v( TAG, "doInBackground : enter" );

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( MainApplication.this );
            mBackendUrl = sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_URL, "" );
            mBackendPort = Integer.parseInt( sharedPref.getString( SettingsActivity.KEY_PREF_BACKEND_PORT, "6544" ) );

            Log.v( TAG, "url=" + getMasterBackendUrl() );

            try {
                Log.v( TAG, "doInBackground : exit" );
                return ServerVersionQuery.getMythVersion( getMasterBackendUrl(), 1000, TimeUnit.MILLISECONDS );
            } catch( IOException e ) {
                Log.e( TAG, "error creating MythTvApiContext, could not reach '" + getMasterBackendUrl() + "'", e );

                Log.v( TAG, "doInBackground : enter, not connected" );
                return null;
            }

        }

        @Override
        protected void onPostExecute( ApiVersion apiVersion ) {
            Log.v( TAG, "onPostExecute : enter" );

            if( null != apiVersion ) {
                Log.v( TAG, "onPostExecute : master backend connected" );

                mApiVersion = apiVersion;

                mMythTvApiContext = MythTvApiContext.newBuilder().setHostName( mBackendUrl ).setPort( mBackendPort ).setVersion( mApiVersion ).setLogLevel(RestAdapter.LogLevel.FULL).build();

                switch( mApiVersion ) {

                    case v027:
                        Log.v( TAG, "onPostExecute : connected to v0.27 master backend" );

                        mContentService = new ContentServiceV27EventHandler( MainApplication.this, mMythTvApiContext );
                        mDvrService = new DvrServiceV27EventHandler( MainApplication.this, mMythTvApiContext );
                        mMythService = new MythServiceV27EventHandler( MainApplication.this, mMythTvApiContext );
                        mVideoService = new VideoServiceV27EventHandler( mMythTvApiContext );

                        break;

                    case v028:
                        Log.v( TAG, "onPostExecute : connected to v0.28 master backend" );

                        mContentService = new ContentServiceV28EventHandler( MainApplication.this, mMythTvApiContext );
                        mDvrService = new DvrServiceV28EventHandler( MainApplication.this, mMythTvApiContext );
                        mMythService = new MythServiceV28EventHandler( MainApplication.this, mMythTvApiContext );
                        mVideoService = new VideoServiceV28EventHandler( mMythTvApiContext );

                        break;
                }

                mConnected = true;

                new RefreshLiveStreamsTask().execute();
                new GetHostNameTask().execute();

                Intent connectedIntent = new Intent( ACTION_CONNECTED );
                sendBroadcast( connectedIntent );

            } else {
                Log.v( TAG, "onPostExecute : master backend not connected" );

                mConnected = false;

                Intent connectedIntent = new Intent( ACTION_NOT_CONNECTED );
                sendBroadcast( connectedIntent );

            }

            Log.v( TAG, "onPostExecute : exit" );
        }

    }

    private class RefreshLiveStreamsTask extends AsyncTask<Void, Void, Void> {

        private final String TAG = RefreshLiveStreamsTask.class.getSimpleName();

        @Override
        protected Void doInBackground( Void... params ) {
            Log.v( TAG, "doInBackground : enter" );

            mContentService.getLiveStreamInfoList( new RequestAllLiveStreamInfosEvent() );

            Log.v( TAG, "doInBackground : exit" );
            return null;
        }

    }

    private class GetHostNameTask extends AsyncTask<Void, Void, HostNameDetailsEvent> {

        private final String TAG = GetHostNameTask.class.getSimpleName();

        @Override
        protected HostNameDetailsEvent doInBackground( Void... params ) {
            Log.v( TAG, "doInBackground : enter" );

            HostNameDetailsEvent event = mMythService.getHostName( new RequestHostNameEvent() );

            Log.v( TAG, "doInBackground : exit" );
            return event;
        }

        @Override
        protected void onPostExecute( HostNameDetailsEvent event ) {
            Log.v( TAG, "onPostExecute : enter" );

            if( event.isEntityFound() ) {
                Log.v( TAG, "onPostExecute : hostname returned" );

                mBackendHostname = event.getDetails().getHostname();

                Intent connectedIntent = new Intent( ACTION_CONNECTED );
                sendBroadcast( connectedIntent );

            }

            Log.v( TAG, "onPostExecute : exit" );
        }

    }

}
