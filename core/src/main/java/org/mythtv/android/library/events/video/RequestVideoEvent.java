package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 4/16/15.
 */
public class RequestVideoEvent extends RequestReadEvent {

    private Integer id;
    private String filename;

    public RequestVideoEvent() { }

    public Integer getId() {

        return id;
    }

    public void setId( Integer id ) {

        this.id = id;

    }

    public String getFilename() {

        return filename;
    }

    public void setFilename( String filename ) {

        this.filename = filename;

    }

}
