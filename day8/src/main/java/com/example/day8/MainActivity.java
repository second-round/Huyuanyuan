package com.example.day8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.day8.adapter.RecyAdapter;
import com.example.day8.bean.UserBean;
import com.example.day8.persenter.PersenterImpl;
import com.example.day8.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.recycle)
    XRecyclerView recycle;
    private PersenterImpl persenter;
    private RecyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        persenter=new PersenterImpl(this);
        Map<String,String> map=new HashMap<>();
        map.put("page","1");
        persenter.sendData("news/news.php?page=1",map,UserBean.class);
        adapter = new RecyAdapter(MainActivity.this);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapter);
    }

    @Override
    public void setData(Object o) {
        UserBean userBean= (UserBean) o;
        adapter.setList(userBean.getData());
    }
}
