package org.mythtv.android.presentation.internal.di.modules;

import android.content.Context;

import org.mythtv.android.data.cache.ProgramCache;
import org.mythtv.android.data.cache.ProgramCacheImpl;
import org.mythtv.android.data.executor.JobExecutor;
import org.mythtv.android.data.repository.DvrDataRepository;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.presentation.AndroidApplication;
import org.mythtv.android.presentation.UIThread;
import org.mythtv.android.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 *
 * Created by dmfrey on 8/30/15.
 */
@Module
public class ApplicationModule {

    private final AndroidApplication application;

    public ApplicationModule( AndroidApplication application ) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor( JobExecutor jobExecutor ) {

        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread( UIThread uiThread ) {

        return uiThread;
    }

    @Provides
    @Singleton
    ProgramCache provideRecordedProgramCache( ProgramCacheImpl recordedProgramCache ) {

        return recordedProgramCache;
    }

    @Provides
    @Singleton
    DvrRepository provideDvrRepository( DvrDataRepository dvrDataRepository) {

        return dvrDataRepository;
    }

}
