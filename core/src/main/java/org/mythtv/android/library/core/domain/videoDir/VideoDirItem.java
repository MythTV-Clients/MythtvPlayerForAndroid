package org.mythtv.android.library.core.domain.videoDir;

import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.events.videoDir.VideoDirItemDetails;

/**
 * Created by dmfrey on 8/3/15.
 */
public class VideoDirItem {

    private Long id;
    private String path;
    private String name;
    private String parent;
    private String filename;
    private int sort;

    private Video video;

    public VideoDirItem() { }

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

    public Video getVideo() {

        return video;
    }

    public void setVideo( Video video ) {

        this.video = video;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoDirItem that = (VideoDirItem) o;

        if (sort != that.sort) return false;
        if (!id.equals(that.id)) return false;
        if (!path.equals(that.path)) return false;
        if (!name.equals(that.name)) return false;
        return !(parent != null ? !parent.equals(that.parent) : that.parent != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + path.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + sort;
        return result;
    }

    public VideoDirItemDetails toDetails() {

        VideoDirItemDetails details = new VideoDirItemDetails();
        details.setId( id );
        details.setPath( path );
        details.setName( name );
        details.setParent( parent );
        details.setFilename( filename );
        details.setSort( sort );
        details.setVideo( null != video ? video.toDetails() : null );

        return details;
    }

    public static VideoDirItem fromDetails( VideoDirItemDetails details ) {

        VideoDirItem videoDirItem = new VideoDirItem();
        videoDirItem.setId( details.getId() );
        videoDirItem.setPath( details.getPath() );
        videoDirItem.setName( details.getName() );
        videoDirItem.setParent( details.getParent() );
        videoDirItem.setFilename( details.getFilename() );
        videoDirItem.setSort( details.getSort() );
        videoDirItem.setVideo( null != details.getVideo() ? Video.fromDetails( details.getVideo() ) : null );

        return videoDirItem;
    }

}
