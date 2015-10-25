package org.mythtv.android.data.entity.mapper;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.LiveStreamInfoListEntity;
import org.mythtv.android.data.entity.LiveStreamInfoWrapperEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeDeserializer;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeSerializer;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 10/17/15.
 */
public class BooleanJsonMapper {

    private static final String TAG = BooleanJsonMapper.class.getSimpleName();

    private final JsonParser parser;

    @Inject

    public BooleanJsonMapper() {

        this.parser = new JsonParser();

    }

    public Boolean transformBoolean( String booleanJsonResponse ) throws JsonSyntaxException {

        try {
            Log.i( TAG, "transformBoolean : booleanJsonResponse=" + booleanJsonResponse );

            JsonObject rootObject = parser.parse( booleanJsonResponse ).getAsJsonObject();

            return rootObject.get( "bool" ).getAsBoolean();

        } catch( JsonSyntaxException jsonException ) {

            throw jsonException;
        }

    }

}
