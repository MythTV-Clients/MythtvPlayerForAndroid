package org.mythtv.android.domain;

import org.joda.time.DateTime;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 7/10/16.
 */

public class MediaItem {

    private int id;
    private Media media;
    private String title;
    private String subTitle;
    private String description;
    private DateTime startDate;
    private int programFlags;
    private int season;
    private int episode;
    private String studio; // video = studio, recording = channel
    private String castMembers;
    private String characters;
    private String url;
    private String fanartUrl;
    private String coverartUrl;
    private String bannerUrl;
    private String previewUrl;
    private String contentType;
    private long duration;
    private int percentComplete;

    private boolean recording;

    private int liveStreamId;
    private String createHttpLiveStreamUrl;
    private String removeHttpLiveStreamUrl;
    private String getHttpLiveStreamUrl;

    private boolean watched;
    private String markWatchedUrl;

    private String updateSavedBookmarkUrl;
    private long bookmark;

    private String inetref;

    public MediaItem() {
    }

    public int getId() {

        return id;
    }

    public void setId( int id ) {

        this.id = id;

    }

    public Media getMedia() {

        return media;
    }

    public void setMedia( Media media ) {

        this.media = media;

    }

    public String getTitle() {

        return title;
    }

    public void setTitle( String title ) {

        this.title = title;

    }

    public String getSubTitle() {

        return subTitle;
    }

    public void setSubTitle( String subTitle ) {

        this.subTitle = subTitle;

    }

    public String getDescription() {

        return description;
    }

    public void setDescription( String description ) {

        this.description = description;

    }

    public DateTime getStartDate() {

        return startDate;
    }

    public void setStartDate( DateTime startDate ) {

        this.startDate = startDate;

    }

    public int getProgramFlags() {

        return programFlags;
    }

    public void setProgramFlags( int programFlags ) {

        this.programFlags = programFlags;

    }

    public int getSeason() {

        return season;
    }

    public void setSeason( int season ) {

        this.season = season;

    }

    public int getEpisode() {

        return episode;
    }

    public void setEpisode( int episode ) {

        this.episode = episode;

    }

    public String getStudio() {

        return studio;
    }

    public void setStudio( String studio ) {

        this.studio = studio;

    }

    public String getCastMembers() {

        return castMembers;
    }

    public void setCastMembers( String castMembers ) {

        this.castMembers = castMembers;

    }

    public String getCharacters() {

        return characters;
    }

    public void setCharacters( String characters ) {

        this.characters = characters;

    }

    public String getUrl() {

        return url;
    }

    public void setUrl( String url ) {

        if( null != url && !"".equals( url ) ) {

            url = url.replaceAll( " ", "%20" );
            this.url = url;

        }

    }

    public String getFanartUrl() {

        return fanartUrl;
    }

    public void setFanartUrl( String fanartUrl ) {

        if( null != fanartUrl && !"".equals( fanartUrl ) ) {

            fanartUrl = fanartUrl.replaceAll( " ", "%20" );
            this.fanartUrl = fanartUrl;

        }

    }

    public String getCoverartUrl() {

        return coverartUrl;
    }

    public void setCoverartUrl( String coverartUrl ) {

        if( null != coverartUrl && !"".equals( coverartUrl ) ) {

            coverartUrl = coverartUrl.replaceAll( " ", "%20" );
            this.coverartUrl = coverartUrl;

        }

    }

    public String getBannerUrl() {

        return bannerUrl;
    }

    public void setBannerUrl( String bannerUrl ) {

        if( null != bannerUrl && !"".equals( bannerUrl ) ) {

            bannerUrl = bannerUrl.replaceAll( " ", "%20" );
            this.bannerUrl = bannerUrl;

        }

    }

    public String getPreviewUrl() {

        return previewUrl;
    }

    public void setPreviewUrl( String previewUrl ) {

        if( null != previewUrl && !"".equals( previewUrl ) ) {

            previewUrl = previewUrl.replaceAll( " ", "%20" );
            this.previewUrl = previewUrl;

        }

    }

