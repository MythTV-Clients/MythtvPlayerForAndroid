package org.mythtv.android.presentation.internal.di.modules;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.mythtv.android.BuildConfig;
import org.mythtv.android.R;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeDeserializer;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeSerializer;
import org.mythtv.android.presentation.internal.di.interceptors.UserAgentInterceptor;

import java.io.File;
import java.lang.reflect.Type;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 5/9/16.
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

        return new Cache( cacheDir, cacheSize );
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
    OkHttpClient provideOkHttpClient( Context context, Cache cache ) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel( HttpLoggingInterceptor.Level.HEADERS );

        return new OkHttpClient.Builder()
                .cache( cache )
                .addNetworkInterceptor( new StethoInterceptor() )
                .addInterceptor( loggingInterceptor )
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
