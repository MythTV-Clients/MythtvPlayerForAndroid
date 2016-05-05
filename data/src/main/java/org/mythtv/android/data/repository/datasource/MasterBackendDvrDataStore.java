package org.mythtv.android.data.repository.datasource;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.cache.ProgramCache;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.mapper.SearchResultEntityDataMapper;
import org.mythtv.android.data.net.DvrApi;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by dmfrey on 8/27/15.
 */
public class MasterBackendDvrDataStore implements DvrDataStore {

    private static final String TAG = MasterBackendDvrDataStore.class.getSimpleName();

    private final DvrApi api;
    private final ProgramCache recordedProgramCache;
    private final SearchDataStoreFactory searchDataStoreFactory;

    private final Action1<ProgramEntity> saveToCacheAction =
            programEntity -> {

                if( null != programEntity ) {

                    MasterBackendDvrDataStore.this.recordedProgramCache.put( programEntity );

                }

            };

    private final Action1<List<TitleInfoEntity>> removeStaleTitleInfosDbAction =
            titleInfoEntities -> {

                if( null != titleInfoEntities ) {

                    final SearchDataStore searchDataStore = MasterBackendDvrDataStore.this.searchDataStoreFactory.createWriteSearchDataStore();

                    Observable
                            .from( titleInfoEntities )
                            .toList()
                            .subscribe( searchDataStore::refreshTitleInfoData );
                }

            };

    private final Action1<List<ProgramEntity>> saveRecordedProgramsToDbAction =
            programEntities -> {

                if( null != programEntities ) {

                    final SearchDataStore searchDataStore = MasterBackendDvrDataStore.this.searchDataStoreFactory.createWriteSearchDataStore();

                    Observable
                            .from( programEntities )
                            .toList()
                            .map( SearchResultEntityDataMapper::transformPrograms )
                            .subscribe( searchDataStore::refreshRecordedProgramData );
                }

            };

    public MasterBackendDvrDataStore( DvrApi api, ProgramCache recordedProgramCache, SearchDataStoreFactory searchDataStoreFactory ) {

        this.api = api;
        this.recordedProgramCache = recordedProgramCache;
        this.searchDataStoreFactory = searchDataStoreFactory;

    }

    @Override
    public Observable<List<TitleInfoEntity>> titleInfoEntityList() {
        Log.d( TAG, "titleInfoEntityList : enter" );

        return this.api.titleInfoEntityList()
                .doOnNext( removeStaleTitleInfosDbAction )
                .doOnError( e -> Log.e( TAG, "titleInfoEntityList : error", e ) );
    }

    @Override
    public Observable<List<ProgramEntity>> recordedProgramEntityList( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {
        Log.d( TAG, "recordedProgramEntityList : enter" );

        Log.d( TAG, "recordedProgramEntityList : descending=" + descending + ", startIndex=" + startIndex + ", count=" + count + ", titleRegEx=" + titleRegEx + ", recGroup=" + recGroup + ", storageGroup=" + storageGroup );

        titleInfoEntityList();

        return this.api.recordedProgramEntityList( descending, startIndex, count, titleRegEx, recGroup, storageGroup )
                .flatMap( Observable::from )
                .filter( programEntity -> !programEntity.getRecording().getRecGroup().equalsIgnoreCase( "LiveTV" ) || !programEntity.getRecording().getStorageGroup().equalsIgnoreCase( "LiveTV" ) || "Deleted".equalsIgnoreCase( programEntity.getRecording().getRecGroup() ) )
                .toList()
                .doOnNext( saveRecordedProgramsToDbAction )
                .doOnError( e -> Log.e( TAG, "recordedProgramEntityList : error", e ) );
    }

    @Override
    public Observable<ProgramEntity> recordedProgramEntityDetails( int chanId, DateTime startTime ) {
        Log.d( TAG, "recordedProgramEntityDetails : enter" );

        Log.d( TAG, "recordedProgramEntityList : chanId=" + chanId + ", startTime=" + startTime );

        return this.api.recordedProgramById( chanId, startTime )
                .doOnNext( saveToCacheAction )
                .doOnError( e -> Log.e( TAG, "recordedProgramEntityList : error", e ) );
    }

    @Override
    public Observable<List<ProgramEntity>> upcomingProgramEntityList( int startIndex, int count, boolean showAll, int recordId, int recStatus ) {
        Log.d( TAG, "upcomingProgramEntityList : enter" );

        Log.d( TAG, "recordedProgramEntityList : startIndex=" + startIndex + ", count=" + count + ", showAll=" + showAll + ", recordId=" + recordId + ", recStatus=" + recStatus );

        return this.api.upcomingProgramEntityList( startIndex, count, showAll, recordId, recStatus );
    }

    @Override
    public Observable<List<EncoderEntity>> encoderEntityList() {
        Log.d( TAG, "encoderEntityList : enter" );

        return this.api.encoderEntityList();
    }

    @Override
    public Observable<Boolean> updateWatchedStatus( int chanId, DateTime startTime, boolean watched ) {
        Log.d( TAG, "updateWatchedStatus : enter" );

        Log.d( TAG, "updateWatchedStatus : chanId=" + chanId + ", startTime=" + startTime + ", watched=" + watched );

        return this.api.updateWatchedStatus( chanId, startTime, watched );
    }

}
