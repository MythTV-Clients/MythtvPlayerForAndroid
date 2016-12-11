/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.data.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.EncoderEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.LongJsonMapper;
import org.mythtv.android.data.entity.mapper.ProgramEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityJsonMapper;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.domain.SettingsKeys;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import rx.Observable;
import rx.Subscriber;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/27/15.
 */
public class DvrApiImpl implements DvrApi {

    private static final String TAG = DvrApiImpl.class.getSimpleName();

    private static final DateTimeFormatter fmt = DateTimeFormat.forPattern( "yyyy-MM-dd'T'HH:mm:ss'Z'" );

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final OkHttpClient okHttpClient;
    private final TitleInfoEntityJsonMapper titleInfoEntityJsonMapper;
    private final ProgramEntityJsonMapper programEntityJsonMapper;
    private final EncoderEntityJsonMapper encoderEntityJsonMapper;
    private final BooleanJsonMapper booleanJsonMapper;
    private final LongJsonMapper longJsonMapper;

    public DvrApiImpl( final Context context, final SharedPreferences sharedPreferences, final OkHttpClient okHttpClient, final TitleInfoEntityJsonMapper titleInfoEntityJsonMapper, final ProgramEntityJsonMapper programEntityJsonMapper, final EncoderEntityJsonMapper encoderEntityJsonMapper, final BooleanJsonMapper booleanJsonMapper, final LongJsonMapper longJsonMapper ) {

        if( null == context || null == sharedPreferences || null == okHttpClient || null == titleInfoEntityJsonMapper || null == programEntityJsonMapper || null == encoderEntityJsonMapper || null == booleanJsonMapper || null == longJsonMapper ) {

            throw new IllegalArgumentException( "The constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();
        this.sharedPreferences = sharedPreferences;
        this.okHttpClient = okHttpClient;
        this.titleInfoEntityJsonMapper = titleInfoEntityJsonMapper;
        this.programEntityJsonMapper = programEntityJsonMapper;
        this.encoderEntityJsonMapper = encoderEntityJsonMapper;
        this.booleanJsonMapper = booleanJsonMapper;
        this.longJsonMapper = longJsonMapper;

    }

    @Override
    public Observable<List<TitleInfoEntity>> titleInfoEntityList() {

        return Observable.create( new Observable.OnSubscribe<List<TitleInfoEntity>>() {

            @Override
            public void call( Subscriber<? super List<TitleInfoEntity>> subscriber ) {
                Log.d(TAG, "titleInfoEntityList.call : enter");

                if( isThereInternetConnection() ) {
                    Log.d( TAG, "titleInfoEntityList.call : network is connected" );

                    try {

                        Reader responseRecordedProgramEntities = getTitleInfoEntitiesFromApi();
                        if( null != responseRecordedProgramEntities ) {
                            Log.d( TAG, "titleInfoEntityList.call : retrieved title info entities" );

                            subscriber.onNext( titleInfoEntityJsonMapper.transformTitleInfoEntityCollection( responseRecordedProgramEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d( TAG, "titleInfoEntityList.call : failed to retrieve title info entities" );

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "titleInfoEntityList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d(TAG, "titleInfoEntityList.call : network is not connected");

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d(TAG, "titleInfoEntityList.call : exit");
            }

        });

    }

    @Override
    public Observable<List<ProgramEntity>> recordedProgramEntityList( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup ) {
        Log.d( TAG, "recordedProgramEntityList : enter" );

        Log.d( TAG, "recordedProgramEntityList : exit" );
        return Observable.create( new Observable.OnSubscribe<List<ProgramEntity>>() {

            @Override
            public void call( Subscriber<? super List<ProgramEntity>> subscriber ) {
                Log.d( TAG, "recordedProgramEntityList.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.d( TAG, "recordedProgramEntityList.call : network is connected" );

                    try {

                        Reader responseRecordedProgramEntities = getRecordedProgramEntitiesFromApi( descending, startIndex, count, titleRegEx, recGroup, storageGroup );
                        if( null != responseRecordedProgramEntities ) {
                            Log.d( TAG, "recordedProgramEntityList.call : retrieved program entities" );

                            subscriber.onNext( programEntityJsonMapper.transformProgramEntityCollection( responseRecordedProgramEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d( TAG, "recordedProgramEntityList.call : failed to retrieve program entities" );

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "recordedProgramEntityList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "recordedProgramEntityList.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "recordedProgramEntityList.call : exit" );
            }

        });

    }

    @Override
    public Observable<ProgramEntity> recordedProgramById( final int recordedId, final int chanId, final DateTime startTime ) {

        return Observable.create( new Observable.OnSubscribe<ProgramEntity>() {

            @Override
            public void call( Subscriber<? super ProgramEntity> subscriber ) {

                if( isThereInternetConnection() ) {

                    try {

                        Reader responseProgramDetails = getRecordedProgramDetailsFromApi( recordedId, chanId, startTime );
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

    @Override
    public Observable<List<ProgramEntity>> upcomingProgramEntityList( int startIndex, int count, boolean showAll, int recordId, int recStatus ) {
        Log.d( TAG, "upcomingProgramEntityList : enter" );

        Log.d( TAG, "upcomingProgramEntityList : exit" );
        return Observable.create( new Observable.OnSubscribe<List<ProgramEntity>>() {

            @Override
            public void call( Subscriber<? super List<ProgramEntity>> subscriber ) {
                Log.d( TAG, "upcomingProgramEntityList.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.d( TAG, "upcomingProgramEntityList.call : network is connected" );

                    try {

                        Reader responseUpcomingProgramEntities = getUpcomingProgramEntitiesFromApi( startIndex, count, showAll, recordId, recStatus );
                        if( null != responseUpcomingProgramEntities ) {
                            Log.d( TAG, "upcomingEntityList.call : retrieved program entities" );

                            subscriber.onNext( programEntityJsonMapper.transformProgramEntityCollection( responseUpcomingProgramEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d( TAG, "upcomingProgramEntityList.call : failed to retrieve program entities" );

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "upcomingProgramEntityList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "upcomingProgramEntityList.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "upcomingProgramEntityList.call : exit" );
            }

        });

    }

    @Override
    public Observable<List<EncoderEntity>> encoderEntityList() {

        return Observable.create( new Observable.OnSubscribe<List<EncoderEntity>>() {

            @Override
            public void call( Subscriber<? super List<EncoderEntity>> subscriber ) {
                Log.d( TAG, "encoderEntityList.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.d(TAG, "encoderEntityList.call : network is connected");

                    try {

                        Reader responseEncoderEntities = getEncoderEntitiesFromApi();
                        if( null != responseEncoderEntities ) {
                            Log.d(TAG, "encoderEntityList.call : retrieved encoder entities");

                            subscriber.onNext( encoderEntityJsonMapper.transformEncoderEntityCollection( responseEncoderEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d(TAG, "encoderEntityList.call : failed to retrieve encoder entities");

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "encoderEntityList.call : error", e );

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

    @Override
    public Observable<Boolean> updateWatchedStatus( final int chanId, final DateTime startTime, final boolean watched ) {

        return Observable.create( new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call( Subscriber<? super Boolean> subscriber ) {
                Log.d( TAG, "updateWatchedStatus.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.d( TAG, "updateWatchedStatus.call : network is connected" );

                    try {

                        Reader response = postUpdateWatchedStatus( chanId, startTime, watched );
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
                    Log.d( TAG, "updateWatchedStatus.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "updateWatchedStatus.call : exit" );
            }

        });

    }

    @Override
    public Observable<Long> getBookmark( final int recordedId, final int chanId, final DateTime startTime, final String offsetType) {

        return Observable.create( new Observable.OnSubscribe<Long>() {

            @Override
            public void call( Subscriber<? super Long> subscriber ) {
                Log.d( TAG, "getBookmark.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.d(TAG, "getBookmark.call : network is connected");

                    try {

                        Reader response = getBookmarkFromApi( recordedId, chanId, startTime, offsetType );
                        if( null != response ) {
                            Log.d( TAG, "getBookmark.call : retrieved status update" );

                            subscriber.onNext( longJsonMapper.transformLong( response ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d( TAG, "getBookmark.call : failed to retrieve status update" );

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "getBookmark.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "getBookmark.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "getBookmark.call : exit" );
            }

        });

    }

    private Reader getTitleInfoEntitiesFromApi() throws MalformedURLException {

        return ApiConnection.create( okHttpClient, getMasterBackendUrl() + TITLE_INFO_LIST_URL ).requestSyncCall();
    }

    private Reader getRecordedProgramEntitiesFromApi( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() );
        sb.append( RECORDED_LIST_BASE_URL );
        sb.append( "?" );
        sb.append( String.format( DESCENDING_QS, descending ) );

        if( startIndex != -1 ) {

            sb.append( "&" );
            sb.append( String.format( START_INDEX_QS, startIndex ) );

        }

        if( count != -1 ) {

            sb.append( "&" );
            sb.append( String.format( COUNT_QS, count ) );

        }

        if( null != titleRegEx && !"".equals( titleRegEx ) ) {

            String  dottedTitleRegex = titleRegEx.replaceAll( "[\\$\\(\\)\\*\\+\\?\\[\\]\\^\\{\\|\\}]", "."  );

            try {

                sb.append( "&" );
                sb.append( String.format( TITLE_REG_EX_QS, URLEncoder.encode( dottedTitleRegex, "UTF-8" ) ) );

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

        Log.i( TAG, "getRecordedProgramEntitiesFromApi : url=" + sb.toString() );
        return ApiConnection.create( okHttpClient, sb.toString() ).requestSyncCall();
    }

    private Reader getRecordedProgramDetailsFromApi( final int recordedId, final int chanId, final DateTime startTime ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() );
        sb.append( RECORDED_BASE_URL );
        sb.append( "?" );

        if( recordedId != -1 ) {

            sb.append( String.format( RECORDED_ID_QS, recordedId ) );

        }

        if( chanId != -1 && null != startTime ) {

            sb.append( String.format( CHAN_ID_QS, chanId ) );
            sb.append( "&" );
            fmt.print( startTime.withZone( DateTimeZone.UTC ) );

        }

        Log.i( TAG, "getRecordedProgramDetailsFromApi : apiUrl=" + sb.toString() );
        return ApiConnection.create( okHttpClient, sb.toString() ).requestSyncCall();
    }

    private Reader getUpcomingProgramEntitiesFromApi( final int startIndex, final int count, final boolean showAll, final int recordId, final int recStatus ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() );
        sb.append( UPCOMING_LIST_BASE_URL );
        sb.append( "?" );
        sb.append( String.format( SHOW_ALL_QS, showAll ) );

        if( startIndex != -1 ) {

            sb.append( "&" );
            sb.append( String.format( START_INDEX_QS, startIndex ) );

        }

        if( count != -1 ) {

            sb.append( "&" );
            sb.append( String.format( COUNT_QS, count ) );

        }

        if( recordId != -1 ) {

            sb.append( "&" );
            sb.append( String.format( RECORD_ID_QS, recordId ) );

        }

        if( recStatus != -1 ) {

            sb.append( "&" );
            sb.append( String.format( REC_STATUS_QS, recStatus ) );

        }

        Log.i( TAG, "getUpcomingProgramEntitiesFromApi : url=" + sb.toString() );
        return ApiConnection.create( okHttpClient, sb.toString() ).requestSyncCall();
    }

    private Reader getEncoderEntitiesFromApi() throws MalformedURLException {

        Log.i( TAG, "getEncoderEntitiesFromApi : url=" + ( getMasterBackendUrl() + ENCODER_LIST_BASE_URL ) );
        return ApiConnection.create( okHttpClient, getMasterBackendUrl() + ENCODER_LIST_BASE_URL ).requestSyncCall();
    }

    private Reader postUpdateWatchedStatus( final int chanId, final DateTime startTime, final boolean watched ) throws MalformedURLException {

        Map<String, String> parameters = new HashMap<>();
        parameters.put( "ChanId", String.valueOf( chanId ) );
        parameters.put( "StartTime", fmt.print( startTime.withZone( DateTimeZone.UTC ) ) );
        parameters.put( "Watched", String.valueOf( watched ) );

        Log.i( TAG, "postUpdateWatchedStatus : url=" + ( getMasterBackendUrl() + UPDATE_RECORDED_WATCHED_STATUS_URL ) );
        return ApiConnection.create( okHttpClient, getMasterBackendUrl() + UPDATE_RECORDED_WATCHED_STATUS_URL ).requestSyncCall( parameters );
    }

    private Reader getBookmarkFromApi( final int recordedId, final int chanId, final DateTime startTime, final String offsetType ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() );
        sb.append( BOOKMARK_BASE_URL );
        sb.append( "?" );

        if( recordedId != -1 ) {

            sb.append( String.format( RECORDED_ID_QS, recordedId ) );

        }

        if( chanId != -1 && null != startTime ) {

            sb.append( String.format( CHAN_ID_QS, chanId ) );
            sb.append( "&" );
            fmt.print( startTime.withZone( DateTimeZone.UTC ) );

        }

        if( null != offsetType && !"".equals( offsetType ) ) {

            sb.append( "&" );
            sb.append( String.format( OFFSET_TYPE_QS, offsetType ) );

        }

        Log.i( TAG, "getBookmarkFromApi : apiUrl=" + sb.toString() );
        return ApiConnection.create( okHttpClient, sb.toString() ).requestSyncCall();
    }

    private boolean isThereInternetConnection() {

        boolean isConnected;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = ( networkInfo != null && networkInfo.isConnectedOrConnecting() );

        return isConnected;
    }

    private String getMasterBackendUrl() {

        String host = getFromPreferences( SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getFromPreferences( SettingsKeys.KEY_PREF_BACKEND_PORT );

        String masterBackend = "http://" + host + ":" + port;
        Log.d( TAG, "getMasterBackendUrl : masterBackend=" + masterBackend );

        return masterBackend;
    }

    private String getFromPreferences( String key ) {

        return sharedPreferences.getString( key, "" );
    }

}
