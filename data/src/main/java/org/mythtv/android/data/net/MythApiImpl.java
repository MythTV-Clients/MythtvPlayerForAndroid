package org.mythtv.android.data.net;

import android.content.Context;
import android.content.SharedPreferences;

import org.mythtv.android.data.entity.BackendInfoEntity;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;

/**
 * Created by dmfrey on 5/30/17.
 */

public class MythApiImpl extends BaseApi implements MythApi {

    private final OkHttpClient okHttpClient;

    public MythApiImpl( final Context context, final SharedPreferences sharedPreferences, final OkHttpClient okHttpClient ) {
        super( context, sharedPreferences );

        if( null == okHttpClient ) {

            throw new IllegalArgumentException( "The constructor parameters cannot be null!!!" );
        }

        this.okHttpClient = okHttpClient;

    }

    @Override
    public Observable<BackendInfoEntity> backendInfo() {

        return null;
    }

}
