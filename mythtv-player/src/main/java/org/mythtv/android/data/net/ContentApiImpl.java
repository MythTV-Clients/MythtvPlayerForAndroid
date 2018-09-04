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
import android.util.Log;

import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityJsonMapper;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.domain.Media;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.OkHttpClient;
import rx.Observable;
import rx.Subscriber;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 10/17/15.
 */
@SuppressWarnings( "PMD" )
public class ContentApiImpl extends BaseApi implements ContentApi {

    private static final String TAG = ContentApiImpl.class.getSimpleName();

    private final OkHttpClient okHttpClient;
    private final LiveStreamInfoEntityJsonMapper liveStreamInfoEntityJsonMapper;
    private final BooleanJsonMapper booleanJsonMapper;

    public ContentApiImpl( final Context context, final SharedPreferences sharedPreferences, final OkHttpClient okHttpClient, final LiveStreamInfoEntityJsonMapper liveStreamInfoEntityJsonMapper, final BooleanJsonMapper booleanJsonMapper ) {
        super( context, sharedPreferences );

        if( null == okHttpClient || null == liveStreamInfoEntityJsonMapper || null == booleanJsonMapper ) {

            throw new IllegalArgumentException( "The constructor parameters cannot be null!!!" );
        }

        this.okHttpClient = okHttpClient;
        this.liveStreamInfoEntityJsonMapper = liveStreamInfoEntityJsonMapper;
        this.booleanJsonMapper = booleanJsonMapper;

    }

