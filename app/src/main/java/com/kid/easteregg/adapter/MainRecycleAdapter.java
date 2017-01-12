package com.kid.easteregg.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kid.easteregg.R;
import com.kid.easteregg.model.AndroidVersion;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/10.
 */

public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.MyViewHolder>
        implements View.OnClickListener {

    private ArrayList<AndroidVersion> mDatas = null;

    public MainRecycleAdapter(ArrayList<AndroidVersion> datas) {
        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.versionText.setText(mDatas.get(position).getVersionText());
        holder.versionImg.setImageResource(mDatas.get(position).getVersionImage());
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(mDatas.get(position));
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView versionText;
        public ImageView versionImg;
        public MyViewHolder(View itemView) {
            super(itemView);
            versionText = (TextView) itemView.findViewById(R.id.tv_verText);
            versionImg = (ImageView) itemView.findViewById(R.id.iv_verImg);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (AndroidVersion)v.getTag());
        }
    }

    //点击事件接口
    public static interface OnRecyclerViewItemClickListener  {
        void onItemClick(View view, AndroidVersion data);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setmOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
