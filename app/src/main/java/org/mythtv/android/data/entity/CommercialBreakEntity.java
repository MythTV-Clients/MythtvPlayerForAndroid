package org.mythtv.android.data.entity;

import org.mythtv.android.domain.CommercialBreak;

/**
 * Created by dmfrey on 10/31/16.
 */

public class CommercialBreakEntity {

    private long start;
    private long end;

    public CommercialBreakEntity() { }

    public CommercialBreakEntity( long start, long end ) {

        this.start = start;
        this.end = end;

    }

    public long getStart() {

        return start;
    }

    public void setStart( long start ) {

        this.start = start;

    }

    public long getEnd() {

        return end;
    }

    public void setEnd( long end ) {

        this.end = end;

    }

    @Override
    public String toString() {
        return "CommercialBreakEntity{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public CommercialBreak toCommercialBreak() {

        return new CommercialBreak( start, end );
    }

    public static CommercialBreakEntity fromCommercialBreak( CommercialBreak commercialBreak ) {

        return new CommercialBreakEntity( commercialBreak.getStart(), commercialBreak.getEnd() );
    }

}
