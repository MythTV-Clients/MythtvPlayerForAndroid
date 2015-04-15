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

import com.squareup.okhttp.OkHttpClient;

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
import org.mythtv.android.library.core.utils.RefreshRecordedProgramsTask;
import org.mythtv.android.library.core.utils.RefreshTitleInfosTask;
import org.mythtv.android.library.events.content.RequestAllLiveStreamsEvent;
import org.mythtv.android.library.events.content.UpdateLiveStreamsEvent;
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

    public static final String KEY_PREF_BACKEND_URL = "backend_url";
    public static final String KEY_PREF_BACKEND_PORT = "backend_port";

    public static final String KEY_PREF_INTERNAL_PLAYER = "internal_player";
    public static final String KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO = "external_player_override_video";
    public static final String KEY_PREF_HLS_SETTINGS = "pref_hls_settings";
    public static final String KEY_PREF_HLS_VIDEO_WIDTH = "hls_video_width";
    public static final String KEY_PREF_HLS_VIDEO_HEIGHT = "hls_video_height";
    public static final String KEY_PREF_HLS_VIDEO_BITRATE = "hls_video_bitrate";
    public static final String KEY_PREF_HLS_AUDIO_BITRATE = "hls_audio_bitrate";

    public static final String ACTION_CONNECTED = "org.mythtv.androidtv.core.service.ACTION_CONNECTED";
    public static final String ACTION_NOT_CONNECTED = "org.mythtv.androidtv.core.service.ACTION_NOT_CONNECTED";

    private static MainApplication sInstance;

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
    private PendingIntent mRefreshLiveStreamPendingIntent;
    private PendingIntent mRefreshTitleInfosPendingIntent;
    private PendingIntent mRefreshRecordedProgramsPendingIntent;

    SharedPreferences mSharedPref;

    public static MainApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPref = PreferenceManager.getDefaultSharedPreferences( MainApplication.this );

        sInstance = this;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        cancelAlarms();

        sInstance = null;
    }

    public void disconnect() {
        Log.i( TAG, "Disconnecting..." );

        mConnected = false;
        cancelAlarms();

        Intent connectedIntent = new Intent( ACTION_NOT_CONNECTED );
        sendBroadcast(connectedIntent);

    }

    private void scheduleAlarms() {

        mAlarmManager = (AlarmManager) getSystemService( Context.ALARM_SERVICE );

        Intent refreshLiveStreamIntent = new Intent( this, RefreshLiveStreamsReceiver.class );
        mRefreshLiveStreamPendingIntent = PendingIntent.getBroadcast( this, 0, refreshLiveStreamIntent, 0 );

//        Intent refreshTitleInfosIntent = new Intent( MainApplication.this, RefreshTitleInfosReceiver.class );
//        mRefreshTitleInfosPendingIntent = PendingIntent.getBroadcast( this, 0, refreshTitleInfosIntent, 0 );

        Intent refreshRecordedProgramsIntent = new Intent( MainApplication.this, RefreshRecordedProgramsReceiver.class );
        mRefreshRecordedProgramsPendingIntent = PendingIntent.getBroadcast( this, 0, refreshRecordedProgramsIntent, 0 );

        mAlarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, mRefreshLiveStreamPendingIntent);
