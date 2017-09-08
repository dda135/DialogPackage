package fanjh.mine.dialogpackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/6.
 */

public class ListAdapter extends RecyclerView.Adapter {

    ArrayList<String> mArrayList;
    LayoutInflater mInflater;
    Context mContext;

    public ListAdapter(Context mContext, ArrayList<String> arrayList) {
        this.mContext = mContext;
        mArrayList = arrayList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onClick(position);
                }
            }
        });
        myViewHolder.mTextView.setText("这是来自疾风剑豪的第" + mArrayList.get(position) + "条召唤消息");
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public ArrayList getDataList() {
        return mArrayList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llItem;
        ImageView mImageView;
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_recycle);
            mTextView = (TextView) itemView.findViewById(R.id.tv_recycle);
        }
    }

    OnItemClickListener mOnItemClickListener;

    interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

}
