package org.mythtv.android.library.persistence.domain.videoDir;

import org.mythtv.android.library.events.videoDir.VideoDirDetails;

/**
 * Created by dmfrey on 8/3/15.
 */
public class VideoDir {

    private Long id;
    private String path;
    private String name;
    private String parent;

    public VideoDir() { }

    public VideoDir( Long id, String path, String name, String parent ) {

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

    public VideoDirDetails toDetails() {

        VideoDirDetails details = new VideoDirDetails();
        details.setId( id );
        details.setPath( path );
        details.setName( name );
        details.setParent( parent );

        return details;
    }

    public static VideoDir fromDetails( VideoDirDetails details ) {

        VideoDir videoDir = new VideoDir();
        videoDir.setId( details.getId() );
        videoDir.setPath( details.getPath() );
        videoDir.setName( details.getName() );
        videoDir.setParent( details.getParent() );

        return videoDir;
    }

}
