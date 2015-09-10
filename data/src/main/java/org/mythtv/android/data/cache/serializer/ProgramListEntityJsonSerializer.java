package org.mythtv.android.data.cache.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.mythtv.android.data.entity.ProgramListEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/26/15.
 */
@Singleton
public class ProgramListEntityJsonSerializer {

    private GsonBuilder builder = new GsonBuilder();
    private final Gson gson;

    @Inject
    public ProgramListEntityJsonSerializer() {

        builder.excludeFieldsWithoutExposeAnnotation();
        gson = builder.create();

    }

    public String serialize( ProgramListEntity programListEntity) {

        String jsonString = gson.toJson( programListEntity, ProgramListEntity.class );

        return jsonString;
    }

    public ProgramListEntity deserialize( String jsonString ) {

        ProgramListEntity programListEntity = gson.fromJson( jsonString, ProgramListEntity.class );

        return programListEntity;
    }

}
