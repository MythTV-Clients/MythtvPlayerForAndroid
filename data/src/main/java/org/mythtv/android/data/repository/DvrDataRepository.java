package org.mythtv.android.data.repository;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityDataMapper;
import org.mythtv.android.data.entity.mapper.ProgramEntityDataMapper;
import org.mythtv.android.data.entity.mapper.SearchResultEntityDataMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityDataMapper;
import org.mythtv.android.data.repository.datasource.ContentDataStore;
import org.mythtv.android.data.repository.datasource.ContentDataStoreFactory;
import org.mythtv.android.data.repository.datasource.DvrDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;
import org.mythtv.android.data.repository.datasource.SearchDataStore;
import org.mythtv.android.data.repository.datasource.SearchDataStoreFactory;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.TitleInfo;
import org.mythtv.android.domain.repository.DvrRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class DvrDataRepository implements DvrRepository {

    private static final String TAG = DvrDataRepository.class.getSimpleName();

    private final DvrDataStoreFactory dvrDataStoreFactory;
    private final TitleInfoEntityDataMapper titleInfoEntityDataMapper;
    private final ProgramEntityDataMapper programEntityDataMapper;
    private final SearchDataStoreFactory searchDataStoreFactory;
    private final SearchResultEntityDataMapper searchResultEntityDataMapper;
    private final ContentDataStoreFactory contentDataStoreFactory;
    private final LiveStreamInfoEntityDataMapper liveStreamInfoEntityDataMapper;

    @Inject
    public DvrDataRepository( DvrDataStoreFactory dvrDataStoreFactory, TitleInfoEntityDataMapper titleInfoEntityDataMapper, ProgramEntityDataMapper programEntityDataMapper, SearchDataStoreFactory searchDataStoreFactory, SearchResultEntityDataMapper searchResultEntityDataMapper, ContentDataStoreFactory contentDataStoreFactory, LiveStreamInfoEntityDataMapper liveStreamInfoEntityDataMapper ) {

        this.dvrDataStoreFactory = dvrDataStoreFactory;
        this.titleInfoEntityDataMapper = titleInfoEntityDataMapper;
        this.programEntityDataMapper = programEntityDataMapper;
        this.searchDataStoreFactory = searchDataStoreFactory;
        this.searchResultEntityDataMapper = searchResultEntityDataMapper;
        this.contentDataStoreFactory = contentDataStoreFactory;
        this.liveStreamInfoEntityDataMapper = liveStreamInfoEntityDataMapper;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<TitleInfo>> titleInfos() {
        Log.d( TAG, "titleInfos : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        final SearchDataStore searchDataStore = this.searchDataStoreFactory.createWriteSearchDataStore();

        dvrDataStore.recordedProgramEntityList( true, -1, -1, null, null, null )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .doOnError( throwable -> Log.e( TAG, "titleInfos : error", throwable ) )
                .map( recordedProgramEntities -> this.searchResultEntityDataMapper.transformPrograms( recordedProgramEntities ) )
                .subscribe( searchResultEntities -> searchDataStore.refreshRecordedProgramData( searchResultEntities ) );

        return dvrDataStore.titleInfoEntityList()
                .map( titleInfoEntities -> this.titleInfoEntityDataMapper.transform( titleInfoEntities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<Program>> recordedPrograms( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {
        Log.d( TAG, "recordedPrograms : enter" );
        Log.d( TAG, "recordedPrograms : descending=" + descending + ", startIndex=" + startIndex + ", count=" + count + ", titleRegEx=" + titleRegEx + ", recGroup=" + recGroup + ", storageGroup=" + storageGroup );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<List<ProgramEntity>> programEntities = dvrDataStore.recordedProgramEntityList(descending, startIndex, count, titleRegEx, recGroup, storageGroup);
        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntities = contentDataStore.liveStreamInfoEntityList(null);

        Observable<List<ProgramEntity>> recordedProgramEntityList = Observable.zip( programEntities, liveStreamInfoEntities, ( programEntityList, liveStreamInfoEntityList ) -> {

            if( null != liveStreamInfoEntityList && !liveStreamInfoEntityList.isEmpty() ) {

                for( ProgramEntity programEntity : programEntityList ) {

                    for( LiveStreamInfoEntity liveStreamInfoEntity : liveStreamInfoEntityList ) {

                        if( liveStreamInfoEntity.getSourceFile().endsWith( programEntity.getFileName() ) ) {

                            programEntity.setLiveStreamInfoEntity( liveStreamInfoEntityList.get( 0 ) );

                        }

                    }

                }
            }

            return programEntityList;
        });

        return recordedProgramEntityList
                .map( recordedProgramEntities -> this.programEntityDataMapper.transform( recordedProgramEntities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<Program> recordedProgram( int chanId, DateTime startTime ) {
        Log.d( TAG, "recordedProgram : enter" );
        Log.d( TAG, "recordedProgram : chanId=" + chanId + ", startTime=" + startTime );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.create( chanId, startTime );
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<ProgramEntity> programEntity = dvrDataStore.recordedProgramEntityDetails( chanId, startTime );
        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntity = programEntity
                .flatMap(recordedProgramEntity -> contentDataStore.liveStreamInfoEntityList(recordedProgramEntity.getFileName()));

        Observable<ProgramEntity> recordedProgramEntity = Observable.zip(programEntity, liveStreamInfoEntity, (programEntity1, liveStreamInfoEntityList) -> {

            if (null != liveStreamInfoEntityList && !liveStreamInfoEntityList.isEmpty()) {

                programEntity1.setLiveStreamInfoEntity(liveStreamInfoEntityList.get(0));

            }

            Log.d( TAG, "recordedProgram : programEntity=" + programEntity1.toString() );
            return programEntity1;
        });

        return recordedProgramEntity
                .map(recordedProgram -> this.programEntityDataMapper.transform(recordedProgram));
    }

}
