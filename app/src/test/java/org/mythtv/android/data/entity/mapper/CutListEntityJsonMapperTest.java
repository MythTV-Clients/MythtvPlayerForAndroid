package org.mythtv.android.data.entity.mapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.CommercialBreakEntity;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 10/31/16.
 */

public class CutListEntityJsonMapperTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE_DVR_GET_COMM_BREAK = "{\"CutList\": {\"Cuttings\": [{\"Mark\": \"4\", \"Offset\": \"0\"},{\"Mark\": \"5\", \"Offset\": \"1927\"},{\"Mark\": \"4\", \"Offset\": \"32462\"},{\"Mark\": \"5\", \"Offset\": \"36105\"},{\"Mark\": \"4\", \"Offset\": \"65251\"},{\"Mark\": \"5\", \"Offset\": \"68924\"},{\"Mark\": \"4\", \"Offset\": \"109781\"},{\"Mark\": \"5\", \"Offset\": \"115184\"},{\"Mark\": \"4\", \"Offset\": \"143979\"},{\"Mark\": \"5\", \"Offset\": \"156030\"},{\"Mark\": \"4\", \"Offset\": \"170542\"},{\"Mark\": \"5\", \"Offset\": \"185268\"},{\"Mark\": \"4\", \"Offset\": \"214037\"},{\"Mark\": \"5\", \"Offset\": \"218408\"},{\"Mark\": \"4\", \"Offset\": \"243762\"},{\"Mark\": \"5\", \"Offset\": \"255496\"},{\"Mark\": \"4\", \"Offset\": \"275332\"},{\"Mark\": \"5\", \"Offset\": \"288071\"},{\"Mark\": \"4\", \"Offset\": \"309225\"},{\"Mark\": \"5\", \"Offset\": \"324148\"}]}}";
    private static final String JSON_RESPONSE_DVR_GET_COMM_BREAK_BAD = "{\"CutList\": {\"Cuttings\": [{\"Mark\": \"5\", \"Offset\": \"1927\"},{\"Mark\": \"4\", \"Offset\": \"32462\"},{\"Mark\": \"5\", \"Offset\": \"36105\"},{\"Mark\": \"4\", \"Offset\": \"65251\"},{\"Mark\": \"5\", \"Offset\": \"68924\"},{\"Mark\": \"4\", \"Offset\": \"109781\"},{\"Mark\": \"5\", \"Offset\": \"115184\"},{\"Mark\": \"4\", \"Offset\": \"143979\"},{\"Mark\": \"5\", \"Offset\": \"156030\"},{\"Mark\": \"4\", \"Offset\": \"170542\"},{\"Mark\": \"5\", \"Offset\": \"185268\"},{\"Mark\": \"4\", \"Offset\": \"214037\"},{\"Mark\": \"5\", \"Offset\": \"218408\"},{\"Mark\": \"4\", \"Offset\": \"243762\"},{\"Mark\": \"5\", \"Offset\": \"255496\"},{\"Mark\": \"4\", \"Offset\": \"275332\"},{\"Mark\": \"5\", \"Offset\": \"288071\"},{\"Mark\": \"4\", \"Offset\": \"309225\"},{\"Mark\": \"5\", \"Offset\": \"324148\"}]}}";

    private CutListEntityJsonMapper mapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        mapper = new CutListEntityJsonMapper( gson );

    }

    @Test
    public void testTransformCutListEntityCollectionHappyCase() {

        List<CommercialBreakEntity> breaks = mapper.transformCutListEntityCollection( JSON_RESPONSE_DVR_GET_COMM_BREAK );

        assertThat( breaks.get( 0 ).getStart(), is( 0L ) );
        assertThat( breaks.get( 0 ).getEnd(), is( 1927L ) );
        assertThat( breaks.get( 1 ).getStart(), is( 32462L ) );
        assertThat( breaks.get( 1 ).getEnd(), is( 36105L ) );
        assertThat( breaks.get( 2 ).getStart(), is( 65251L ) );
        assertThat( breaks.get( 2 ).getEnd(), is( 68924L ) );
        assertThat( breaks.get( 3 ).getStart(), is( 109781L ) );
        assertThat( breaks.get( 3 ).getEnd(), is( 115184L ) );
        assertThat( breaks.get( 4 ).getStart(), is( 143979L ) );
        assertThat( breaks.get( 4 ).getEnd(), is( 156030L ) );
        assertThat( breaks.get( 5 ).getStart(), is( 170542L ) );
        assertThat( breaks.get( 5 ).getEnd(), is( 185268L ) );
        assertThat( breaks.get( 6 ).getStart(), is( 214037L ) );
        assertThat( breaks.get( 6 ).getEnd(), is( 218408L ) );
        assertThat( breaks.get( 7 ).getStart(), is( 243762L ) );
        assertThat( breaks.get( 7 ).getEnd(), is( 255496L ) );
        assertThat( breaks.get( 8 ).getStart(), is( 275332L ) );
        assertThat( breaks.get( 8 ).getEnd(), is( 288071L ) );
        assertThat( breaks.get( 9 ).getStart(), is( 309225L ) );
        assertThat( breaks.get( 9 ).getEnd(), is( 324148L ) );
        assertThat( breaks.size(), is( 10 ) );

    }

    @Test
    public void testTransformCutListEntityCollectionBadCase() {

        List<CommercialBreakEntity> breaks = mapper.transformCutListEntityCollection( JSON_RESPONSE_DVR_GET_COMM_BREAK_BAD );

        assertThat( breaks, not( nullValue() ) );
        assertThat( breaks, hasSize( 0 ) );

    }

}
