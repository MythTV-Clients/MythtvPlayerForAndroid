package org.mythtv.android.domain;

import org.joda.time.DateTime;

/**
 * Created by dmfrey on 7/10/16.
 */

public class MediaItem {

    private static final String WIDTH_QS = "&Width=%s";

    public enum Media {
        PROGRAM, VIDEO
    }

    private int id;
    private Media media;
    private String title;
    private String subTitle;
    private String description;
    private DateTime startDate;
    private int season;
    private int episode;
    private String studio; // video = studio, recording = channel
    private String url;
    private String fanartUrl;
    private String coverartUrl;
    private String bannerUrl;
    private String previewUrl;
    private String contentType;
    private long duration;

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

    public String getUrl() {

        return url;
    }

    public void setUrl( String url ) {

        url = url.replaceAll( " ", "%20" );
        this.url = url;

    }

    public String getFanartUrl() {

        return fanartUrl;
    }

    public String getFanartUrl( String width ) {

        String url = fanartUrl;
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public void setFanartUrl( String fanartUrl ) {

        fanartUrl = fanartUrl.replaceAll( " ", "%20" );
        this.fanartUrl = fanartUrl;

    }

    public String getCoverartUrl() {

        return coverartUrl;
    }

    public String getCoverartUrl( String width ) {

        String url = coverartUrl;
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public void setCoverartUrl( String coverartUrl ) {

        coverartUrl = coverartUrl.replaceAll( " ", "%20" );
        this.coverartUrl = coverartUrl;

    }

    public String getBannerUrl() {

        return bannerUrl;
    }

    public String getBannerUrl( String width ) {

        String url = bannerUrl;
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public void setBannerUrl( String bannerUrl ) {

        bannerUrl = bannerUrl.replaceAll( " ", "%20" );

        this.bannerUrl = bannerUrl;

    }

    public String getPreviewUrl() {

        return previewUrl;
    }

    public String getPreviewUrl( String width ) {

        String url = previewUrl;
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public void setPreviewUrl( String previewUrl ) {

        this.previewUrl = previewUrl;

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

    @Override
    public boolean equals( Object o ) {

        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;

        MediaItem mediaItem = (MediaItem) o;

        if( getId() != mediaItem.getId() ) return false;
        return getMedia() == mediaItem.getMedia();

    }

    @Override
    public int hashCode() {

        int result = getId();
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
                ", season=" + season +
                ", episode=" + episode +
                ", studio='" + studio + '\'' +
                ", url='" + url + '\'' +
                ", fanartUrl='" + fanartUrl + '\'' +
                ", coverartUrl='" + coverartUrl + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", contentType='" + contentType + '\'' +
                ", duration=" + duration +
                '}';
    }

}
