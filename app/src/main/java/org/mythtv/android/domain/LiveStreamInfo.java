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

package org.mythtv.android.domain;

import org.joda.time.DateTime;

import lombok.Data;

/*
 * Created by dmfrey on 10/17/15.
 */
@Data
public class LiveStreamInfo {

    private int id;
    private int width;
    private int height;
    private int bitrate;
    private int audioBitrate;
    private int segmentSize;
    private int maxSegments;
    private int startSegment;
    private int currentSegment;
    private int segmentCount;
    private int percentComplete;
    private DateTime created;
    private DateTime lastModified;
    private String relativeUrl;
    private String fullUrl;
    private String statusString;
    private int statusInt;
    private String statusMessage;
    private String sourceFile;
    private String sourceHost;
    private int sourceWidth;
    private int sourceHeight;
    private int audioOnlyBitrate;

}
