package org.mythtv.android.presentation.internal.di.modules;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.GetSearchResultList;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.domain.repository.SearchRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmfrey on 10/12/15.
 */
@Module
public class SearchResultsModule {

    private String searchText = null;

    public SearchResultsModule() { }

    public SearchResultsModule( String searchText ) {

        this.searchText = searchText;

    }

    @Provides
    @PerActivity
    @Named( "searchResultList" )
    UseCase provideSearchResultListUseCase( SearchRepository searchRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        return new GetSearchResultList( searchText, searchRepository, threadExecutor, postExecutionThread );
    }

}
