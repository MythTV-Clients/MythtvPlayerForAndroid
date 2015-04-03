package org.mythtv.android.library.persistence.domain.dvr;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.mythtv.android.library.events.dvr.ArtworkInfoDetails;
import org.mythtv.android.library.events.dvr.CastMemberDetails;
import org.mythtv.android.library.events.dvr.ProgramDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/12/14.
 */
public class Program implements Serializable {

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
    private ChannelInfo channel;
    private RecordingInfo recording;
    private List<ArtworkInfo> artworkInfos;
    private List<CastMember> castMembers;

    public Program() { }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
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

    public ChannelInfo getChannel() {
        return channel;
    }

    public void setChannel(ChannelInfo channel) {
        this.channel = channel;
    }

    public RecordingInfo getRecording() {
        return recording;
    }

    public void setRecording(RecordingInfo recording) {
        this.recording = recording;
    }

    public List<ArtworkInfo> getArtworkInfos() {
        return artworkInfos;
    }

    public void setArtworkInfos(List<ArtworkInfo> artworkInfos) {
        this.artworkInfos = artworkInfos;
    }

    public List<CastMember> getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(List<CastMember> castMembers) {
        this.castMembers = castMembers;
    }

    @Override
    public String toString() {
        return "Program{" +
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
                '}';
    }

    public ProgramDetails toDetails() {

        ProgramDetails details = new ProgramDetails();
        details.setId(id);
        details.setStartTime(startTime);
        details.setEndTime(endTime);
        details.setTitle(title);
        details.setSubTitle(subTitle);
        details.setCategory(category);
        details.setCatType(catType);
        details.setRepeat(repeat);
        details.setVideoProps(videoProps);
        details.setAudioProps(audioProps);
        details.setSubProps(subProps);
        details.setSeriesId(seriesId);
        details.setProgramId(programId);
        details.setStars(stars);
        details.setFileSize(fileSize);
        details.setLastModified(lastModified);
        details.setProgramFlags(programFlags);
        details.setFileName(fileName);
        details.setHostName(hostName);
        details.setAirdate(airdate);
        details.setDescription(description);
        details.setInetref( inetref );
        details.setSeason( season );
        details.setEpisode( episode );
        details.setTotalEpisodes( totalEpisodes );

        if( null != channel ) {
            details.setChannel( channel.toDetails() );
        }

        if( null != recording ) {
            details.setRecording( recording.toDetails() );
        }

        List<ArtworkInfoDetails> artworkInfoDetails = new ArrayList<ArtworkInfoDetails>();
        if( null != artworkInfos && !artworkInfos.isEmpty() ) {
            for( ArtworkInfo artworkInfo : artworkInfos ) {
                artworkInfoDetails.add( artworkInfo.toDetails() );
            }
        }
        details.setArtworkInfos( artworkInfoDetails );

        List<CastMemberDetails> castMemberDetails = new ArrayList<CastMemberDetails>();
        if( null != castMembers && !castMembers.isEmpty() ) {
            for( CastMember castMember : castMembers ) {
                castMemberDetails.add( castMember.toDetails() );
            }
        }
        details.setCastMembers( castMemberDetails );

        return details;
    }

    public static Program fromDetails( ProgramDetails details ) {

        Program program = new Program();
        program.setId(details.getId());
        program.setStartTime( details.getStartTime() );
        program.setEndTime( details.getEndTime() );
        program.setTitle( details.getTitle() );
        program.setSubTitle( details.getSubTitle() );
        program.setCategory( details.getCategory() );
        program.setCatType( details.getCatType() );
        program.setRepeat( details.getRepeat() );
        program.setVideoProps( details.getVideoProps() );
        program.setAudioProps( details.getAudioProps() );
        program.setSubProps( details.getSubProps() );
        program.setSeriesId( details.getSeriesId() );
        program.setProgramId( details.getProgramId() );
        program.setStars( details.getStars() );
        program.setFileSize( details.getFileSize() );
        program.setLastModified( details.getLastModified() );
        program.setProgramFlags( details.getProgramFlags() );
        program.setFileName( details.getFileName() );
        program.setHostName( details.getHostName() );
        program.setAirdate( details.getAirdate() );
        program.setDescription( details.getDescription() );
        program.setInetref( details.getInetref() );
        program.setSeason( details.getSeason() );
        program.setEpisode( details.getEpisode() );
        program.setTotalEpisodes( details.getTotalEpisodes() );

        if( null != details.getChannel() ) {
            program.setChannel( ChannelInfo.fromDetails( details.getChannel() ) );
        }

        if( null != details.getRecording() ) {
            program.setRecording( RecordingInfo.fromDetails( details.getRecording() ) );
        }

        List<ArtworkInfo> artworkInfos = new ArrayList<ArtworkInfo>();
        if( null != details.getArtworkInfos() && !details.getArtworkInfos().isEmpty() ) {
            for( ArtworkInfoDetails detail : details.getArtworkInfos() ) {
                artworkInfos.add( ArtworkInfo.fromDetails( detail ) );
            }
        }
        program.setArtworkInfos( artworkInfos );

        List<CastMember> castMembers = new ArrayList<CastMember>();
        if( null != details.getCastMembers() && !details.getCastMembers().isEmpty() ) {
            for( CastMemberDetails detail : details.getCastMembers() ) {
                castMembers.add( CastMember.fromDetails( detail ) );
            }
        }
        program.setCastMembers( castMembers );

        return program;
    }

}
