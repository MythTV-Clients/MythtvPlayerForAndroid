package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.SearchRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by dmfrey on 10/12/15.
 */
public class GetSearchResultList extends UseCase {

    private final String searchText;
    private final SearchRepository searchRepository;

    @Inject
    public GetSearchResultList( String searchText, SearchRepository searchRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.searchText = searchText;
        this.searchRepository = searchRepository;

    }

    @Override
    protected Observable buildUseCaseObservable() {

        return this.searchRepository.search( searchText );
    }

}
