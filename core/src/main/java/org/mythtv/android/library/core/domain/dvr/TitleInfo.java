package org.mythtv.android.library.core.domain.dvr;

import org.mythtv.android.library.events.dvr.TitleInfoDetails;

/**
 * Created by dmfrey on 12/7/14.
 */
public class TitleInfo {

    private String title;
    private String inetref;

    public TitleInfo() { }

    public TitleInfo( String title, String inetref ) {

        this.title = title;
        this.inetref = inetref;

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
        details.setTitle( title );
        details.setInetref( inetref );

        return details;
    }

    public static TitleInfo fromDetails( TitleInfoDetails details ) {

        TitleInfo titleInfo = new TitleInfo();
        titleInfo.setTitle( details.getTitle() );
        titleInfo.setInetref( details.getInetref() );

        return titleInfo;
    }

}
