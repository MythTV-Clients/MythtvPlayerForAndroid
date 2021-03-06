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

package org.mythtv.android.presentation.internal.di.modules;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.DynamicUseCase;
import org.mythtv.android.domain.interactor.GetMediaItemList;
import org.mythtv.android.domain.interactor.GetSeriesList;
import org.mythtv.android.domain.repository.MediaItemRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides mediaItems related collaborators.
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
 */
@Module
public class MediaItemsModule {

    public MediaItemsModule() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    @Provides
    @PerActivity
    @Named( "seriesList" )
    DynamicUseCase provideGetSeriesListUseCase( MediaItemRepository mediaItemRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetSeriesList( mediaItemRepository, threadExecutor, postExecutionThread );
    }

    @Provides
    @PerActivity
    @Named( "mediaItemList" )
    DynamicUseCase provideGetMediaItemListUseCase( MediaItemRepository mediaItemRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetMediaItemList( mediaItemRepository, threadExecutor, postExecutionThread );
    }

}
