package org.mythtv.android.tv.internal.di.modules;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.GetRecordedProgramList;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides programs related collaborators.
 *
 * Created by dmfrey on 8/30/15.
 */
@Module
public class ProgramsModule {

    private boolean descending = false;
    private int startIndex = -1;
    private int count = -1;
    private String titleRegEx = null;
    private String recGroup = null;
    private String storageGroup = null;

    public ProgramsModule() {}

    public ProgramsModule( boolean descending, int startIndex, int count, String titleRegEx, String recGroup, String storageGroup ) {

        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;
        this.titleRegEx = titleRegEx;
        this.recGroup = recGroup;
        this.storageGroup = storageGroup;

    }

    @Provides
    @PerActivity
    @Named( "programList" )
    UseCase provideRecordedProgramListUseCase( DvrRepository dvrRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetRecordedProgramList( descending, startIndex, count, titleRegEx, recGroup, storageGroup, dvrRepository, threadExecutor, postExecutionThread );
    }

}
