package org.mythtv.android.data.entity.mapper.serializers;

import com.google.gson.JsonElement;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 9/24/15.
 */
public class DateTimeDeserializerTest {

    private DateTimeSerializer serializer;
    private DateTimeDeserializer deserializer;

    @Before
    public void setup() throws Exception {

        serializer = new DateTimeSerializer();
        deserializer = new DateTimeDeserializer();

    }

    @Test
    public void testDeerialize() {

        JsonElement jsonElement = serializer.serialize(new DateTime(), null, null);
        assertThat( deserializer.deserialize( jsonElement, null, null ), is( notNullValue() ) );

    }

}
