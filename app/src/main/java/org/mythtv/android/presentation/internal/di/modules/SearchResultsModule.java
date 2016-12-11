package org.mythtv.android.presentation.internal.di.modules;

import android.util.Log;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.DynamicUseCase;
import org.mythtv.android.domain.interactor.GetSearchResultList;
import org.mythtv.android.domain.repository.SearchRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 10/12/15.
 */
@Module
public class SearchResultsModule {

    private static final String TAG = SearchResultsModule.class.getSimpleName();

    public SearchResultsModule() { }

    @Provides
    @PerActivity
    @Named( "searchResultList" )
    DynamicUseCase provideSearchResultListUseCase( SearchRepository searchRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        Log.d( TAG, "provideSearchResultListUseCase : enter" );

        return new GetSearchResultList( searchRepository, threadExecutor, postExecutionThread );
    }

}
