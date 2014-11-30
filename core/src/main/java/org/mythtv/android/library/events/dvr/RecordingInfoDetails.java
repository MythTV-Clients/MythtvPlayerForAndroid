package org.mythtv.android.library.events.dvr;

import org.joda.time.DateTime;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RecordingInfoDetails {

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

    public RecordingInfoDetails() {
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

}
