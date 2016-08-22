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

package org.mythtv.android.presentation.model;

import org.mythtv.android.domain.utils.DomainUtils;

//import lombok.Data;

/*
 * Created by dmfrey on 12/7/14.
 */
//@Data
public class TitleInfoModel implements Comparable<TitleInfoModel> {

    private String title;
    private String inetref;
    private int count;

    public TitleInfoModel() {
    }

    public TitleInfoModel(String title, String inetref, int count) {
        this.title = title;
        this.inetref = inetref;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInetref() {
        return inetref;
    }

    public void setInetref(String inetref) {
        this.inetref = inetref;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleInfoModel that = (TitleInfoModel) o;

        if (!title.equals(that.title)) return false;
        return inetref != null ? inetref.equals(that.inetref) : that.inetref == null;

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (inetref != null ? inetref.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TitleInfoModel{" +
                "title='" + title + '\'' +
                ", inetref='" + inetref + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo( TitleInfoModel another ) {

        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if( this == another ) return EQUAL;

        String thisTitle = DomainUtils.removeArticles( this.title.toUpperCase() );
        String thatTitle = DomainUtils.removeArticles( another.title.toUpperCase() );

        int comparison = thisTitle.compareTo( thatTitle );
        if( comparison != EQUAL ) return comparison;

        String thisInetref = ( null != inetref ? inetref : "" );
        String thatInetref = ( null != another.inetref ? another.inetref : "" );

        comparison = thisInetref.compareTo( thatInetref );
        if( comparison != EQUAL ) return comparison;

        return EQUAL;
    }

}
