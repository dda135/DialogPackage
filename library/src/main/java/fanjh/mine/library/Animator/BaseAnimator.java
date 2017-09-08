package fanjh.mine.library.Animator;

import android.animation.Animator;
import android.view.View;
import android.animation.AnimatorSet;
import android.view.animation.Interpolator;


/**
 * Created by Administrator on 2017/8/31.
 */

public abstract class BaseAnimator {

    public int animatorDuration = 1000;//动画执行的默认时间

    public AnimatorSet mAnimatorSet = new AnimatorSet();

    public int delayTime;

    public Interpolator mInterpolator;

    public AnimatorListener mAnimatorListener;

    public abstract void setAnimator(View view);

    public void startAnimator(View view) {
        resetAnimation(view);
        setAnimator(view);

        if (null != mInterpolator) {
            mAnimatorSet.setInterpolator(mInterpolator);
        }

        if (delayTime > 0) {
            mAnimatorSet.setStartDelay(delayTime);
        }

        if (null != mAnimatorListener) {
            mAnimatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mAnimatorListener.onAnimationStart(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mAnimatorListener.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    mAnimatorListener.onAnimationCancel(animation);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                   mAnimatorListener.onAnimationRepeat(animation);
                }
            });
        }
        mAnimatorSet.start();

    }

    public void playOnView(View view){
        startAnimator(view);
    }

    public static void resetAnimation(View view) {
        view.setTranslationX(0);
        view.setTranslationY(0);
        view.setScaleX(1);
        view.setScaleY(1);
        view.setRotationX(0);
        view.setRotationY(0);
        view.setAlpha(1);
    }

    public BaseAnimator setAnimatorDuration(int animatorDuration){
        this.animatorDuration = animatorDuration;
        return this;
    }

    public BaseAnimator interPolator(Interpolator interpolator){
        this.mInterpolator = interpolator;
        return this;
    }

    public BaseAnimator delay(int delayTime){
        this.delayTime = delayTime;
        return this;
    }

    public BaseAnimator addAnimatorListener(AnimatorListener animatorListener){
        this.mAnimatorListener = animatorListener;
        return this;
    }

    public interface AnimatorListener{
        void onAnimationStart(Animator animation);
        void onAnimationEnd(Animator animation);
        void onAnimationCancel(Animator animation);
        void onAnimationRepeat(Animator animation);
    }
}