//        mAlarmManager.setInexactRepeating( AlarmManager.RTC, System.currentTimeMillis() + 120000, 600000, mRefreshTitleInfosPendingIntent );
        mAlarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis() + 120000, 600000, mRefreshRecordedProgramsPendingIntent);

    }

    private void cancelAlarms() {

        if( mAlarmManager != null ) {
            Log.v( TAG, "onPause : cancelling live stream refresh" );

            mAlarmManager.cancel( mRefreshLiveStreamPendingIntent );
        }

    }

    public void resetBackend() {
        Log.v( TAG, "resetBackend : enter" );

        initializeApi();

        Log.v( TAG, "resetBackend : exit" );
    }

    public ApiVersion getApiVersion() { return mApiVersion; }

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

    public boolean isInternalPlayerEnabled() {

        boolean useInternalPlayer = mSharedPref.getBoolean( KEY_PREF_INTERNAL_PLAYER, true );

        return useInternalPlayer;
    }

    public boolean isExternalPlayerVideoOverrideEnabled() {

        boolean useExternalPlayerForVideo = mSharedPref.getBoolean( KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO, true );

        return useExternalPlayerForVideo;
    }

    public int getVideoWidth() {

        String width = mSharedPref.getString( KEY_PREF_HLS_VIDEO_WIDTH, "0" );

        return Integer.parseInt( width );
    }

    public int getVideoHeight() {

        String height = mSharedPref.getString( KEY_PREF_HLS_VIDEO_HEIGHT, "0" );

        return Integer.parseInt( height );
    }

    public int getVideoBitrate() {

        String bitrate = mSharedPref.getString( KEY_PREF_HLS_VIDEO_BITRATE, "800000" );

        return Integer.parseInt( bitrate );
    }

    public int getAudioBitrate() {

        String bitrate = mSharedPref.getString( KEY_PREF_HLS_AUDIO_BITRATE, "64000");

        return Integer.parseInt( bitrate );
    }

    public String getMasterBackendHostName() { return mBackendHostname; }

    private void initializeApi() {

        new ServerVersionAsyncTask().execute();

    }

    private class ServerVersionAsyncTask extends AsyncTask<Void, Void, ApiVersion> {

        private final String TAG = ServerVersionAsyncTask.class.getSimpleName();

        @Override
        protected ApiVersion doInBackground( Void... params ) {
            Log.v( TAG, "doInBackground : enter" );

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( MainApplication.this );
            mBackendUrl = sharedPref.getString( KEY_PREF_BACKEND_URL, "" );
            mBackendPort = Integer.parseInt( sharedPref.getString( KEY_PREF_BACKEND_PORT, "6544" ) );

            Log.v( TAG, "url=" + getMasterBackendUrl() );

            try {

                Log.v( TAG, "doInBackground : exit" );
                return ServerVersionQuery.getMythVersion( getMasterBackendUrl(), 1000, TimeUnit.MILLISECONDS );

            } catch( IOException e ) {
                Log.e( TAG, "error creating MythTvApiContext, could not reach '" + getMasterBackendUrl() + "'", e );

                disconnect();

                Log.v( TAG, "doInBackground : enter, not connected" );
                return null;
            }

        }

        @Override
        protected void onPostExecute( ApiVersion apiVersion ) {
            Log.v( TAG, "onPostExecute : enter" );

            if( null != apiVersion && !apiVersion.equals( ApiVersion.NotSupported ) ) {
                Log.v( TAG, "onPostExecute : master backend connected" );

                mApiVersion = apiVersion;

                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.setFollowRedirects( true );

                mMythTvApiContext = MythTvApiContext.newBuilder().setOkHttpClient( okHttpClient ).setHostName( mBackendUrl ).setPort( mBackendPort ).setVersion( mApiVersion ).setLogLevel(RestAdapter.LogLevel.FULL).build();

                switch( mApiVersion ) {

                    case v027:
                        Log.v( TAG, "onPostExecute : connected to v0.27 master backend" );

                        mContentService = new ContentServiceV27EventHandler();
                        mDvrService = new DvrServiceV27EventHandler();
                        mMythService = new MythServiceV27EventHandler();
                        mVideoService = new VideoServiceV27EventHandler();

                        break;

                    case v028:
                        Log.v( TAG, "onPostExecute : connected to v0.28 master backend" );

                        mContentService = new ContentServiceV28EventHandler();
                        mDvrService = new DvrServiceV28EventHandler();
                        mMythService = new MythServiceV28EventHandler();
                        mVideoService = new VideoServiceV28EventHandler();

                        break;
                }

                mConnected = true;

                new RefreshLiveStreamsTask().execute();
//                new RefreshTitleInfosTask( null ).execute();
                new RefreshRecordedProgramsTask( null ).execute();

                scheduleAlarms();

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

            mContentService.updateLiveStreams( new UpdateLiveStreamsEvent() );

            Log.v( TAG, "doInBackground : exit" );
            return null;
        }

    }

}
