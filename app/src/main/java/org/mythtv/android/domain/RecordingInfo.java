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

import org.joda.time.DateTime;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/12/14.
 */
public class RecordingInfo {

    private int recordedId;
    private int status;
    private int priority;
    private DateTime startTs;
    private DateTime endTs;
    private int recordId;
    private String recGroup;
    private String playGroup;
    private String storageGroup;
    private int recType;
    private int dupInType;
    private int dupMethod;
    private int encoderId;
    private String encoderName;
    private String profile;

    public RecordingInfo() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public RecordingInfo(int recordedId, int status, int priority, DateTime startTs, DateTime endTs, int recordId, String recGroup, String playGroup, String storageGroup, int recType, int dupInType, int dupMethod, int encoderId, String encoderName, String profile) {
        this.recordedId = recordedId;
        this.status = status;
        this.priority = priority;
        this.startTs = startTs;
        this.endTs = endTs;
        this.recordId = recordId;
        this.recGroup = recGroup;
        this.playGroup = playGroup;
        this.storageGroup = storageGroup;
        this.recType = recType;
        this.dupInType = dupInType;
        this.dupMethod = dupMethod;
        this.encoderId = encoderId;
        this.encoderName = encoderName;
        this.profile = profile;
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

}
