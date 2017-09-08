package fanjh.mine.dialogpackage.Animator;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import fanjh.mine.library.Animator.BaseAnimator;

/**
 * Created by Administrator on 2017/9/6.
 */

public class TestExitAnimator extends BaseAnimator {

    @Override
    public void setAnimator(View view) {
        //mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view,"alpha",1,0).setDuration(300));
        DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
        mAnimatorSet.playTogether(//
                ObjectAnimator.ofFloat(view, "translationY", 0, 250 * dm.density), //
                ObjectAnimator.ofFloat(view, "alpha", 1, 0));
    }
}
