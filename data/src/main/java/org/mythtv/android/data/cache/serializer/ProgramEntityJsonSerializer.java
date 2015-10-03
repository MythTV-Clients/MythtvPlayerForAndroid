package org.mythtv.android.data.cache.serializer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeDeserializer;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeSerializer;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/26/15.
 */
@Singleton
public class ProgramEntityJsonSerializer {

    private final Gson gson;

    @Inject
    public ProgramEntityJsonSerializer() {

        Type dateTimeType = new TypeToken<DateTime>(){}.getType();

        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE )
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter( dateTimeType, new DateTimeSerializer() )
                .registerTypeAdapter( dateTimeType, new DateTimeDeserializer() )
                .create();

    }

    public String serialize( ProgramEntity programEntity) {

        String jsonString = gson.toJson( programEntity, ProgramEntity.class );

        return jsonString;
    }

    public ProgramEntity deserialize( String jsonString ) {

        ProgramEntity programEntity = gson.fromJson( jsonString, ProgramEntity.class );

        return programEntity;
    }

}
