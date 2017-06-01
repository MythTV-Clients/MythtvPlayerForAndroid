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

import com.google.auto.value.AutoValue;

import org.mythtv.android.domain.utils.DomainUtils;

import java.util.Locale;

import javax.annotation.Nullable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/23/16.
 */
@AutoValue
public abstract class Series implements Comparable<Series> {

    @Nullable
    public abstract String title();

    @Nullable
    public abstract Media media();

    @Nullable
    public abstract String artworkUrl();

    public abstract int count();

    @Nullable
    public abstract String inetref();

    public static Series create( String title, Media media, String artwork, int count, String inetref ) {

        return new AutoValue_Series( title, media, artwork, count, inetref );
    }

    @Override
    public int compareTo( Series another ) {

        final int EQUAL = 0;

        if( this == another ) {

            return EQUAL;
        }

        String thisTitle = DomainUtils.removeArticles( this.title().toUpperCase( Locale.getDefault() ) );
        String thatTitle = DomainUtils.removeArticles( another.title().toUpperCase( Locale.getDefault() ) );

        int comparison = thisTitle.compareTo( thatTitle );
        if( comparison != EQUAL ) {

            return comparison;
        }

        comparison = inetref().compareTo( another.inetref() );
        if( comparison != EQUAL ) {

            return comparison;
        }

        return EQUAL;
    }

}
