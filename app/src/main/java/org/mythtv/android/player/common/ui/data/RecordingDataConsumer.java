package org.mythtv.android.player.common.ui.data;

import org.mythtv.android.library.core.domain.dvr.Program;

import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public interface RecordingDataConsumer extends ErrorHandler {

    public void onSetPrograms(List<Program> programs);

}
