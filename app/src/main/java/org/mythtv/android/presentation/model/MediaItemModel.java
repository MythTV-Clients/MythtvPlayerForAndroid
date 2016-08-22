package org.mythtv.android.presentation.model;

import android.os.Bundle;

import java.util.ArrayList;

//import lombok.Data;

/**
 * Created by dmfrey on 7/10/16.
 */

//@Data
public class MediaItemModel {

    public static final String KEY_TITLE = "title";
    public static final String KEY_SUBTITLE = "subtitle";
    public static final String KEY_STUDIO = "studio";
    public static final String KEY_URL = "url";
    public static final String KEY_CONTENT_TYPE = "contentType";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_IMAGES = "images";

    private String title;
    private String subTitle;
    private String studio;
    private String url;
    private String contentType;
    private long duration;
    private ArrayList<String> images = new ArrayList<>();

    public MediaItemModel() {

    }

    public MediaItemModel(String title, String subTitle, String studio, String url, String contentType, long duration, ArrayList<String> images) {
        this.title = title;
        this.subTitle = subTitle;
        this.studio = studio;
        this.url = url;
        this.contentType = contentType;
        this.duration = duration;
        this.images = images;
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

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "MediaItemModel{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", studio='" + studio + '\'' +
                ", url='" + url + '\'' +
                ", contentType='" + contentType + '\'' +
                ", duration=" + duration +
                ", images=" + images +
                '}';
    }

    public Bundle toBundle() {

        Bundle wrapper = new Bundle();
        wrapper.putString( KEY_TITLE, title );
        wrapper.putString( KEY_SUBTITLE, subTitle );
        wrapper.putString( KEY_URL, url );
        wrapper.putString( KEY_STUDIO, studio );
        wrapper.putStringArrayList( KEY_IMAGES, images );
        wrapper.putString( KEY_CONTENT_TYPE, selectContentType() );

        return wrapper;
    }

    public static final MediaItemModel fromBundle( Bundle wrapper ) {

        if( null == wrapper ) {

            return null;
        }

        MediaItemModel media = new MediaItemModel();
        media.setUrl( wrapper.getString( KEY_URL ) );
        media.setTitle( wrapper.getString( KEY_TITLE ) );
        media.setSubTitle( wrapper.getString( KEY_SUBTITLE ) );
        media.setStudio( wrapper.getString( KEY_STUDIO ) );
        media.images.addAll( wrapper.getStringArrayList( KEY_IMAGES ) );
        media.setContentType( wrapper.getString( KEY_CONTENT_TYPE ) );

        return media;
    }

    private String selectContentType() {

        String contentType = "video/mp4";
        if( url.endsWith( "m3u8" ) ) {

            contentType = "application/x-mpegURL";

        } else if( url.endsWith( "mkv" ) ) {

            contentType = "video/divx";

        } else if( url.endsWith( "avi" ) ) {

            contentType = "video/avi";

        } else if( url.endsWith( "3gp" ) ) {

            contentType = "video/3gpp";

        }

        return contentType;
    }

}
