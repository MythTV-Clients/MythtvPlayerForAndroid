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

package org.mythtv.android.library.core.domain.dvr;

import org.mythtv.android.library.events.dvr.ChannelInfoDetails;
import org.mythtv.android.library.events.dvr.ProgramDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/12/14.
 */
public class ChannelInfo implements Serializable {

    private Integer chanId;
    private String chanNum;
    private String callSign;
    private String iconURL;
    private String channelName;
    private Integer mplexId;
    private Integer serviceId;
    private Integer aTSCMajorChan;
    private Integer aTSCMinorChan;
    private String format;
    private String frequencyId;
    private Integer fineTune;
    private String chanFilters;
    private Integer sourceId;
    private Integer inputId;
    private Boolean commFree;
    private Boolean useEIT;
    private Boolean visible;
    private String xMLTVID;
    private String defaultAuth;
    private List<Program> programs;

    public ChannelInfo() {
    }

    public Integer getChanId() {
        return chanId;
    }

    public void setChanId(Integer chanId) {
        this.chanId = chanId;
    }

    public String getChanNum() {
        return chanNum;
    }

    public void setChanNum(String chanNum) {
        this.chanNum = chanNum;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getMplexId() {
        return mplexId;
    }

    public void setMplexId(Integer mplexId) {
        this.mplexId = mplexId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getaTSCMajorChan() {
        return aTSCMajorChan;
    }

    public void setaTSCMajorChan(Integer aTSCMajorChan) {
        this.aTSCMajorChan = aTSCMajorChan;
    }

    public Integer getaTSCMinorChan() {
        return aTSCMinorChan;
    }

    public void setaTSCMinorChan(Integer aTSCMinorChan) {
        this.aTSCMinorChan = aTSCMinorChan;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(String frequencyId) {
        this.frequencyId = frequencyId;
    }

    public Integer getFineTune() {
        return fineTune;
    }

    public void setFineTune(Integer fineTune) {
        this.fineTune = fineTune;
    }

    public String getChanFilters() {
        return chanFilters;
    }

    public void setChanFilters(String chanFilters) {
        this.chanFilters = chanFilters;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getInputId() {
        return inputId;
    }

    public void setInputId(Integer inputId) {
        this.inputId = inputId;
    }

    public Boolean getCommFree() {
        return commFree;
    }

    public void setCommFree(Boolean commFree) {
        this.commFree = commFree;
    }

    public Boolean getUseEIT() {
        return useEIT;
    }

    public void setUseEIT(Boolean useEIT) {
        this.useEIT = useEIT;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getxMLTVID() {
        return xMLTVID;
    }

    public void setxMLTVID(String xMLTVID) {
        this.xMLTVID = xMLTVID;
    }

    public String getDefaultAuth() {
        return defaultAuth;
    }

    public void setDefaultAuth(String defaultAuth) {
        this.defaultAuth = defaultAuth;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChannelInfo that = (ChannelInfo) o;

        return chanId.equals(that.chanId);

    }

    @Override
    public int hashCode() {
        return chanId.hashCode();
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                "chanId=" + chanId +
                ", chanNum='" + chanNum + '\'' +
                ", callSign='" + callSign + '\'' +
                ", iconURL='" + iconURL + '\'' +
                ", channelName='" + channelName + '\'' +
                ", mplexId=" + mplexId +
                ", serviceId=" + serviceId +
                ", aTSCMajorChan=" + aTSCMajorChan +
                ", aTSCMinorChan=" + aTSCMinorChan +
                ", format='" + format + '\'' +
                ", frequencyId='" + frequencyId + '\'' +
                ", fineTune=" + fineTune +
                ", chanFilters='" + chanFilters + '\'' +
                ", sourceId=" + sourceId +
                ", inputId=" + inputId +
                ", commFree=" + commFree +
                ", useEIT=" + useEIT +
                ", visible=" + visible +
                ", xMLTVID='" + xMLTVID + '\'' +
                ", defaultAuth='" + defaultAuth + '\'' +
                ", programs=" + programs +
                '}';
    }

    public ChannelInfoDetails toDetails() {

        ChannelInfoDetails details = new ChannelInfoDetails();
        details.setChanId( chanId );
        details.setChanNum( chanNum );
        details.setCallSign( callSign );
        details.setIconURL( iconURL );
        details.setChannelName( channelName );
        details.setMplexId( mplexId );
        details.setServiceId( serviceId );
        details.setaTSCMajorChan( aTSCMajorChan );
        details.setaTSCMinorChan( aTSCMinorChan );
        details.setFormat( format );
        details.setFrequencyId( frequencyId );
        details.setFineTune( fineTune );
        details.setChanFilters( chanFilters );
        details.setSourceId( sourceId );
        details.setInputId( inputId );
        details.setCommFree( commFree );
        details.setUseEIT( useEIT );
        details.setVisible( visible );
        details.setxMLTVID( xMLTVID );
        details.setDefaultAuth( defaultAuth );

        List<ProgramDetails> programDetails = new ArrayList<ProgramDetails>();
        if( null != programs && !programs.isEmpty() ) {
            for( Program program : programs ) {
                programDetails.add( program.toDetails() );
            }
        }
        details.setPrograms( programDetails );

        return details;
    }

    public static ChannelInfo fromDetails( ChannelInfoDetails details ) {

        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setChanId( details.getChanId() );
        channelInfo.setChanNum( details.getChanNum() );
        channelInfo.setCallSign( details.getCallSign() );
        channelInfo.setIconURL( details.getIconURL() );
        channelInfo.setChannelName( details.getChannelName() );
        channelInfo.setMplexId( details.getMplexId() );
        channelInfo.setServiceId( details.getServiceId() );
        channelInfo.setaTSCMajorChan( details.getaTSCMajorChan() );
        channelInfo.setaTSCMinorChan( details.getaTSCMinorChan() );
        channelInfo.setFormat( details.getFormat() );
        channelInfo.setFrequencyId( details.getFrequencyId() );
        channelInfo.setFineTune( details.getFineTune() );
        channelInfo.setChanFilters( details.getChanFilters() );
        channelInfo.setSourceId( details.getSourceId() );
        channelInfo.setInputId( details.getInputId() );
        channelInfo.setCommFree( details.getCommFree() );
        channelInfo.setUseEIT( details.getUseEIT() );
        channelInfo.setVisible( details.getVisible() );
        channelInfo.setxMLTVID( details.getxMLTVID() );
        channelInfo.setDefaultAuth( details.getDefaultAuth() );

        List<Program> programs = new ArrayList<Program>();
        if( null != details.getPrograms() && !details.getPrograms().isEmpty() ) {
            for( ProgramDetails detail : details.getPrograms() ) {
                programs.add( Program.fromDetails( detail ) );
            }
        }
        channelInfo.setPrograms( programs );

        return channelInfo;
    }

}
