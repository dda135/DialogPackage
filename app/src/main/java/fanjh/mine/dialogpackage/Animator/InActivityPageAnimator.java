package fanjh.mine.dialogpackage.Animator;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import fanjh.mine.library.Animator.BaseAnimator;

/**
 * Created by Administrator on 2017/9/6.
 */

public class InActivityPageAnimator extends BaseAnimator {
    @Override
    public void setAnimator(View view) {

        DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
        ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, "rotationX", 10, 0f).setDuration(150);
        rotationX.setStartDelay(200);
        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.9f).setDuration(350),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.9f).setDuration(350), ObjectAnimator.ofFloat(view, "rotationX", 0, 10).setDuration(150), rotationX,
                ObjectAnimator.ofFloat(view, "translationY", 0, -0.1f * dm.heightPixels).setDuration(350));
    }
}
