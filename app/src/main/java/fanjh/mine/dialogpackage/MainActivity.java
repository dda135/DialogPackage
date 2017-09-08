package fanjh.mine.dialogpackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fanjh.mine.dialogpackage.Animator.DropFromTopAnimator;
import fanjh.mine.dialogpackage.Animator.InActivityPageAnimator;
import fanjh.mine.dialogpackage.Animator.OutActivityPageAnimator;
import fanjh.mine.dialogpackage.Animator.SlideToBottomAnimator;
import fanjh.mine.dialogpackage.Animator.TestEnterAnimator;
import fanjh.mine.dialogpackage.Animator.TestExitAnimator;
import fanjh.mine.library.CustomDialog;

public class MainActivity extends AppCompatActivity {

    Button mButton;
    Button listBtn;
    Button bottomBtn;
    CustomDialog mCustomDialog;
    CustomDialog mListDialog;
    CustomDialog mBottomDialog;
    LinearLayout parent;
    private View dialogView;
    private View reView;
    private View bottomView;
    RecyclerView recyclerView;
    RecyclerView bottomRecycler;
    View.OnClickListener mListener;
    LayoutAnimationController mLac;
    LayoutAnimationController bottomLac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mButton = (Button) findViewById(R.id.btn);
        listBtn = (Button) findViewById(R.id.btn_list);
        bottomBtn = (Button) findViewById(R.id.btn_bottom);
        parent = (LinearLayout) findViewById(R.id.parent);
        initDialogView();
        initRecycleView();
        initBottomView();
        final CustomDialog.Builder builder = new CustomDialog.Builder(this);
        mCustomDialog = builder.setLayout(dialogView).canCancelDialog(true).widthScale(0.8f).heightScale(0.5f).setDialogTheme(R.style.Theme_AppCompat_Dialog)
                .setGravity(Gravity.CENTER).setViewOnClickListener(R.id.btn_sure, mListener).setViewOnClickListener(R.id.btn_cancel, mListener).
                        showAnimator(new DropFromTopAnimator()).dismissAnimator(new SlideToBottomAnimator()).build();

        CustomDialog.Builder listBuilder = new CustomDialog.Builder(this);
        mListDialog = listBuilder.setLayout(reView).canCancelDialog(true).widthScale(0.8f).setDialogHeight(500).setGravity(Gravity.CENTER)
                .setDialogTheme(R.style.Theme_AppCompat_Dialog).showAnimator(new DropFromTopAnimator()).dismissAnimator(new TestExitAnimator()).build();

        mBottomDialog = listBuilder.setLayout(bottomView).canCancelDialog(false).widthScale(0.8f).setDialogHeight(400).setGravity(Gravity.BOTTOM | Gravity.CENTER)
                .setDialogTheme(R.style.Theme_AppCompat_Dialog).showAnimator(new TestEnterAnimator()).dismissAnimator(new TestExitAnimator()).addActivityView(parent)
                .windwoInAnimator(new InActivityPageAnimator()).windowOutAnimator(new OutActivityPageAnimator()).build();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog.show();
            }
        });
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListDialog.show();
                if (null != reView && null != recyclerView && null != mLac) {
                    recyclerView.setLayoutAnimation(mLac);
                }
            }
        });
        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomDialog.show();
                if (null != bottomView && null != bottomRecycler && null != bottomLac) {
                    bottomRecycler.setLayoutAnimation(bottomLac);
                }
            }
        });

    }

    private void initRecycleView() {
        ArrayList<String> mList = new ArrayList<>();
        reView = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
        recyclerView = (RecyclerView) reView.findViewById(R.id.recycle_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < 5; i++) {
            mList.add("" + i);
        }
        ListAdapter myAdapter = new ListAdapter(this, mList);
        recyclerView.setAdapter(myAdapter);
        TranslateAnimation animation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 2f, TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0f);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        mLac = new LayoutAnimationController(animation, 0.12f);
        mLac.setInterpolator(new DecelerateInterpolator());
    }

    private void initDialogView() {
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_view, null);
        TextView title = (TextView) dialogView.findViewById(R.id.tv_title);
        TextView content = (TextView) dialogView.findViewById(R.id.tv_content);
        title.setText("这是我设置的title");
        content.setText("这是我设置的content");
        mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_sure:
                        Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
                        if (null != mCustomDialog && mCustomDialog.isShowing()) {
                            mCustomDialog.dismiss();
                        }
                        break;
                    case R.id.btn_cancel:
                        Toast.makeText(MainActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
                        if (null != mCustomDialog && mCustomDialog.isShowing()) {
                            mCustomDialog.dismiss();
                        }
                        break;
                }
            }
        };
    }

    private void initBottomView() {
        bottomView = LayoutInflater.from(this).inflate(R.layout.dialog_bottom, null);
        bottomRecycler = (RecyclerView) bottomView.findViewById(R.id.bot_list);
        bottomRecycler.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> mList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mList.add("" + i);
        }
        ListAdapter adapter = new ListAdapter(this, mList);
        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(MainActivity.this, "选中第" + position + "个剑豪", Toast.LENGTH_SHORT).show();
                mBottomDialog.dismiss();
            }
        });
        bottomRecycler.setAdapter(adapter);
        TranslateAnimation animation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 6f, TranslateAnimation.RELATIVE_TO_SELF, 0f);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        bottomLac = new LayoutAnimationController(animation, 0.2f);
        bottomLac.setInterpolator(new DecelerateInterpolator());
    }

}
