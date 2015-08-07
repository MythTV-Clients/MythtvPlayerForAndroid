package org.mythtv.android.library.events.videoDir;

/**
 * Created by dmfrey on 8/3/15.
 */
public class VideoDirDetails {

    private Long id;
    private String path;
    private String name;
    private String parent;

    public VideoDirDetails() { }

    public VideoDirDetails( Long id, String path, String name, String parent ) {

        this.id = id;
        this.path = path;
        this.name = name;
        this.parent = parent;

    }

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

}
