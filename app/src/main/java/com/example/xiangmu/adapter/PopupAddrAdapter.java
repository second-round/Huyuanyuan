package com.example.xiangmu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiangmu.R;
import com.example.xiangmu.bean.AddressListBean;

import java.util.ArrayList;
import java.util.List;

public class PopupAddrAdapter extends RecyclerView.Adapter<PopupAddrAdapter.ViewHolder> {
    private List<AddressListBean.ResuleBean> list;
    private Context context;

    public PopupAddrAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setList(List<AddressListBean.ResuleBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.item_addr,null);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(list.get(i).getRealName());
        viewHolder.phone.setText(list.get(i).getPhone());
        viewHolder.addr.setText(list.get(i).getAddress());
        viewHolder.choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addrClickListener!=null){
                    addrClickListener.callBack(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone,choose,addr;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            choose=itemView.findViewById(R.id.choose);
            addr=itemView.findViewById(R.id.addr);
        }
    }
    private AddrClickListener addrClickListener;
    public void setAddrClickListener(AddrClickListener addrClickListener) {
        this.addrClickListener = addrClickListener;
    }
    public interface AddrClickListener {
        void callBack(int i);
    }

}
