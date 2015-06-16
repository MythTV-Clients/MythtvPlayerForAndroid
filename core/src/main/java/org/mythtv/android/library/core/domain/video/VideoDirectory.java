/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.library.core.domain.video;

import java.util.List;

/*
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
