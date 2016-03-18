package org.mythtv.android.presentation.utils;

/**
 * Created by dmfrey on 2/1/16.
 */
public class ArticleCleaner {

    private ArticleCleaner() { }

    public static String clean( String value ) {

        if( null == value || "".equals( value ) ) {
            return value;
        }

        String upper = value.toUpperCase();
        if( upper.startsWith( "THE " ) ) {
            value = value.substring( "THE ".length() );
        }

        if( upper.startsWith( "AN " ) ) {
            value = value.substring( "AN ".length() );
        }

        if( upper.startsWith( "A " ) ) {
            value = value.substring( "A ".length() );
        }

        return value;
    }

}