    @Override
    public Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList( final String filename ) {

        return Observable.create( new Observable.OnSubscribe<List<LiveStreamInfoEntity>>() {

            @Override
            public void call( Subscriber<? super List<LiveStreamInfoEntity>> subscriber ) {
                Log.d(TAG, "LiveStreamInfoEntityList.call : enter");

                if( isThereInternetConnection() ) {
                    Log.d(TAG, "LiveStreamInfoEntityList.call : network is connected");

                    try {

                        String responseLiveStreamInfoEntities = getLiveStreamInfoEntitiesFromApi( filename );
                        if( null == responseLiveStreamInfoEntities ) {
                            Log.d( TAG, "LiveStreamInfoEntityList.call : failed to retrieve LiveStream info entities" );

                            subscriber.onError( new NetworkConnectionException() );

                        } else {
                            Log.d( TAG, "LiveStreamInfoEntityList.call : retrieved LiveStream info entities" );

                            subscriber.onNext( liveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntityCollection( responseLiveStreamInfoEntities) );
                            subscriber.onCompleted();

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "LiveStreamInfoEntityList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "LiveStreamInfoEntityList.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "LiveStreamInfoEntityList.call : exit" );
            }

        });

    }

    @Override
    public Observable<LiveStreamInfoEntity> addLiveStream( final Media media, final int id ) {

        return Observable.create( new Observable.OnSubscribe<LiveStreamInfoEntity>() {

            @Override
            public void call( Subscriber<? super LiveStreamInfoEntity> subscriber ) {
                Log.d(TAG, "LiveStreamInfoEntity.call : enter");

                if( isThereInternetConnection() ) {
                    Log.d(TAG, "LiveStreamInfoEntity.call : network is connected");

                    try {

                        switch( media ) {

                            case PROGRAM :

                                String responseAddRecordingLiveStreamInfo = addRecordingLiveStreamInfoEntityFromApi( id );
                                if( null == responseAddRecordingLiveStreamInfo ) {
                                    Log.d( TAG, "LiveStreamInfoEntity.call : failed to add recording LiveStream" );

                                    subscriber.onError( new NetworkConnectionException() );

                                } else {
                                    Log.d( TAG, "LiveStreamInfoEntity.call : added recording LiveStream info" );

                                    subscriber.onNext( liveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntity( responseAddRecordingLiveStreamInfo) );
                                    subscriber.onCompleted();

                                }

                                break;

                            case VIDEO :

                                String responseAddVideoLiveStreamInfo = addVideoLiveStreamInfoEntityFromApi( id );
                                if( null == responseAddVideoLiveStreamInfo ) {
                                    Log.d( TAG, "LiveStreamInfoEntity.call : failed to add video LiveStream" );

                                    subscriber.onError( new NetworkConnectionException() );

                                } else {
                                    Log.d( TAG, "LiveStreamInfoEntity.call : added video LiveStream info" );

                                    subscriber.onNext( liveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntity( responseAddVideoLiveStreamInfo) );
                                    subscriber.onCompleted();

                                }

                                break;

                            default :
                                Log.d( TAG, "LiveStreamInfoEntity.call : media type not supported" );

                                subscriber.onError( new NetworkConnectionException() );

                                break;
                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "LiveStreamInfoEntity.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "LiveStreamInfoEntity.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "LiveStreamInfoEntity.call : exit" );
            }

        });

    }

    @Override
    public Observable<Boolean> removeLiveStream( final int id ) {

        return Observable.create( new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call( Subscriber<? super Boolean> subscriber ) {
                Log.d(TAG, "LiveStreamInfoEntity.call : enter");

                if( isThereInternetConnection() ) {
                    Log.d(TAG, "LiveStreamInfoEntity.call : network is connected");

                    try {

                        String responseRemoveLiveStreamInfoEntity = removeLiveStreamInfoEntityFromApi( id );
                        if( null == responseRemoveLiveStreamInfoEntity ) {
                            Log.d( TAG, "LiveStreamInfoEntity.call : failed to remove LiveStream info entity" );

                            subscriber.onError( new NetworkConnectionException() );

                        } else {
                            Log.d( TAG, "LiveStreamInfoEntity.call : removed LiveStream info entity" );

                            subscriber.onNext( booleanJsonMapper.transformBoolean( responseRemoveLiveStreamInfoEntity ) );
                            subscriber.onCompleted();

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "LiveStreamInfoEntity.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "LiveStreamInfoEntityL.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "LiveStreamInfoEntity.call : exit" );
            }

        });

    }

    private String getLiveStreamInfoEntitiesFromApi( String filename ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() ).append( LIVE_STREAM_INFO_LIST_BASE_URL );

        if( null != filename && !"".equals( filename ) ) {

            try {

                String encodedFilename = URLEncoder.encode( filename, "UTF-8" );
                encodedFilename = encodedFilename.replaceAll( "%2F", "/" );
                encodedFilename = encodedFilename.replaceAll( "\\+", "%20" );

                sb.append( '?' ).append( String.format( FILENAME_QS, encodedFilename ) );

            } catch( UnsupportedEncodingException e ) {

                Log.e( TAG, "addLiveStreamFromApi : error", e );

            }

        }

        Log.d( TAG, "getLiveStreamInfoEntitiesFromApi : url=" + sb.toString() );
        return ApiConnection.create( okHttpClient, sb.toString() ).requestSyncCall();
    }

    private String addRecordingLiveStreamInfoEntityFromApi( final int id ) throws MalformedURLException {
        Log.d( TAG, "addRecordingLiveStreamInfoEntityFromApi : enter" );

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() ).append( ADD_RECORDING_LIVE_STREAM_BASE_URL )
                .append( '?' ).append( String.format( RECORDEDID_QS, id ) )
                .append( '&' ).append( WIDTH_QS );

        Log.d( TAG, "addRecordingLiveStreamInfoEntityFromApi : url=" + sb.toString() );
        return ApiConnection.create( okHttpClient, sb.toString() ).requestSyncCall();
    }

    private String addVideoLiveStreamInfoEntityFromApi( final int id ) throws MalformedURLException {
        Log.d( TAG, "addVideoLiveStreamInfoEntityFromApi : enter" );

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() ).append( ADD_VIDEO_LIVE_STREAM_BASE_URL )
                .append( '?' ).append( String.format( ID_QS, id ) )
                .append( '&' ).append( WIDTH_QS );

        Log.d( TAG, "addVideoLiveStreamInfoEntityFromApi : url=" + sb.toString() );
        return ApiConnection.create( okHttpClient, sb.toString() ).requestSyncCall();
    }

    private String removeLiveStreamInfoEntityFromApi( final int id ) throws MalformedURLException {
        Log.d( TAG, "removeLiveStreamInfoEntityFromApi : enter" );

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() ).append( REMOVE_LIVE_STREAM_BASE_URL )
                .append( '?' ).append( String.format( ID_QS, id ) );

        Log.d( TAG, "removeLiveStreamInfoEntityFromApi : url=" + sb.toString() );
        return ApiConnection.create( okHttpClient, sb.toString() ).requestSyncCall();
    }

}
