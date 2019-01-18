package com.example.xiangmu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiangmu.R;
import com.example.xiangmu.bean.AlldorInfoByStatusBean;
import com.example.xiangmu.bean.ByIdBean;
import com.example.xiangmu.bean.ByName;
import com.example.xiangmu.bean.OrderBean;
import com.example.xiangmu.bean.ShowShoppingBean;

import java.util.ArrayList;
import java.util.List;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder> {

    private List<OrderBean.OrderListBean> list;
    private Context context;
    private int status;
    public StringAdapter(Context context,int status) {
        this.context = context;
        list=new ArrayList<>();
        this.status=status;
    }

    public void setData(List<OrderBean.OrderListBean> datas) {
        list.clear();
        if (datas!=null){
            list.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_text,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(list.get(i).getOrderId());
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(manager);
        AlldorInfoByStatusAdapter adapter=new AlldorInfoByStatusAdapter(context);
        viewHolder.recyclerView.setAdapter(adapter);
        adapter.setmList(list.get(i).getDetailList());
        viewHolder.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancleListener!=null){
                    cancleListener.callBack(list.get(i));
                }
            }
        });

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopCarListener!=null){
                    shopCarListener.callBack(list.get(i));
                }
            }
        });
        String orderStatus = list.get(i).getOrderStatus();
        int i1 = Integer.parseInt(orderStatus);
        switch (i1){
            case 1:
                viewHolder.button.setText("去支付");
                break;
            case 2:
                viewHolder.button.setText("点击收货");
                viewHolder.cancle.setVisibility(View.INVISIBLE);
                break;
            case 3:
                viewHolder.button.setText("去评价");
                viewHolder.cancle.setVisibility(View.INVISIBLE);
                break;
            case 9:
                viewHolder.cancle.setVisibility(View.INVISIBLE);
                viewHolder.button.setText("完成");
                break;
            default:
                break;
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        RecyclerView recyclerView;
        private Button button,cancle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.orderId);
            recyclerView=itemView.findViewById(R.id.recycle);
            button=itemView.findViewById(R.id.pay);
            cancle=itemView.findViewById(R.id.cancle);
        }
    }


    private ShopCarListener shopCarListener;
    public void setShopCarListener(ShopCarListener shopCarListener) {
        this.shopCarListener = shopCarListener;
    }
    public interface ShopCarListener {
        void callBack(OrderBean.OrderListBean list);
    }
    private CancleListener cancleListener;
    public void cancleListener(CancleListener cancleListener) {
        this.cancleListener = cancleListener;
    }
    public interface CancleListener {
        void callBack(OrderBean.OrderListBean list);
    }
}