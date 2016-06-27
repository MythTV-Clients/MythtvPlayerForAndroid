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

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by dmfrey on 8/27/15.
 */
public class ApiConnection implements Callable<String> {

    private static final String TAG = ApiConnection.class.getSimpleName();

    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";

    private static final String ACCEPT_LABEL = "Accept";
    private static final String ACCEPT_VALUE_JSON = "application/json";

    private OkHttpClient okHttpClient;

    private URL url;
    private String response;

    private ApiConnection( OkHttpClient okHttpClient, String url ) throws MalformedURLException {

        this.okHttpClient = okHttpClient;
        this.url = new URL( url );

    }

    public static ApiConnection create( OkHttpClient okHttpClient, String url ) throws MalformedURLException {

        return new ApiConnection( okHttpClient, url );
    }

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * Performs an HTTP GET
     *
     * @return A string response
     */
    @Nullable
    public String requestSyncCall() {

        connectToApi();

        return response;
    }

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * Performs an HTTP POST
     *
     * @return A string response
     */
    @Nullable
    public String requestSyncCall( Map<String, String> parameters ) {

        FormBody.Builder builder = new FormBody.Builder();

        if( null != parameters && !parameters.isEmpty() ) {

            for( String key : parameters.keySet() ) {
                Log.i( TAG, "requestSyncCall : key=" + key + ", value=" + parameters.get( key ) );
                builder.add( key, parameters.get( key ) );

            }

        }

        connectToApi( builder.build() );

        return response;
    }

    private void connectToApi() {
        Log.d( TAG, "connectToApi : url=" + this.url );

        OkHttpClient okHttpClient = this.okHttpClient;
        final Request request = new Request.Builder()
                .url( this.url )
                .addHeader( ACCEPT_LABEL, ACCEPT_VALUE_JSON )
                .get()
                .build();

        try {

            this.response = okHttpClient.newCall( request ).execute().body().string();

//            Logging my be causes of OutOfMemory
//            Log.d( TAG, "connectToApi : response=" + this.response );

        } catch( IOException e ) {

            Log.e( TAG, "connectToApi : error", e );

        }

    }

    private void connectToApi( FormBody formBody ) {
        Log.d( TAG, "connectToApi : url=" + this.url );

        OkHttpClient okHttpClient = this.okHttpClient;
        final Request request = new Request.Builder()
                .url( this.url )
                .addHeader( ACCEPT_LABEL, ACCEPT_VALUE_JSON )
                .post( formBody )
                .build();

        try {

            this.response = okHttpClient.newCall( request ).execute().body().string();

//            Logging my be causes of OutOfMemory
//            Log.d( TAG, "connectToApi : response=" + this.response );

        } catch( IOException e ) {

            Log.e( TAG, "connectToApi : error", e );

        }

    }

    @Override
    public String call() throws Exception {

        return requestSyncCall();
    }

}