package org.mythtv.android.data.entity.mapper.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Type;

/**
 * Created by dmfrey on 9/7/15.
 */
public class DateTimeDeserializer implements JsonDeserializer<DateTime> {

    private static final String TAG = DateTimeDeserializer.class.getSimpleName();
    private static final String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Override
    public DateTime deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException {

        DateTime dateTime = DateTime.parse( json.getAsJsonPrimitive().getAsString(), DateTimeFormat.forPattern( pattern ).withZoneUTC() );

        return dateTime;
    }

}
