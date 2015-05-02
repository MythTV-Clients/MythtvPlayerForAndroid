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

package org.mythtv.android.library.events.content;

import org.mythtv.android.library.events.CreateEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class AddLiveStreamEvent extends CreateEvent {

    private final String storageGroup;
    private final String fileName;
    private final String hostName;
    private final Integer maxSegments;
    private final Integer width;
    private final Integer height;
    private final Integer bitrate;
    private final Integer audioBitrate;
    private final Integer sampleRate;

    public AddLiveStreamEvent( String storageGroup, String fileName, String hostName, Integer maxSegments, Integer width, Integer height, Integer bitrate, Integer audioBitrate, Integer sampleRate ) {

        this.storageGroup = storageGroup;
        this.fileName = fileName;
        this.hostName = hostName;
        this.maxSegments = maxSegments;
        this.width = width;
        this.height = height;
        this.bitrate = bitrate;
        this.audioBitrate = audioBitrate;
        this.sampleRate = sampleRate;

    }

    public String getStorageGroup() {
        return storageGroup;
    }

    public String getFileName() {
        return fileName;
    }

    public String getHostName() {
        return hostName;
    }

    public Integer getMaxSegments() {
        return maxSegments;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public Integer getAudioBitrate() {
        return audioBitrate;
    }

    public Integer getSampleRate() {
        return sampleRate;
    }
}
