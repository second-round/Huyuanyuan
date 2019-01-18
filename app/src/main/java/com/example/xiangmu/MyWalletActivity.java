package com.example.xiangmu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.xiangmu.adapter.WalletAdapter;
import com.example.xiangmu.bean.MyWalletBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWalletActivity extends AppCompatActivity implements IView {

    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    private PersenterImpl persenter;
    private WalletAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        persenter=new PersenterImpl(this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(manager);
        persenter.sendGet(Constant.QIANBAO+"?page=1&count=8",MyWalletBean.class);
        adapter=new WalletAdapter(this);
        recycle.setAdapter(adapter);
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof MyWalletBean){
            MyWalletBean myWalletBean= (MyWalletBean) data;
            money.setText(myWalletBean.getResult().getBalance()+"");
            adapter.setList(myWalletBean.getResult().getDetailList());
        }
    }
}