    public String getContentType() {

        return contentType;
    }

    public void setContentType( String contentType ) {

        this.contentType = contentType;
    }

    public long getDuration() {

        return duration;
    }

    public void setDuration( long duration ) {

        this.duration = duration;

    }

    public int getPercentComplete() {

        return percentComplete;
    }

    public void setPercentComplete( int percentComplete ) {

        this.percentComplete = percentComplete;

    }

    public boolean isRecording() {

        return recording;
    }

    public void setRecording( boolean recording ) {

        this.recording = recording;

    }

    public int getLiveStreamId() {

        return liveStreamId;
    }

    public void setLiveStreamId( int liveStreamId ) {

        this.liveStreamId = liveStreamId;

    }

    public String getCreateHttpLiveStreamUrl() {

        return createHttpLiveStreamUrl;
    }

    public void setCreateHttpLiveStreamUrl( String createHttpLiveStreamUrl ) {

        this.createHttpLiveStreamUrl = createHttpLiveStreamUrl;

    }

    public String getRemoveHttpLiveStreamUrl() {

        return removeHttpLiveStreamUrl;
    }

    public void setRemoveHttpLiveStreamUrl( String removeHttpLiveStreamUrl ) {

        this.removeHttpLiveStreamUrl = removeHttpLiveStreamUrl;

    }

    public String getGetHttpLiveStreamUrl() {

        return getHttpLiveStreamUrl;
    }

    public void setGetHttpLiveStreamUrl( String getHttpLiveStreamUrl ) {

        this.getHttpLiveStreamUrl = getHttpLiveStreamUrl;

    }

    public boolean isWatched() {

        return watched;
    }

    public void setWatched( boolean watched ) {

        this.watched = watched;

    }

    public String getMarkWatchedUrl() {

        return markWatchedUrl;
    }

    public void setMarkWatchedUrl( String markWatchedUrl ) {

        this.markWatchedUrl = markWatchedUrl;

    }

    public String getUpdateSavedBookmarkUrl() {

        return updateSavedBookmarkUrl;
    }

    public void setUpdateSavedBookmarkUrl( String updateSavedBookmarkUrl ) {

        this.updateSavedBookmarkUrl = updateSavedBookmarkUrl;

    }

    public long getBookmark() {

        return bookmark;
    }

    public void setBookmark(long bookmark ) {

        this.bookmark = bookmark;

    }

    public String getInetref() {

        return inetref;
    }

    public void setInetref( String inetref ) {

        this.inetref = inetref;

    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaItem mediaItem = (MediaItem) o;

        return getId() == mediaItem.getId() && getMedia() == mediaItem.getMedia();
    }

    @Override
    public int hashCode() {

        int result = getId() ^ (getId() >>> 31);
        result = 31 * result + getMedia().hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "id=" + id +
                ", media=" + media +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", programFlags=" + programFlags +
                ", season=" + season +
                ", episode=" + episode +
                ", studio='" + studio + '\'' +
                ", castMembers='" + castMembers + '\'' +
                ", characters='" + characters + '\'' +
                ", url='" + url + '\'' +
                ", fanartUrl='" + fanartUrl + '\'' +
                ", coverartUrl='" + coverartUrl + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", contentType='" + contentType + '\'' +
                ", duration=" + duration +
                ", percentComplete=" + percentComplete +
                ", recording=" + recording +
                ", liveStreamId=" + liveStreamId +
                ", createHttpLiveStreamUrl='" + createHttpLiveStreamUrl + '\'' +
                ", removeHttpLiveStreamUrl='" + removeHttpLiveStreamUrl + '\'' +
                ", getHttpLiveStreamUrl='" + getHttpLiveStreamUrl + '\'' +
                ", watched=" + watched +
                ", markWatchedUrl='" + markWatchedUrl + '\'' +
                ", updateSavedBookmarkUrl='" + updateSavedBookmarkUrl + '\'' +
                ", bookmark=" + bookmark +
                ", inetref='" + inetref + '\'' +
                '}';
    }

}
