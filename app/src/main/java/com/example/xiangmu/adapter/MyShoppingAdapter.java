package com.example.xiangmu.adapter;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiangmu.R;
import com.example.xiangmu.bean.ShowShoppingBean;
import com.example.xiangmu.view.MyCustomView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyShoppingAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
    private List<ShowShoppingBean.ResuleBean> mlist;
    private Context context;

    public MyShoppingAdapter(Context context) {
        this.context = context;
        mlist=new ArrayList<>();
    }

    public void setList(List<ShowShoppingBean.ResuleBean> list) {
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyShoppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_shop,viewGroup,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final XRecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder holder= (ViewHolder) viewHolder;
        holder.tv_shop_name.setText(mlist.get(i).getCommodityName());
        holder.tv_shop_price.setText("￥"+mlist.get(i).getPrice()+"");
        Uri parse = Uri.parse(mlist.get(i).getPic());
        Glide.with(context).load(mlist.get(i).getPic()).into(holder.sd_shop_sim);
        holder.che_box.setChecked(mlist.get(i).isItem_check());

        holder.che_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mlist.get(i).setItem_check(holder.che_box.isChecked());
                if (shopCarListener!=null){
                    shopCarListener.callBack(mlist);
                }
            }
        });
        holder.cus_view.setDAta(this,mlist,i);
        holder.cus_view.setCallbacklistener(new MyCustomView.Callbacklistener() {
            @Override
            public void callback(int num) {
                mlist.get(i).setCount(num);
                if (shopCarListener!=null){
                    shopCarListener.callBack(mlist);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cus_view)
        MyCustomView cus_view;
        @BindView(R.id.sd_shop_sim)
        ImageView sd_shop_sim;
        @BindView(R.id.tv_shop_name)
        TextView tv_shop_name;
        @BindView(R.id.tv_shop_price)
        TextView tv_shop_price;
        @BindView(R.id.che_box)
        CheckBox che_box;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private ShopCarListener shopCarListener;
    public void setShopCarListener(ShopCarListener shopCarListener) {
        this.shopCarListener = shopCarListener;
    }
    public interface ShopCarListener {
        void callBack(List<ShowShoppingBean.ResuleBean> mlist);
    }
}
