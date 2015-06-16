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

package org.mythtv.android.library.core.domain.video;

import org.joda.time.DateTime;
import org.mythtv.android.library.core.domain.dvr.ArtworkInfo;
import org.mythtv.android.library.core.domain.dvr.CastMember;
import org.mythtv.android.library.core.utils.Utils;
import org.mythtv.android.library.events.dvr.ArtworkInfoDetails;
import org.mythtv.android.library.events.dvr.CastMemberDetails;
import org.mythtv.android.library.events.video.VideoDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by dmfrey on 11/24/14.
 */
public class Video implements Serializable, Comparable<Video> {

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
    private Integer length;
    private Integer playCount;
    private Integer season;
    private Integer episode;
    private Integer parentalLevel;
    private Boolean visible;
    private Boolean watched;
    private Boolean processed;
    private String contentType;
    private String filePath;
    private String fileName;
    private String hash;
    private String hostName;
    private String coverart;
    private String fanart;
    private String banner;
    private String screenshot;
    private String trailer;
    private List<ArtworkInfo> artworkInfos;
    private List<CastMember> castMembers;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId( Integer id ) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle( String title ) {
        this.title = title;
    }

    /**
     * @return the subTitle
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * @param subTitle the subTitle to set
     */
    public void setSubTitle( String subTitle ) {
        this.subTitle = subTitle;
    }

    /**
     * @return the tagline
     */
    public String getTagline() {
        return tagline;
    }

    /**
     * @param tagline the tagline to set
     */
    public void setTagline( String tagline ) {
        this.tagline = tagline;
    }

    /**
     * @return the director
     */
    public String getDirector() {
        return director;
    }

    /**
     * @param director the director to set
     */
    public void setDirector( String director ) {
        this.director = director;
    }

    /**
     * @return the studio
     */
    public String getStudio() {
        return studio;
    }

