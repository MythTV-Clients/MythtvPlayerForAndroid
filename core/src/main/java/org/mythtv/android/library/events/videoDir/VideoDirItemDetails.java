package org.mythtv.android.library.events.videoDir;

import org.mythtv.android.library.events.video.VideoDetails;

/**
 * Created by dmfrey on 8/3/15.
 */
public class VideoDirItemDetails {

    private Long id;
    private String path;
    private String name;
    private String parent;
    private String filename;
    private int sort;

    private VideoDetails video;

    public VideoDirItemDetails() { }

    public Long getId() {

        return id;
    }

    public void setId( Long id ) {

        this.id = id;

    }

    public String getPath() {

        return path;
    }

    public void setPath( String path ) {

        this.path = path;

    }

    public String getName() {

        return name;
    }

    public void setName( String name ) {

        this.name = name;

    }

    public String getParent() {

        return parent;
    }

    public void setParent( String parent ) {

        this.parent = parent;

    }

    public String getFilename() {

        return filename;
    }

    public void setFilename( String filename ) {

        this.filename = filename;

    }

    public int getSort() {

        return sort;
    }

    public void setSort( int sort ) {

        this.sort = sort;

    }

    public VideoDetails getVideo() {

        return video;
    }

    public void setVideo( VideoDetails video ) {

        this.video = video;

    }

}
