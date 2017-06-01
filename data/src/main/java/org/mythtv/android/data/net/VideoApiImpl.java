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

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.VideoMetadataInfoEntityJsonMapper;
import org.mythtv.android.data.exception.NetworkConnectionException;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/9/15.
 */
public class VideoApiImpl extends BaseApi implements VideoApi {

    private static final String TAG = VideoApiImpl.class.getSimpleName();

    private final OkHttpClient okHttpClient;
    private final VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper;
    private final BooleanJsonMapper booleanJsonMapper;

    public VideoApiImpl( final Context context, final SharedPreferences sharedPreferences, final OkHttpClient okHttpClient, final VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper, final BooleanJsonMapper booleanJsonMapper ) {
        super( context, sharedPreferences );

        if( null == okHttpClient || null == videoMetadataInfoEntityJsonMapper || null == booleanJsonMapper ) {

            throw new IllegalArgumentException( "The constructor parameters cannot be null!!!" );
        }

        this.okHttpClient = okHttpClient;
        this.videoMetadataInfoEntityJsonMapper = videoMetadataInfoEntityJsonMapper;
        this.booleanJsonMapper = booleanJsonMapper;

    }

    @Override
    public Flowable<VideoMetadataInfoEntity> getVideoList( final String folder, final String sort, final boolean descending, final int startIndex, final int count ) {
        Log.d( TAG, "getVideoList : enter" );

        Log.d( TAG, "getVideoList : exit" );
        return Flowable.create( emitter -> {

                if( isThereInternetConnection() ) {
                    Log.d( TAG, "getVideoList.call : network is connected" );

                    try {

                        String responseVideoEntities = getVideoEntitiesFromApi( folder, sort, descending, startIndex, count );
                        if( null == responseVideoEntities ) {
                            Log.d( TAG, "getVideoList.call : failed to retrieve video entities" );

                            emitter.onError( new NetworkConnectionException() );

                        } else {
                            Log.d( TAG, "getVideoList.call : retrieved video entities" );

                            Observable.fromIterable( videoMetadataInfoEntityJsonMapper.transformVideoMetadataInfoEntityCollection( responseVideoEntities ) )
                                    .subscribe(
                                            emitter::onNext,
                                            emitter::onError,
                                            emitter::onComplete
                                    );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "getVideoList.call : error", e );

                        emitter.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "getVideoList.call : network is not connected" );

                    emitter.onError( new NetworkConnectionException() );

                }

       }, BackpressureStrategy.BUFFER );

    }

    @Override
    public Observable<VideoMetadataInfoEntity> getVideoById( final int id ) {

        return Observable.create( emitter -> {

                if( isThereInternetConnection() ) {

                    try {

                        String responseVideoDetails = getVideoDetailsFromApi( id );
                        if( null == responseVideoDetails ) {

                            emitter.onError( new NetworkConnectionException() );

                        } else {

                            emitter.onNext( videoMetadataInfoEntityJsonMapper.transformVideoMetadataInfoEntity( responseVideoDetails ) );
                            emitter.onComplete();

                        }

                    } catch( Exception e ) {

                        emitter.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {

                    emitter.onError( new NetworkConnectionException() );

                }

       });

    }

    @Override
    public Observable<Boolean> updateWatchedStatus( final int videoId, final boolean watched ) {

        return Observable.create( emitter -> {

                if( isThereInternetConnection() ) {
                    Log.d(TAG, "updateWatchedStatus.call : network is connected");

                    try {

                        String response = postUpdateWatchedStatus( videoId, watched );
                        if( null == response ) {
                            Log.d( TAG, "updateWatchedStatus.call : failed to retrieve status update" );

                            emitter.onError( new NetworkConnectionException() );

                        } else {
                            Log.d( TAG, "updateWatchedStatus.call : retrieved status update" );

                            emitter.onNext( booleanJsonMapper.transformBoolean( response ) );
                            emitter.onComplete();

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "updateWatchedStatus.call : error", e );

                        emitter.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "encoderEntityList.call : network is not connected" );

                    emitter.onError( new NetworkConnectionException() );

                }

        });

    }

    private String getVideoEntitiesFromApi( final String folder, final String sort, final boolean descending, final int startIndex, final int count ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() ).append( VIDEO_LIST_BASE_URL ).append( '?' ).append( String.format( DESCENDING_QS, descending ) );

        if( null != folder && !"".equals( folder ) ) {

            try {

                sb.append( '&' ).append( String.format( FOLDER_QS, URLEncoder.encode( folder, "UTF-8" ) ) );

            } catch( UnsupportedEncodingException e ) {

                Log.e( TAG, "getVideoEntitiesFromApi : error", e );
            }

        }

        if( null != sort && !"".equals( sort ) ) {

            try {

                sb.append( '&' ).append( String.format( SORT_QS, URLEncoder.encode( sort, "UTF-8" ) ) );

            } catch( UnsupportedEncodingException e ) {

                Log.e( TAG, "getVideoEntitiesFromApi : error", e );
            }
        }

        if( startIndex != -1 ) {

            sb.append( '&' ).append( String.format( START_INDEX_QS, startIndex ) );

        }

        if( count != -1 ) {

            sb.append( '&' ).append( String.format( COUNT_QS, startIndex ) );

        }

        return ApiConnection.create( okHttpClient, sb.toString() ).requestSyncCall();
    }

    private String getVideoDetailsFromApi( int id ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() ).append( VIDEO_BASE_URL ).append( String.format( ID_QS, id ) );
        Log.d( TAG, "getVideoDetailsFromApi : url=" + sb.toString() );

        return ApiConnection.create( okHttpClient, sb.toString() ).requestSyncCall();
    }

    private String postUpdateWatchedStatus( final int videoId, final boolean watched ) throws MalformedURLException {

        Map<String, String> parameters = new HashMap<>();
        parameters.put( "Id", String.valueOf( videoId ) );
        parameters.put( "Watched", String.valueOf( watched ) );

        Log.i( TAG, "postUpdateRecordingWatchedStatus : url=" + ( getMasterBackendUrl() + UPDATE_VIDEO_WATCHED_STATUS_URL ) );
        return ApiConnection.create( okHttpClient, getMasterBackendUrl() + UPDATE_VIDEO_WATCHED_STATUS_URL ).requestSyncCall( parameters );
    }

}
