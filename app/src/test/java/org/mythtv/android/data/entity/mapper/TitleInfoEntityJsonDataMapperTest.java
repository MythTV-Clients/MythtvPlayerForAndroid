package org.mythtv.android.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.TitleInfoEntity;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
public class TitleInfoEntityJsonDataMapperTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE_DVR_GET_TITLE_INFO = "{\"Title\": \"Descendants\", \"Inetref\": \"tmdb3.py_277217\", \"Count\": \"1\"}";
    private static final String JSON_RESPONSE_DVR_GET_TITLE_INFO_BAD = "{\"Title\": \"Descendants\", \"Inetref\": \"tmdb3.py_277217\", \"Count\": \"1\"";
    private static final String JSON_RESPONSE_DVR_GET_TITLE_INFO_LIST = "{\"TitleInfoList\": {\"TitleInfos\": [{\"Title\": \"Descendants\", \"Inetref\": \"tmdb3.py_277217\", \"Count\": \"1\"},{\"Title\": \"Halt and Catch Fire\", \"Inetref\": \"ttvdb.py_271910\", \"Count\": \"3\"},{\"Title\": \"MasterChef\", \"Inetref\": \"ttvdb.py_167751\", \"Count\": \"15\"},{\"Title\": \"Rachael Ray's Kids Cook-Off\", \"Inetref\": \"ttvdb.py_273064\", \"Count\": \"5\"},{\"Title\": \"Star Wars: Droid Tales\", \"Inetref\": \"ttvdb.py_298171\", \"Count\": \"1\"}]}}";
    private static final String JSON_RESPONSE_DVR_GET_TITLE_INFO_LIST_BAD = "{\"TitleInfoList\": {\"TitleInfos\": [{\"Title\": \"Descendants\", \"Inetref\": \"tmdb3.py_277217\", \"Count\": \"1\"},{\"Title\": \"Halt and Catch Fire\", \"Inetref\": \"ttvdb.py_271910\", \"Count\": \"3\"},{\"Title\": \"MasterChef\", \"Inetref\": \"ttvdb.py_167751\", \"Count\": \"15\"},{\"Title\": \"Rachael Ray's Kids Cook-Off\", \"Inetref\": \"ttvdb.py_273064\", \"Count\": \"5\"},{\"Title\": \"Star Wars: Droid Tales\", \"Inetref\": \"ttvdb.py_298171\", \"Count\": \"1\"";

    private TitleInfoEntityJsonMapper titleInfoEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Gson mockGson;

    @Before
    public void setUp() {

        titleInfoEntityJsonMapper = new TitleInfoEntityJsonMapper( gson );

    }

    @Test
    public void testTransformTitleInfoEntityHappyCase() {

        TitleInfoEntity titleInfoEntity = titleInfoEntityJsonMapper.transformTitleInfoEntity( JSON_RESPONSE_DVR_GET_TITLE_INFO );

        assertThat( titleInfoEntity.title(), is( "Descendants" ) );
        assertThat( titleInfoEntity.inetref(), is( "tmdb3.py_277217" ) );
        assertThat( titleInfoEntity.count(), is( 1 ) );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformTitleInfoEntityBadJson() {

        titleInfoEntityJsonMapper.transformTitleInfoEntity( JSON_RESPONSE_DVR_GET_TITLE_INFO_BAD );

    }

    @Test
    public void testTransformTitleInfoEntityCollectionHappyCase() {

        Collection<TitleInfoEntity> titleInfoEntityCollection = titleInfoEntityJsonMapper.transformTitleInfoEntityCollection( JSON_RESPONSE_DVR_GET_TITLE_INFO_LIST );

        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 0 ] ).title(), is( "Descendants" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 0 ] ).inetref(), is( "tmdb3.py_277217" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 0 ] ).count(), is( 1 ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 1 ] ).title(), is( "Halt and Catch Fire" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 1 ] ).inetref(), is( "ttvdb.py_271910" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 1 ] ).count(), is( 3 ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 2 ] ).title(), is( "MasterChef" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 2 ] ).inetref(), is( "ttvdb.py_167751" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 2 ] ).count(), is( 15 ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 3 ] ).title(), is( "Rachael Ray's Kids Cook-Off" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 3 ] ).inetref(), is( "ttvdb.py_273064" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 3 ] ).count(), is( 5 ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 4 ] ).title(), is( "Star Wars: Droid Tales" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 4 ] ).inetref(), is( "ttvdb.py_298171" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 4 ] ).count(), is( 1 ) );
        assertThat( titleInfoEntityCollection.size(), is( 5 ) );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformTitleInfoEntityCollectionBadJson() {

        titleInfoEntityJsonMapper.transformTitleInfoEntityCollection( JSON_RESPONSE_DVR_GET_TITLE_INFO_LIST_BAD );

    }

}
