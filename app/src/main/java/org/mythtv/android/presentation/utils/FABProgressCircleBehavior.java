package org.mythtv.android.presentation.utils;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.github.jorgecastilloprz.FABProgressCircle;

/**
 *
 * @author dmfrey
 *
 * Created on 2/8/17.
 *
 */

public class FABProgressCircleBehavior extends CoordinatorLayout.Behavior<FABProgressCircle> {

//    public FABProgressCircleBehavior() {
//        // This constructor is intentionally empty. Nothing special is needed here.
//    }

    @Override
    public boolean onDependentViewChanged( CoordinatorLayout parent, FABProgressCircle child, View dependency ) {

        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);

        return true;
    }

    @Override
    public boolean layoutDependsOn( CoordinatorLayout parent, FABProgressCircle child, View dependency ) {

        return dependency instanceof Snackbar.SnackbarLayout;
    }

}
