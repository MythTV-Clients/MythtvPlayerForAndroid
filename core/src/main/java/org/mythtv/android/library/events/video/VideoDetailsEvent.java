package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.ReadEvent;

/**
 * Created by dmfrey on 4/16/15.
 */
public class VideoDetailsEvent extends ReadEvent {

    private final Integer id;
    private final VideoDetails details;

    public VideoDetailsEvent( final Integer id, final VideoDetails details ) {

        this.id = id;
        this.details = details;

    }

    public Integer getId() { return id; }

    public VideoDetails getDetails() { return details; }

    public static VideoDetailsEvent notFound( final Integer id ) {

        VideoDetailsEvent ev = new VideoDetailsEvent( id, null );
        ev.entityFound = false;

        return ev;
    }

}
