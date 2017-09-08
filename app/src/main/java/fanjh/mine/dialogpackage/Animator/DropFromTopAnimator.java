package fanjh.mine.dialogpackage.Animator;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import fanjh.mine.library.Animator.BaseAnimator;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DropFromTopAnimator extends BaseAnimator {
    @Override
    public void setAnimator(View view) {

        DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 0, 1, 1, 1),//
                ObjectAnimator.ofFloat(view, "translationY", -250 * dm.density, 30, -10, 0).setDuration(1000));
    }
}
