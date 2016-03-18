package org.mythtv.android.presentation.view;

import org.mythtv.android.presentation.model.ProgramModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link ProgramModel}.
 *
 * Created by dmfrey on 8/31/15.
 */
public interface ProgramListView extends LoadDataView {

    /**
     * Render a program list in the UI.
     *
     * @param programModelCollection The collection of {@link ProgramModel} that will be shown.
     */
    void renderProgramList( Collection<ProgramModel> programModelCollection );

    /**
     * View a {@link ProgramModel} details.
     *
     * @param programModel The program that will be shown.
     */
    void viewProgram( ProgramModel programModel );

}
