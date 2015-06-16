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

package org.mythtv.android.library.events.dvr;

import java.util.List;

/*
 * Created by dmfrey on 11/12/14.
 */
public class ChannelInfoDetails {

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
    private List<ProgramDetails> programs;

    public ChannelInfoDetails() {
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

    public List<ProgramDetails> getPrograms() {
        return programs;
    }

    public void setPrograms(List<ProgramDetails> programs) {
        this.programs = programs;
    }
}
