package org.mythtv.android.presentation.model;

import android.os.Parcel;
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
public abstract class ErrorModel implements Parcelable {

    public static final String KEY_FIELD = "field";
    public static final String KEY_DEFAULT_MESSAGE = "default_message";
    public static final String KEY_MESSAGE_RESOURCE = "message_resource";

    public abstract String field();
    public abstract String defaultMessage();
    public abstract int messageResource();

    public static ErrorModel create( String field, String defaultMessage, int messageResource ) {

        return new AutoValue_ErrorModel( field, defaultMessage, messageResource );
    }

    public static final Parcelable.Creator<ErrorModel> CREATOR = new Parcelable.Creator<ErrorModel>() {

        public ErrorModel createFromParcel( Parcel in ) {

            return ErrorModel.create( in.readString(), in.readString(), in.readInt() );
        }

        public ErrorModel[] newArray( int size ) {

            return new ErrorModel[ size ];
        }

    };

    public static ErrorModel fromError( final Error error ) {

        return ErrorModel.create( error.field(), error.defaultMessage(), error.messageResource() );
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {

        dest.writeString( field() );
        dest.writeString( defaultMessage() );
        dest.writeInt( messageResource() );

    }

}
