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

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityJsonMapper;
import org.mythtv.android.data.exception.NetworkConnectionException;

import java.io.Reader;
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
public class ContentApiImpl extends AbstractBaseApi implements ContentApi {

    private static final String TAG = ContentApiImpl.class.getSimpleName();

    private static final DateTimeFormatter fmt = DateTimeFormat.forPattern( "yyyy-MM-dd'T'HH:mm:ss'Z'" );

    private final OkHttpClient okHttpClient;
    private final LiveStreamInfoEntityJsonMapper liveStreamInfoEntityJsonMapper;
    private final BooleanJsonMapper booleanJsonMapper;

    public ContentApiImpl( Context context, SharedPreferences sharedPreferences, OkHttpClient okHttpClient, LiveStreamInfoEntityJsonMapper liveStreamInfoEntityJsonMapper, BooleanJsonMapper booleanJsonMapper ) {
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

                        Reader responseLiveStreamInfoEntities = getLiveStreamInfoEntitiesFromApi( filename );
                        if( null != responseLiveStreamInfoEntities ) {
                            Log.d(TAG, "LiveStreamInfoEntityList.call : retrieved LiveStream info entities");

                            subscriber.onNext( liveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntityCollection(responseLiveStreamInfoEntities) );
                            subscriber.onCompleted();

                        } else {
                            Log.d(TAG, "LiveStreamInfoEntityList.call : failed to retrieve LiveStream info entities");

                            subscriber.onError( new NetworkConnectionException() );

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

    private Reader getLiveStreamInfoEntitiesFromApi( String filename ) throws MalformedURLException {

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

}
