package com.example.xiangmu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.xiangmu.R;
import com.example.xiangmu.bean.AlldorInfoByStatusBean;
import com.example.xiangmu.view.CustomJiaJian;

import java.util.ArrayList;
import java.util.List;

public class AlldorInfoByStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AlldorInfoByStatusBean.Result.DetailListBean> mList;
    private Context mContext;

    public AlldorInfoByStatusAdapter(Context mContext) {
        this.mContext = mContext;
        mList=new ArrayList<>();
    }

    public void setmList(List<AlldorInfoByStatusBean.Result.DetailListBean> datas) {
        mList.clear();
        if (datas!=null){
            mList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public List<AlldorInfoByStatusBean.Result.DetailListBean> getmList() {
        return mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_allorders_recycl,viewGroup,
                false);
        return new AlldorInfoByStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        AlldorInfoByStatusViewHolder holder= (AlldorInfoByStatusViewHolder) viewHolder;
        //设置商品的图片
        Glide.with(mContext).load(mList.get(i).commodityPic);
        holder.recycle_title.setText(mList.get(i).getCommodityName());
        holder.recycle_price.setText(mList.get(i).getCommodityPrice()+"");
        //调用customjiajian里面的方法设置 加减号中间的数字
        holder.customjiajian.setEditText(mList.get(i).getCommodityCount());
        holder.customjiajian.setCustomListener(new CustomJiaJian.CustomListener() {
            @Override
            public void jiajian(int count) {
                //改变数据源中的数量
                mList.get(i).setCommodityCount(count);
                notifyDataSetChanged();
            }

            @Override
            public void shuRuZhi(int count) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
    static class AlldorInfoByStatusViewHolder extends RecyclerView.ViewHolder{
        public ImageView recycle_icon;
        public TextView recycle_title;
        public TextView recycle_price;
        public CustomJiaJian customjiajian;
        public AlldorInfoByStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            recycle_icon=itemView.findViewById(R.id.recycle_icon);
            recycle_title=itemView.findViewById(R.id.recycle_title);
            recycle_price=itemView.findViewById(R.id.recycle_price);
            customjiajian=itemView.findViewById(R.id.customjiajian);
        }
    }
    UpdateListener updateListener;
    public void setUpdateListener(UpdateListener updateListener){
        this.updateListener = updateListener;
    }
    //接口
    public interface UpdateListener{
        void setTotal(String total, String num, boolean allCheck);
    }
}
