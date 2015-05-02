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

import org.joda.time.DateTime;
import org.mythtv.android.library.events.CreateEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class AddRecordingLiveStreamEvent extends CreateEvent {

    private final Integer recordedId;
    private final Integer chanId;
    private final DateTime startTime;
    private final Integer maxSegments;
    private final Integer width;
    private final Integer height;
    private final Integer bitrate;
    private final Integer audioBitrate;
    private final Integer sampleRate;

    public AddRecordingLiveStreamEvent( final Integer recordedId, final Integer chanId, final DateTime startTime, final Integer maxSegments, final Integer width, final Integer height, final Integer bitrate, final Integer audioBitrate, final Integer sampleRate ) {

        this.recordedId = recordedId;
        this.chanId = chanId;
        this.startTime = startTime;
        this.maxSegments = maxSegments;
        this.width = width;
        this.height = height;
        this.bitrate = bitrate;
        this.audioBitrate = audioBitrate;
        this.sampleRate = sampleRate;

    }

    public Integer getRecordedId() {
        return recordedId;
    }

    public Integer getChanId() {
        return chanId;
    }

    public DateTime getStartTime() {
        return startTime;
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
