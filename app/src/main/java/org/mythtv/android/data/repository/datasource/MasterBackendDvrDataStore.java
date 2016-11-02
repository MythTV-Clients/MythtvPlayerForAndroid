/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.data.repository.datasource;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.CommercialBreakEntity;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.mapper.MediaItemDataMapper;
import org.mythtv.android.data.net.DvrApi;
import org.mythtv.android.domain.MediaItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by dmfrey on 8/27/15.
 */
public class MasterBackendDvrDataStore implements DvrDataStore {

    private static final String TAG = MasterBackendDvrDataStore.class.getSimpleName();

    private final DvrApi api;
    private final SearchDataStoreFactory searchDataStoreFactory;

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
                            .map( entities -> {

                                try {

                                    return MediaItemDataMapper.transformPrograms( programEntities );

                                } catch( UnsupportedEncodingException e ) {
                                    Log.e( TAG, "saveRecordedProgramsToDbAction : error", e );
                                }

                                return new ArrayList<MediaItem>();
                            })
                            .subscribe( searchDataStore::refreshRecordedProgramData );
                }

            };

    public MasterBackendDvrDataStore( final DvrApi api, final SearchDataStoreFactory searchDataStoreFactory ) {

        this.api = api;
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
    public Observable<List<ProgramEntity>> recordedProgramEntityList( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup ) {
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
    public Observable<ProgramEntity> recordedProgramEntityDetails( final int recordedId ) {
        Log.d( TAG, "recordedProgramEntityDetails : enter" );

        Log.d( TAG, "recordedProgramEntityList : recordedId=" + recordedId );

        return this.api.recordedProgramById( recordedId, -1, null )
                .doOnError( e -> Log.e( TAG, "recordedProgramEntityList : error", e ) );
    }

    @Override
    public Observable<List<ProgramEntity>> upcomingProgramEntityList( final int startIndex, final int count, final boolean showAll, final int recordId, final int recStatus ) {
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
    public Observable<Boolean> updateWatchedStatus( final int chanId, final DateTime startTime, final boolean watched ) {
        Log.d( TAG, "updateWatchedStatus : enter" );

        Log.d( TAG, "updateWatchedStatus : chanId=" + chanId + ", startTime=" + startTime + ", watched=" + watched );

        return this.api.updateWatchedStatus( chanId, startTime, watched );
    }

    @Override
    public Observable<Long> getBookmark( final int recordedId, final String offsetType ) {
        Log.d( TAG, "getBookmark : enter" );

        Log.d( TAG, "getBookmark : recordedId=" + recordedId + ", offsetType=" + offsetType );

        return this.api.getBookmark( recordedId, -1, null, offsetType );
    }

    @Override
    public Observable<List<CommercialBreakEntity>> getComercialBreaks( int recordedId, String offsetType ) {
        Log.d( TAG, "getComercialBreaks : enter" );

        Log.d( TAG, "getComercialBreaks : recordedId=" + recordedId + ", offsetType=" + offsetType );

        return this.api.getRecordedCommBreakList( recordedId, -1, null, offsetType );
    }

}
