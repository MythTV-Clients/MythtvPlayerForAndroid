package org.mythtv.android.library.core.domain.content;

import org.joda.time.DateTime;
import org.mythtv.android.library.events.content.LiveStreamDetails;

import java.io.Serializable;

/**
 * Created by dmfrey on 11/18/14.
 */
public class LiveStreamInfo implements Serializable {

    private Integer id;
    private Integer width;
    private Integer height;
    private Integer bitrate;
    private Integer audioBitrate;
    private Integer segmentSize;
    private Integer maxSegments;
    private Integer startSegment;
    private Integer currentSegment;
    private Integer segmentCount;
    private Integer percentComplete;
    private DateTime created;
    private DateTime lastModified;
    private String relativeURL;
    private String fullURL;
    private String statusStr;
    private Integer statusInt;
    private String statusMessage;
    private String sourceFile;
    private String sourceHost;
    private Integer sourceWidth;
    private Integer sourceHeight;
    private Integer audioOnlyBitrate;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId( Integer id ) {
        this.id = id;
    }

    /**
     * @return the width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth( Integer width ) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight( Integer height ) {
        this.height = height;
    }

    /**
     * @return the bitrate
     */
    public Integer getBitrate() {
        return bitrate;
    }

    /**
     * @param bitrate the bitrate to set
     */
    public void setBitrate( Integer bitrate ) {
        this.bitrate = bitrate;
    }

    /**
     * @return the audioBitrate
     */
    public Integer getAudioBitrate() {
        return audioBitrate;
    }

    /**
     * @param audioBitrate the audioBitrate to set
     */
    public void setAudioBitrate( Integer audioBitrate ) {
        this.audioBitrate = audioBitrate;
    }

    /**
     * @return the segmentSize
     */
    public Integer getSegmentSize() {
        return segmentSize;
    }

    /**
     * @param segmentSize the segmentSize to set
     */
    public void setSegmentSize( Integer segmentSize ) {
        this.segmentSize = segmentSize;
    }

    /**
     * @return the maxSegments
     */
    public Integer getMaxSegments() {
        return maxSegments;
    }

    /**
     * @param maxSegments the maxSegments to set
     */
    public void setMaxSegments( Integer maxSegments ) {
        this.maxSegments = maxSegments;
    }

    /**
     * @return the startSegment
     */
    public Integer getStartSegment() {
        return startSegment;
    }

    /**
     * @param startSegment the startSegment to set
     */
    public void setStartSegment( Integer startSegment ) {
        this.startSegment = startSegment;
    }

    /**
     * @return the currentSegment
     */
    public Integer getCurrentSegment() {
        return currentSegment;
    }

    /**
     * @param currentSegment the currentSegment to set
     */
    public void setCurrentSegment( Integer currentSegment ) {
        this.currentSegment = currentSegment;
    }

    /**
     * @return the segmentCount
     */
    public Integer getSegmentCount() {
        return segmentCount;
    }

    /**
     * @param segmentCount the segmentCount to set
     */
    public void setSegmentCount( Integer segmentCount ) {
        this.segmentCount = segmentCount;
    }

    /**
     * @return the percentComplete
     */
    public Integer getPercentComplete() {
        return percentComplete;
    }

    /**
     * @param percentComplete the percentComplete to set
     */
    public void setPercentComplete( Integer percentComplete ) {
        this.percentComplete = percentComplete;
    }

    /**
     * @return the created
     */
    public DateTime getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated( DateTime created ) {
        this.created = created;
    }

    /**
     * @return the lastModified
     */
    public DateTime getLastModified() {
        return lastModified;
    }

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified( DateTime lastModified ) {
        this.lastModified = lastModified;
    }

    /**
     * @return the relativeURL
     */
    public String getRelativeURL() {
        return relativeURL;
    }

    /**
     * @param relativeURL the relativeURL to set
     */
    public void setRelativeURL( String relativeURL ) {
        this.relativeURL = relativeURL;
    }

    /**
     * @return the fullURL
     */
    public String getFullURL() {
        return fullURL;
    }

    /**
     * @param fullURL the fullURL to set
     */
    public void setFullURL( String fullURL ) {
        this.fullURL = fullURL;
    }

    /**
     * @return the statusStr
     */
    public String getStatusStr() {
        return statusStr;
    }

    /**
     * @param statusStr the statusStr to set
     */
    public void setStatusStr( String statusStr ) {
        this.statusStr = statusStr;
    }

    /**
     * @return the statusInt
     */
    public Integer getStatusInt() {
        return statusInt;
    }

    /**
     * @param statusInt the statusInt to set
     */
    public void setStatusInt( Integer statusInt ) {
        this.statusInt = statusInt;
    }

    /**
     * @return the statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * @param statusMessage the statusMessage to set
     */
    public void setStatusMessage( String statusMessage ) {
        this.statusMessage = statusMessage;
    }