    /**
     * @param studio the studio to set
     */
    public void setStudio( String studio ) {
        this.studio = studio;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription( String description ) {
        this.description = description;
    }

    /**
     * @return the certification
     */
    public String getCertification() {
        return certification;
    }

    /**
     * @param certification the certification to set
     */
    public void setCertification( String certification ) {
        this.certification = certification;
    }

    /**
     * @return the inetref
     */
    public String getInetref() {
        return inetref;
    }

    /**
     * @param inetref the inetref to set
     */
    public void setInetref( String inetref ) {
        this.inetref = inetref;
    }

    /**
     * @return the collectionref
     */
    public Integer getCollectionref() {
        return collectionref;
    }

    /**
     * @param collectionref the collectionref to set
     */
    public void setCollectionref( Integer collectionref ) {
        this.collectionref = collectionref;
    }

    /**
     * @return the homePage
     */
    public String getHomePage() {
        return homePage;
    }

    /**
     * @param homePage the homePage to set
     */
    public void setHomePage( String homePage ) {
        this.homePage = homePage;
    }

    /**
     * @return the releaseDate
     */
    public DateTime getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate the releaseDate to set
     */
    public void setReleaseDate( DateTime releaseDate ) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return the addDate
     */
    public DateTime getAddDate() {
        return addDate;
    }

    /**
     * @param addDate the addDate to set
     */
    public void setAddDate( DateTime addDate ) {
        this.addDate = addDate;
    }

    /**
     * @return the userRating
     */
    public Float getUserRating() {
        return userRating;
    }

    /**
     * @param userRating the userRating to set
     */
    public void setUserRating( Float userRating ) {
        this.userRating = userRating;
    }

    /**
     * @return the length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength( Integer length ) {
        this.length = length;
    }

    /**
     * @return the playCount
     */
    public Integer getPlayCount() {
        return playCount;
    }

    /**
     * @param playCount the playCount to set
     */
    public void setPlayCount( Integer playCount ) {
        this.playCount = playCount;
    }

    /**
     * @return the season
     */
    public Integer getSeason() {
        return season;
    }

    /**
     * @param season the season to set
     */
    public void setSeason( Integer season ) {
        this.season = season;
    }

    /**
     * @return the episode
     */
    public Integer getEpisode() {
        return episode;
    }

    /**
     * @param episode the episode to set
     */
    public void setEpisode( Integer episode ) {
        this.episode = episode;
    }

    /**
     * @return the parentalLevel
     */
    public Integer getParentalLevel() {
        return parentalLevel;
    }

    /**
     * @param parentalLevel the parentalLevel to set
     */
    public void setParentalLevel( Integer parentalLevel ) {
        this.parentalLevel = parentalLevel;
    }

    /**
     * @return the visible
     */
    public Boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible( Boolean visible ) {
        this.visible = visible;
    }

    /**
     * @return the watched
     */
    public Boolean isWatched() {
        return watched;
    }

    /**
     * @param watched the watched to set
     */
    public void setWatched( Boolean watched ) {
        this.watched = watched;
    }

    /**
     * @return the processed
     */
    public Boolean isProcessed() {
        return processed;
    }

    /**
     * @param processed the processed to set
     */
    public void setProcessed( Boolean processed ) {
        this.processed = processed;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType( String contentType ) {
        this.contentType = contentType;
    }

    public String getFilePath() { return filePath; }

    public void setFilePath( String filePath ) { this.filePath = filePath; }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash( String hash ) {
        this.hash = hash;
    }

    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setHostName( String hostName ) {
        this.hostName = hostName;
    }

    /**
     * @return the coverart
     */
    public String getCoverart() {
        return coverart;
    }

    /**
     * @param coverart the coverart to set
     */
    public void setCoverart( String coverart ) {
        this.coverart = coverart;
    }

    /**
     * @return the fanart
     */
    public String getFanart() {
        return fanart;
    }

    /**
     * @param fanart the fanart to set
     */
    public void setFanart( String fanart ) {
        this.fanart = fanart;
    }

    /**
     * @return the banner
     */
    public String getBanner() {
        return banner;
    }

    /**
     * @param banner the banner to set
     */
    public void setBanner( String banner ) {
        this.banner = banner;
    }

    /**
     * @return the screenshot
     */
    public String getScreenshot() {
        return screenshot;
    }

    /**
     * @param screenshot the screenshot to set
     */
    public void setScreenshot( String screenshot ) {
        this.screenshot = screenshot;
    }

    /**
     * @return the trailer
     */
    public String getTrailer() {
        return trailer;
    }

    /**
     * @param trailer the trailer to set
     */
    public void setTrailer( String trailer ) {
        this.trailer = trailer;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        if (!id.equals(video.id)) return false;
        if (!title.equals(video.title)) return false;
        if (subTitle != null ? !subTitle.equals(video.subTitle) : video.subTitle != null)
            return false;
        if (tagline != null ? !tagline.equals(video.tagline) : video.tagline != null) return false;
        if (inetref != null ? !inetref.equals(video.inetref) : video.inetref != null) return false;
        if (season != null ? !season.equals(video.season) : video.season != null) return false;
        return !(episode != null ? !episode.equals(video.episode) : video.episode != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (subTitle != null ? subTitle.hashCode() : 0);
        result = 31 * result + (tagline != null ? tagline.hashCode() : 0);
        result = 31 * result + (inetref != null ? inetref.hashCode() : 0);
        result = 31 * result + (season != null ? season.hashCode() : 0);
        result = 31 * result + (episode != null ? episode.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo( Video another ) {

        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if( this == another ) return EQUAL;

        String thisTitle = Utils.removeArticles( this.title.toUpperCase() );
        String thatTitle = Utils.removeArticles( another.title.toUpperCase() );

        int comparison = thisTitle.compareTo(thatTitle);
        if( comparison != EQUAL ) return comparison;

//        String thisInetref = ( null != inetref ? inetref : "" );
//        String thatInetref = ( null != another.inetref ? another.inetref : "" );
//
//        comparison = thisInetref.compareTo( thatInetref );
//        if( comparison != EQUAL ) return comparison;
//
//        String thisSubTitle = ( null != subTitle ? subTitle : "" );
//        String thatSubTitle = ( null != another.subTitle ? another.subTitle : "" );
//
//        comparison = thisSubTitle.compareTo( thatSubTitle );
//        if( comparison != EQUAL ) return comparison;
//
//        String thisTagline = ( null != tagline ? tagline : "" );
//        String thatTagline = ( null != another.tagline ? another.tagline : "" );
//
//        comparison = thisTagline.compareTo( thatTagline );
//        if( comparison != EQUAL ) return comparison;

        if( season > another.season ) {
            return AFTER;
        } else if( season < another.season ) {
            return BEFORE;
        }

        if( episode > another.episode ) {
            return AFTER;
        } else if( episode < another.episode ) {
            return BEFORE;
        }

        return EQUAL;
    }

    @Override
    public String toString() {
        return "Video{" +
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
                ", artworkInfos=" + artworkInfos +
                ", castMembers=" + castMembers +
                '}';
    }

    public VideoDetails toDetails() {

        VideoDetails details = new VideoDetails();
        details.setId( id );
        details.setTitle( title );
        details.setSubTitle( subTitle );
        details.setTagline( tagline );
        details.setDirector( director );
        details.setStudio( studio );
        details.setDescription( description );
        details.setCertification( certification );
        details.setInetref( inetref );
        details.setCollectionref( collectionref );
        details.setHomePage( homePage );
        details.setReleaseDate( releaseDate );
        details.setAddDate( addDate );
        details.setUserRating( userRating );
        details.setLength( length );
        details.setPlayCount( playCount );
        details.setSeason( season );
        details.setEpisode( episode );
        details.setParentalLevel( parentalLevel );
        details.setVisible( visible );
        details.setWatched( watched );
        details.setProcessed( processed );
        details.setContentType( contentType );
        details.setFilePath( filePath );
        details.setFileName( fileName );
        details.setHash( hash );
        details.setHostName( hostName );
        details.setCoverart( coverart );
        details.setFanart( fanart );
        details.setBanner( banner );
        details.setScreenshot( screenshot );
        details.setTrailer( trailer );

        List<ArtworkInfoDetails> artworkInfoDetails = new ArrayList<>();
        if( null != artworkInfos && !artworkInfos.isEmpty() ) {
            for( ArtworkInfo artworkInfo : artworkInfos ) {
                artworkInfoDetails.add( artworkInfo.toDetails() );
            }
        }
        details.setArtworkInfos( artworkInfoDetails );

        List<CastMemberDetails> castMemberDetails = new ArrayList<>();
        if( null != castMembers && !castMembers.isEmpty() ) {
            for( CastMember castMember : castMembers ) {
                castMemberDetails.add( castMember.toDetails() );
            }
        }
        details.setCastMembers( castMemberDetails );

        return details;
    }

    public static Video fromDetails( VideoDetails details ) {

        Video video = new Video();
        video.setId( details.getId() );
        video.setTitle( details.getTitle() );
        video.setSubTitle( details.getSubTitle() );
        video.setTagline( details.getTagline() );
        video.setDirector( details.getDirector() );
        video.setStudio( details.getStudio() );
        video.setDescription( details.getDescription() );
        video.setCertification( details.getCertification() );
        video.setInetref( details.getInetref() );
        video.setCollectionref( details.getCollectionref() );
        video.setHomePage( details.getHomePage() );
        video.setReleaseDate( details.getReleaseDate() );
        video.setAddDate( details.getAddDate() );
        video.setUserRating( details.getUserRating() );
        video.setLength( details.getLength() );
        video.setPlayCount( details.getPlayCount() );
        video.setSeason( details.getSeason() );
        video.setEpisode( details.getEpisode() );
        video.setParentalLevel( details.getParentalLevel() );
        video.setVisible( details.isVisible() );
        video.setWatched( details.isWatched() );
        video.setProcessed( details.isProcessed() );
        video.setContentType( details.getContentType() );
        video.setFilePath( details.getFilePath() );
        video.setFileName( details.getFileName() );
        video.setHash( details.getHash() );
        video.setHostName( details.getHostName() );
        video.setCoverart( details.getCoverart() );
        video.setFanart( details.getFanart() );
        video.setBanner( details.getBanner() );
        video.setScreenshot( details.getScreenshot() );
        video.setTrailer(details.getTrailer());

        List<ArtworkInfo> artworkInfos = new ArrayList<>();
        if( null != details.getArtworkInfos() && !details.getArtworkInfos().isEmpty() ) {
            for( ArtworkInfoDetails artworkInfoDetails : details.getArtworkInfos() ) {
                artworkInfos.add( ArtworkInfo.fromDetails(artworkInfoDetails) );
            }
        }
        video.setArtworkInfos(artworkInfos);

        List<CastMember> castMembers = new ArrayList<>();
        if( null != details.getCastMembers() && !details.getCastMembers().isEmpty() ) {
            for( CastMemberDetails castMemberDetails : details.getCastMembers() ) {
                castMembers.add( CastMember.fromDetails(castMemberDetails) );
            }
        }
        video.setCastMembers(castMembers);

        return video;
    }

}
