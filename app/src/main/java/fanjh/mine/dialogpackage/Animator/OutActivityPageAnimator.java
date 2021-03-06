package fanjh.mine.dialogpackage.Animator;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import fanjh.mine.library.Animator.BaseAnimator;

/**
 * Created by Administrator on 2017/9/6.
 */

public class OutActivityPageAnimator extends BaseAnimator {
    @Override
    public void setAnimator(View view) {
       /* mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view,"scaleX",0.9f,1f).setDuration(500),
                ObjectAnimator.ofFloat(view,"scaleY",0.9f,1f).setDuration(500));*/

        DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
        ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, "rotationX", 10, 0f).setDuration(150);
        rotationX.setStartDelay(200);
        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view,"scaleX",0.9f,1f).setDuration(350),
                ObjectAnimator.ofFloat(view,"scaleY",0.9f,1f).setDuration(350),ObjectAnimator.ofFloat(view,"rotationX",0,10).setDuration(150),rotationX,
                ObjectAnimator.ofFloat(view, "translationY", -0.1f * dm.heightPixels, 0).setDuration(350));
    }
}
