package org.mythtv.android.app.internal.di.modules;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.DynamicUseCase;
import org.mythtv.android.domain.interactor.PostUpdatedVideoWatchedStatus;
import org.mythtv.android.domain.repository.VideoRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that updates a video's watched status.
 *
 * Created by dmfrey on 4/18/16.
 */
@Module
public class VideoWatchedStatusModule {

    public VideoWatchedStatusModule() { }

    @Provides
    @PerActivity
    @Named( "updateVideoWatchedStatus" )
    DynamicUseCase provideUpdateVideoProgramWatchedStatusUseCase( VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new PostUpdatedVideoWatchedStatus( videoRepository, threadExecutor, postExecutionThread );
    }

}
