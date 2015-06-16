/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.player.common.ui.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/*
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
