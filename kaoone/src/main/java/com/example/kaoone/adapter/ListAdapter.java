package com.example.kaoone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kaoone.R;
import com.example.kaoone.userbean.TwoBean;

import java.util.List;

public class ListAdapter extends BaseAdapter{
    private List<TwoBean.UserBean> list;
    private Context context;

    public ListAdapter(List<TwoBean.UserBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (holder==null) {
            view=View.inflate(context, R.layout.item,null);
            holder = new ViewHolder();
            holder.textView=view.findViewById(R.id.text);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        holder.textView.setText(list.get(i).getName());
        return view;
    }
    class ViewHolder{
        TextView textView;
    }
}
