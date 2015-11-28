package org.mythtv.android.presentation.internal.di.modules;

import org.mythtv.android.domain.ContentType;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.GetVideoContentTypeList;
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
public class VideosModule {

    public VideosModule() { }

    @Provides
    @PerActivity
    @Named( "movieList" )
    UseCase provideMovieListUseCase( VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetVideoContentTypeList( ContentType.MOVIE, videoRepository, threadExecutor, postExecutionThread );
    }

    @Provides
    @PerActivity
    @Named( "homeVideoList" )
    UseCase provideHomeVideoListUseCase( VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetVideoContentTypeList( ContentType.HOMEVIDEO, videoRepository, threadExecutor, postExecutionThread );
    }

    @Provides
    @PerActivity
    @Named( "televisionList" )
    UseCase provideTelevisionListUseCase( VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetVideoContentTypeList( ContentType.TELEVISION, videoRepository, threadExecutor, postExecutionThread );
    }

    @Provides
    @PerActivity
    @Named( "musicVideoList" )
    UseCase provideMusicVideoListUseCase( VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetVideoContentTypeList( ContentType.MUSICVIDEO, videoRepository, threadExecutor, postExecutionThread );
    }

    @Provides
    @PerActivity
    @Named( "adultList" )
    UseCase provideAdultListUseCase( VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetVideoContentTypeList( ContentType.ADULT, videoRepository, threadExecutor, postExecutionThread );
    }

}
