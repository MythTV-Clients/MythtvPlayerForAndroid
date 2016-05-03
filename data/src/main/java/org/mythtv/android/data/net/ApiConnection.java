package org.mythtv.android.data.net;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by dmfrey on 8/27/15.
 */
public class ApiConnection implements Callable<String> {

    private static final String TAG = ApiConnection.class.getSimpleName();

    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";

    private static final String ACCEPT_LABEL = "Accept";
    private static final String ACCEPT_VALUE_JSON = "application/json";

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 20 * 1024 * 1024;

    private Context context;
    private URL url;
    private int readTimeout, connectTimeout;
    private String response;

    private ApiConnection( Context context, String url, int readTimeout, int connectTimeout ) throws MalformedURLException {

        this.context = context;
        this.url = new URL( url );
        this.readTimeout = readTimeout;
        this.connectTimeout = connectTimeout;

    }

    public static ApiConnection create( Context context, String url, int readTimeout, int connectTimeout ) throws MalformedURLException {

        return new ApiConnection( context, url, readTimeout, connectTimeout );
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

        OkHttpClient okHttpClient = this.createClient();
        final Request request = new Request.Builder()
                .url( this.url )
                .addHeader( ACCEPT_LABEL, ACCEPT_VALUE_JSON )
                .get()
                .build();

        try {

            this.response = okHttpClient.newCall( request ).execute().body().string();
            Log.d( TAG, "connectToApi : response=" + this.response );

        } catch( IOException e ) {

            Log.e( TAG, "connectToApi : error", e );

        }

    }

    private void connectToApi( FormBody formBody ) {
        Log.d( TAG, "connectToApi : url=" + this.url );

        OkHttpClient okHttpClient = this.createClient();
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

    private OkHttpClient createClient() {

        final OkHttpClient.Builder okHttpClient =
                new OkHttpClient.Builder()
                    .readTimeout( readTimeout, TimeUnit.MILLISECONDS )
                    .connectTimeout( connectTimeout, TimeUnit.MILLISECONDS );

        final File baseDir = context.getCacheDir();
        if( null != baseDir ) {

            final File cacheDir = new File( baseDir, "HttpResponseCache" );
            okHttpClient.cache( new Cache( cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE ) );

        }

        return okHttpClient.build();
    }

    @Override
    public String call() throws Exception {

        return requestSyncCall();
    }

}
