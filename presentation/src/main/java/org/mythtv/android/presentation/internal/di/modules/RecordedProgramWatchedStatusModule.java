package org.mythtv.android.presentation.internal.di.modules;

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

    public RecordedProgramWatchedStatusModule() { }

    @Provides
    @PerActivity
    @Named( "updateRecordedProgramWatchedStatus" )
    DynamicUseCase provideUpdateRecordedProgramWatchedStatusUseCase( DvrRepository dvrRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new PostUpdatedRecordedWatchedStatus( dvrRepository, threadExecutor, postExecutionThread );
    }

}
