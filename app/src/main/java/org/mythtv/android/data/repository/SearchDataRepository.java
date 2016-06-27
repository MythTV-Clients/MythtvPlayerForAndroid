/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
