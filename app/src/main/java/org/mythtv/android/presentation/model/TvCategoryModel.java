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

package org.mythtv.android.presentation.model;

//import lombok.Data;
//import lombok.RequiredArgsConstructor;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/28/16.
 */
//@Data
//@RequiredArgsConstructor( suppressConstructorProperties = true )
public class TvCategoryModel {

    private final int position;
    private final String title;
    private final Integer drawable;

    public TvCategoryModel(int position, String title, Integer drawable) {
        this.position = position;
        this.title = title;
        this.drawable = drawable;
    }

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDrawable() {
        return drawable;
    }

    @Override
    public String toString() {
        return "TvCategoryModel{" +
                "position=" + position +
                ", title='" + title + '\'' +
                ", drawable=" + drawable +
                '}';
    }

}
