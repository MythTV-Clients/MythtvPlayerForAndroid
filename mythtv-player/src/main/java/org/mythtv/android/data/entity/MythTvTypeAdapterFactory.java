package org.mythtv.android.data.entity;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 3/29/17.
 */
@GsonTypeAdapterFactory
public abstract class MythTvTypeAdapterFactory implements TypeAdapterFactory {

    public static TypeAdapterFactory create() {

        return new AutoValueGson_MythTvTypeAdapterFactory();
    }

}
