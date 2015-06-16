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

package org.mythtv.android.library.persistence.domain;

import android.provider.BaseColumns;

/*
 * Created by dmfrey on 1/25/15.
 */
public abstract class AbstractBaseDatabase implements BaseColumns {

    public static final String FIELD_ID_DATA_TYPE = "INTEGER";
    public static final String FIELD_ID_PRIMARY_KEY = "PRIMARY KEY";
    public static final String FIELD_ID_PRIMARY_KEY_AUTOINCREMENT = "PRIMARY KEY AUTOINCREMENT NOT NULL";

    public static final String FIELD_CREATED_DATE = "created";
    public static final String FIELD_CREATED_DATE_DATA_TYPE = "INTEGER";

    public static final String FIELD_LAST_MODIFIED_DATE = "LAST_MODIFIED_DATE";
    public static final String FIELD_LAST_MODIFIED_DATE_DATA_TYPE = "INTEGER";

}
