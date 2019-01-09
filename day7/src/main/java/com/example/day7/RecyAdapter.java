package com.example.day7;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {
    private List<UserBean.DataBean> list;
    private Context context;

    public RecyAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<UserBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(View.inflate(context,R.layout.item,viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView1.setText(list.get(i).getPrice()+"");
        viewHolder.textView2.setText(list.get(i).getTitle());
        String images = list.get(i).getImages();
        String[] split = images.split("\\|");
        List<String> list = Arrays.asList(split);
        Glide.with(context).load(list.get(0)).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView1,textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textView1=itemView.findViewById(R.id.textView);
            textView2=itemView.findViewById(R.id.textView2);
        }
    }
}
