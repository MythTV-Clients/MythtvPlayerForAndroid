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

package org.mythtv.android.presentation.model;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/12/14.
 */
public class ProgramModel implements Serializable {

    private long id;
    private DateTime startTime;
    private DateTime endTime;
    private String title;
    private String subTitle;
    private String category;
    private String catType;
    private Boolean repeat;
    private Integer videoProps;
    private Integer audioProps;
    private Integer subProps;
    private String seriesId;
    private String programId;
    private Double stars;
    private Long fileSize;
    private DateTime lastModified;
    private Integer programFlags;
    private String fileName;
    private String hostName;
    private LocalDate airdate;
    private String description;
    private String inetref;
    private Integer season;
    private Integer episode;
    private Integer totalEpisodes;
    private ChannelInfoModel channel;
    private RecordingInfoModel recording;
    private List<ArtworkInfoModel> artworkInfos;
    private List<CastMemberModel> castMembers;

    private LiveStreamInfoModel liveStreamInfo;

    public ProgramModel() { }

    public ProgramModel(long id, DateTime startTime, DateTime endTime, String title, String subTitle, String category, String catType, Boolean repeat, Integer videoProps, Integer audioProps, Integer subProps, String seriesId, String programId, Double stars, Long fileSize, DateTime lastModified, Integer programFlags, String fileName, String hostName, LocalDate airdate, String description, String inetref, Integer season, Integer episode, Integer totalEpisodes, ChannelInfoModel channel, RecordingInfoModel recording, List<ArtworkInfoModel> artworkInfos, List<CastMemberModel> castMembers, LiveStreamInfoModel liveStreamInfo) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.subTitle = subTitle;
        this.category = category;
        this.catType = catType;
        this.repeat = repeat;
        this.videoProps = videoProps;
        this.audioProps = audioProps;
        this.subProps = subProps;
        this.seriesId = seriesId;
        this.programId = programId;
        this.stars = stars;
        this.fileSize = fileSize;
        this.lastModified = lastModified;
        this.programFlags = programFlags;
        this.fileName = fileName;
        this.hostName = hostName;
        this.airdate = airdate;
        this.description = description;
        this.inetref = inetref;
        this.season = season;
        this.episode = episode;
        this.totalEpisodes = totalEpisodes;
        this.channel = channel;
        this.recording = recording;
        this.artworkInfos = artworkInfos;
        this.castMembers = castMembers;
        this.liveStreamInfo = liveStreamInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }

    public Integer getVideoProps() {
        return videoProps;
    }

    public void setVideoProps(Integer videoProps) {
        this.videoProps = videoProps;
    }

    public Integer getAudioProps() {
        return audioProps;
    }

    public void setAudioProps(Integer audioProps) {
        this.audioProps = audioProps;
    }

    public Integer getSubProps() {
        return subProps;
    }

    public void setSubProps(Integer subProps) {
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

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public DateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(DateTime lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getProgramFlags() {
        return programFlags;
    }

    public void setProgramFlags(Integer programFlags) {
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

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Integer getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(Integer totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    public ChannelInfoModel getChannel() {
        return channel;
    }

    public void setChannel(ChannelInfoModel channel) {
        this.channel = channel;
    }

    public RecordingInfoModel getRecording() {
        return recording;
    }

    public void setRecording(RecordingInfoModel recording) {
        this.recording = recording;
    }

    public List<ArtworkInfoModel> getArtworkInfos() {
        return artworkInfos;
    }

    public void setArtworkInfos(List<ArtworkInfoModel> artworkInfos) {
        this.artworkInfos = artworkInfos;
    }

    public List<CastMemberModel> getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(List<CastMemberModel> castMembers) {
        this.castMembers = castMembers;
    }

    public LiveStreamInfoModel getLiveStreamInfo() {
        return liveStreamInfo;
    }

    public void setLiveStreamInfo(LiveStreamInfoModel liveStreamInfo) {
        this.liveStreamInfo = liveStreamInfo;
    }

    @Override
    public String toString() {
        return "ProgramModel{" +
                "id=" + id +
                ", startTime=" + startTime +
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
                ", artworkInfos=" + artworkInfos +
                ", castMembers=" + castMembers +
                ", liveStreamInfo=" + liveStreamInfo +
                '}';
    }

}
