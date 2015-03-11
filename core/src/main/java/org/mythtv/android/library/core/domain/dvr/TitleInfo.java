package org.mythtv.android.library.core.domain.dvr;

import org.mythtv.android.library.events.dvr.TitleInfoDetails;

import java.io.Serializable;

/**
 * Created by dmfrey on 12/7/14.
 */
public class TitleInfo implements Serializable {

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

}
