package org.mythtv.android.library.ui.data;

import org.mythtv.android.library.core.domain.dvr.Program;

import java.util.List;
import java.util.Map;

/**
 * Created by dmfrey on 11/29/14.
 */
public interface RecordingDataConsumer extends ErrorHandler {

    public void onSetPrograms(List<Program> programs);

}
