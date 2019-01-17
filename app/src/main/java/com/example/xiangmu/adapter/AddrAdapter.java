package com.example.xiangmu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.xiangmu.R;
import com.example.xiangmu.bean.AddressListBean;

import java.util.ArrayList;
import java.util.List;

public class AddrAdapter extends RecyclerView.Adapter<AddrAdapter.ViewHolder> {
    private List<AddressListBean.ResuleBean> list;
    private Context context;

    public AddrAdapter(Context context) {
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
        View view=View.inflate(context,R.layout.item_addrs,null);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(list.get(i).getRealName());
        viewHolder.phone.setText(list.get(i).getPhone());
        viewHolder.addr.setText(list.get(i).getAddress());
        String whetherDefault = list.get(i).getWhetherDefault();
        if (Integer.parseInt(whetherDefault)==1){
            viewHolder.check.setChecked(true);
        }else {
            viewHolder.check.setChecked(false);
        }
        viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (addrClickListener!=null){
                    addrClickListener.callBack(i);
                }
            }
        });

        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delClickListener.callBack(i);
            }
        });

        viewHolder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeClickListener.callBack(i);
            }
        });

//        viewHolder.choose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (addrClickListener!=null){
//                    addrClickListener.callBack(i);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone,addr;
        RadioButton check;
        Button change,del;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            addr=itemView.findViewById(R.id.addr);
            check=itemView.findViewById(R.id.check);
            change=itemView.findViewById(R.id.change);
            del=itemView.findViewById(R.id.del);
        }
    }
    private AddrClickListener addrClickListener;
    public void setAddrClickListener(AddrClickListener addrClickListener) {
        this.addrClickListener = addrClickListener;
    }
    public interface AddrClickListener {
        void callBack(int i);
    }
private ChangeClickListener changeClickListener;
    public void setChangeClickListener(ChangeClickListener changeClickListener) {
        this.changeClickListener = changeClickListener;
    }
    public interface ChangeClickListener {
        void callBack(int i);
    }
private DelClickListener delClickListener;
    public void delClickListener(DelClickListener delClickListener) {
        this.delClickListener = delClickListener;
    }
    public interface DelClickListener {
        void callBack(int i);
    }

}