    /**
     * @return the sourceFile
     */
    public String getSourceFile() {
        return sourceFile;
    }

    /**
     * @param sourceFile the sourceFile to set
     */
    public void setSourceFile( String sourceFile ) {
        this.sourceFile = sourceFile;
    }

    /**
     * @return the sourceHost
     */
    public String getSourceHost() {
        return sourceHost;
    }

    /**
     * @param sourceHost the sourceHost to set
     */
    public void setSourceHost( String sourceHost ) {
        this.sourceHost = sourceHost;
    }

    /**
     * @return the sourceWidth
     */
    public Integer getSourceWidth() {
        return sourceWidth;
    }

    /**
     * @param sourceWidth the sourceWidth to set
     */
    public void setSourceWidth( Integer sourceWidth ) {
        this.sourceWidth = sourceWidth;
    }

    /**
     * @return the sourceHeight
     */
    public Integer getSourceHeight() {
        return sourceHeight;
    }

    /**
     * @param sourceHeight the sourceHeight to set
     */
    public void setSourceHeight( Integer sourceHeight ) {
        this.sourceHeight = sourceHeight;
    }

    /**
     * @return the audioOnlyBitrate
     */
    public Integer getAudioOnlyBitrate() {
        return audioOnlyBitrate;
    }

    /**
     * @param audioOnlyBitrate the audioOnlyBitrate to set
     */
    public void setAudioOnlyBitrate( Integer audioOnlyBitrate ) {
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
                ", relativeURL='" + relativeURL + '\'' +
                ", fullURL='" + fullURL + '\'' +
                ", statusStr='" + statusStr + '\'' +
                ", statusInt=" + statusInt +
                ", statusMessage='" + statusMessage + '\'' +
                ", sourceFile='" + sourceFile + '\'' +
                ", sourceHost='" + sourceHost + '\'' +
                ", sourceWidth=" + sourceWidth +
                ", sourceHeight=" + sourceHeight +
                ", audioOnlyBitrate=" + audioOnlyBitrate +
                '}';
    }

    public LiveStreamDetails toDetails() {

        LiveStreamDetails details = new LiveStreamDetails();
        details.setId( id );
        details.setWidth( width );
        details.setHeight( height );
        details.setBitrate( bitrate );
        details.setAudioBitrate( audioBitrate );
        details.setSegmentSize( segmentSize );
        details.setMaxSegments( maxSegments );
        details.setStartSegment( startSegment );
        details.setCurrentSegment( currentSegment );
        details.setSegmentCount( segmentCount );
        details.setPercentComplete( percentComplete );
        details.setCreated( created );
        details.setLastModified( lastModified );
        details.setRelativeURL( relativeURL );
        details.setFullURL( fullURL );
        details.setStatusStr( statusStr );
        details.setStatusInt( statusInt );
        details.setStatusMessage( statusMessage );
        details.setSourceFile( sourceFile );
        details.setSourceHost( sourceHost );
        details.setSourceWidth( sourceWidth );
        details.setSourceHeight( sourceHeight );
        details.setAudioOnlyBitrate( audioOnlyBitrate );

        return details;
    }

    public static LiveStreamInfo fromDetails( LiveStreamDetails details ) {

        LiveStreamInfo liveStreamInfo = new LiveStreamInfo();
        liveStreamInfo.setId( details.getId() );
        liveStreamInfo.setWidth( details.getWidth() );
        liveStreamInfo.setHeight( details.getHeight() );
        liveStreamInfo.setBitrate( details.getBitrate() );
        liveStreamInfo.setAudioBitrate( details.getAudioBitrate() );
        liveStreamInfo.setSegmentSize( details.getSegmentSize() );
        liveStreamInfo.setMaxSegments( details.getMaxSegments() );
        liveStreamInfo.setStartSegment( details.getStartSegment() );
        liveStreamInfo.setCurrentSegment( details.getCurrentSegment() );
        liveStreamInfo.setSegmentCount( details.getSegmentCount() );
        liveStreamInfo.setPercentComplete( details.getPercentComplete() );
        liveStreamInfo.setCreated( details.getCreated() );
        liveStreamInfo.setLastModified( details.getLastModified() );
        liveStreamInfo.setRelativeURL( details.getRelativeURL() );
        liveStreamInfo.setFullURL( details.getFullURL() );
        liveStreamInfo.setStatusStr( details.getStatusStr() );
        liveStreamInfo.setStatusInt( details.getStatusInt() );
        liveStreamInfo.setStatusMessage( details.getStatusMessage() );
        liveStreamInfo.setSourceFile( details.getSourceFile() );
        liveStreamInfo.setSourceHost( details.getSourceHost() );
        liveStreamInfo.setSourceWidth( details.getSourceWidth() );
        liveStreamInfo.setSourceHeight( details.getSourceHeight() );
        liveStreamInfo.setAudioOnlyBitrate( details.getAudioOnlyBitrate() );

        return liveStreamInfo;
    }

}
