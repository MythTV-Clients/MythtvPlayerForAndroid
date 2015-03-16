package org.mythtv.android.library.persistence.repository;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by dmfrey on 3/15/15.
 */
public class MythtvSearchSuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "org.mythtv.android.library.persistence.repository.MythtvSearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MythtvSearchSuggestionProvider() {

        setupSuggestions( AUTHORITY, MODE );

    }

}
