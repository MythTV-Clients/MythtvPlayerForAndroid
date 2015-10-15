package org.mythtv.android.data.repository;

import org.mythtv.android.data.entity.mapper.SearchResultEntityDataMapper;
import org.mythtv.android.data.repository.datasource.SearchDataStore;
import org.mythtv.android.data.repository.datasource.SearchDataStoreFactory;
import org.mythtv.android.domain.SearchResult;
import org.mythtv.android.domain.repository.SearchRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by dmfrey on 10/10/15.
 */
@Singleton
public class SearchDataRepository implements SearchRepository {

    private static final String TAG = SearchDataRepository.class.getSimpleName();

    private final SearchDataStoreFactory searchDataStoreFactory;
    private final SearchResultEntityDataMapper searchResultEntityDataMapper;

    @Inject
    public SearchDataRepository( SearchDataStoreFactory searchDataStoreFactory, SearchResultEntityDataMapper searchResultEntityDataMapper ) {

        this.searchDataStoreFactory = searchDataStoreFactory;
        this.searchResultEntityDataMapper = searchResultEntityDataMapper;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<SearchResult>> search( String searchString ) {

        final SearchDataStore searchDataStore = this.searchDataStoreFactory.createReadSearchDataStore();

        return searchDataStore.search( searchString )
                .map( searchResultEntities -> this.searchResultEntityDataMapper.transform( searchResultEntities ) );
    }

}
