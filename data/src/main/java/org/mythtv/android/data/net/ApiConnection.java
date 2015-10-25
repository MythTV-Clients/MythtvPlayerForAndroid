package org.mythtv.android.data.net;

import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    private URL url;
    private String response;

    private ApiConnection(String url) throws MalformedURLException {

        this.url = new URL( url );

    }

    public static ApiConnection createGET( String url ) throws MalformedURLException {

        return new ApiConnection( url );
    }

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * @return A string response
     */
    @Nullable
    public String requestSyncCall() {

        connectToApi();

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

    private OkHttpClient createClient() {

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout( 10000, TimeUnit.MILLISECONDS );
        okHttpClient.setConnectTimeout( 15000, TimeUnit.MILLISECONDS );

        return okHttpClient;
    }

    @Override
    public String call() throws Exception {

        return requestSyncCall();
    }

}
