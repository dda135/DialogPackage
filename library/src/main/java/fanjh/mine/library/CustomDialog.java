package fanjh.mine.library;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import fanjh.mine.library.Animator.BaseAnimator;
import fanjh.mine.library.util.StatusBarUtils;

/**
 * 一个自定义的dialog，支持自定义布局和自定义动画
 * Created by Administrator on 2017/8/31.
 */

public class CustomDialog extends Dialog {

    private int dialogWidth;//宽度

    private int dialogHeight;//高度

    private int dissmissDelayTime = 15000;//动画消失时间

    private boolean canCancelDialog = true;//是否可以触摸背景退出,默认为true

    private int dialogGravity = Gravity.CENTER;//位置,默认为居中

    private View mView;//自定义的布局

    private View activityView;//Activity页面的根布局，可在这里设置动画

    private BaseAnimator showAnimator;//展示动画

    private BaseAnimator dismissAnimator;//消失动画

    private BaseAnimator windowInAnimator;//window进入动画

    private BaseAnimator windowOutAnimator;//window退出动画

    LinearLayout mLlTop;//mLlControlHeight的父布局，覆盖整个屏幕

    LinearLayout mLlControlHeight;//contentView的父布局

    private Handler mHandler = new Handler(Looper.getMainLooper());

    Context mContext;

    public CustomDialog(Builder builder) {
        super(builder.mContext);
        mContext = builder.mContext;
        setDefaultTheme();
        initData(builder);
    }

