package org.mythtv.android.presentation.view;

import org.mythtv.android.presentation.model.TvCategoryModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link TvCategoryModel}.
 *
 * Created by dmfrey on 1/28/16.
 */
public interface TvCategoryListView extends LoadDataView {

    /**
     * Render a tvCategory list in the UI.
     *
     * @param tvCategoryModelCollection The collection of {@link TvCategoryModel} that will be shown.
     */
    void renderTvCategoryList( Collection<TvCategoryModel> tvCategoryModelCollection);

    /**
     * View a {@link TvCategoryModel} details.
     *
     * @param tvCategoryModel The tvCategory that will be shown.
     */
    void viewTvCategory( TvCategoryModel tvCategoryModel );

}
