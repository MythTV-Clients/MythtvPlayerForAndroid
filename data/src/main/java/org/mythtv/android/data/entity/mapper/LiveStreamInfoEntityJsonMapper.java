package org.mythtv.android.data.entity.mapper;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class LiveStreamInfoEntityJsonMapper {

    private static final String TAG = LiveStreamInfoEntityJsonMapper.class.getSimpleName();

    private final Gson gson;

    @Inject

    public LiveStreamInfoEntityJsonMapper() {

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

    public LiveStreamInfoEntity transformLiveStreamInfoEntity( String liveStreamInfoJsonResponse ) throws JsonSyntaxException {

        try {
            Log.i( TAG, "transformLiveStreamInfoEntity : liveStreamInfoJsonResponse=" + liveStreamInfoJsonResponse );

            Type liveStreamInfoWrapperEntityType = new TypeToken<LiveStreamInfoWrapperEntity>() {}.getType();
            LiveStreamInfoWrapperEntity liveStreamInfoWrapperEntity = this.gson.fromJson( liveStreamInfoJsonResponse, liveStreamInfoWrapperEntityType );

            return liveStreamInfoWrapperEntity.getLiveStreamInfo();

        } catch( JsonSyntaxException jsonException ) {

            throw jsonException;
        }

    }

    public List<LiveStreamInfoEntity> transformLiveStreamInfoEntityCollection( String liveStreamInfoListJsonResponse ) throws JsonSyntaxException {

        try {
            Log.i( TAG, "transformLiveStreamInfoEntityCollection : " + liveStreamInfoListJsonResponse );

            Type liveStreamInfoListEntityType = new TypeToken<LiveStreamInfoListEntity>() {}.getType();
            LiveStreamInfoListEntity liveStreamInfoListEntity = gson.fromJson( liveStreamInfoListJsonResponse, liveStreamInfoListEntityType );
            Log.i( TAG, "transformLiveStreamInfoEntityCollection : liveStreamInfoListEntity=" + liveStreamInfoListEntity.toString() );

            return Arrays.asList( liveStreamInfoListEntity.getLiveStreamInfos().getLiveStreamInfos() );

        } catch( JsonSyntaxException jsonException ) {

            throw jsonException;
        }

    }

}
