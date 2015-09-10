package org.mythtv.android.presentation.presenter;

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 *
 * Created by dmfrey on 8/30/15.
 */
public interface Presenter {

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    void destroy();

}
