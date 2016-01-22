package org.mythtv.android.presentation.view;

import org.mythtv.android.presentation.model.EncoderModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link EncoderModel}.
 *
 * Created by dmfrey on 11/13/15.
 */
public interface EncoderListView extends LoadDataView {

    /**
     * Render an encoder list in the UI.
     *
     * @param encoderModelCollection The collection of {@link EncoderModel} that will be shown.
     */
    void renderEncoderList( Collection<EncoderModel> encoderModelCollection);

}
