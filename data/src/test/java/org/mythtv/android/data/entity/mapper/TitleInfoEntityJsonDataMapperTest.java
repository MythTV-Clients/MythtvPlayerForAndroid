package org.mythtv.android.data.entity.mapper;

import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.data.entity.TitleInfoEntity;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
@RunWith( MockitoJUnitRunner.class )
public class TitleInfoEntityJsonDataMapperTest {

    private static final String JSON_RESPONSE_DVR_GET_TITLE_INFO = "{\"Title\": \"Descendants\", \"Inetref\": \"tmdb3.py_277217\", \"Count\": \"1\"}";
    private static final String JSON_RESPONSE_DVR_GET_TITLE_INFO_BAD = "{\"Title\": \"Descendants\", \"Inetref\": \"tmdb3.py_277217\", \"Count\": \"1\"";
    private static final String JSON_RESPONSE_DVR_GET_TITLE_INFO_LIST = "{\"TitleInfoList\": {\"TitleInfos\": [{\"Title\": \"Descendants\", \"Inetref\": \"tmdb3.py_277217\", \"Count\": \"1\"},{\"Title\": \"Halt and Catch Fire\", \"Inetref\": \"ttvdb.py_271910\", \"Count\": \"3\"},{\"Title\": \"MasterChef\", \"Inetref\": \"ttvdb.py_167751\", \"Count\": \"15\"},{\"Title\": \"Rachael Ray's Kids Cook-Off\", \"Inetref\": \"ttvdb.py_273064\", \"Count\": \"5\"},{\"Title\": \"Star Wars: Droid Tales\", \"Inetref\": \"ttvdb.py_298171\", \"Count\": \"1\"}]}}";
    private static final String JSON_RESPONSE_DVR_GET_TITLE_INFO_LIST_BAD = "{\"TitleInfoList\": {\"TitleInfos\": [{\"Title\": \"Descendants\", \"Inetref\": \"tmdb3.py_277217\", \"Count\": \"1\"},{\"Title\": \"Halt and Catch Fire\", \"Inetref\": \"ttvdb.py_271910\", \"Count\": \"3\"},{\"Title\": \"MasterChef\", \"Inetref\": \"ttvdb.py_167751\", \"Count\": \"15\"},{\"Title\": \"Rachael Ray's Kids Cook-Off\", \"Inetref\": \"ttvdb.py_273064\", \"Count\": \"5\"},{\"Title\": \"Star Wars: Droid Tales\", \"Inetref\": \"ttvdb.py_298171\", \"Count\": \"1\"";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private TitleInfoEntityJsonMapper titleInfoEntityJsonMapper;

    @Before
    public void setUp() {

        titleInfoEntityJsonMapper = new TitleInfoEntityJsonMapper();

    }

    @Test
    public void testTransformTitleInfoEntityHappyCase() {

        TitleInfoEntity titleInfoEntity = titleInfoEntityJsonMapper.transformTitleInfoEntity( JSON_RESPONSE_DVR_GET_TITLE_INFO );

        assertThat( titleInfoEntity.title() ).isEqualTo( "Descendants" );
        assertThat( titleInfoEntity.inetref() ).isEqualTo( "tmdb3.py_277217" );
        assertThat( titleInfoEntity.count() ).isEqualTo( 1 );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformTitleInfoEntityBadJson() {

        titleInfoEntityJsonMapper.transformTitleInfoEntity( JSON_RESPONSE_DVR_GET_TITLE_INFO_BAD );

    }

    @Test
    public void testTransformTitleInfoEntityCollectionHappyCase() {

        Collection<TitleInfoEntity> titleInfoEntityCollection = titleInfoEntityJsonMapper.transformTitleInfoEntityCollection( JSON_RESPONSE_DVR_GET_TITLE_INFO_LIST );

        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 0 ] ).title() ).isEqualTo( "Descendants" );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 0 ] ).inetref() ).isEqualTo( "tmdb3.py_277217" );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 0 ] ).count() ).isEqualTo( 1 );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 1 ] ).title() ).isEqualTo( "Halt and Catch Fire" );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 1 ] ).inetref() ).isEqualTo( "ttvdb.py_271910" );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 1 ] ).count() ).isEqualTo( 3 );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 2 ] ).title() ).isEqualTo( "MasterChef" );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 2 ] ).inetref() ).isEqualTo( "ttvdb.py_167751" );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 2 ] ).count() ).isEqualTo( 15 );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 3 ] ).title() ).isEqualTo( "Rachael Ray's Kids Cook-Off" );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 3 ] ).inetref() ).isEqualTo( "ttvdb.py_273064" );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 3 ] ).count() ).isEqualTo( 5 );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 4 ] ).title() ).isEqualTo( "Star Wars: Droid Tales" );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 4 ] ).inetref() ).isEqualTo( "ttvdb.py_298171" );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 4 ] ).count() ).isEqualTo( 1 );
        assertThat( titleInfoEntityCollection.size() ).isEqualTo( 5 );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformTitleInfoEntityCollectionBadJson() {

        titleInfoEntityJsonMapper.transformTitleInfoEntityCollection( JSON_RESPONSE_DVR_GET_TITLE_INFO_LIST_BAD );

    }

}
