package org.mythtv.android.library.persistence.domain;

import android.provider.BaseColumns;

/**
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
