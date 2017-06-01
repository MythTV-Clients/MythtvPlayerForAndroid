package org.mythtv.android.data.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 5/30/17.
 */
@AutoValue
public abstract class BackendInfoWrapperEntity {

    @SerializedName( "BackendInfo" )
    public abstract BackendInfoEntity backendInfo();

    public static BackendInfoWrapperEntity create( final BackendInfoEntity backendInfoEntity ) {

        return new AutoValue_BackendInfoWrapperEntity( backendInfoEntity );
    }

    public static TypeAdapter<BackendInfoWrapperEntity> typeAdapter( Gson gson ) {

        return new AutoValue_BackendInfoWrapperEntity.GsonTypeAdapter( gson );
    }

}
