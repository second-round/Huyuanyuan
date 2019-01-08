package com.example.xiangmu.fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.xiangmu.R;
import com.example.xiangmu.bean.CircleBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;

public class FragmentQuanZi extends Fragment implements IView {
    private PersenterImpl persenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_quanzi,container,false);
        initView();
        return view;
    }

    private void initView() {
        persenter=new PersenterImpl(this);
        persenter.sendGet(Constant.CIRCLEPATH,CircleBean.class);
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof CircleBean){
            CircleBean circleBean= (CircleBean) data;
            Toast.makeText(getActivity(),circleBean.getResult().get(2).getCommodityId(),Toast.LENGTH_SHORT).show();
        }
    }
}