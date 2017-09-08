package fanjh.mine.dialogpackage.Animator;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import fanjh.mine.library.Animator.BaseAnimator;

/**
 * Created by Administrator on 2017/9/6.
 */

public class TestEnterAnimator extends BaseAnimator {
    @Override
    public void setAnimator(View view) {
        /*mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(view,"alpha",0,1).setDuration(animatorDuration)
        );*/

        //mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "rotation", 10, -10, 6, -6, 3, -3, 0, 0));

        /*DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 0, 1, 1, 1),//
                ObjectAnimator.ofFloat(view, "translationY", -250 * dm.density, 30, -10, 0).setDuration(1000));*/
        DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", 250 * dm.density, 0), //
                ObjectAnimator.ofFloat(view, "alpha", 0, 1));
    }
}
