package org.mythtv.android.data.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.mapper.ProgramEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityJsonMapper;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.domain.SettingsKeys;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by dmfrey on 8/27/15.
 */
public class DvrApiImpl implements DvrApi {

    private static final String TAG = DvrApiImpl.class.getSimpleName();

    private static final DateTimeFormatter fmt = DateTimeFormat.forPattern( "yyyy-MM-dd'T'HH:mm:ss'Z'" );

    private final Context context;
    private final TitleInfoEntityJsonMapper titleInfoEntityJsonMapper;
    private final ProgramEntityJsonMapper programEntityJsonMapper;

    public DvrApiImpl( Context context, TitleInfoEntityJsonMapper titleInfoEntityJsonMapper, ProgramEntityJsonMapper programEntityJsonMapper ) {

        if( null == context || null == titleInfoEntityJsonMapper || null == programEntityJsonMapper ) {

            throw new IllegalArgumentException( "The constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();
        this.titleInfoEntityJsonMapper = titleInfoEntityJsonMapper;
        this.programEntityJsonMapper = programEntityJsonMapper;

    }

    @Override
    public Observable<List<TitleInfoEntity>> titleInfoEntityList() {

        return Observable.create( new Observable.OnSubscribe<List<TitleInfoEntity>>() {

            @Override
            public void call( Subscriber<? super List<TitleInfoEntity>> subscriber ) {
                Log.i( TAG, "titleInfoEntityList.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.i( TAG, "titleInfoEntityList.call : network is connected" );

                    try {

                        String responseRecordedProgramEntities = getTitleInfoEntitiesFromApi();
                        if( null != responseRecordedProgramEntities ) {
                            Log.i( TAG, "titleInfoEntityList.call : retrieved title info entities" );

                            subscriber.onNext( titleInfoEntityJsonMapper.transformTitleInfoEntityCollection( responseRecordedProgramEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.i( TAG, "titleInfoEntityList.call : failed to retrieve title info entities" );

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "titleInfoEntityList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.i( TAG, "titleInfoEntityList.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.i( TAG, "titleInfoEntityList.call : exit" );
            }

        });

    }

    @Override
    public Observable<List<ProgramEntity>> recordedProgramEntityList( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup ) {

        return Observable.create( new Observable.OnSubscribe<List<ProgramEntity>>() {

            @Override
            public void call( Subscriber<? super List<ProgramEntity>> subscriber ) {
                Log.i( TAG, "recordedProgramEntityList.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.i( TAG, "recordedProgramEntityList.call : network is connected" );

                    try {

                        String responseRecordedProgramEntities = getRecordedProgramEntitiesFromApi( descending, startIndex, count, titleRegEx, recGroup, storageGroup );
                        if( null != responseRecordedProgramEntities ) {
                            Log.i( TAG, "recordedProgramEntityList.call : retrieved program entities" );

                            subscriber.onNext( programEntityJsonMapper.transformProgramEntityCollection( responseRecordedProgramEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.i( TAG, "recordedProgramEntityList.call : failed to retrieve program entities" );

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "recordedProgramEntityList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.i( TAG, "recordedProgramEntityList.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.i( TAG, "recordedProgramEntityList.call : exit" );
            }

        });

    }

    @Override
    public Observable<ProgramEntity> recordedProgramById( int chanId, DateTime startTime ) {

        return Observable.create( new Observable.OnSubscribe<ProgramEntity>() {

            @Override
            public void call( Subscriber<? super ProgramEntity> subscriber ) {

                if( isThereInternetConnection() ) {

                    try {

                        String responseProgramDetails = getRecordedProgramDetailsFromApi( chanId, startTime );
                        if( null != responseProgramDetails ) {

                            subscriber.onNext( programEntityJsonMapper.transformProgramEntity( responseProgramDetails ) );
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

    private String getTitleInfoEntitiesFromApi() throws MalformedURLException {

        return ApiConnection.createGET( getMasterBackendUrl() + TITLE_INFO_LIST_URL ).requestSyncCall();
    }

    private String getRecordedProgramEntitiesFromApi( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() );
        sb.append( DvrApi.RECORDED_LIST_BASE_URL );
        sb.append( "?" );
        sb.append( String.format( DESCENDING_QS, descending ) );

        if( startIndex != -1 ) {

            sb.append( "&" );
            sb.append( String.format( START_INDEX_QS, startIndex ) );

        }

        if( count != -1 ) {

            sb.append( "&" );
            sb.append( String.format( COUNT_QS, startIndex ) );

        }

        if( null != titleRegEx && !"".equals( titleRegEx ) ) {

            try {

                sb.append( "&" );
                sb.append( String.format( TITLE_REG_EX_QS, URLEncoder.encode( titleRegEx, "UTF-8" ) ) );

            } catch( UnsupportedEncodingException e ) {

                Log.e( TAG, "getRecordedProgramEntitiesFromApi : error", e );
            }
        }

        if( null != recGroup && !"".equals( recGroup ) ) {

            sb.append( "&" );
            sb.append( String.format( REC_GROUP_QS, recGroup ) );

        }

        if( null != storageGroup && !"".equals( storageGroup ) ) {

            sb.append( "&" );
            sb.append( String.format( STORAGE_GROUP_QS, storageGroup ) );

        }

        return ApiConnection.createGET( sb.toString() ).requestSyncCall();
    }

    private String getRecordedProgramDetailsFromApi( int chanId, DateTime startTime ) throws MalformedURLException {

        String apiUrl = String.format( DvrApi.RECORDED_BASE_URL, chanId, fmt.print( startTime.withZone( DateTimeZone.UTC ) ) );

        return ApiConnection.createGET( getMasterBackendUrl() + apiUrl ).requestSyncCall();
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
        Log.i( TAG, "getMasterBackendUrl : masterBackend=" + masterBackend );

        return masterBackend;
    }

    public String getFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getString( key, "" );
    }

}
