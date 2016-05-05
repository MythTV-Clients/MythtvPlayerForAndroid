package org.mythtv.android.data.repository.datasource;

import android.content.Context;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.cache.ProgramCache;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.EncoderEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.ProgramEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityJsonMapper;
import org.mythtv.android.data.net.DvrApi;
import org.mythtv.android.data.net.DvrApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class DvrDataStoreFactory {

    private static final String TAG = DvrDataStoreFactory.class.getSimpleName();

    private final Context context;
    private final ProgramCache recordedProgramCache;
    private final SearchDataStoreFactory searchDataStoreFactory;

    @Inject
    public DvrDataStoreFactory( Context context, ProgramCache recordedProgramCache, SearchDataStoreFactory searchDataStoreFactory ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context || null == recordedProgramCache || null == searchDataStoreFactory ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();
        this.recordedProgramCache = recordedProgramCache;
        this.searchDataStoreFactory = searchDataStoreFactory;

        Log.d( TAG, "initialize : exit" );
    }

    public DvrDataStore create( int chanId, DateTime startTime ) {
        Log.d( TAG, "create : enter" );

        DvrDataStore dvrDataStore;

        if( !this.recordedProgramCache.isExpired() && this.recordedProgramCache.isCached( chanId, startTime ) ) {
            Log.d( TAG, "create : cache is not expired and recordedProgram exists in cache" );

            dvrDataStore = new DiskDvrDataStore( this.recordedProgramCache );

        } else {
            Log.d( TAG, "create : query backend for data" );

            dvrDataStore = createMasterBackendDataStore();

        }

        Log.d( TAG, "create : exit" );
        return dvrDataStore;
    }

    public DvrDataStore createMasterBackendDataStore() {
        Log.d( TAG, "createMasterBackendDataStore : enter" );

        TitleInfoEntityJsonMapper titleInfoEntityJsonMapper = new TitleInfoEntityJsonMapper();
        ProgramEntityJsonMapper programEntityJsonMapper = new ProgramEntityJsonMapper();
        EncoderEntityJsonMapper encoderEntityJsonMapper = new EncoderEntityJsonMapper();
        BooleanJsonMapper booleanJsonMapper = new BooleanJsonMapper();
        DvrApi api = new DvrApiImpl( this.context, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper );

        Log.d( TAG, "createMasterBackendDataStore : exit" );
        return new MasterBackendDvrDataStore( api, this.recordedProgramCache, this.searchDataStoreFactory );
    }

}
