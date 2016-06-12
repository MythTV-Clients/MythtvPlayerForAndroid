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

package org.mythtv.android.mapper;

import org.mythtv.android.domain.SearchResult;
import org.mythtv.android.internal.di.PerActivity;
import org.mythtv.android.model.SearchResultModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link SearchResult} (in the domain layer) to {@link SearchResultModel} in the
 * presentation layer.
 */
@PerActivity
public class SearchResultModelDataMapper {

    private static final String TAG = SearchResultModelDataMapper.class.getSimpleName();

    @Inject
    public SearchResultModelDataMapper() { }

    public SearchResultModel transform( SearchResult searchResult ) {

        SearchResultModel searchResultModel = null;
        if( null != searchResult ) {

            searchResultModel = new SearchResultModel();
            searchResultModel.setChanId( searchResult.getChanId() );
            searchResultModel.setStartTime( searchResult.getStartTime() );
            searchResultModel.setTitle( searchResult.getTitle() );
            searchResultModel.setSubTitle( searchResult.getSubTitle() );
            searchResultModel.setCategory( searchResult.getCategory() );
            searchResultModel.setCallsign( searchResult.getCallsign() );
            searchResultModel.setChannelNumber( searchResult.getChannelNumber() );
            searchResultModel.setSeason( searchResult.getSeason() );
            searchResultModel.setEpisode( searchResult.getEpisode() );
            searchResultModel.setDescription(searchResult.getDescription() );
            searchResultModel.setInetref( searchResult.getInetref() );
            searchResultModel.setCastMembers( searchResult.getCastMembers() );
            searchResultModel.setCharacters( searchResult.getCharacters() );
            searchResultModel.setVideoId( searchResult.getVideoId() );
            searchResultModel.setRating( searchResult.getRating() );
            searchResultModel.setStorageGroup( searchResult.getStorageGroup() );
            searchResultModel.setFilename( searchResult.getFilename() );
            searchResultModel.setHostname( searchResult.getHostname() );
            searchResultModel.setType( searchResult.getType() );

        }

        return searchResultModel;
    }

    public List<SearchResultModel> transform( Collection<SearchResult> searchResultCollection ) {

        List<SearchResultModel> searchResultModelList = new ArrayList<>( searchResultCollection.size() );

        SearchResultModel searchResultModel;
        for( SearchResult searchResult : searchResultCollection ) {

            searchResultModel = transform( searchResult );
            if( null != searchResultModel ) {

                searchResultModelList.add( searchResultModel );

            }

        }

        return searchResultModelList;
    }

}
