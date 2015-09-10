package org.mythtv.android.presentation.internal.di.modules;

import org.joda.time.DateTime;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.GetRecordedProgramDetails;
import org.mythtv.android.domain.interactor.GetRecordedProgramList;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides user related collaborators.
 *
 * Created by dmfrey on 8/30/15.
 */
@Module
public class ProgramModule {

    private int chanId = -1;
    private DateTime startTime = null;

    public ProgramModule() {}

    public ProgramModule( int chanId, DateTime startTime ) {

        this.chanId = chanId;
        this.startTime = startTime;

    }

    @Provides
    @PerActivity
    @Named( "programDetails" )
    UseCase provideGetUserDetailsUseCase( DvrRepository dvrRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetRecordedProgramDetails( chanId, startTime, dvrRepository, threadExecutor, postExecutionThread );
    }

}
