package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by dmfrey on 8/27/15.
 */
public class CastEntity {

    @SerializedName( "CastMembers" )
    private CastMemberEntity[] castMembers;

    public CastEntity() {
    }

    public CastMemberEntity[] getCastMembers() {
        return castMembers;
    }

    public void setCastMembers( CastMemberEntity[] castMembers ) {

        this.castMembers = castMembers;

    }

    @Override
    public String toString() {
        return "CastEntity{" +
                "castMembers=" + Arrays.toString(castMembers) +
                '}';
    }
}
