package org.mythtv.android.domain;

/**
 *
 * @author dmfrey
 *
 * Created on 1/20/17.
 */

public class Error {

    private final String field;
    private final String defaultMessage;
    private final int messageResource;

    public Error(final String field, final String defaultMessage, final int messageResource ) {

        this.field = field;
        this.defaultMessage = defaultMessage;
        this.messageResource = messageResource;

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

}
