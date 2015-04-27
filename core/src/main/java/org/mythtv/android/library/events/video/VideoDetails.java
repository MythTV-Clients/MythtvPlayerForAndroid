package org.mythtv.android.library.events.video;

import org.joda.time.DateTime;
import org.mythtv.android.library.events.dvr.ArtworkInfoDetails;
import org.mythtv.android.library.events.dvr.CastMemberDetails;

import java.util.List;

/**
 * Created by dmfrey on 11/24/14.
 */
public class VideoDetails {

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
    private List<ArtworkInfoDetails> artworkInfos;
    private List<CastMemberDetails> castMembers;

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
