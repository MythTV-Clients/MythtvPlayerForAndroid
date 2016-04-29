package org.mythtv.android.data.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.VideoMetadataInfoEntityJsonMapper;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.domain.SettingsKeys;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by dmfrey on 11/9/15.
 */
public class VideoApiImpl implements VideoApi {

    private static final String TAG = VideoApiImpl.class.getSimpleName();

    private final Context context;
    private final VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper;
    private final BooleanJsonMapper booleanJsonMapper;

    public VideoApiImpl( Context context, VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper, BooleanJsonMapper booleanJsonMapper ) {

        if( null == context || null == videoMetadataInfoEntityJsonMapper || null == booleanJsonMapper ) {

            throw new IllegalArgumentException( "The constructor parameters cannot be null!!!" );
        }

        this.context = context;
        this.videoMetadataInfoEntityJsonMapper = videoMetadataInfoEntityJsonMapper;
        this.booleanJsonMapper = booleanJsonMapper;

    }

    @Override
    public Observable<List<VideoMetadataInfoEntity>> getVideoList( final String folder, final String sort, final boolean descending, final int startIndex, final int count ) {
        Log.d( TAG, "getVideoList : enter" );

        Log.d( TAG, "getVideoList : exit" );
        return Observable.create( new Observable.OnSubscribe<List<VideoMetadataInfoEntity>>() {

            @Override
            public void call( Subscriber<? super List<VideoMetadataInfoEntity>> subscriber ) {
                Log.d( TAG, "getVideoList.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.d( TAG, "getVideoList.call : network is connected" );

                    try {

                        String responseVideoEntities = getVideoEntitiesFromApi( folder, sort, descending, startIndex, count );
                        if( null != responseVideoEntities ) {
                            Log.d( TAG, "getVideoList.call : retrieved video entities" );

                            subscriber.onNext( videoMetadataInfoEntityJsonMapper.transformVideoMetadataInfoEntityCollection( responseVideoEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d( TAG, "getVideoList.call : failed to retrieve video entities" );

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "getVideoList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "getVideoList.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "getVideoList.call : exit" );
            }

        });

    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoById( final int id ) {

        return Observable.create( new Observable.OnSubscribe<VideoMetadataInfoEntity>() {

            @Override
            public void call( Subscriber<? super VideoMetadataInfoEntity> subscriber ) {

                if( isThereInternetConnection() ) {

                    try {

                        String responseVideoDetails = getVideoDetailsFromApi( id );
                        if( null != responseVideoDetails ) {

                            subscriber.onNext( videoMetadataInfoEntityJsonMapper.transformVideoMetadataInfoEntity( responseVideoDetails ) );
                            subscriber.onCompleted();

                        } else {

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {

                    subscriber.onError( new NetworkConnectionException() );

                }

            }

        });

    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoByFilename( String filename ) {

        return null;
    }

    @Override
    public Observable<Boolean> updateWatchedStatus( final int videoId, final boolean watched ) {

        return Observable.create( new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call( Subscriber<? super Boolean> subscriber ) {
                Log.d( TAG, "updateWatchedStatus.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.d(TAG, "updateWatchedStatus.call : network is connected");

                    try {

                        String response = postUpdateWatchedStatus( videoId, watched );
                        if( null != response ) {
                            Log.d( TAG, "updateWatchedStatus.call : retrieved status update" );

                            subscriber.onNext( booleanJsonMapper.transformBoolean( response ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d( TAG, "updateWatchedStatus.call : failed to retrieve status update" );

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "updateWatchedStatus.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "encoderEntityList.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "encoderEntityList.call : exit" );
            }

        });

    }

    private String getVideoEntitiesFromApi( final String folder, final String sort, final boolean descending, final int startIndex, final int count ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() );
        sb.append( VideoApi.VIDEO_LIST_BASE_URL );
        sb.append( "?" );
        sb.append( String.format( DESCENDING_QS, descending ) );

        if( null != folder && !"".equals( folder ) ) {

            try {

                sb.append( "&" );
                sb.append( String.format( FOLDER_QS, URLEncoder.encode( folder, "UTF-8" ) ) );

            } catch( UnsupportedEncodingException e ) {

                Log.e( TAG, "getVideoEntitiesFromApi : error", e );
            }

        }

        if( null != sort && !"".equals( sort ) ) {

            try {

                sb.append( "&" );
                sb.append( String.format( SORT_QS, URLEncoder.encode( sort, "UTF-8" ) ) );

            } catch( UnsupportedEncodingException e ) {

                Log.e( TAG, "getVideoEntitiesFromApi : error", e );
            }
        }

        if( startIndex != -1 ) {

            sb.append( "&" );
            sb.append( String.format( START_INDEX_QS, startIndex ) );

        }

        if( count != -1 ) {

            sb.append( "&" );
            sb.append( String.format( COUNT_QS, startIndex ) );

        }

        return ApiConnection.create( sb.toString(), getIntFromPreferences( this.context, SettingsKeys.KEY_PREF_READ_TIMEOUT, 10000 ), getIntFromPreferences( this.context, SettingsKeys.KEY_PREF_CONNECT_TIMEOUT, 15000 ) ).requestSyncCall();
    }

    private String getVideoDetailsFromApi( int id ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() );
        sb.append( VideoApi.VIDEO_BASE_URL );
        sb.append( String.format( ID_QS, id ) );
        Log.d( TAG, "getVideoDetailsFromApi : url=" + sb.toString() );

        return ApiConnection.create( sb.toString(), getIntFromPreferences( this.context, SettingsKeys.KEY_PREF_READ_TIMEOUT, 10000 ), getIntFromPreferences( this.context, SettingsKeys.KEY_PREF_CONNECT_TIMEOUT, 15000 ) ).requestSyncCall();
    }

    private String postUpdateWatchedStatus( final int videoId, final boolean watched ) throws MalformedURLException {

        Map<String, String> parameters = new HashMap<>();
        parameters.put( "Id", String.valueOf( videoId ) );
        parameters.put( "Watched", String.valueOf( watched ) );

        Log.i( TAG, "postUpdateRecordingWatchedStatus : url=" + ( getMasterBackendUrl() + UPDATE_VIDEO_WATCHED_STATUS_URL ) );
        return ApiConnection.create( getMasterBackendUrl() + UPDATE_VIDEO_WATCHED_STATUS_URL ).requestSyncCall( parameters );
    }

    private boolean isThereInternetConnection() {

        boolean isConnected;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = ( networkInfo != null && networkInfo.isConnectedOrConnecting() );

        return isConnected;
    }

    private String getMasterBackendUrl() {

        String host = getFromPreferences( this.context, SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getFromPreferences( this.context, SettingsKeys.KEY_PREF_BACKEND_PORT );

        String masterBackend = "http://" + host + ":" + port;
        Log.d( TAG, "getMasterBackendUrl : masterBackend=" + masterBackend );

        return masterBackend;
    }

    private String getFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getString( key, "" );
    }

    private int getIntFromPreferences( Context context, String key, int defaultValue ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return Integer.parseInt( sharedPreferences.getString( key, String.valueOf( defaultValue ) ) );
    }

}
