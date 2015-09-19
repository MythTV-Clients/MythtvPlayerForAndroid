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


import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.List;

import lombok.Data;

/*
 * Created by dmfrey on 11/12/14.
 */
@Data
public class ProgramModel {

    private long id;
    private DateTime startTime;
    private DateTime endTime;
    private String title;
    private String subTitle;
    private String category;
    private String catType;
    private Boolean repeat;
    private Integer videoProps;
    private Integer audioProps;
    private Integer subProps;
    private String seriesId;
    private String programId;
    private Double stars;
    private Long fileSize;
    private DateTime lastModified;
    private Integer programFlags;
    private String fileName;
    private String hostName;
    private LocalDate airdate;
    private String description;
    private String inetref;
    private Integer season;
    private Integer episode;
    private Integer totalEpisodes;
    private ChannelInfoModel channel;
    private RecordingInfoModel recording;
    private List<ArtworkInfoModel> artworkInfos;
    private List<CastMemberModel> castMembers;

}
