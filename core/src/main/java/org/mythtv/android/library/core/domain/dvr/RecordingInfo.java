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

import org.joda.time.DateTime;
import org.mythtv.android.library.events.dvr.RecordingInfoDetails;

import java.io.Serializable;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RecordingInfo implements Serializable {

    private Integer recordedId;
    private Integer status;
    private Integer priority;
    private DateTime startTs;
    private DateTime endTs;
    private Integer recordId;
    private String recGroup;
    private String playGroup;
    private String storageGroup;
    private Integer recType;
    private Integer dupInType;
    private Integer dupMethod;
    private Integer encoderId;
    private String encoderName;
    private String profile;

    public RecordingInfo() {
    }

    public Integer getRecordedId() {
        return recordedId;
    }

    public void setRecordedId(Integer recordedId) {
        this.recordedId = recordedId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public DateTime getStartTs() {
        return startTs;
    }

    public void setStartTs(DateTime startTs) {
        this.startTs = startTs;
    }

    public DateTime getEndTs() {
        return endTs;
    }

    public void setEndTs(DateTime endTs) {
        this.endTs = endTs;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getRecGroup() {
        return recGroup;
    }

    public void setRecGroup(String recGroup) {
        this.recGroup = recGroup;
    }

    public String getPlayGroup() {
        return playGroup;
    }

    public void setPlayGroup(String playGroup) {
        this.playGroup = playGroup;
    }

    public String getStorageGroup() {
        return storageGroup;
    }

    public void setStorageGroup(String storageGroup) {
        this.storageGroup = storageGroup;
    }

    public Integer getRecType() {
        return recType;
    }

    public void setRecType(Integer recType) {
        this.recType = recType;
    }

    public Integer getDupInType() {
        return dupInType;
    }

    public void setDupInType(Integer dupInType) {
        this.dupInType = dupInType;
    }

    public Integer getDupMethod() {
        return dupMethod;
    }

    public void setDupMethod(Integer dupMethod) {
        this.dupMethod = dupMethod;
    }

    public Integer getEncoderId() {
        return encoderId;
    }

    public void setEncoderId(Integer encoderId) {
        this.encoderId = encoderId;
    }

    public String getEncoderName() {
        return encoderName;
    }

    public void setEncoderName(String encoderName) {
        this.encoderName = encoderName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecordingInfo that = (RecordingInfo) o;

        return startTs.equals(that.startTs);

    }

    @Override
    public int hashCode() {
        return startTs.hashCode();
    }

    @Override
    public String toString() {
        return "RecordingInfo{" +
                "recordedId=" + recordedId +
                ", status=" + status +
                ", priority=" + priority +
                ", startTs=" + startTs +
                ", endTs=" + endTs +
                ", recordId=" + recordId +
                ", recGroup='" + recGroup + '\'' +
                ", playGroup='" + playGroup + '\'' +
                ", storageGroup='" + storageGroup + '\'' +
                ", recType=" + recType +
                ", dupInType=" + dupInType +
                ", dupMethod=" + dupMethod +
                ", encoderId=" + encoderId +
                ", encoderName='" + encoderName + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }

    public RecordingInfoDetails toDetails() {

        RecordingInfoDetails details = new RecordingInfoDetails();
        details.setRecordedId( recordedId );
        details.setStatus( status );
        details.setPriority( priority );
        details.setStartTs( startTs );
        details.setEndTs( endTs );
        details.setRecordId( recordId );
        details.setRecGroup( recGroup );
        details.setPlayGroup( playGroup );
        details.setStorageGroup( storageGroup );
        details.setRecType( recType );
        details.setDupInType( dupInType );
        details.setDupMethod( dupMethod );
        details.setEncoderId( encoderId );
        details.setEncoderName( encoderName );
        details.setProfile( profile );

        return details;
    }

    public static RecordingInfo fromDetails( RecordingInfoDetails details ) {

        RecordingInfo recordingInfo = new RecordingInfo();
        recordingInfo.setRecordedId( details.getRecordedId() );
        recordingInfo.setStatus( details.getStatus() );
        recordingInfo.setPriority( details.getPriority() );
        recordingInfo.setStartTs( details.getStartTs() );
        recordingInfo.setEndTs( details.getEndTs() );
        recordingInfo.setRecordId( details.getRecordId() );
        recordingInfo.setRecGroup( details.getRecGroup() );
        recordingInfo.setPlayGroup( details.getPlayGroup() );
        recordingInfo.setStorageGroup( details.getStorageGroup() );
        recordingInfo.setRecType( details.getRecType() );
        recordingInfo.setDupInType( details.getDupInType() );
        recordingInfo.setDupMethod( details.getDupMethod() );
        recordingInfo.setEncoderId( details.getEncoderId() );
        recordingInfo.setEncoderName( details.getEncoderName() );
        recordingInfo.setProfile( details.getProfile() );

        return recordingInfo;
    }

}
