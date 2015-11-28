package org.mythtv.android.presentation.internal.di.modules;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.GetVideo;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.domain.repository.VideoRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmfrey on 11/9/15.
 */
@Module
public class VideoModule {

    private int id = -1;

    public VideoModule() { }

    public VideoModule( int id ) {

        this.id = id;

    }

    @Provides
    @PerActivity
    @Named( "videoDetails" )
    UseCase provideVideoUseCase( VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetVideo( id, videoRepository, threadExecutor, postExecutionThread );
    }

}
