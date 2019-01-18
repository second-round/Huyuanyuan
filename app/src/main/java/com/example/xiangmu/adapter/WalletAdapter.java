package com.example.xiangmu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.xiangmu.R;
import com.example.xiangmu.bean.AddressListBean;
import com.example.xiangmu.bean.MyWalletBean;

import java.util.ArrayList;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private List<MyWalletBean.ResultBean.DetailListBean> list;
    private Context context;

    public WalletAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setList(List<MyWalletBean.ResultBean.DetailListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.item_wallet,null);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.money.setText(list.get(i).getAmount()+"");
        viewHolder.time.setText(list.get(i).getCreateTime()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView money,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            money=itemView.findViewById(R.id.money);
            time=itemView.findViewById(R.id.time);
        }
    }
}
