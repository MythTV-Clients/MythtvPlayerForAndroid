package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 10/31/16.
 */

public class CuttingEntity {

    @SerializedName( "Mark" )
    private int mark;

    @SerializedName( "Offset" )
    private long offset;

    public CuttingEntity() { }

    public CuttingEntity( int mark, long offset ) {

        this.mark = mark;
        this.offset = offset;

    }

    public int getMark() {

        return mark;
    }

    public void setMark( int mark ) {

        this.mark = mark;

    }

    public long getOffset() {

        return offset;
    }

    public void setOffset( long offset ) {

        this.offset = offset;

    }

    @Override
    public String toString() {
        return "CuttingEntity{" +
                "mark=" + mark +
                ", offset=" + offset +
                '}';
    }

}
