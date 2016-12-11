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

//import lombok.Data;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/7/15.
 */
//@Data
public class TitleInfosEntity {

    @SerializedName( "TitleInfos" )
    private TitleInfoEntity[] titleInfos;

    public TitleInfosEntity() {
    }

    public TitleInfosEntity(TitleInfoEntity[] titleInfos ) {

        this.titleInfos = titleInfos;

    }

    public TitleInfoEntity[] getTitleInfos() {

        return titleInfos;
    }

    public void setTitleInfos( TitleInfoEntity[] titleInfos ) {

        this.titleInfos = titleInfos;

    }

    @Override
    public String toString() {
        return "TitleInfosEntity{" +
                "titleInfos=" + Arrays.toString(titleInfos) +
                '}';
    }

}
