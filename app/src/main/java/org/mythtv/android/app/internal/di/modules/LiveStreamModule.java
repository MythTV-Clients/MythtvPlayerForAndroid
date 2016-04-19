package org.mythtv.android.app.internal.di.modules;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.DynamicUseCase;
import org.mythtv.android.domain.interactor.GetAddLiveStreamDetails;
import org.mythtv.android.domain.interactor.GetLiveStreamDetails;
import org.mythtv.android.domain.interactor.GetLiveStreamsList;
import org.mythtv.android.domain.interactor.GetRemoveLiveStreamDetails;
import org.mythtv.android.domain.repository.ContentRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides live stream related collaborators.
 *
 * Created by dmfrey on 10/23/15.
 */
@Module
public class LiveStreamModule {

    public LiveStreamModule() {}

    @Provides
    @PerActivity
    @Named( "addLiveStream" )
    DynamicUseCase provideAddLiveStreamModule( ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetAddLiveStreamDetails( contentRepository, threadExecutor, postExecutionThread );
    }

    @Provides
    @PerActivity
    @Named( "removeLiveStream" )
    DynamicUseCase provideRemoveLiveStreamModule( ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetRemoveLiveStreamDetails( contentRepository, threadExecutor, postExecutionThread );
    }

    @Provides
    @PerActivity
    @Named( "getLiveStreams" )
    DynamicUseCase provideGetLiveStreamsListUseCase( ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetLiveStreamsList( contentRepository, threadExecutor, postExecutionThread );
    }

    @Provides
    @PerActivity
    @Named( "getLiveStream" )
    DynamicUseCase provideGetLiveStreamUseCase( ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetLiveStreamDetails( contentRepository, threadExecutor, postExecutionThread );
    }

}
