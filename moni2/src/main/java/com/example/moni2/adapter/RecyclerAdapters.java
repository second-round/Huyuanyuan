package com.example.moni2.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moni2.R;
import com.example.moni2.bean.PhoneBeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerAdapters extends RecyclerView.Adapter<RecyclerAdapters.ViewHolder> {
    private Context context;
    private List<PhoneBeans.DataBean> phone;
    private boolean isLinear=true;
    public RecyclerAdapters(Context context,boolean isLinear) {
        this.context = context;
        this.isLinear=isLinear;
        phone=new ArrayList<>();
    }

    public void setPhone(List<PhoneBeans.DataBean> phone) {
        this.phone = phone;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder=null;
        if (isLinear){
            View v=View.inflate(context,R.layout.item_one,null);
            holder=new ViewHolder(v);
        }else {
            View v=View.inflate(context,R.layout.item_two,null);
            holder=new ViewHolder(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(phone.get(i).getTitle());
        viewHolder.textView2.setText("￥"+phone.get(i).getPrice()+"元");
        viewHolder.textView3.setText(phone.get(i).getSalenum()+"");
        String images = phone.get(i).getImages();
        String[] split = images.split("\\|");
        List<String> list = Arrays.asList(split);
        Glide.with(context).load(list.get(0)).into(viewHolder.imageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener!=null){
                    clickListener.onitemClick(v,i);
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longItemClickListener!=null){
                    longItemClickListener.onItemLongClick(v,i);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return phone.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView,textView2,textView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            textView2=itemView.findViewById(R.id.textView2);
            textView3=itemView.findViewById(R.id.textView3);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }


    //接口回调,条目点击事件

    public interface OnItemClickListener{
        void onitemClick(View item, int position);
    }
    public interface OnLongItemClickListener {
        void onItemLongClick(View itemView, int position);
    }
    private OnItemClickListener clickListener;
    private OnLongItemClickListener longItemClickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener=clickListener;
    }
    public void setOnLongItemClickListener(OnLongItemClickListener longItemClickListener){
        this.longItemClickListener=longItemClickListener;
    }






















}
