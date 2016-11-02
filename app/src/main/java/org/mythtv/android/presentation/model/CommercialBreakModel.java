package org.mythtv.android.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.mythtv.android.domain.CommercialBreak;

/**
 * Created by dmfrey on 10/31/16.
 */

public class CommercialBreakModel implements Parcelable {

    private long start;
    private long end;

    public CommercialBreakModel() { }

    public CommercialBreakModel( long start, long end ) {

        this.start = start;
        this.end = end;

    }

    public CommercialBreakModel( Parcel in ) {

        long[] data = new long[ 2 ];

        in.readLongArray( data );

        this.start = data[ 0 ];
        this.end = data[ 1 ];

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

    public static CommercialBreakModel fromCommercialBreak( CommercialBreak commercialBreak ) {

        return new CommercialBreakModel( commercialBreak.getStart(), commercialBreak.getEnd() );
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {

        dest.writeLongArray( new long[] { start, end } );

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public CommercialBreakModel createFromParcel( Parcel in ) {

            return new CommercialBreakModel( in );
        }

        public CommercialBreakModel[] newArray( int size ) {

            return new CommercialBreakModel[ size ];
        }

    };

}
