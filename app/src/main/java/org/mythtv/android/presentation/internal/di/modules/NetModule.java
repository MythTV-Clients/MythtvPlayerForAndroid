package org.mythtv.android.presentation.internal.di.modules;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.mythtv.android.BuildConfig;
import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.interceptors.UserAgentInterceptor;

import java.io.File;

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
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache( final Context context ) {

        final File cacheDir = new File( context.getCacheDir(), "HttpResponseCache" );
        final int cacheSize = 100 * 1024 * 1024; // 100 MiB

        return new Cache( cacheDir, cacheSize );
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient( final Context context, final Cache cache ) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel( HttpLoggingInterceptor.Level.HEADERS );

        return new OkHttpClient.Builder()
                .cache( cache )
                .addNetworkInterceptor( new StethoInterceptor() )
                .addInterceptor( loggingInterceptor )
                .addInterceptor( new UserAgentInterceptor( context.getResources().getString( R.string.app_name )+ "/" + BuildConfig.VERSION_NAME ) )
                .build();
    }

}
