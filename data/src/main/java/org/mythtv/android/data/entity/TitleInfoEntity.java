/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

/*
 * Created by dmfrey on 12/7/14.
 */
public class TitleInfoEntity {

    @SerializedName( "Title" )
    private String title;

    @SerializedName( "Inetref" )
    private String inetref;

    @SerializedName( "Count" )
    private int count;

    public TitleInfoEntity() { }

    public TitleInfoEntity( String title, String inetref, int count ) {

        this.title = title;
        this.inetref = inetref;
        this.count = count;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getInetref() {
        return inetref;
    }

    public void setInetref( String inetref ) {
        this.inetref = inetref;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TitleInfoEntity{" +
                "title='" + title + '\'' +
                ", inetref='" + inetref + '\'' +
                ", count=" + count +
                '}';
    }

}
