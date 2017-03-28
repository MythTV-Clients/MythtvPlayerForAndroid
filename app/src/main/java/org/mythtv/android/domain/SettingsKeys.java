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

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/2/15.
 */
public final class SettingsKeys {

    public static final String KEY_PREF_BACKEND_URL = "backend_url";
    public static final String KEY_PREF_BACKEND_PORT = "backend_port";

    public static final String KEY_PREF_FILTER_HLS_ONLY = "filter_hls_only";

    public static final String KEY_PREF_INTERNAL_PLAYER = "internal_player";
    public static final String KEY_PREF_SHOW_ADULT_TAB = "show_adult_tab";

    public static final String KEY_PREF_ENABLE_RECORDING_GROUP_FILTER = "enable_recording_group_filter";
    public static final String KEY_PREF_RECORDING_GROUP_FILTER = "recording_group_filter";

    public static final String KEY_PREF_ENABLE_PARENTAL_CONTROLS = "enable_parental_controls";
    public static final String KEY_PREF_PARENTAL_CONTROL_LEVEL = "parental_control_level";

    public static final String KEY_PREF_RESTRICT_CONTENT_TYPES = "restrict_content_types";
    public static final String KEY_PREF_RATING_NR = "rating_nr";
    public static final String KEY_PREF_RATING_G = "rating_g";
    public static final String KEY_PREF_RATING_PG = "rating_pg";
    public static final String KEY_PREF_RATING_PG13 = "rating_pg13";
    public static final String KEY_PREF_RATING_R = "rating_r";
    public static final String KEY_PREF_RATING_NC17 = "rating_nc17";

    public static final String KEY_PREF_ENABLE_ANALYTICS = "enable_analytics";

    private SettingsKeys() { }

}
