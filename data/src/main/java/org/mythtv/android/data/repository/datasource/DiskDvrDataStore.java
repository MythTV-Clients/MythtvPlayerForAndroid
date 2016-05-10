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

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
public class DiskDvrDataStore implements DvrDataStore {

    private static final String TAG = DiskDvrDataStore.class.getSimpleName();

    private final ProgramCache recordedProgramCache;

    public DiskDvrDataStore( ProgramCache recordedProgramCache ) {

        this.recordedProgramCache = recordedProgramCache;

    }

    @Override
    public Observable<List<TitleInfoEntity>> titleInfoEntityList() {

        throw new UnsupportedOperationException( "Operation is not available" );
    }

    @Override
    public Observable<List<ProgramEntity>> recordedProgramEntityList( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup ) {

        throw new UnsupportedOperationException( "Operation is not available" );
    }

    @Override
    public Observable<ProgramEntity> recordedProgramEntityDetails( int chanId, DateTime startTime ) {
        Log.d( TAG, "recordedProgramEntityDetails : enter" );
        Log.d( TAG, "recordedProgramEntityDetails : chanId=" + chanId + ", startTime=" + startTime );

        return this.recordedProgramCache.get( chanId, startTime );
    }

    @Override
    public Observable<List<ProgramEntity>> upcomingProgramEntityList( int startIndex, int count, boolean showAll, int recordId, int recStatus ) {

        throw new UnsupportedOperationException( "Operation is not available" );

    }

    @Override
    public Observable<List<EncoderEntity>> encoderEntityList() {

        throw new UnsupportedOperationException( "Operation is not available" );

    }

    @Override
    public Observable<Boolean> updateWatchedStatus( final int chanId, final DateTime startTime, final boolean watched ) {

        throw new UnsupportedOperationException( "Operation is not available" );

    }

}
