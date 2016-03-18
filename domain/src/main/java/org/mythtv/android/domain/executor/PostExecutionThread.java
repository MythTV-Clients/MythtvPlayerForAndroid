package org.mythtv.android.domain.executor;

import rx.Scheduler;

/**
 * Thread abstraction created to change the execution context from any thread to any other thread.
 * Useful to encapsulate a UI Thread for example, since some job will be done in background, an
 * implementation of this interface will change context and update the UI.
 *
 * Created by dmfrey on 8/26/15.
 */
public interface PostExecutionThread {

    Scheduler getScheduler();

}
