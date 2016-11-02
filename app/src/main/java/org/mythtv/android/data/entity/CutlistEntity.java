package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by dmfrey on 10/31/16.
 */

public class CutListEntity {

    @SerializedName( "Cuttings" )
    private CuttingEntity[] cuttingEntities;

    public CutListEntity() { }

    public CutListEntity( CuttingEntity[] cuttingEntities ) {

        this.cuttingEntities = cuttingEntities;

    }

    public CuttingEntity[] getCuttingEntities() {

        return cuttingEntities;
    }

    public void setCuttingEntities( CuttingEntity[] cuttingEntities ) {

        this.cuttingEntities = cuttingEntities;

    }

    @Override
    public String toString() {
        return "CutListEntity{" +
                "cuttingEntities=" + Arrays.toString(cuttingEntities) +
                '}';
    }

}
