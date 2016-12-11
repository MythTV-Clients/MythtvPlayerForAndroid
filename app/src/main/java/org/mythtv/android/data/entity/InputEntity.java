/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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

//import lombok.Data;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/18/16.
 */
//@Data
public class InputEntity {

    @SerializedName( "Id" )
    private int id;

    @SerializedName( "CardId" )
    private int cardId;

    @SerializedName( "SourceId" )
    private int sourceId;

    @SerializedName( "InputName" )
    private String inputName;

    @SerializedName( "DisplayName" )
    private String displayName;

    @SerializedName( "QuickTune" )
    private boolean quickTune;

    @SerializedName( "RecPriority" )
    private int recordPriority;

    @SerializedName( "ScheduleOrder" )
    private int scheduleOrder;

    @SerializedName( "LiveTVOrder" )
    private int liveTvOrder;

    public InputEntity() {
    }

    public InputEntity(int id, int cardId, int sourceId, String inputName, String displayName, boolean quickTune, int recordPriority, int scheduleOrder, int liveTvOrder ) {

        this.id = id;
        this.cardId = cardId;
        this.sourceId = sourceId;
        this.inputName = inputName;
        this.displayName = displayName;
        this.quickTune = quickTune;
        this.recordPriority = recordPriority;
        this.scheduleOrder = scheduleOrder;
        this.liveTvOrder = liveTvOrder;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isQuickTune() {
        return quickTune;
    }

    public void setQuickTune(boolean quickTune) {
        this.quickTune = quickTune;
    }

    public int getRecordPriority() {
        return recordPriority;
    }

    public void setRecordPriority(int recordPriority) {
        this.recordPriority = recordPriority;
    }

    public int getScheduleOrder() {
        return scheduleOrder;
    }

    public void setScheduleOrder(int scheduleOrder) {
        this.scheduleOrder = scheduleOrder;
    }

    public int getLiveTvOrder() {
        return liveTvOrder;
    }

    public void setLiveTvOrder(int liveTvOrder) {
        this.liveTvOrder = liveTvOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InputEntity that = (InputEntity) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "InputEntity{" +
                "id=" + id +
                ", cardId=" + cardId +
                ", sourceId=" + sourceId +
                ", inputName='" + inputName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", quickTune=" + quickTune +
                ", recordPriority=" + recordPriority +
                ", scheduleOrder=" + scheduleOrder +
                ", liveTvOrder=" + liveTvOrder +
                '}';
    }

}
