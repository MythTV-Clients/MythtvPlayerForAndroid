package org.mythtv.android.tv.internal.di.modules;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.GetUpcomingProgramList;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides programs related collaborators.
 *
 * Created by dmfrey on 1/24/16.
 */
@Module
public class UpcomingProgramsModule {

    public UpcomingProgramsModule() {}

    @Provides
    @PerActivity
    @Named( "upcomingProgramsList" )
    UseCase provideUpcomingProgramListUseCase( DvrRepository dvrRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetUpcomingProgramList( dvrRepository, threadExecutor, postExecutionThread );
    }

}
