package org.mythtv.android.presentation.view;

import org.mythtv.android.presentation.model.TitleInfoModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link TitleInfoModel}.
 *
 * Created by dmfrey on 9/9/15.
 */
public interface TitleInfoListView extends LoadDataView {

    /**
     * Render a titleInfo list in the UI.
     *
     * @param titleInfoModelCollection The collection of {@link TitleInfoModel} that will be shown.
     */
    void renderTitleInfoList( Collection<TitleInfoModel> titleInfoModelCollection );

    /**
     * View a {@link TitleInfoModel} details.
     *
     * @param titleInfoModel The titleInfo that will be shown.
     */
    void viewTitleInfo( TitleInfoModel titleInfoModel );

}
