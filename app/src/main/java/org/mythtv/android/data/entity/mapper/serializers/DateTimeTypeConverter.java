package org.mythtv.android.data.entity.mapper.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Type;
import java.util.Date;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 3/30/17.
 */

public class DateTimeTypeConverter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    private static final String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Override
    public JsonElement serialize( DateTime src, Type srcType, JsonSerializationContext context ) {

        return new JsonPrimitive( src.withZone( DateTimeZone.UTC ).toString( pattern ) );
    }

    @Override
    public DateTime deserialize( JsonElement json, Type type, JsonDeserializationContext context ) throws JsonParseException {

        try {

            if( "".equals( json.getAsJsonPrimitive().getAsString() ) ) {

                return null;
            }

            return DateTime.parse( json.getAsString(), DateTimeFormat.forPattern( pattern ).withZoneUTC() );

        } catch( IllegalArgumentException e ) {

            // May be it came in formatted as a java.util.Date, so try that
            Date date = context.deserialize( json, Date.class );

            return new DateTime(date );
        }

    }

}
