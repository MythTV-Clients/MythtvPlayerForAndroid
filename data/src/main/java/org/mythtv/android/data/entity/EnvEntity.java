package org.mythtv.android.data.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 5/30/17.
 */
@AutoValue
public abstract class EnvEntity {

    @SerializedName( "LANG" )
    public abstract String lang();

    @SerializedName( "LCALL" )
    public abstract String lcall();

    @SerializedName( "LCCTYPE" )
    public abstract String lccType();

    @SerializedName( "HOME" )
    public abstract String home();

    @SerializedName( "MYTHCONFDIR" )
    public abstract String mythConfDir();

    public static EnvEntity create( final String lang, final String lcall, final String lccType, final String home, final String mythConfDir ) {

        return new AutoValue_EnvEntity( lang, lcall, lccType, home, mythConfDir );
    }

    public static TypeAdapter<EnvEntity> typeAdapter( Gson gson ) {

        return new AutoValue_EnvEntity.GsonTypeAdapter( gson );
    }

}
