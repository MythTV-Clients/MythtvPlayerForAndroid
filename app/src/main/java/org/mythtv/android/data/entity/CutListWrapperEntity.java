package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 10/31/16.
 */

public class CutListWrapperEntity {

    @SerializedName( "CutList" )
    private CutListEntity cutListEntity;

    public CutListWrapperEntity() { }

    public CutListWrapperEntity( CutListEntity cutListEntity ) {

        this.cutListEntity = cutListEntity;

    }

    public CutListEntity getCutListEntity() {

        return cutListEntity;
    }

    public void setCutListEntity( CutListEntity cutListEntity ) {

        this.cutListEntity = cutListEntity;

    }

    @Override
    public String toString() {
        return "CutListWrapperEntity{" +
                "cutListEntity=" + cutListEntity +
                '}';
    }

}
