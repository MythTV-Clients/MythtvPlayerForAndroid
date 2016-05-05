package org.mythtv.android.data.entity.mapper;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.EncoderListEntity;
import org.mythtv.android.data.entity.EncoderWrapperEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeDeserializer;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeSerializer;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 1/18/16.
 */
public class EncoderEntityJsonMapper {

    private static final String TAG = EncoderEntityJsonMapper.class.getSimpleName();

    private final Gson gson;

    @Inject
    public EncoderEntityJsonMapper() {

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

    public EncoderEntity transformEncoderEntity(String encoderJsonResponse ) throws JsonSyntaxException {

//        Log.i( TAG, "transformEncoderEntity : encoderJsonResponse=" + encoderJsonResponse );
        Type encoderWrapperEntityType = new TypeToken<EncoderWrapperEntity>() {}.getType();
        EncoderWrapperEntity encoderWrapperEntity = this.gson.fromJson( encoderJsonResponse, encoderWrapperEntityType );

        return encoderWrapperEntity.getEncoder();
    }

    public List<EncoderEntity> transformEncoderEntityCollection( String encoderListJsonResponse ) throws JsonSyntaxException {

//        Log.i( TAG, "transformEncoderEntityCollection : " + encoderListJsonResponse );
        Type encoderListEntityType = new TypeToken<EncoderListEntity>() {}.getType();
        EncoderListEntity encoderListEntity = this.gson.fromJson( encoderListJsonResponse, encoderListEntityType );
//        Log.i( TAG, "transformEncoderEntityCollection : encoderListJsonResponse=" + encoderListEntity.toString() );

        return Arrays.asList( encoderListEntity.getEncoders().getEncoders() );
    }

}