    public CustomDialog(@StyleRes int themeResId, Builder builder) {
        super(builder.mContext, themeResId);
        mContext = builder.mContext;
        setDefaultTheme();
        initData(builder);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DisplayMetrics mDisplayMetrics = mContext.getResources().getDisplayMetrics();
        int mMaxHeight = mDisplayMetrics.heightPixels - StatusBarUtils.getHeight(mContext);

        mLlTop = new LinearLayout(mContext);
        mLlTop.setGravity(Gravity.CENTER);

        mLlControlHeight = new LinearLayout(mContext);
        mLlControlHeight.setOrientation(LinearLayout.VERTICAL);

        mLlControlHeight.addView(mView);
        mLlTop.addView(mLlControlHeight);

        setContentView(mLlTop, new ViewGroup.LayoutParams(mDisplayMetrics.widthPixels, mMaxHeight));

        mLlTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canCancelDialog) {
                    dismiss();
                }
            }
        });

        mView.setClickable(true);

       /* setCanceledOnTouchOutside(canCancelDialog);
        setCancelable(canCancelDialog);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = dialogWidth;
        layoutParams.height = dialogHeight;
        layoutParams.gravity = dialogGravity;
        getWindow().setAttributes(layoutParams);
        if (dimEnable) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }*/
    }

    private void initData(Builder builder) {
        dialogWidth = builder.dialogWidth;
        dialogHeight = builder.dialogHeight;
        canCancelDialog = builder.canCancelDialog;
        dialogGravity = builder.dialogGravity;
        showAnimator = builder.showAnimator;
        dismissAnimator = builder.dismissAnimator;
        mView = builder.mView;
        windowInAnimator = builder.windowInAnimator;
        windowOutAnimator = builder.windowOutAnimator;
        activityView = builder.activityView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mView.getLayoutParams();//设置dialog的宽高
        layoutParams.width = dialogWidth;
        layoutParams.height = dialogHeight;
        mLlTop.setGravity(dialogGravity);
        getWindow().setGravity(dialogGravity);
        /*if (dimEnable) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }*/
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (null != showAnimator || null != windowInAnimator) {
            if (null != showAnimator) {
                showAnimator.addAnimatorListener(new BaseAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOnView(mLlControlHeight);
            }

            if (null != activityView && null != windowInAnimator) {
                windowInAnimator.addAnimatorListener(new BaseAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOnView(activityView);
            }
        } else {
            BaseAnimator.resetAnimation(mLlControlHeight);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 延迟关闭dialog
     */
    private void delayDismiss() {
        if (dissmissDelayTime > 0) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, dissmissDelayTime);
        }
    }

    /**
     * dialog anim by styles(动画弹出对话框,style动画资源)
     */
    public void show(int animStyle) {
        Window window = getWindow();
        window.setWindowAnimations(animStyle);
        show();
    }

    public void showAtLocation(int gravity, int x, int y, int animStyle) {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(gravity);
        params.x = x;
        params.y = y;
        window.setWindowAnimations(animStyle);
        show();
    }

    @Override
    public void dismiss() {
        if (null != dismissAnimator || null != windowOutAnimator) {
            if (null != dismissAnimator) {
                dismissAnimator.addAnimatorListener(new BaseAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        CustomDialog.super.dismiss();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        CustomDialog.super.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOnView(mLlControlHeight);
            }

            if (null != windowOutAnimator) {
                windowOutAnimator.addAnimatorListener(new BaseAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOnView(activityView);
            }
        } else {
            super.dismiss();
        }
    }

    /**
     * 设置dialog默认的主题
     */
    private void setDefaultTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * builder模式设置参数
     */
    public static class Builder {

        private Context mContext;

        private int dialogWidth;

        private int dialogHeight;

        private boolean canCancelDialog;

        private int dialogGravity;

        private int dissmissDelayTime;

        private BaseAnimator windowInAnimator;//window进入动画

        private BaseAnimator windowOutAnimator;//window退出动画

        private BaseAnimator showAnimator;//展示动画

        private BaseAnimator dismissAnimator;//消失动画

        private View mView;

        private View activityView;//Activity页面的根布局，可在这里设置动画

        private int dialogTheme = -1;

        private View.OnClickListener mOnClickListener;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder dissmissDelayTime(int dissmissDelayTime) {
            this.dissmissDelayTime = dissmissDelayTime;
            return this;
        }

        public Builder setDialogTheme(int dialogTheme) {
            this.dialogTheme = dialogTheme;
            return this;
        }

        public Builder setDialogWidth(int dialogWidth) {
            this.dialogWidth = dialogWidth;
            return this;
        }

        public Builder setDialogHeight(int dialogHeight) {
            this.dialogHeight = dialogHeight;
            return this;
        }

        public Builder canCancelDialog(boolean canCancelDialog) {
            this.canCancelDialog = canCancelDialog;
            return this;
        }

        public Builder setGravity(int dialogGravity) {
            this.dialogGravity = dialogGravity;
            return this;
        }

        public Builder setLayout(View view) {
            mView = view;
            return this;
        }

        public Builder showAnimator(BaseAnimator showAnimator) {
            this.showAnimator = showAnimator;
            return this;
        }

        public Builder dismissAnimator(BaseAnimator dismissAnimator) {
            this.dismissAnimator = dismissAnimator;
            return this;
        }

        public Builder widthScale(float widthScale) {
            int screenWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
            this.dialogWidth = (int) (screenWidth * widthScale);
            return this;
        }

        public Builder heightScale(float heightScale) {
            int screenWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
            this.dialogHeight = (int) (screenWidth * heightScale);
            return this;
        }

        public Builder setViewOnClickListener(int itemId, View.OnClickListener clickListener) {
            mView.findViewById(itemId).setOnClickListener(clickListener);
            return this;
        }

        public Builder windwoInAnimator(BaseAnimator windowInAnimator) {
            this.windowInAnimator = windowInAnimator;
            return this;
        }

        public Builder windowOutAnimator(BaseAnimator windowOutAnimator) {
            this.windowOutAnimator = windowOutAnimator;
            return this;
        }

        public Builder addActivityView(View activityView) {
            this.activityView = activityView;
            return this;
        }

        public CustomDialog build() {
            if (-1 != dialogTheme) {
                return new CustomDialog(this);
            } else {
                return new CustomDialog(dialogTheme, this);
            }
        }

    }
}
