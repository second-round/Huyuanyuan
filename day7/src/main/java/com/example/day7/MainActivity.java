package com.example.day7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.day7.ivew.IView;
import com.example.day7.netWork.Apis;
import com.example.day7.persenter.PersenterImpl;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IView {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    private PersenterImpl persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        persenter=new PersenterImpl(this);
        Map<String,String> map=new HashMap<>();
        map.put("pscid","1");
        persenter.sendMessage(Apis.URL,map,UserBean.class);
    }

    @Override
    public void getData(Object data) {

    }
}
