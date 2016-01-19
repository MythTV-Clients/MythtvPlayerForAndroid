package org.mythtv.android.data.entity.mapper;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.SearchResultEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dmfrey on 10/8/15.
 */
@Singleton
public class SearchResultEntityDataMapper {

    private static final String TAG = SearchResultEntityDataMapper.class.getSimpleName();

    private SearchResultEntityDataMapper() { }

    public static SearchResult transform( SearchResultEntity searchResultEntity ) {

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
            searchResult.setVideoId( searchResultEntity.getVideoId() );
            searchResult.setRating( searchResultEntity.getRating() );
            searchResult.setStorageGroup( searchResultEntity.getStoreageGroup() );
            searchResult.setFilename( searchResultEntity.getFilename() );
            searchResult.setHostname( searchResultEntity.getHostname() );
            searchResult.setType( SearchResult.Type.valueOf( searchResultEntity.getType() ) );

        }

        return searchResult;
    }

    public static List<SearchResult> transform( Collection<SearchResultEntity> searchResultEntityCollection ) {

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

    public static SearchResultEntity transformProgram( ProgramEntity programEntity ) {

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

    public static List<SearchResultEntity> transformPrograms( Collection<ProgramEntity> programEntityCollection ) {

        List<SearchResultEntity> searchResultEntityList = new ArrayList<>( programEntityCollection.size() );

        SearchResultEntity searchResult;
        for( ProgramEntity programEntity : programEntityCollection ) {

            searchResult = transformProgram( programEntity );
            if( null != searchResult ) {

                searchResultEntityList.add( searchResult );

            }

        }

        return searchResultEntityList;
    }

    public static SearchResultEntity transformVideo( VideoMetadataInfoEntity videoEntity ) {

        SearchResultEntity searchResult = null;
        if( null != videoEntity ) {

            searchResult = new SearchResultEntity();
            searchResult.setVideoId( videoEntity.getId() );
            searchResult.setTitle( videoEntity.getTitle() );
            searchResult.setSubTitle( videoEntity.getSubTitle() );
            searchResult.setSeason( videoEntity.getSeason() );
            searchResult.setEpisode( videoEntity.getEpisode() );
            searchResult.setDescription( videoEntity.getDescription() );
            searchResult.setInetref( videoEntity.getInetref() );

            searchResult.setFilename( videoEntity.getFileName() );
            searchResult.setHostname( videoEntity.getHostName() );
            searchResult.setType( SearchResult.Type.VIDEO.name() );

        }

        return searchResult;
    }

    public static List<SearchResultEntity> transformVideos( Collection<VideoMetadataInfoEntity> videoEntityCollection ) {

        List<SearchResultEntity> searchResultEntityList = new ArrayList<>( videoEntityCollection.size() );

        SearchResultEntity searchResult;
        for( VideoMetadataInfoEntity videoEntity : videoEntityCollection ) {

            searchResult = transformVideo( videoEntity );
            if( null != searchResult ) {

                searchResultEntityList.add( searchResult );

            }

        }

        return searchResultEntityList;
    }

}
