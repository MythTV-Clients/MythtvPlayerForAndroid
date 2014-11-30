package org.mythtv.android.library.events.content;

import org.mythtv.android.library.events.CreateEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class AddVideoLiveStreamEvent extends CreateEvent {

    private final Integer id;
    private final Integer maxSegments;
    private final Integer width;
    private final Integer height;
    private final Integer bitrate;
    private final Integer audioBitrate;
    private final Integer sampleRate;

    public AddVideoLiveStreamEvent( Integer id, Integer maxSegments, Integer width, Integer height, Integer bitrate, Integer audioBitrate, Integer sampleRate ) {

        this.id = id;
        this.maxSegments = maxSegments;
        this.width = width;
        this.height = height;
        this.bitrate = bitrate;
        this.audioBitrate = audioBitrate;
        this.sampleRate = sampleRate;

    }

    public Integer getId() {
        return id;
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
