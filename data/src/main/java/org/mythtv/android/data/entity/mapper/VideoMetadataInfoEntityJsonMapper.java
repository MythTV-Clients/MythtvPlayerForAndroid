package org.mythtv.android.data.entity.mapper;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoListWrapperEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoWrapperEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeDeserializer;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeSerializer;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 8/27/15.
 */
public class VideoMetadataInfoEntityJsonMapper {

    private static final String TAG = VideoMetadataInfoEntityJsonMapper.class.getSimpleName();

    private final Gson gson;

    @Inject
    public VideoMetadataInfoEntityJsonMapper() {

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

    public VideoMetadataInfoEntity transformVideoMetadataInfoEntity( String videoMetadataInfoJsonResponse ) throws JsonSyntaxException {
        Log.d( TAG, "transformVideoMetadataInfoEntity : enter" );

        try {
            Log.d( TAG, "transformVideoMetadataInfoEntity : videoMetadataInfoJsonResponse=" + videoMetadataInfoJsonResponse );

            Type videoMetadataInfoWrapperEntityType = new TypeToken<VideoMetadataInfoWrapperEntity>() {}.getType();
            VideoMetadataInfoWrapperEntity videoMetadataInfoWrapperEntity = this.gson.fromJson( videoMetadataInfoJsonResponse, videoMetadataInfoWrapperEntityType );

            return videoMetadataInfoWrapperEntity.getVideoMetadataInfo();

        } catch( JsonSyntaxException jsonException ) {

            throw jsonException;
        }

    }

    public List<VideoMetadataInfoEntity> transformVideoMetadataInfoEntityCollection( String videoMetadataInfoListJsonResponse ) throws JsonSyntaxException {

        try {

            Type videoMetadataInfoListEntityType = new TypeToken<VideoMetadataInfoListWrapperEntity>() {}.getType();
            VideoMetadataInfoListWrapperEntity videoMetadataInfoListEntity = gson.fromJson( videoMetadataInfoListJsonResponse, videoMetadataInfoListEntityType );

            return Arrays.asList( videoMetadataInfoListEntity.getVideoMetadataInfoListEntity().getVideoMetadataInfosEntity() );

        } catch( JsonSyntaxException jsonException ) {

            throw jsonException;
        }

    }

}
