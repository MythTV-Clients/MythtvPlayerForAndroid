package org.mythtv.android.library.core.domain.dvr;

import org.mythtv.android.library.events.dvr.TitleInfoDetails;

import java.io.Serializable;

/**
 * Created by dmfrey on 12/7/14.
 */
public class TitleInfo implements Serializable, Comparable<TitleInfo> {

    private long id;
    private String title;
    private String inetref;

    public TitleInfo() { }

    public TitleInfo( long id, String title, String inetref ) {

        this.id = id;
        this.title = title;
        this.inetref = inetref;

    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getInetref() {
        return inetref;
    }

    public void setInetref( String inetref ) {
        this.inetref = inetref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleInfo titleInfo = (TitleInfo) o;

        if (!title.equals(titleInfo.title)) return false;
        return !(inetref != null ? !inetref.equals(titleInfo.inetref) : titleInfo.inetref != null);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (inetref != null ? inetref.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo( TitleInfo another ) {

        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if( this == another ) return EQUAL;

        String thisTitle = removeArticles( this.title.toUpperCase() );
        String thatTitle = removeArticles( another.title.toUpperCase() );

        int comparison = thisTitle.compareTo( thatTitle );
        if( comparison != EQUAL ) return comparison;

        String thisInetref = ( null != inetref ? inetref : "" );
        String thatInetref = ( null != another.inetref ? another.inetref : "" );

        comparison = thisInetref.compareTo( thatInetref );
        if( comparison != EQUAL ) return comparison;

        assert this.equals( another ) : "compareTo inconsistent with equals.";

        return EQUAL;
    }

    public TitleInfoDetails toDetails() {

        TitleInfoDetails details = new TitleInfoDetails();
        details.setId( id );
        details.setTitle( title );
        details.setInetref( inetref );

        return details;
    }

    public static TitleInfo fromDetails( TitleInfoDetails details ) {

        TitleInfo titleInfo = new TitleInfo();
        titleInfo.setId( details.getId() );
        titleInfo.setTitle( details.getTitle() );
        titleInfo.setInetref( details.getInetref() );

        return titleInfo;
    }

    private String removeArticles( String value ) {

        if( null == value ) {

            return value;
        }

        String ret = value;

        if( value.toLowerCase().startsWith( "the " ) ) {
            ret = ret.substring( "the ".length() );
        }

        if( value.toLowerCase().startsWith("an ") ) {
            ret = ret.substring( "an ".length() );
        }

        if( value.toLowerCase().startsWith( "a " ) ) {
            ret = ret.substring( "a ".length() );
        }

        return ret;
    }

}
