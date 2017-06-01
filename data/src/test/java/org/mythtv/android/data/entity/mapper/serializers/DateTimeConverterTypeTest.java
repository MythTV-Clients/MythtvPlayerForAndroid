package org.mythtv.android.data.entity.mapper.serializers;

import com.google.gson.JsonElement;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 3/30/17.
 */

public class DateTimeConverterTypeTest {

    private DateTimeTypeConverter converter;

    @Before
    public void setup() {

        converter = new DateTimeTypeConverter();

    }

    @Test
    public void testDeerialize() {

        JsonElement jsonElement = converter.serialize( new DateTime(), null, null );
        assertThat( converter.deserialize( jsonElement, null, null ), is( notNullValue() ) );

    }

    @Test
    public void testSerialize() {

        assertThat( converter.serialize( new DateTime(), null, null ), is( notNullValue() ) );

    }

}
