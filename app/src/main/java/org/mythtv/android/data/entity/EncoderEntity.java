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

import java.util.Arrays;

/**
 * Created by dmfrey on 1/18/16.
 */
public class EncoderEntity {

    @SerializedName( "Id" )
    private int id;

    @SerializedName( "HostName" )
    private String hostname;

    @SerializedName( "Local" )
    private boolean local;

    @SerializedName( "Connected" )
    private boolean connected;

    @SerializedName( "State" )
    private int state;

    @SerializedName( "SleepStatus" )
    private int sleepStatus;

    @SerializedName( "LowOnFreeSpace" )
    private boolean lowOnFreeSpace;

    @SerializedName( "Inputs" )
    private InputEntity[] inputs;

    @SerializedName( "Recording" )
    private ProgramEntity recording;

    public EncoderEntity() {
    }

    public EncoderEntity(int id, String hostname, boolean local, boolean connected, int state, int sleepStatus, boolean lowOnFreeSpace, InputEntity[] inputs, ProgramEntity recording) {
        this.id = id;
        this.hostname = hostname;
        this.local = local;
        this.connected = connected;
        this.state = state;
        this.sleepStatus = sleepStatus;
        this.lowOnFreeSpace = lowOnFreeSpace;
        this.inputs = inputs;
        this.recording = recording;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSleepStatus() {
        return sleepStatus;
    }

    public void setSleepStatus(int sleepStatus) {
        this.sleepStatus = sleepStatus;
    }

    public boolean isLowOnFreeSpace() {
        return lowOnFreeSpace;
    }

    public void setLowOnFreeSpace(boolean lowOnFreeSpace) {
        this.lowOnFreeSpace = lowOnFreeSpace;
    }

    public InputEntity[] getInputs() {
        return inputs;
    }

    public void setInputs(InputEntity[] inputs) {
        this.inputs = inputs;
    }

    public ProgramEntity getRecording() {
        return recording;
    }

    public void setRecording(ProgramEntity recording) {
        this.recording = recording;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EncoderEntity that = (EncoderEntity) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "EncoderEntity{" +
                "id=" + id +
                ", hostname='" + hostname + '\'' +
                ", local=" + local +
                ", connected=" + connected +
                ", state=" + state +
                ", sleepStatus=" + sleepStatus +
                ", lowOnFreeSpace=" + lowOnFreeSpace +
                ", inputs=" + Arrays.toString(inputs) +
                ", recording=" + recording +
                '}';
    }

}
