package org.mythtv.android.presentation.internal.di.modules;

import android.util.Log;

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

    private static final String TAG = SearchResultsModule.class.getSimpleName();

    private String searchText = null;

//    public SearchResultsModule() { }

    public SearchResultsModule( String searchText ) {
        Log.d( TAG, "initialize : enter - searchText=" + searchText );

        this.searchText = searchText;

        Log.d( TAG, "initialize : exit" );
    }

    @Provides
    @PerActivity
    @Named( "searchResultList" )
    UseCase provideSearchResultListUseCase( SearchRepository searchRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        Log.d( TAG, "provideSearchResultListUseCase : enter - searchText=" + searchText );

        return new GetSearchResultList( searchText, searchRepository, threadExecutor, postExecutionThread );
    }

    public void setSearchText( String searchText ) {
        Log.d( TAG, "setSearchText : enter - searchText=" + searchText );

        this.searchText = searchText;

        Log.d( TAG, "setSearchText : exit" );
    }

}
