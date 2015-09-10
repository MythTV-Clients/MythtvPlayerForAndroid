package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmfrey on 8/27/15.
 */
public class TitleInfoListEntity {

    @SerializedName( "TitleInfoList" )
    private TitleInfosEntity titleInfos;

    public TitleInfoListEntity() {
    }

    public TitleInfosEntity getTitleInfos() {

        return titleInfos;
    }

    public void setTitleInfos( TitleInfosEntity titleInfos ) {

        this.titleInfos = titleInfos;

    }

    @Override
    public String toString() {
        return "TitleInfoListEntity{" +
                "titleInfos=" + titleInfos +
                '}';
    }

}
