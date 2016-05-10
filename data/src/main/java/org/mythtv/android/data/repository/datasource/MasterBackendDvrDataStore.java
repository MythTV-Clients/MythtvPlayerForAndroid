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
import org.mythtv.android.data.cache.ProgramCache;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
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

    private final Action1<ProgramEntity> saveToCacheAction =
            programEntity -> {
                if( null != programEntity ) {
                    MasterBackendDvrDataStore.this.recordedProgramCache.put( programEntity );
                }
            };

    public MasterBackendDvrDataStore( DvrApi api, ProgramCache recordedProgramCache ) {

        this.api = api;
        this.recordedProgramCache = recordedProgramCache;

    }

    @Override
    public Observable<List<TitleInfoEntity>> titleInfoEntityList() {
        Log.d( TAG, "titleInfoEntityList : enter" );

        Log.d( TAG, "titleInfoEntityList : exit" );
        return this.api.titleInfoEntityList();
    }

    @Override
    public Observable<List<ProgramEntity>> recordedProgramEntityList( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {
        Log.d( TAG, "recordedProgramEntityList : enter" );

        Log.d( TAG, "recordedProgramEntityList : descending=" + descending + ", startIndex=" + startIndex + ", count=" + count + ", titleRegEx=" + titleRegEx + ", recGroup=" + recGroup + ", storageGroup=" + storageGroup );

        Log.d( TAG, "recordedProgramEntityList : exit" );
        return this.api.recordedProgramEntityList( descending, startIndex, count, titleRegEx, recGroup, storageGroup )
                .flatMap( Observable::from )
                .filter( programEntity -> !programEntity.getRecording().getRecGroup().equalsIgnoreCase( "LiveTV" ) || !programEntity.getRecording().getStorageGroup().equalsIgnoreCase( "LiveTV" ) || "Deleted".equalsIgnoreCase( programEntity.getRecording().getRecGroup() ) )
                .toList();
    }

    @Override
    public Observable<ProgramEntity> recordedProgramEntityDetails( int chanId, DateTime startTime ) {

        return this.api.recordedProgramById( chanId, startTime )
                .doOnNext( saveToCacheAction );
    }

    @Override
    public Observable<List<ProgramEntity>> upcomingProgramEntityList( int startIndex, int count, boolean showAll, int recordId, int recStatus ) {

        return this.api.upcomingProgramEntityList( startIndex, count, showAll, recordId, recStatus );
    }

    @Override
    public Observable<List<EncoderEntity>> encoderEntityList() {
        Log.d( TAG, "encoderEntityList : enter" );

        Log.d( TAG, "encoderEntityList : exit" );
        return this.api.encoderEntityList();
    }

    @Override
    public Observable<Boolean> updateWatchedStatus(int chanId, DateTime startTime, boolean watched ) {
        Log.d( TAG, "updateWatchedStatus : enter" );

        Log.d( TAG, "updateWatchedStatus : exit" );
        return this.api.updateWatchedStatus( chanId, startTime, watched );
    }

}
