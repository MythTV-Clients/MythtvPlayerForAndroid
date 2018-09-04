package org.mythtv.android.domain;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

/**
 *
 * @author dmfrey
 *
 * Created on 1/20/17.
 */
@AutoValue
public abstract class Error {

    @Nullable
    public abstract String field();

    @Nullable
    public abstract String defaultMessage();

    public abstract int messageResource();

    public static Error create( String field, String defaultMessage, int messageResource ) {

        return new AutoValue_Error( field, defaultMessage, messageResource );
    }

}
