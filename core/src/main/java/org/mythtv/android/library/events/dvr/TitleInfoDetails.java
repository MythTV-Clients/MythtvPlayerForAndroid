package org.mythtv.android.library.events.dvr;

/**
 * Created by dmfrey on 12/7/14.
 */
public class TitleInfoDetails {

    private String title;
    private String inetref;

    public TitleInfoDetails() { }

    public TitleInfoDetails(String title, String inetref) {

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

}
