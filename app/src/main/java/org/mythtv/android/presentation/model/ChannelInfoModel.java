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

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/12/14.
 */
public class ChannelInfoModel implements Serializable {

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
    private List<ProgramModel> programs;

    public ChannelInfoModel() {
    }

    public ChannelInfoModel(Integer chanId, String chanNum, String callSign, String iconURL, String channelName, Integer mplexId, Integer serviceId, Integer aTSCMajorChan, Integer aTSCMinorChan, String format, String frequencyId, Integer fineTune, String chanFilters, Integer sourceId, Integer inputId, Boolean commFree, Boolean useEIT, Boolean visible, String xMLTVID, String defaultAuth, List<ProgramModel> programs) {
        this.chanId = chanId;
        this.chanNum = chanNum;
        this.callSign = callSign;
        this.iconURL = iconURL;
        this.channelName = channelName;
        this.mplexId = mplexId;
        this.serviceId = serviceId;
        this.aTSCMajorChan = aTSCMajorChan;
        this.aTSCMinorChan = aTSCMinorChan;
        this.format = format;
        this.frequencyId = frequencyId;
        this.fineTune = fineTune;
        this.chanFilters = chanFilters;
        this.sourceId = sourceId;
        this.inputId = inputId;
        this.commFree = commFree;
        this.useEIT = useEIT;
        this.visible = visible;
        this.xMLTVID = xMLTVID;
        this.defaultAuth = defaultAuth;
        this.programs = programs;
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

    public Integer getATSCMajorChan() {
        return aTSCMajorChan;
    }

    public void setATSCMajorChan(Integer aTSCMajorChan) {
        this.aTSCMajorChan = aTSCMajorChan;
    }

    public Integer getATSCMinorChan() {
        return aTSCMinorChan;
    }

    public void setATSCMinorChan(Integer aTSCMinorChan) {
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

    public String getXMLTVID() {
        return xMLTVID;
    }

    public void setXMLTVID(String xMLTVID) {
        this.xMLTVID = xMLTVID;
    }

    public String getDefaultAuth() {
        return defaultAuth;
    }

    public void setDefaultAuth(String defaultAuth) {
        this.defaultAuth = defaultAuth;
    }

    public List<ProgramModel> getPrograms() {
        return programs;
    }

    public void setPrograms(List<ProgramModel> programs) {
        this.programs = programs;
    }

    @Override
    public String toString() {
        return "ChannelInfoModel{" +
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

}
