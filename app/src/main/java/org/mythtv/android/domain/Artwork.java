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

package org.mythtv.android.domain;

//import lombok.Data;

import java.util.Arrays;

/**
 * Created by dmfrey on 8/27/15.
 */
//@Data
public class Artwork {

    private ArtworkInfo[] artworkInfos;

    public Artwork() {
    }

    public Artwork(ArtworkInfo[] artworkInfos) {
        this.artworkInfos = artworkInfos;
    }

    public ArtworkInfo[] getArtworkInfos() {
        return artworkInfos;
    }

    public void setArtworkInfos(ArtworkInfo[] artworkInfos) {
        this.artworkInfos = artworkInfos;
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "artworkInfos=" + Arrays.toString(artworkInfos) +
                '}';
    }
}
