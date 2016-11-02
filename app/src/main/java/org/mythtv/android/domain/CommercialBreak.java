package org.mythtv.android.domain;

/**
 * Created by dmfrey on 10/31/16.
 */

public class CommercialBreak {

    private long start;
    private long end;

    public CommercialBreak() { }

    public CommercialBreak(long start, long end ) {

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


}
