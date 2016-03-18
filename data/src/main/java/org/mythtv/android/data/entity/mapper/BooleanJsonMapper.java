package org.mythtv.android.data.entity.mapper;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

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

        Log.i( TAG, "transformBoolean : booleanJsonResponse=" + booleanJsonResponse );
        JsonObject rootObject = parser.parse( booleanJsonResponse ).getAsJsonObject();

        return rootObject.get( "bool" ).getAsBoolean();
    }

}
