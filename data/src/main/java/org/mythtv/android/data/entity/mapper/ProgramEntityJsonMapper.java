package org.mythtv.android.data.entity.mapper;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.ProgramListEntity;
import org.mythtv.android.data.entity.ProgramWrapperEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeDeserializer;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeSerializer;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 8/27/15.
 */
public class ProgramEntityJsonMapper {

    private static final String TAG = ProgramEntityJsonMapper.class.getSimpleName();

    private final Gson gson;

    @Inject

    public ProgramEntityJsonMapper() {

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

    public ProgramEntity transformProgramEntity( String programJsonResponse ) throws JsonSyntaxException {

        try {

            Type programWrapperEntityType = new TypeToken<ProgramWrapperEntity>() {}.getType();
            ProgramWrapperEntity programWrapperEntity = this.gson.fromJson( programJsonResponse, programWrapperEntityType );

            return programWrapperEntity.getProgram();

        } catch( JsonSyntaxException jsonException ) {

            throw jsonException;
        }

    }

    public List<ProgramEntity> transformProgramEntityCollection( String programListJsonResponse ) throws JsonSyntaxException {

        try {

            Type programListEntityType = new TypeToken<ProgramListEntity>() {}.getType();
            ProgramListEntity programListEntity = this.gson.fromJson( programListJsonResponse, programListEntityType );

            return Arrays.asList( programListEntity.getPrograms().getPrograms() );

        } catch( JsonSyntaxException jsonException ) {

            throw jsonException;
        }

    }

}
