package org.mythtv.android.data.entity.mapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.TitleInfoEntity;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
public class TitleInfoEntityJsonDataMapperTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE_DVR_GET_TITLE_INFO_LIST = "{\"TitleInfoList\": {\"TitleInfos\": [{\"Title\": \"Descendants\", \"Inetref\": \"tmdb3.py_277217\", \"Count\": \"1\"},{\"Title\": \"Halt and Catch Fire\", \"Inetref\": \"ttvdb.py_271910\", \"Count\": \"3\"},{\"Title\": \"MasterChef\", \"Inetref\": \"ttvdb.py_167751\", \"Count\": \"15\"},{\"Title\": \"Rachael Ray's Kids Cook-Off\", \"Inetref\": \"ttvdb.py_273064\", \"Count\": \"5\"},{\"Title\": \"Star Wars: Droid Tales\", \"Inetref\": \"ttvdb.py_298171\", \"Count\": \"1\"}]}}";

    private TitleInfoEntityJsonMapper titleInfoEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        titleInfoEntityJsonMapper = new TitleInfoEntityJsonMapper();

    }

    @Test
    public void testTransformTitleInfoEntityCollectionHappyCase() {

        Collection<TitleInfoEntity> titleInfoEntityCollection = titleInfoEntityJsonMapper.transformTitleInfoEntityCollection( JSON_RESPONSE_DVR_GET_TITLE_INFO_LIST );

        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 0 ] ).getTitle(), is( "Descendants" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 0 ] ).getInetref(), is( "tmdb3.py_277217" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 0 ] ).getCount(), is( 1 ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 1 ] ).getTitle(), is( "Halt and Catch Fire" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 1 ] ).getInetref(), is( "ttvdb.py_271910" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 1 ] ).getCount(), is( 3 ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 2 ] ).getTitle(), is( "MasterChef" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 2 ] ).getInetref(), is( "ttvdb.py_167751" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 2 ] ).getCount(), is( 15 ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 3 ] ).getTitle(), is( "Rachael Ray's Kids Cook-Off" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 3 ] ).getInetref(), is( "ttvdb.py_273064" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 3 ] ).getCount(), is( 5 ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 4 ] ).getTitle(), is( "Star Wars: Droid Tales" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 4 ] ).getInetref(), is( "ttvdb.py_298171" ) );
        assertThat( ( (TitleInfoEntity) titleInfoEntityCollection.toArray() [ 4 ] ).getCount(), is( 1 ) );
        assertThat( titleInfoEntityCollection.size(), is( 5 ) );

    }

}
