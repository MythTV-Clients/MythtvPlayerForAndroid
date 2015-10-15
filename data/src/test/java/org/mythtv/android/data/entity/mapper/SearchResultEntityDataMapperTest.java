package org.mythtv.android.data.entity.mapper;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.entity.SearchResultEntity;
import org.mythtv.android.domain.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 10/10/15.
 */
public class SearchResultEntityDataMapperTest {

    private static final int FAKE_CHAN_ID = 1;
    private static final long FAKE_START_TIME = new DateTime().getMillis();
    private static final String FAKE_TITLE = "fake title";
    private static final String FAKE_SUB_TITLE = "fake sub title";
    private static final String FAKE_CATEGORY = "fake category";
    private static final String FAKE_CALLSIGN = "fake callsign";
    private static final String FAKE_CHANNEL_NUMBER = "fake channel number";
    private static final String FAKE_DESCRIPTION = "fake description";
    private static final String FAKE_INETREF = "fake inetref";
    private static final int FAKE_SEASON = 1;
    private static final int FAKE_EPISODE = 1;
    private static final String FAKE_CAST_MEMBERS = "fake cast members";
    private static final String FAKE_CHARACTERS = "fake characters";
    private static final String FAKE_RATING = "fake rating";
    private static final SearchResult.Type FAKE_TYPE = SearchResult.Type.RECORDING;

    private SearchResultEntityDataMapper searchResultEntityDataMapper;

    @Before
    public void setup() throws Exception {

        searchResultEntityDataMapper = new SearchResultEntityDataMapper();

    }

    @Test
    public void testTransformSearchResultEntity() throws Exception {

        SearchResultEntity searchResultEntity = createFakeSearchResultEntity();
        SearchResult searchResult = searchResultEntityDataMapper.transform( searchResultEntity );

        assertThat( searchResult, is( instanceOf( SearchResult.class ) ) );
        assertThat( searchResult.getChanId(), is( FAKE_CHAN_ID ) );
        assertThat( searchResult.getStartTime(), is( new DateTime( FAKE_START_TIME ) ) );
        assertThat( searchResult.getTitle(), is( FAKE_TITLE ) );
        assertThat( searchResult.getSubTitle(), is( FAKE_SUB_TITLE ) );
        assertThat( searchResult.getCategory(), is( FAKE_CATEGORY ) );
        assertThat( searchResult.getCallsign(), is( FAKE_CALLSIGN ) );
        assertThat( searchResult.getChannelNumber(), is( FAKE_CHANNEL_NUMBER ) );
        assertThat( searchResult.getDescription(), is( FAKE_DESCRIPTION ) );
        assertThat( searchResult.getInetref(), is( FAKE_INETREF ) );
        assertThat( searchResult.getSeason(), is( FAKE_SEASON ) );
        assertThat( searchResult.getEpisode(), is( FAKE_EPISODE ) );
        assertThat( searchResult.getCastMembers(), is( FAKE_CAST_MEMBERS ) );
        assertThat( searchResult.getCharacters(), is( FAKE_CHARACTERS ) );
        assertThat( searchResult.getRating(), is( FAKE_RATING ) );
        assertThat( searchResult.getType(), is( FAKE_TYPE ) );

    }

    @Test
    public void testTransformSearchResultEntityCollection() throws Exception {

        SearchResultEntity mockSearchResultEntityOne = createFakeSearchResultEntity();
        SearchResultEntity mockSearchResultEntityTwo = createFakeSearchResultEntity();

        List<SearchResultEntity> searchResultEntityList = new ArrayList<>( 5 );
        searchResultEntityList.add( mockSearchResultEntityOne );
        searchResultEntityList.add( mockSearchResultEntityTwo );

        Collection<SearchResult> searchResultCollection = searchResultEntityDataMapper.transform( searchResultEntityList );

        assertThat( searchResultCollection.toArray()[ 0 ], is( instanceOf( SearchResult.class ) ) );
        assertThat( searchResultCollection.toArray()[ 1 ], is( instanceOf( SearchResult.class ) ) );
        assertThat( searchResultCollection.size(), is( 2 ) );

    }

    private SearchResultEntity createFakeSearchResultEntity() {

        SearchResultEntity searchResultEntity = new SearchResultEntity();
        searchResultEntity.setChanId( FAKE_CHAN_ID );
        searchResultEntity.setStartTime( FAKE_START_TIME );
        searchResultEntity.setTitle( FAKE_TITLE );
        searchResultEntity.setSubTitle( FAKE_SUB_TITLE );
        searchResultEntity.setCategory( FAKE_CATEGORY );
        searchResultEntity.setCallsign( FAKE_CALLSIGN );
        searchResultEntity.setChannelNumber( FAKE_CHANNEL_NUMBER );
        searchResultEntity.setDescription( FAKE_DESCRIPTION );
        searchResultEntity.setInetref( FAKE_INETREF );
        searchResultEntity.setSeason( FAKE_SEASON );
        searchResultEntity.setEpisode( FAKE_EPISODE );
        searchResultEntity.setCastMembers( FAKE_CAST_MEMBERS );
        searchResultEntity.setCharacters( FAKE_CHARACTERS );
        searchResultEntity.setRating( FAKE_RATING );
        searchResultEntity.setType( FAKE_TYPE.name() );

        return searchResultEntity;
    }

}
