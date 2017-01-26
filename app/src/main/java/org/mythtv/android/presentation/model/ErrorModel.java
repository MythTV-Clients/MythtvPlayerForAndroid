package org.mythtv.android.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.mythtv.android.domain.Error;

/**
 *
 * @author dmfrey
 *
 * Created on 1/20/17.
 */

public class ErrorModel implements Parcelable {

    public static final String KEY_FIELD = "field";
    public static final String KEY_DEFAULT_MESSAGE = "default_message";
    public static final String KEY_MESSAGE_RESOURCE = "message_resource";

    private String field;
    private String defaultMessage;
    private int messageResource;

    public ErrorModel( final String field, final String defaultMessage, final int messageResource ) {

        this.field = field;
        this.defaultMessage = defaultMessage;
        this.messageResource = messageResource;

    }

    public ErrorModel( Parcel in ) {

        readFromParcel( in );

    }

    public String getField() {

        return field;
    }

    public String getDefaultMessage() {

        return defaultMessage;
    }

    public int getMessageResource() {

        return messageResource;
    }

    @Override
    public String toString() {
        return "Error{" +
                "field='" + field + '\'' +
                ", defaultMessage='" + defaultMessage + '\'' +
                ", messageResource=" + messageResource +
                '}';
    }

    public static final Parcelable.Creator<ErrorModel> CREATOR = new Parcelable.Creator<ErrorModel>() {

        public ErrorModel createFromParcel( Parcel in ) {

            return new ErrorModel( in );
        }

        public ErrorModel[] newArray( int size ) {

            return new ErrorModel[ size ];
        }

    };

    public static ErrorModel fromError( final Error error ) {

        return new ErrorModel( error.getField(), error.getDefaultMessage(), error.getMessageResource() );
    }

    public void readFromParcel( Parcel in ) {

        field = in.readString();
        defaultMessage = in.readString();
        messageResource = in.readInt();

    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {

        dest.writeString( field );
        dest.writeString( defaultMessage );
        dest.writeInt( messageResource );

    }

}
