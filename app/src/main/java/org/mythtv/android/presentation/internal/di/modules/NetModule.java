package org.mythtv.android.presentation.internal.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeDeserializer;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeSerializer;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.BuildConfig;
import org.mythtv.android.presentation.R;
import org.mythtv.android.presentation.internal.di.interceptors.UserAgentInterceptor;

import java.io.File;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by dmfrey on 5/9/16.
 */
@Module
public class NetModule {

    public NetModule() {

    }

    @Provides
    @Singleton
    Cache provideOkHttpCache( Context context ) {

        final File cacheDir = new File( context.getCacheDir(), "HttpResponseCache" );
        final int cacheSize = 100 * 1024 * 1024; // 100 MiB
        Cache cache = new Cache( cacheDir, cacheSize );

        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {

        Type dateTimeType = new TypeToken<DateTime>(){}.getType();

        return new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE )
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter( dateTimeType, new DateTimeSerializer() )
                .registerTypeAdapter( dateTimeType, new DateTimeDeserializer() )
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient( Context context, SharedPreferences sharedPreferences, Cache cache ) {

        final int readTimeout = Integer.parseInt( sharedPreferences.getString( SettingsKeys.KEY_PREF_READ_TIMEOUT, "10000" ) );
        final int connectTimeout = Integer.parseInt( sharedPreferences.getString( SettingsKeys.KEY_PREF_CONNECT_TIMEOUT, "10000" ) );

        return new OkHttpClient.Builder()
                .readTimeout( readTimeout, TimeUnit.MILLISECONDS )
                .connectTimeout( connectTimeout, TimeUnit.MILLISECONDS )
                .cache( cache )
                .addNetworkInterceptor( new StethoInterceptor() )
                .addInterceptor( new UserAgentInterceptor( context.getResources().getString( R.string.app_name )+ "/" + BuildConfig.VERSION_NAME ) )
                .build();
    }

    @Provides
    @Singleton
    Picasso providePicasso( Context context, OkHttpClient okHttpClient ) {

        final Picasso picasso = new Picasso.Builder( context )
                .downloader( new OkHttp3Downloader( okHttpClient ) )
                .build();

        Picasso.setSingletonInstance( picasso );

        return picasso;
    }

}
