package org.mythtv.android.domain;

import org.joda.time.DateTime;

//import lombok.Data;

/**
 * Created by dmfrey on 7/10/16.
 */

//@Data
public class MediaItem {

    public enum Media {
        PROGRAM, VIDEO
    }

    public enum Artwork {
        coverart, fanart, banner
    }

    private int id;
    private Media media;
    private String title;
    private String subTitle;
    private String description;
    private DateTime startDate;
    private String season;
    private String episode;
    private String studio; // video = studio, recording = channel
    private String url;
    private String contentType;
    private long duration;

    public MediaItem() {
    }

    public MediaItem(int id, Media media, String title, String subTitle, String studio, String url, String contentType, long duration) {
        this.id = id;
        this.media = media;
        this.title = title;
        this.subTitle = subTitle;
        this.studio = studio;
        this.url = url;
        this.contentType = contentType;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
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

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "id=" + id +
                ", media=" + media +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", studio='" + studio + '\'' +
                ", url='" + url + '\'' +
                ", contentType='" + contentType + '\'' +
                ", duration=" + duration +
                '}';
    }

}
