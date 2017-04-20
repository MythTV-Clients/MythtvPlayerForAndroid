package org.mythtv.android.presentation.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import org.mythtv.android.domain.Error;

/**
 *
 * @author dmfrey
 *
 * Created on 1/20/17.
 */
@AutoValue
@SuppressWarnings( "PMD.EmptyMethodInAbstractClassShouldBeAbstract")
public abstract class ErrorModel implements Parcelable {

    public abstract String field();
    public abstract String defaultMessage();
    public abstract int messageResource();

    public static ErrorModel create( String field, String defaultMessage, int messageResource ) {

        return new AutoValue_ErrorModel( field, defaultMessage, messageResource );
    }

    public static ErrorModel fromError( final Error error ) {

        return ErrorModel.create( error.field(), error.defaultMessage(), error.messageResource() );
    }

}
