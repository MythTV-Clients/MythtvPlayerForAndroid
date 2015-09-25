package org.mythtv.android.data.entity.mapper.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;

import java.lang.reflect.Type;

/**
 * Created by dmfrey on 9/7/15.
 */
public class DateTimeSerializer implements JsonSerializer<DateTime> {

    private static final String TAG = DateTimeSerializer.class.getSimpleName();
    private static final String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Override
    public JsonElement serialize( DateTime src, Type typeOfSrc, JsonSerializationContext context ) {

        return new JsonPrimitive( src.toString( pattern ) );
    }

}
