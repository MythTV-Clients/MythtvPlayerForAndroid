package org.mythtv.android.data.entity.mapper;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.SearchResultEntity;
import org.mythtv.android.domain.CastMember;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 10/8/15.
 */
@Singleton
public class SearchResultEntityDataMapper {

    private static final String TAG = SearchResultEntityDataMapper.class.getSimpleName();

    @Inject
    public SearchResultEntityDataMapper() { }

    public SearchResult transform( SearchResultEntity searchResultEntity ) {

        SearchResult searchResult = null;
        if( null != searchResultEntity ) {

            searchResult = new SearchResult();
            searchResult.setChanId( searchResultEntity.getChanId() );
            searchResult.setStartTime( searchResultEntity.getStartTime() > -1 ? new DateTime( searchResultEntity.getStartTime() ) : null );
            searchResult.setTitle( searchResultEntity.getTitle() );
            searchResult.setSubTitle( searchResultEntity.getSubTitle() );
            searchResult.setCategory( searchResultEntity.getCategory() );
            searchResult.setCallsign( searchResultEntity.getCallsign() );
            searchResult.setChannelNumber( searchResultEntity.getChannelNumber() );
            searchResult.setSeason( searchResultEntity.getSeason() );
            searchResult.setEpisode( searchResultEntity.getEpisode() );
            searchResult.setDescription(searchResultEntity.getDescription() );
            searchResult.setInetref( searchResultEntity.getInetref() );
            searchResult.setCastMembers( searchResultEntity.getCastMembers() );
            searchResult.setCharacters( searchResultEntity.getCharacters() );
            searchResult.setRating( searchResultEntity.getRating() );
            searchResult.setStoreageGroup( searchResultEntity.getStoreageGroup() );
            searchResult.setFilename( searchResultEntity.getFilename() );
            searchResult.setHostname( searchResultEntity.getHostname() );
            searchResult.setType( SearchResult.Type.valueOf( searchResultEntity.getType() ) );

        }

        return searchResult;
    }

    public List<SearchResult> transform( Collection<SearchResultEntity> searchResultEntityCollection ) {

        List<SearchResult> searchResultList = new ArrayList<>( searchResultEntityCollection.size() );

        SearchResult searchResult;
        for( SearchResultEntity searchResultEntity : searchResultEntityCollection ) {

            searchResult = transform( searchResultEntity );
            if( null != searchResult ) {

                searchResultList.add( searchResult );

            }

        }

        return searchResultList;
    }

    public SearchResultEntity transformProgram( ProgramEntity programEntity ) {

        SearchResultEntity searchResult = null;
        if( null != programEntity ) {

            if( "LiveTV".equalsIgnoreCase( programEntity.getRecording().getRecGroup() ) ) {

                return null;
            }

            searchResult = new SearchResultEntity();
            searchResult.setChanId( programEntity.getChannel().getChanId() );
            searchResult.setStartTime( programEntity.getRecording().getStartTs().getMillis() );
            searchResult.setTitle( programEntity.getTitle() );
            searchResult.setSubTitle( programEntity.getSubTitle() );
            searchResult.setCategory( programEntity.getCategory() );
            searchResult.setChannelNumber( programEntity.getChannel().getChanNum() );
            searchResult.setCallsign( programEntity.getChannel().getCallSign() );
            searchResult.setSeason( programEntity.getSeason() );
            searchResult.setEpisode( programEntity.getEpisode() );
            searchResult.setDescription( programEntity.getDescription() );
            searchResult.setInetref( programEntity.getInetref() );

            List<String> castMembers = new ArrayList<>();
            List<String> characters = new ArrayList<>();
            for( CastMemberEntity castMember : programEntity.getCast().getCastMembers() ) {

                if( !castMembers.contains( castMember.getName() ) ) {
                    castMembers.add( castMember.getName() );
                }

                if( !characters.contains( castMember.getCharacterName() ) ) {
                    characters.add( castMember.getCharacterName() );
                }

            }
            if( !castMembers.isEmpty() ) {
                String cast = "";
                for( String name : castMembers ) {
                    cast += name + " ";
                }
                searchResult.setCastMembers( cast.trim() );
            }
            if( !characters.isEmpty() ) {
                String cast = "";
                for( String name : characters ) {
                    cast += name + " ";
                }
                searchResult.setCharacters( cast.trim() );
            }

            searchResult.setStoreageGroup( programEntity.getRecording().getStorageGroup() );
            searchResult.setFilename( programEntity.getFileName() );
            searchResult.setHostname( programEntity.getHostName() );
            searchResult.setType( SearchResult.Type.RECORDING.name() );

        }

        return searchResult;
    }

    public List<SearchResultEntity> transformPrograms( Collection<ProgramEntity> programEntityCollection ) {
        Log.d( TAG, "transformPrograms : enter" );

        List<SearchResultEntity> searchResultEntityList = new ArrayList<>( programEntityCollection.size() );

        SearchResultEntity searchResult;
        for( ProgramEntity programEntity : programEntityCollection ) {

            searchResult = transformProgram( programEntity );
            if( null != searchResult ) {
                Log.d( TAG, "transformPrograms : searchResult=" + searchResult );

                searchResultEntityList.add( searchResult );

            }

        }

        Log.d( TAG, "transformPrograms : exit" );
        return searchResultEntityList;
    }

}
