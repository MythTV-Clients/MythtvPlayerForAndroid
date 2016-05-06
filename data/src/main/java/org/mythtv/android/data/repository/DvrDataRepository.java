package org.mythtv.android.data.repository;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.mapper.EncoderEntityDataMapper;
import org.mythtv.android.data.entity.mapper.ProgramEntityDataMapper;
import org.mythtv.android.data.entity.mapper.SearchResultEntityDataMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityDataMapper;
import org.mythtv.android.data.repository.datasource.ContentDataStore;
import org.mythtv.android.data.repository.datasource.ContentDataStoreFactory;
import org.mythtv.android.data.repository.datasource.DvrDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;
import org.mythtv.android.data.repository.datasource.SearchDataStore;
import org.mythtv.android.data.repository.datasource.SearchDataStoreFactory;
import org.mythtv.android.domain.Encoder;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.TitleInfo;
import org.mythtv.android.domain.repository.DvrRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class DvrDataRepository implements DvrRepository {

    private static final String TAG = DvrDataRepository.class.getSimpleName();

    private final DvrDataStoreFactory dvrDataStoreFactory;
    private final SearchDataStoreFactory searchDataStoreFactory;
    private final ContentDataStoreFactory contentDataStoreFactory;

    @Inject
    public DvrDataRepository( DvrDataStoreFactory dvrDataStoreFactory, SearchDataStoreFactory searchDataStoreFactory, ContentDataStoreFactory contentDataStoreFactory ) {

        this.dvrDataStoreFactory = dvrDataStoreFactory;
        this.searchDataStoreFactory = searchDataStoreFactory;
        this.contentDataStoreFactory = contentDataStoreFactory;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<TitleInfo>> titleInfos() {
        Log.d( TAG, "titleInfos : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        final SearchDataStore searchDataStore = this.searchDataStoreFactory.createWriteSearchDataStore();

        return dvrDataStore.recordedProgramEntityList( true, -1, -1, null, null, null )
//                .subscribeOn( Schedulers.io() )
//                .observeOn( AndroidSchedulers.mainThread() )
                .doOnError( throwable -> Log.e( TAG, "titleInfos : error", throwable ) )
//                .flatMap( Observable::from )
//                .filter( programEntity -> !programEntity.getRecording().getRecGroup().equalsIgnoreCase( "LiveTV" ) || !programEntity.getRecording().getStorageGroup().equalsIgnoreCase( "LiveTV" ) )
//                .toList()
                .map( recordedProgramEntities -> SearchResultEntityDataMapper.transformPrograms( recordedProgramEntities ) )
                .doOnNext( searchResultEntities -> searchDataStore.refreshRecordedProgramData( searchResultEntities ) )
                .flatMap( searchResultEntities -> dvrDataStore.titleInfoEntityList() )
                .map( titleInfoEntities -> TitleInfoEntityDataMapper.transform( titleInfoEntities ) );

//        return dvrDataStore.titleInfoEntityList()
//                .map( titleInfoEntities -> TitleInfoEntityDataMapper.transform( titleInfoEntities ) )
//                .doOnError( throwable -> Log.e( TAG, "titleInfos : error", throwable ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<Program>> recordedPrograms( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {
        Log.d( TAG, "recordedPrograms : enter" );
        Log.d( TAG, "recordedPrograms : descending=" + descending + ", startIndex=" + startIndex + ", count=" + count + ", titleRegEx=" + titleRegEx + ", recGroup=" + recGroup + ", storageGroup=" + storageGroup );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();
        final SearchDataStore searchDataStore = this.searchDataStoreFactory.createWriteSearchDataStore();

        Observable<List<ProgramEntity>> programEntities = dvrDataStore.recordedProgramEntityList( descending, startIndex, count, titleRegEx, recGroup, storageGroup )
                .flatMap( Observable::from )
//                .filter( programEntity -> !programEntity.getRecording().getRecGroup().equalsIgnoreCase( "LiveTV" ) || !programEntity.getRecording().getStorageGroup().equalsIgnoreCase( "LiveTV" ) )
                .toList();
        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntities = contentDataStore.liveStreamInfoEntityList( null );

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

        programEntities
                .flatMap( Observable::from )
                .filter( programEntity -> !programEntity.getRecording().getRecGroup().equalsIgnoreCase( "LiveTV" ) || !programEntity.getRecording().getStorageGroup().equalsIgnoreCase( "LiveTV" ) )
                .toList()
                .map( recordedProgramEntities -> SearchResultEntityDataMapper.transformPrograms( recordedProgramEntities ) )
                .doOnNext( searchResultEntities -> searchDataStore.refreshRecordedProgramData( searchResultEntities ) );

        return recordedProgramEntityList
                .doOnError( throwable -> Log.e( TAG, "recordedPrograms : error", throwable ) )
                .map( recordedProgramEntities -> ProgramEntityDataMapper.transform( recordedProgramEntities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<Program> recordedProgram( int chanId, DateTime startTime ) {
        Log.d( TAG, "recordedProgram : enter" );
        Log.d( TAG, "recordedProgram : chanId=" + chanId + ", startTime=" + startTime );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();
        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        Observable<ProgramEntity> programEntity = dvrDataStore.recordedProgramEntityDetails( chanId, startTime );
        Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntity = programEntity
                .flatMap(recordedProgramEntity -> contentDataStore.liveStreamInfoEntityList(recordedProgramEntity.getFileName()));

        Observable<ProgramEntity> recordedProgramEntity = Observable.zip( programEntity, liveStreamInfoEntity, ( programEntity1, liveStreamInfoEntityList ) -> {

            if( null != liveStreamInfoEntityList && !liveStreamInfoEntityList.isEmpty() ) {

                programEntity1.setLiveStreamInfoEntity( liveStreamInfoEntityList.get( 0 ) );

            }

            Log.d( TAG, "recordedProgram : programEntity=" + programEntity1.toString() );
            return programEntity1;
        });

        return recordedProgramEntity
                .doOnError( throwable -> Log.e( TAG, "recordedProgram : error", throwable ) )
                .map( recordedProgram -> ProgramEntityDataMapper.transform( recordedProgram ) );
    }

    @Override
    public Observable<List<Program>> upcoming( int startIndex, int count, boolean showAll, int recordId, int recStatus ) {
        Log.d( TAG, "upcoming : enter" );
        Log.d( TAG, "upcoming : startIndex=" + startIndex + ", count=" + count + ", showAll=" + showAll + ", recordId=" + recordId + ", recStatus=" + recStatus );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.upcomingProgramEntityList( startIndex, count, showAll, recordId, recStatus )
                .doOnError( throwable -> Log.e( TAG, "upcoming : error", throwable ) )
                .map( ProgramEntityDataMapper::transform );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<Program>> recent() {
        Log.d( TAG, "recent : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.recordedProgramEntityList( true, 1, 10, null, null, "Default" )
                .flatMap( Observable::from )
                .toList()
                .doOnError( throwable -> Log.e( TAG, "recent : error", throwable ) )
                .map( recordedProgramEntities -> ProgramEntityDataMapper.transform( recordedProgramEntities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<Encoder>> encoders() {
        Log.d( TAG, "encoders : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.encoderEntityList()
                .doOnError( throwable -> Log.e( TAG, "encoders : error", throwable ) )
                .map( encoderEntities -> EncoderEntityDataMapper.transformCollection( encoderEntities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<Boolean> updateWatchedStatus(final int chanId, final DateTime startTime, final boolean watched ) {
        Log.d( TAG, "updateWatchedStatus : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.updateWatchedStatus( chanId, startTime, watched )
                .doOnError( throwable -> Log.e( TAG, "updateWatchedStatus : error", throwable ) )
                .doOnCompleted( () -> dvrDataStore.recordedProgramEntityList( true, -1, -1, null, null, null ) );
    }

}
