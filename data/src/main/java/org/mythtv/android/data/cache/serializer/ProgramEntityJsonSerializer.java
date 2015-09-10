package org.mythtv.android.data.cache.serializer;

import com.google.gson.Gson;

import org.mythtv.android.data.entity.ProgramEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/26/15.
 */
@Singleton
public class ProgramEntityJsonSerializer {

    private final Gson gson = new Gson();

    @Inject
    public ProgramEntityJsonSerializer() {}

    public String serialize( ProgramEntity programEntity) {

        String jsonString = gson.toJson( programEntity, ProgramEntity.class );

        return jsonString;
    }

    public ProgramEntity deserialize( String jsonString ) {

        ProgramEntity programEntity = gson.fromJson( jsonString, ProgramEntity.class );

        return programEntity;
    }

}
