package com.example.xiangmu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.example.xiangmu.adapter.MineFootAdapter;
import com.example.xiangmu.bean.MineFootBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFootActivity extends AppCompatActivity implements IView {

    @BindView(R.id.xrecycle)
    XRecyclerView xrecycle;
    private int mPage;
    PersenterImpl persenter;
    private MineFootAdapter footAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_foot);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mPage=1;
        persenter=new PersenterImpl(this);
        footAdapter=new MineFootAdapter(this);
        xrecycle.setLayoutManager(new StaggeredGridLayoutManager(2,GridLayoutManager.VERTICAL));
        xrecycle.setAdapter(footAdapter);
        xrecycle.setLoadingMoreEnabled(true);
        xrecycle.setPullRefreshEnabled(true);
        xrecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });
        loadData();
    }

    private void loadData() {
        persenter.sendGet(Constant.ZHUJI+"?page="+mPage+"&count=10",MineFootBean.class);
    }


    @Override
    public void requesta(Object data) {
        if (data instanceof MineFootBean){
            MineFootBean footBean= (MineFootBean) data;
            if (footBean==null||!footBean.isSuccess()){
                Toast.makeText(MainFootActivity.this,
                        footBean.getMessage(),Toast.LENGTH_LONG).show();
            }else {
                if (mPage==1){
                    footAdapter.setList(footBean.getResult());
                }else {
                    footAdapter.addList(footBean.getResult());
                }
                mPage++;
                xrecycle.refreshComplete();
                xrecycle.loadMoreComplete();
            }
        }
    }
}
