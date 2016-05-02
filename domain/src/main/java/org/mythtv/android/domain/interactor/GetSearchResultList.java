package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.SearchRepository;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by dmfrey on 10/12/15.
 */
public class GetSearchResultList extends DynamicUseCase {

    private final SearchRepository searchRepository;

    @Inject
    public GetSearchResultList( SearchRepository searchRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.searchRepository = searchRepository;

    }

    @Override
    protected Observable buildUseCaseObservable( Map parameters ) {

        final String searchText = (String) parameters.get( "SEARCH_TEXT" );

        return this.searchRepository.search( searchText );
    }

}
