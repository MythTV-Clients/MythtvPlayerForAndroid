package org.mythtv.android.data.entity.mapper.serializers;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by dmfrey on 9/24/15.
 */
public class DateTimeSerializerTest {

    private DateTimeSerializer serializer;

    @Before
    public void setup() throws Exception {

        serializer = new DateTimeSerializer();

    }

    @Test
    public void testSerialize() {

        assertThat( serializer.serialize( new DateTime(), null, null ), is( notNullValue() ) );

    }
}
