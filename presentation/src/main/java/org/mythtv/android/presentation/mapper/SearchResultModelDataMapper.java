package org.mythtv.android.presentation.mapper;

import android.util.Log;

import org.mythtv.android.domain.SearchResult;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.SearchResultModel;

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
            searchResultModel.setRating( searchResult.getRating() );
            searchResultModel.setStoreageGroup( searchResult.getStoreageGroup() );
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
