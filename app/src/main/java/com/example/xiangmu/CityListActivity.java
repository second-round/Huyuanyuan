package com.example.xiangmu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.xiangmu.adapter.AddrAdapter;
import com.example.xiangmu.adapter.PopupAddrAdapter;
import com.example.xiangmu.bean.AddressListBean;
import com.example.xiangmu.bean.RegBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityListActivity extends AppCompatActivity implements IView {

    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.add)
    Button add;
    private AddrAdapter addrAdapter;
    private PersenterImpl persenter;
    private AddressListBean addressListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);
        persenter=new PersenterImpl(this);
        initView();

    }

    private void initView() {
        addrAdapter=new AddrAdapter(CityListActivity.this);
        LinearLayoutManager manager=new LinearLayoutManager(CityListActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(manager);
        recycle.setAdapter(addrAdapter);
        persenter.sendGet(Constant.MYADDRESS, AddressListBean.class);
        addrAdapter.setAddrClickListener(new AddrAdapter.AddrClickListener() {
            @Override
            public void callBack(int i) {
                Map<String,String> map=new HashMap<>();
                map.put("id",addressListBean.getResult().get(i).getId());
                persenter.sendMessage(Constant.MORENDIZHI,map,RegBean.class);
            }
        });
    }

    @OnClick(R.id.add)
    public void onViewClicked() {
        Intent intent=new Intent(CityListActivity.this,CityAddActivity.class);
        intent.putExtra("name","2");
        startActivity(intent);
        finish();
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof AddressListBean){
            addressListBean = (AddressListBean) data;
            addrAdapter.setList(addressListBean.getResult());
        }
        if (data instanceof RegBean){
            persenter.sendGet(Constant.MYADDRESS, AddressListBean.class);
        }
    }
}
