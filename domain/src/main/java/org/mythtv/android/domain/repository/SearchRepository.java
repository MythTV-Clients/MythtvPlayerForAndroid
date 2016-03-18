package org.mythtv.android.domain.repository;

import org.mythtv.android.domain.SearchResult;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 10/8/15.
 */
public interface SearchRepository {

    Observable<List<SearchResult>> search( String searchString );

}
