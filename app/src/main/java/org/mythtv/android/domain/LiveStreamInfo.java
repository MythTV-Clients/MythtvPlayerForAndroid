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

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 10/17/15.
 */
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

    public LiveStreamInfo() {
    }

    public LiveStreamInfo(int id, int width, int height, int bitrate, int audioBitrate, int segmentSize, int maxSegments, int startSegment, int currentSegment, int segmentCount, int percentComplete, DateTime created, DateTime lastModified, String relativeUrl, String fullUrl, String statusString, int statusInt, String statusMessage, String sourceFile, String sourceHost, int sourceWidth, int sourceHeight, int audioOnlyBitrate) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.bitrate = bitrate;
        this.audioBitrate = audioBitrate;
        this.segmentSize = segmentSize;
        this.maxSegments = maxSegments;
        this.startSegment = startSegment;
        this.currentSegment = currentSegment;
        this.segmentCount = segmentCount;
        this.percentComplete = percentComplete;
        this.created = created;
        this.lastModified = lastModified;
        this.relativeUrl = relativeUrl;
        this.fullUrl = fullUrl;
        this.statusString = statusString;
        this.statusInt = statusInt;
        this.statusMessage = statusMessage;
        this.sourceFile = sourceFile;
        this.sourceHost = sourceHost;
        this.sourceWidth = sourceWidth;
        this.sourceHeight = sourceHeight;
        this.audioOnlyBitrate = audioOnlyBitrate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getAudioBitrate() {
        return audioBitrate;
    }

    public void setAudioBitrate(int audioBitrate) {
        this.audioBitrate = audioBitrate;
    }

    public int getSegmentSize() {
        return segmentSize;
    }

    public void setSegmentSize(int segmentSize) {
        this.segmentSize = segmentSize;
    }

    public int getMaxSegments() {
        return maxSegments;
    }

    public void setMaxSegments(int maxSegments) {
        this.maxSegments = maxSegments;
    }

    public int getStartSegment() {
        return startSegment;
    }

    public void setStartSegment(int startSegment) {
        this.startSegment = startSegment;
    }

    public int getCurrentSegment() {
        return currentSegment;
    }

    public void setCurrentSegment(int currentSegment) {
        this.currentSegment = currentSegment;
    }

    public int getSegmentCount() {
        return segmentCount;
    }

    public void setSegmentCount(int segmentCount) {
        this.segmentCount = segmentCount;
    }

    public int getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(int percentComplete) {
        this.percentComplete = percentComplete;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public DateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(DateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public int getStatusInt() {
        return statusInt;
    }

    public void setStatusInt(int statusInt) {
        this.statusInt = statusInt;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getSourceHost() {
        return sourceHost;
    }

    public void setSourceHost(String sourceHost) {
        this.sourceHost = sourceHost;
    }

    public int getSourceWidth() {
        return sourceWidth;
    }

    public void setSourceWidth(int sourceWidth) {
        this.sourceWidth = sourceWidth;
    }

    public int getSourceHeight() {
        return sourceHeight;
    }

    public void setSourceHeight(int sourceHeight) {
        this.sourceHeight = sourceHeight;
    }

    public int getAudioOnlyBitrate() {
        return audioOnlyBitrate;
    }

    public void setAudioOnlyBitrate(int audioOnlyBitrate) {
        this.audioOnlyBitrate = audioOnlyBitrate;
    }

    @Override
    public String toString() {
        return "LiveStreamInfo{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", bitrate=" + bitrate +
                ", audioBitrate=" + audioBitrate +
                ", segmentSize=" + segmentSize +
                ", maxSegments=" + maxSegments +
                ", startSegment=" + startSegment +
                ", currentSegment=" + currentSegment +
                ", segmentCount=" + segmentCount +
                ", percentComplete=" + percentComplete +
                ", created=" + created +
                ", lastModified=" + lastModified +
                ", relativeUrl='" + relativeUrl + '\'' +
                ", fullUrl='" + fullUrl + '\'' +
                ", statusString='" + statusString + '\'' +
                ", statusInt=" + statusInt +
                ", statusMessage='" + statusMessage + '\'' +
                ", sourceFile='" + sourceFile + '\'' +
                ", sourceHost='" + sourceHost + '\'' +
                ", sourceWidth=" + sourceWidth +
                ", sourceHeight=" + sourceHeight +
                ", audioOnlyBitrate=" + audioOnlyBitrate +
                '}';
    }

}
