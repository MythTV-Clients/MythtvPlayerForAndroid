package org.mythtv.android.library.events.dvr;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by dmfrey on 11/12/14.
 */
public class ProgramDetails {

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
    private ChannelInfoDetails channel;
    private RecordingInfoDetails recording;
    private List<ArtworkInfoDetails> artworkInfos;
    private List<CastMemberDetails> castMembers;

    public ProgramDetails() {
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

    public ChannelInfoDetails getChannel() {
        return channel;
    }

    public void setChannel(ChannelInfoDetails channel) {
        this.channel = channel;
    }

    public RecordingInfoDetails getRecording() {
        return recording;
    }

    public void setRecording(RecordingInfoDetails recording) {
        this.recording = recording;
    }

    public List<ArtworkInfoDetails> getArtworkInfos() {
        return artworkInfos;
    }

    public void setArtworkInfos(List<ArtworkInfoDetails> artworkInfos) {
        this.artworkInfos = artworkInfos;
    }

    public List<CastMemberDetails> getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(List<CastMemberDetails> castMembers) {
        this.castMembers = castMembers;
    }
}
