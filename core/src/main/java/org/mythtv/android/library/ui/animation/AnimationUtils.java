package org.mythtv.android.library.ui.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnticipateInterpolator;

/**
 * Created by dmfrey on 3/9/15.
 */
public class AnimationUtils {

    public static void animate( RecyclerView.ViewHolder holder, boolean goesDown ) {

        AnimatorSet animatorSet = new AnimatorSet();
        //ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat( holder.itemView, "scaleX" ,0.5F, 0.8F, 1.0F );
        //ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat( holder.itemView, "scaleY", 0.5F, 0.8F, 1.0F );
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat( holder.itemView, "translationY", goesDown ? 100 : -100, 0 );
        //ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat( holder.itemView, "translationX", -50, 50, -30, 30, -20, 20, -5, 5, 0 );
        animatorSet.playTogether( /* animatorTranslateX,*/ animatorTranslateY /*, animatorScaleX, animatorScaleY */ );
//        animatorSet.setInterpolator( new AnticipateInterpolator() );
        animatorSet.setDuration( 1000 );
        animatorSet.start();

    }

}
