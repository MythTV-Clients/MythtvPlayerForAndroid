package org.mythtv.android.presentation.view;

import org.mythtv.android.presentation.model.SearchResultModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link SearchResultModel}.
 *
 * Created by dmfrey on 10/14/15.
 */
public interface SearchResultListView extends LoadDataView {

    /**
     * Render a searchResult list in the UI.
     *
     * @param searchResultModelCollection The collection of {@link SearchResultModel} that will be shown.
     */
    void renderSearchResultList( Collection<SearchResultModel> searchResultModelCollection);

    /**
     * View a {@link SearchResultModel} details.
     *
     * @param searchResultModel The search result that will be shown.
     */
    void viewSearchResult( SearchResultModel searchResultModel );

}
