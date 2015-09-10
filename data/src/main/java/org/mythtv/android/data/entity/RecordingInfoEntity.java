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

package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

/*
 * Created by dmfrey on 11/12/14.
 */
public class RecordingInfoEntity {

    @SerializedName( "RecordedId" )
    private int recordedId;

    @SerializedName( "Status" )
    private int status;

    @SerializedName( "Priority" )
    private int priority;

    @SerializedName( "StartTs" )
    private DateTime startTs;

    @SerializedName( "EndTs" )
    private DateTime endTs;

    @SerializedName( "RecordId" )
    private int recordId;

    @SerializedName( "RecGroup" )
    private String recGroup;

    @SerializedName( "PlayGroup" )
    private String playGroup;

    @SerializedName( "StorageGroup" )
    private String storageGroup;

    @SerializedName( "RecType" )
    private int recType;

    @SerializedName( "DupInType" )
    private int dupInType;

    @SerializedName( "DupMethod" )
    private int dupMethod;

    @SerializedName( "EncoderId" )
    private int encoderId;

    @SerializedName( "EncoderName" )
    private String encoderName;

    @SerializedName( "Profile" )
    private String profile;

    public RecordingInfoEntity() {
    }

    public int getRecordedId() {
        return recordedId;
    }

    public void setRecordedId(int recordedId) {
        this.recordedId = recordedId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
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

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
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

    public int getRecType() {
        return recType;
    }

    public void setRecType(int recType) {
        this.recType = recType;
    }

    public int getDupInType() {
        return dupInType;
    }

    public void setDupInType(int dupInType) {
        this.dupInType = dupInType;
    }

    public int getDupMethod() {
        return dupMethod;
    }

    public void setDupMethod(int dupMethod) {
        this.dupMethod = dupMethod;
    }

    public int getEncoderId() {
        return encoderId;
    }

    public void setEncoderId(int encoderId) {
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
    public String toString() {
        return "RecordingInfoEntity{" +
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

}
