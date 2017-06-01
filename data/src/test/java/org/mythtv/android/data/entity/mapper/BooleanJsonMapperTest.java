package org.mythtv.android.data.entity.mapper;

import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 12/1/15.
 */
public class BooleanJsonMapperTest {

    private static final String JSON_RESPONSE = "{\"bool\": false}";
    private static final String JSON_RESPONSE_BAD = "{\"bool\": false";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BooleanJsonMapper booleanJsonMapper;

    @Before
    public void setup() {

        booleanJsonMapper = new BooleanJsonMapper();

    }

    @Test
    public void testTransformBooleanHappyCase() {

        Boolean booleanEntity = booleanJsonMapper.transformBoolean( JSON_RESPONSE );

        assertThat( booleanEntity ).isFalse();

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformLiveStreamInfoEntityBadJson() {

        booleanJsonMapper.transformBoolean( JSON_RESPONSE_BAD );

    }

}
