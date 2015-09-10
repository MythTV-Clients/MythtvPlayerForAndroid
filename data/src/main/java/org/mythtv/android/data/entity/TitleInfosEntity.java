package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.Arrays;

/**
 * Created by dmfrey on 9/7/15.
 */
public class TitleInfosEntity {

    @SerializedName( "TitleInfos" )
    private TitleInfoEntity[] titleInfos;

    public TitleInfosEntity() { }

    public TitleInfoEntity[] getTitleInfos() {

        return titleInfos;
    }

    public void setTitleInfos( TitleInfoEntity[] titleInfos ) {

        this.titleInfos = titleInfos;

    }

    @Override
    public String toString() {

        return "TitleInfosEntity{" +
                "titleInfos=" + Arrays.toString( titleInfos ) +
                '}';
    }

}
