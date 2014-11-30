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
