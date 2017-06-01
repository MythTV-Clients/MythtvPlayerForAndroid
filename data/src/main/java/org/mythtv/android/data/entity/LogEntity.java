package org.mythtv.android.data.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 5/30/17.
 */
@AutoValue
public abstract class LogEntity {

    @SerializedName( "LogArgs" )
    public abstract String logArgs();

    public static LogEntity create( final String logArgs ) {

        return new AutoValue_LogEntity( logArgs );
    }

    public static TypeAdapter<LogEntity> typeAdapter( Gson gson ) {

        return new AutoValue_LogEntity.GsonTypeAdapter( gson );
    }

}
