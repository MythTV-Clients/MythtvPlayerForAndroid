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

import java.io.Serializable;

//import lombok.Data;

/*
 * Created by dmfrey on 10/30/15.
 */
//@Data
public class VideoMetadataInfoModel implements Serializable {

    private Integer id;
    private String title;
    private String subTitle;
    private String tagline;
    private String director;
    private String studio;
    private String description;
    private String certification;
    private String inetref;
    private Integer collectionref;
    private String homePage;
    private DateTime releaseDate;
    private DateTime addDate;
    private Float userRating;
    private int length;
    private int playCount;
    private int season;
    private int episode;
    private int parentalLevel;
    private boolean visible;
    private boolean watched;
    private boolean processed;
    private String contentType;
    private String fileName;
    private String hash;
    private String hostName;
    private String coverart;
    private String fanart;
    private String banner;
    private String screenshot;
    private String trailer;
    private ArtworkModel artwork;
    private CastModel cast;
    private LiveStreamInfoModel liveStreamInfo;

    public VideoMetadataInfoModel() {
    }

    public VideoMetadataInfoModel(Integer id, String title, String subTitle, String tagline, String director, String studio, String description, String certification, String inetref, Integer collectionref, String homePage, DateTime releaseDate, DateTime addDate, Float userRating, int length, int playCount, int season, int episode, int parentalLevel, boolean visible, boolean watched, boolean processed, String contentType, String fileName, String hash, String hostName, String coverart, String fanart, String banner, String screenshot, String trailer, ArtworkModel artwork, CastModel cast, LiveStreamInfoModel liveStreamInfo) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.tagline = tagline;
        this.director = director;
        this.studio = studio;
        this.description = description;
        this.certification = certification;
        this.inetref = inetref;
        this.collectionref = collectionref;
        this.homePage = homePage;
        this.releaseDate = releaseDate;
        this.addDate = addDate;
        this.userRating = userRating;
        this.length = length;
        this.playCount = playCount;
        this.season = season;
        this.episode = episode;
        this.parentalLevel = parentalLevel;
        this.visible = visible;
        this.watched = watched;
        this.processed = processed;
        this.contentType = contentType;
        this.fileName = fileName;
        this.hash = hash;
        this.hostName = hostName;
        this.coverart = coverart;
        this.fanart = fanart;
        this.banner = banner;
        this.screenshot = screenshot;
        this.trailer = trailer;
        this.artwork = artwork;
        this.cast = cast;
        this.liveStreamInfo = liveStreamInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getInetref() {
        return inetref;
    }

    public void setInetref(String inetref) {
        this.inetref = inetref;
    }

    public Integer getCollectionref() {
        return collectionref;
    }

    public void setCollectionref(Integer collectionref) {
        this.collectionref = collectionref;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public DateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(DateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public DateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(DateTime addDate) {
        this.addDate = addDate;
    }

    public Float getUserRating() {
        return userRating;
    }

    public void setUserRating(Float userRating) {
        this.userRating = userRating;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
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

    public int getParentalLevel() {
        return parentalLevel;
    }

    public void setParentalLevel(int parentalLevel) {
        this.parentalLevel = parentalLevel;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCoverart() {
        return coverart;
    }

    public void setCoverart(String coverart) {
        this.coverart = coverart;
    }

    public String getFanart() {
        return fanart;
    }

    public void setFanart(String fanart) {
        this.fanart = fanart;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public ArtworkModel getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkModel artwork) {
        this.artwork = artwork;
    }

    public CastModel getCast() {
        return cast;
    }

    public void setCast(CastModel cast) {
        this.cast = cast;
    }

    public LiveStreamInfoModel getLiveStreamInfo() {
        return liveStreamInfo;
    }

    public void setLiveStreamInfo(LiveStreamInfoModel liveStreamInfo) {
        this.liveStreamInfo = liveStreamInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoMetadataInfoModel that = (VideoMetadataInfoModel) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "VideoMetadataInfoModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", tagline='" + tagline + '\'' +
                ", director='" + director + '\'' +
                ", studio='" + studio + '\'' +
                ", description='" + description + '\'' +
                ", certification='" + certification + '\'' +
                ", inetref='" + inetref + '\'' +
                ", collectionref=" + collectionref +
                ", homePage='" + homePage + '\'' +
                ", releaseDate=" + releaseDate +
                ", addDate=" + addDate +
                ", userRating=" + userRating +
                ", length=" + length +
                ", playCount=" + playCount +
                ", season=" + season +
                ", episode=" + episode +
                ", parentalLevel=" + parentalLevel +
                ", visible=" + visible +
                ", watched=" + watched +
                ", processed=" + processed +
                ", contentType='" + contentType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", hash='" + hash + '\'' +
                ", hostName='" + hostName + '\'' +
                ", coverart='" + coverart + '\'' +
                ", fanart='" + fanart + '\'' +
                ", banner='" + banner + '\'' +
                ", screenshot='" + screenshot + '\'' +
                ", trailer='" + trailer + '\'' +
                ", artwork=" + artwork +
                ", cast=" + cast +
                ", liveStreamInfo=" + liveStreamInfo +
                '}';
    }

}
