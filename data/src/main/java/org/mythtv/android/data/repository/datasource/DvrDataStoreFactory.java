package org.mythtv.android.data.repository.datasource;

import android.content.Context;

import org.joda.time.DateTime;
import org.mythtv.android.data.cache.ProgramCache;
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

    private final Context context;
    private final ProgramCache recordedProgramCache;

    @Inject
    public DvrDataStoreFactory(Context context, ProgramCache recordedProgramCache) {

        if( null == context || null == recordedProgramCache ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();
        this.recordedProgramCache = recordedProgramCache;

    }

    public DvrDataStore create( int chanId, DateTime startTime ) {

        DvrDataStore dvrDataStore;

        if( !this.recordedProgramCache.isExpired() && this.recordedProgramCache.isCached( chanId, startTime ) ) {

            dvrDataStore = new DiskDvrDataStore( this.recordedProgramCache );

        } else {

            dvrDataStore = createMasterBackendDataStore();

        }

        return dvrDataStore;
    }

    public DvrDataStore createMasterBackendDataStore() {

        TitleInfoEntityJsonMapper titleInfoEntityJsonMapper = new TitleInfoEntityJsonMapper();
        ProgramEntityJsonMapper programEntityJsonMapper = new ProgramEntityJsonMapper();
        DvrApi api = new DvrApiImpl( this.context, titleInfoEntityJsonMapper, programEntityJsonMapper );

        return new MasterBackendDvrDataStore( api, this.recordedProgramCache );
    }

}
