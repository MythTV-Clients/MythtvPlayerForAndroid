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

package org.mythtv.android.domain;

import android.support.annotation.NonNull;

import org.mythtv.android.domain.utils.DomainUtils;

/*
 * Created by dmfrey on 9/23/16.
 */
public class Series implements Comparable<Series> {

    private String title;
    private Media media;
    private String artworkUrl;
    private int count;

    public Series() { }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia( Media media ) {

        this.media = media;

    }

    public String getArtworkUrl() {

        return artworkUrl;
    }

    public void setArtworkUrl( String artworkUrl ) {

        this.artworkUrl = artworkUrl;

    }

    public int getCount() {
        return count;
    }

    public void setCount( int count ) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Series{" +
                "title='" + title + '\'' +
                ", media=" + media +
                ", artworkUrl='" + artworkUrl + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo( @NonNull Series another ) {

        final int EQUAL = 0;

        if( this == another ) return EQUAL;

        String thisTitle = DomainUtils.removeArticles( this.title.toUpperCase() );
        String thatTitle = DomainUtils.removeArticles( another.title.toUpperCase() );

        int comparison = thisTitle.compareTo( thatTitle );
        if( comparison != EQUAL ) return comparison;

        return EQUAL;
    }

}
