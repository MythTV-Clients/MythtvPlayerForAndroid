package org.mythtv.android.data.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 5/30/17.
 */
@AutoValue
public abstract class BuildEntity {

    @SerializedName( "Version" )
    public abstract String version();

    @SerializedName( "LibX264")
    public abstract boolean libX264();

    @SerializedName( "LibDNS_SD")
    public abstract boolean libDnsSd();

    public static BuildEntity create( final String version, final boolean libX264, final boolean libDnsSd ) {

        return new AutoValue_BuildEntity( version, libX264, libDnsSd );
    }

    public static TypeAdapter<BuildEntity> typeAdapter( Gson gson ) {

        return new AutoValue_BuildEntity.GsonTypeAdapter( gson );
    }

}
