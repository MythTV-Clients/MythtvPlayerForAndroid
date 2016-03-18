package org.mythtv.android.domain;

import org.joda.time.DateTime;

import lombok.Data;

/**
 * Created by dmfrey on 10/8/15.
 */
@Data
public class SearchResult {

    public enum Type { RECORDING, VIDEO }

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
    private String contentType;
    private String rating;
    private String storageGroup;
    private String filename;
    private String hostname;
    private Type type;

}
