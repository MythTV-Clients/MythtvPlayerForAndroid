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

package org.mythtv.android.domain;

import java.util.List;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/12/14.
 */
public class ChannelInfo {

    private int chanId;
    private String chanNum;
    private String callSign;
    private String iconURL;
    private String channelName;
    private int mplexId;
    private int serviceId;
    private int aTSCMajorChan;
    private int aTSCMinorChan;
    private String format;
    private String frequencyId;
    private int fineTune;
    private String chanFilters;
    private int sourceId;
    private int inputId;
    private boolean commFree;
    private boolean useEIT;
    private boolean visible;
    private String xMLTVID;
    private String defaultAuth;
    private List<Program> programs;

    public ChannelInfo() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public ChannelInfo(int chanId, String chanNum, String callSign, String iconURL, String channelName, int mplexId, int serviceId, int aTSCMajorChan, int aTSCMinorChan, String format, String frequencyId, int fineTune, String chanFilters, int sourceId, int inputId, boolean commFree, boolean useEIT, boolean visible, String xMLTVID, String defaultAuth, List<Program> programs) {
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

    public int getChanId() {
        return chanId;
    }

    public void setChanId(int chanId) {
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

    public int getMplexId() {
        return mplexId;
    }

    public void setMplexId(int mplexId) {
        this.mplexId = mplexId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getATSCMajorChan() {
        return aTSCMajorChan;
    }

    public void setATSCMajorChan(int aTSCMajorChan) {
        this.aTSCMajorChan = aTSCMajorChan;
    }

    public int getATSCMinorChan() {
        return aTSCMinorChan;
    }

    public void setATSCMinorChan(int aTSCMinorChan) {
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

    public int getFineTune() {
        return fineTune;
    }

    public void setFineTune(int fineTune) {
        this.fineTune = fineTune;
    }

    public String getChanFilters() {
        return chanFilters;
    }

    public void setChanFilters(String chanFilters) {
        this.chanFilters = chanFilters;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getInputId() {
        return inputId;
    }

    public void setInputId(int inputId) {
        this.inputId = inputId;
    }

    public boolean isCommFree() {
        return commFree;
    }

    public void setCommFree(boolean commFree) {
        this.commFree = commFree;
    }

    public boolean isUseEIT() {
        return useEIT;
    }

    public void setUseEIT(boolean useEIT) {
        this.useEIT = useEIT;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
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

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
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
}
