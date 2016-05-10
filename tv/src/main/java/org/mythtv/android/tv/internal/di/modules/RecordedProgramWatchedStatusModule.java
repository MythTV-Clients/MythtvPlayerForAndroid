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

package org.mythtv.android.tv.internal.di.modules;

import org.joda.time.DateTime;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.DynamicUseCase;
import org.mythtv.android.domain.interactor.PostUpdatedRecordedWatchedStatus;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that updates a recorded program's watched status.
 *
 * Created by dmfrey on 4/9/16.
 */
@Module
public class RecordedProgramWatchedStatusModule {

    private int chanId = -1;
    private DateTime startTime = null;
    private boolean watched;

    public RecordedProgramWatchedStatusModule() { }

    public RecordedProgramWatchedStatusModule( int chanId, DateTime startTime, boolean watched ) {

        this.chanId = chanId;
        this.startTime = startTime;
        this.watched = watched;

    }

    @Provides
    @PerActivity
    @Named( "updateRecordedProgramWatchedStatus" )
    DynamicUseCase provideGetUserDetailsUseCase(DvrRepository dvrRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new PostUpdatedRecordedWatchedStatus( dvrRepository, threadExecutor, postExecutionThread );
    }

}
