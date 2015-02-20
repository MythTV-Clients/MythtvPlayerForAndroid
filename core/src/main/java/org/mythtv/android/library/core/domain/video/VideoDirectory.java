package org.mythtv.android.library.core.domain.video;

import java.util.List;

/**
 * Created by dmfrey on 2/18/15.
 */
public class VideoDirectory {

    private final String name;
    private VideoDirectory parent;

    private List<Video> videos;
    private List<VideoDirectory> subDirectories;

    public VideoDirectory( final String name ) {

        this.name = name;

    }

    public String getName() {

        return name;
    }

    public VideoDirectory getParent() {

        return parent;
    }

    public void setParent( VideoDirectory parent ) {

        this.parent = parent;

    }

    public List<Video> getVideos() {

        return videos;
    }

    public void setVideos( List<Video> videos ) {

        this.videos = videos;

    }

    public List<VideoDirectory> getSubDirectories() {

        return subDirectories;
    }

    public void setSubDirectories( List<VideoDirectory> subDirectories ) {

        this.subDirectories = subDirectories;

    }

}
