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

package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/*
 * Created by dmfrey on 11/12/14.
 */
public class ProgramEntity {

    @SerializedName( "StartTime" )
    private DateTime startTime;

    @SerializedName( "EndTime" )
    private DateTime endTime;

    @SerializedName( "Title" )
    private String title;

    @SerializedName( "SubTitle" )
    private String subTitle;

    @SerializedName( "Category" )
    private String category;

    @SerializedName( "CatType" )
    private String catType;

    @SerializedName( "Repeat" )
    private boolean repeat;

    @SerializedName( "VideoProps" )
    private int videoProps;

    @SerializedName( "AudioProps" )
    private int audioProps;

    @SerializedName( "SubProps" )
    private int subProps;

    @SerializedName( "SeriesId" )
    private String seriesId;

    @SerializedName( "ProgramId" )
    private String programId;

    @SerializedName( "Stars" )
    private double stars;

    @SerializedName( "FileSize" )
    private long fileSize;

    @SerializedName( "LastModified" )
    private DateTime lastModified;

    @SerializedName( "ProgramFlags" )
    private int programFlags;

    @SerializedName( "FileName" )
    private String fileName;

    @SerializedName( "HostName" )
    private String hostName;

    @SerializedName( "AirDate" )
    private LocalDate airdate;

    @SerializedName( "Description" )
    private String description;

    @SerializedName( "Inetref" )
    private String inetref;

    @SerializedName( "Season" )
    private int season;

    @SerializedName( "Episode" )
    private int episode;

    @SerializedName( "TotalEpisodes" )
    private int totalEpisodes;

    @SerializedName( "Channel" )
    private ChannelInfoEntity channel;

    @SerializedName( "Recording" )
    private RecordingInfoEntity recording;

    @SerializedName( "Artwork" )
    private ArtworkEntity artwork;

    @SerializedName( "Cast" )
    private CastEntity cast;

    public ProgramEntity() { }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCatType() {
        return catType;
    }

    public void setCatType(String catType) {
        this.catType = catType;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public int getVideoProps() {
        return videoProps;
    }

    public void setVideoProps(int videoProps) {
        this.videoProps = videoProps;
    }

    public int getAudioProps() {
        return audioProps;
    }

    public void setAudioProps(int audioProps) {
        this.audioProps = audioProps;
    }

    public int getSubProps() {
        return subProps;
    }

    public void setSubProps(int subProps) {
        this.subProps = subProps;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public DateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(DateTime lastModified) {
        this.lastModified = lastModified;
    }

    public int getProgramFlags() {
        return programFlags;
    }

    public void setProgramFlags(int programFlags) {
        this.programFlags = programFlags;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public LocalDate getAirdate() {
        return airdate;
    }

    public void setAirdate(LocalDate airdate) {
        this.airdate = airdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInetref() {
        return inetref;
    }

    public void setInetref(String inetref) {
        this.inetref = inetref;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    public ChannelInfoEntity getChannel() {
        return channel;
    }

    public void setChannel(ChannelInfoEntity channel) {
        this.channel = channel;
    }

    public RecordingInfoEntity getRecording() {
        return recording;
    }

    public void setRecording(RecordingInfoEntity recording) {
        this.recording = recording;
    }

    public ArtworkEntity getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkEntity artwork) {
        this.artwork = artwork;
    }

    public CastEntity getCast() {
        return cast;
    }

    public void setCast(CastEntity cast) {
        this.cast = cast;
    }

    @Override
    public String toString() {
        return "ProgramEntity{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", category='" + category + '\'' +
                ", catType='" + catType + '\'' +
                ", repeat=" + repeat +
                ", videoProps=" + videoProps +
                ", audioProps=" + audioProps +
                ", subProps=" + subProps +
                ", seriesId='" + seriesId + '\'' +
                ", programId='" + programId + '\'' +
                ", stars=" + stars +
                ", fileSize=" + fileSize +
                ", lastModified=" + lastModified +
                ", programFlags=" + programFlags +
                ", fileName='" + fileName + '\'' +
                ", hostName='" + hostName + '\'' +
                ", airdate=" + airdate +
                ", description='" + description + '\'' +
                ", inetref='" + inetref + '\'' +
                ", season=" + season +
                ", episode=" + episode +
                ", totalEpisodes=" + totalEpisodes +
                ", channel=" + channel +
                ", recording=" + recording +
                ", artwork=" + artwork +
                ", cast=" + cast +
                '}';
    }

}
