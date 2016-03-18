package org.mythtv.android.data.repository.datasource;

import android.content.Context;
import android.util.Log;

import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityJsonMapper;
import org.mythtv.android.data.net.ContentApi;
import org.mythtv.android.data.net.ContentApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class ContentDataStoreFactory {

    private static final String TAG = ContentDataStoreFactory.class.getSimpleName();

    private final Context context;

    @Inject
    public ContentDataStoreFactory( Context context ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();

        Log.d( TAG, "initialize : exit" );
    }

    public ContentDataStore create() {
        Log.d( TAG, "create : enter" );

        Log.d( TAG, "create : exit" );
        return createMasterBackendDataStore();
    }

    public ContentDataStore createMasterBackendDataStore() {
        Log.d( TAG, "createMasterBackendDataStore : enter" );

        LiveStreamInfoEntityJsonMapper liveStreamInfoEntityJsonMapper = new LiveStreamInfoEntityJsonMapper();
        BooleanJsonMapper booleanJsonMapper = new BooleanJsonMapper();
        ContentApi api = new ContentApiImpl( this.context, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Log.d( TAG, "createMasterBackendDataStore : exit" );
        return new MasterBackendContentDataStore( api );
    }

}
