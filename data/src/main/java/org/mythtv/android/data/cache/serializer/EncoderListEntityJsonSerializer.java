package org.mythtv.android.data.cache.serializer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.EncoderListEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeDeserializer;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeSerializer;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/26/15.
 */
@Singleton
public class EncoderListEntityJsonSerializer {

    private final Gson gson;

    @Inject
    public EncoderListEntityJsonSerializer() {

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

    public String serialize( EncoderListEntity entityListEntity ) {

        String jsonString = gson.toJson( entityListEntity, EncoderListEntity.class );

        return jsonString;
    }

    public EncoderListEntity deserialize( String jsonString ) {

        EncoderListEntity encoderListEntity = gson.fromJson( jsonString, EncoderListEntity.class );

        return encoderListEntity;
    }

}
