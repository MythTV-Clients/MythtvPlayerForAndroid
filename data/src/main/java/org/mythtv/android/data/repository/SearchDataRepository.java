package org.mythtv.android.data.repository;

import android.util.Log;

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

    @Inject
    public SearchDataRepository( SearchDataStoreFactory searchDataStoreFactory ) {

        this.searchDataStoreFactory = searchDataStoreFactory;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<SearchResult>> search( String searchString ) {
        Log.d( TAG, "search : enter - searchString=" + searchString );

        final SearchDataStore searchDataStore = this.searchDataStoreFactory.createReadSearchDataStore();

        return searchDataStore.search( searchString )
                .map( searchResultEntities -> SearchResultEntityDataMapper.transform( searchResultEntities ) );
    }

}
