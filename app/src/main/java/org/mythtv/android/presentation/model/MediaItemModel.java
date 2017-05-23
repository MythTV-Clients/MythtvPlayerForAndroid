package org.mythtv.android.presentation.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import org.joda.time.DateTime;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.annotations.IgnoreHashEquals;

import java.util.List;

import javax.annotation.Nullable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 7/10/16.
 */
@AutoValue
@SuppressWarnings( "PMD.GodClass" )
public abstract class MediaItemModel implements Parcelable {

    public static final String WIDTH_QS = "&Width=%s";

    public abstract int id();

    @Nullable
    public abstract Media media();

    @Nullable
    public abstract String title();

    @Nullable
    public abstract String subTitle();

    @Nullable
    public abstract String description();

    @Nullable
    public abstract DateTime startDate();

    public abstract int programFlags();

    public abstract int season();

    public abstract int episode();

    @Nullable
    public abstract String studio(); // video = studio, recording = channel

    @Nullable
    public abstract String castMembers();

    @Nullable
    public abstract String characters();

    @Nullable
    public abstract String url();

    @Nullable
    public abstract String fanartUrl();

    @Nullable
    public abstract String coverartUrl();

    @Nullable
    public abstract String bannerUrl();

    @Nullable
    public abstract String previewUrl();

    @Nullable
    public abstract String contentType();

    public abstract long duration();

    public abstract int percentComplete();

    public abstract boolean recording();

    public abstract int liveStreamId();

    public abstract boolean watched();

    @Nullable
    public abstract String updateSavedBookmarkUrl();

    public abstract long bookmark();

    @Nullable
    public abstract String inetref();

    @Nullable
    public abstract String certification();

    public abstract int parentalLevel();

    @Nullable
    public abstract String recordingGroup();

    @Nullable
    @IgnoreHashEquals
    public abstract List<ErrorModel> validationErrors();

    public static MediaItemModel create( int id, Media media, String title, String subTitle, String description, DateTime startDate, int programFlags, int season, int episode, String studio,
                                   String castMembers, String characters, String url, String fanartUrl, String coverartUrl, String bannerUrl, String previewUrl, String contentType,
                                   long duration, int percentComplete, boolean recording, int liveStreamId,
                                   boolean watched, String updateSavedBookmarkUrl, long bookmark, String inetref,
                                   String certification, int parentalLevel, String recordingGroup, List<ErrorModel> validationErrors ) {

        return new AutoValue_MediaItemModel( id, media, title, subTitle, description, startDate, programFlags, season, episode, studio,
                castMembers, characters, url, fanartUrl, coverartUrl, bannerUrl, previewUrl, contentType,
                duration, percentComplete, recording, liveStreamId,
                watched, updateSavedBookmarkUrl, bookmark, inetref,
                certification, parentalLevel, recordingGroup, validationErrors );
    }

    public boolean isValid() {

        return null != media() && validationErrors().isEmpty();
    }

    public String getFanartUrl( String width ) {

        String url = fanartUrl();
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public String getPreviewUrl( String width ) {

        String url = previewUrl();
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

}
