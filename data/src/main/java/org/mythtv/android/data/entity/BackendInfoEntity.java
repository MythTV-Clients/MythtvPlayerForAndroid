package org.mythtv.android.data.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 5/30/17.
 */
@AutoValue
public abstract class BackendInfoEntity {

    @SerializedName( "Build" )
    public abstract BuildEntity build();

    @SerializedName( "Env" )
    public abstract EnvEntity env();

    @SerializedName( "Log" )
    public abstract LogEntity log();

    public static BackendInfoEntity create( final BuildEntity build, final EnvEntity env, final LogEntity log ) {

        return new AutoValue_BackendInfoEntity( build, env, log );
    }

    public static TypeAdapter<BackendInfoEntity> typeAdapter( Gson gson ) {

        return new AutoValue_BackendInfoEntity.GsonTypeAdapter( gson );
    }

}
