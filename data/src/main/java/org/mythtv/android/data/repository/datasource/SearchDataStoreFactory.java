package org.mythtv.android.data.repository.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.mythtv.android.data.repository.DatabaseHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 10/10/15.
 */
@Singleton
public class SearchDataStoreFactory {

    private static final String TAG = SearchDataStoreFactory.class.getSimpleName();

    private DatabaseHelper mOpenHelper;

    @Inject
    public SearchDataStoreFactory( Context context ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.mOpenHelper = new DatabaseHelper( context.getApplicationContext() );

        Log.d( TAG, "initialize : exit" );
    }

    public SearchDataStore createReadSearchDataStore() {
        Log.d( TAG, "createReadSearchDataStore : enter" );

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        SearchDataStore searchDataStore = new DbSearchDataStore( db );

        Log.d( TAG, "createReadSearchDataStore : exit" );
        return  searchDataStore;
    }

    public SearchDataStore createWriteSearchDataStore() {
        Log.d( TAG, "createWriteSearchDataStore : enter" );

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        SearchDataStore searchDataStore = new DbSearchDataStore( db );

        Log.d( TAG, "createWriteSearchDataStore : exit" );
        return  searchDataStore;
    }

}
