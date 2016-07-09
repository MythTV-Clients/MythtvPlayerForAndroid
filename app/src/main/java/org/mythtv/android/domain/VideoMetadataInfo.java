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

import org.joda.time.DateTime;

import lombok.Data;

/*
 * Created by dmfrey on 10/30/15.
 */
@Data
public class VideoMetadataInfo {

    private Integer id;
    private String title;
    private String subTitle;
    private String tagline;
    private String director;
    private String studio;
    private String description;
    private String certification;
    private String inetref;
    private Integer collectionref;
    private String homePage;
    private DateTime releaseDate;
    private DateTime addDate;
    private Float userRating;
    private int length;
    private int playCount;
    private int season;
    private int episode;
    private int parentalLevel;
    private boolean visible;
    private boolean watched;
    private boolean processed;
    private String contentType;
    private String fileName;
    private String hash;
    private String hostName;
    private String coverart;
    private String fanart;
    private String banner;
    private String screenshot;
    private String trailer;
    private Artwork artwork;
    private Cast cast;

}
