package org.mythtv.android.presentation.model;

import org.joda.time.DateTime;
import org.mythtv.android.domain.SearchResult;

import lombok.Data;

/**
 * Created by dmfrey on 10/8/15.
 */
@Data
public class SearchResultModel {

    private int chanId;
    private DateTime startTime;
    private String title;
    private String subTitle;
    private String category;
    private String callsign;
    private String channelNumber;
    private String description;
    private String inetref;
    private int season;
    private int episode;
    private String castMembers;
    private String characters;
    private int videoId;
    private String rating;
    private String storeageGroup;
    private String filename;
    private String hostname;
    private SearchResult.Type type;

}
