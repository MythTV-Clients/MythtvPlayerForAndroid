package org.mythtv.android.app.internal.di.modules;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.GetAddLiveStreamDetails;
import org.mythtv.android.domain.interactor.GetLiveStreamsList;
import org.mythtv.android.domain.interactor.UseCase;
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

    private String storageGroup = null;
    private String filename = null;
    private String hostname = null;

    public LiveStreamModule() {}

    public LiveStreamModule( String storageGroup, String filename, String hostname ) {

        this.storageGroup = storageGroup;
        this.filename = filename;
        this.hostname = hostname;

    }

    @Provides
    @PerActivity
    @Named( "addLiveStream" )
    UseCase provideAddLiveStreamModule( ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetAddLiveStreamDetails( storageGroup, filename, hostname, contentRepository, threadExecutor, postExecutionThread );
    }

    @Provides
    @PerActivity
    @Named( "getLiveStreams" )
    UseCase provideGetLiveStreamsListUseCase( ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetLiveStreamsList( filename, contentRepository, threadExecutor, postExecutionThread );
    }

}
