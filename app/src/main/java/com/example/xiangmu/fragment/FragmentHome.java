package com.example.xiangmu.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xiangmu.MyInfoActivity;
import com.example.xiangmu.R;
import com.example.xiangmu.adapter.ByIdAdapter;
import com.example.xiangmu.adapter.ByNameAdapter;
import com.example.xiangmu.adapter.ErJiAdapter;
import com.example.xiangmu.adapter.HotAdapter;
import com.example.xiangmu.adapter.MoAdapter;
import com.example.xiangmu.adapter.PinAdapter;
import com.example.xiangmu.adapter.TopHomeAdapter;
import com.example.xiangmu.app.Addevent;
import com.example.xiangmu.bean.BannerBean;
import com.example.xiangmu.bean.ByIdBean;
import com.example.xiangmu.bean.ByName;
import com.example.xiangmu.bean.ErJi;
import com.example.xiangmu.bean.EventBean;
import com.example.xiangmu.bean.GoodsBean;
import com.example.xiangmu.bean.HomeBean;
import com.example.xiangmu.bean.TopLasBean;
import com.example.xiangmu.pager.PagerTransformer;
import com.example.xiangmu.pager.RoundAngleImageView;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentHome extends Fragment implements IView {
    @BindView(R.id.choose)
    ImageView choose;
    @BindView(R.id.huans)
    ImageView huans;
    @BindView(R.id.fow_viewpager)
    ViewPager fowViewpager;
    Unbinder unbinder;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.edits)
    EditText edits;
    @BindView(R.id.recy_re)
    RecyclerView recyRe;
    @BindView(R.id.recy_mo)
    RecyclerView recyMo;
    @BindView(R.id.recy_pin)
    RecyclerView recyPin;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.byName)
    RecyclerView byName;
    @BindView(R.id.point_re)
    ImageView pointRe;
    @BindView(R.id.point_mo)
    ImageView pointMo;
    @BindView(R.id.point_pin)
    ImageView pointPin;
    @BindView(R.id.res)
    TextView res;
    @BindView(R.id.mos)
    TextView mos;
    @BindView(R.id.pins)
    TextView pins;
    private TopHomeAdapter adapter;
    private ErJiAdapter adapters;
    private PersenterImpl persenter;
    int j = 5000;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            j++;
            fowViewpager.setCurrentItem(j);
            handler.sendEmptyMessageDelayed(0, 2000);
        }
    };
    private List<BannerBean.ResultBean> result;
    private PopupWindow popupWindow;
    private HotAdapter hotAdapter;
    private MoAdapter moAdapter;
    private PinAdapter pinAdapter;
    private ByNameAdapter byNameAdapter;
    private TopLasBean topLasBean;
    private List<ErJi.ResultBean> jiResult;
    private String id;
    private PopupWindow popupWindows;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        byName.setLayoutManager(manager);
        initData();
        initRecy();
        return view;
    }

    private void getGoods(int id){
        persenter.sendGet(Constant.ZHU_SANSHOP+"?commodityId="+id,GoodsBean.class);
    }

    private void initRecy() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyRe.setLayoutManager(gridLayoutManager);
        hotAdapter = new HotAdapter(getActivity());
        recyRe.setAdapter(hotAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyMo.setLayoutManager(layoutManager);
        moAdapter = new MoAdapter(getActivity());
        recyMo.setAdapter(moAdapter);
        GridLayoutManager gridLayoutManagers = new GridLayoutManager(getActivity(), 2);
        gridLayoutManagers.setOrientation(LinearLayoutManager.VERTICAL);
        recyPin.setLayoutManager(gridLayoutManagers);
        pinAdapter = new PinAdapter(getActivity());
        recyPin.setAdapter(pinAdapter);
    }

    private void initPager() {
        fowViewpager.setPageMargin(30);
        fowViewpager.setOffscreenPageLimit(4);
        fowViewpager.setPageTransformer(true, new PagerTransformer());
        fowViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 10000;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int p = position % 4;
                RoundAngleImageView view = new RoundAngleImageView(getActivity());
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity()).load(result.get(p).getImageUrl()).into(view);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        fowViewpager.setCurrentItem(j);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.sendEmptyMessageDelayed(0, 2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.choose, R.id.huans, R.id.text,R.id.point_re,R.id.point_mo,R.id.point_pin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose:
                initDatas();
                break;
            case R.id.huans:
                text.setVisibility(View.VISIBLE);
                huans.setVisibility(View.GONE);
                edits.setVisibility(View.VISIBLE);
                break;
            case R.id.text:
                id="";
                if (edits.getText().toString().equals("")) {
                    huans.setVisibility(View.VISIBLE);
                    text.setVisibility(View.GONE);
                    edits.setVisibility(View.INVISIBLE);
                } else {

                    persenter.sendGet(Constant.QUERY_NAME + "?keyword=" + edits.getText().toString() + "&page=" + "1" + "&count=8", ByName.class);
                }
                break;
            case R.id.point_re:
                res.setVisibility(View.VISIBLE);
                id="1002";
                getData(id);
                backPage();
                break;
            case R.id.point_mo:
                mos.setVisibility(View.VISIBLE);
                id = "1003";
                getData(id);
                backPage();
                break;
            case R.id.point_pin:
                pins.setVisibility(View.VISIBLE);
                id="1004";
                getData(id);
                backPage();
                break;
            default:
                break;
        }
    }
    private void getData(String id) {
        persenter.sendGet(Constant.ZHU_SHOPXIANG+"?labelId="+id+"&page=1&count=8",ByName.class);
    }
    private void initDatas() {
        View v = View.inflate(getActivity(), R.layout.popup, null);
        RecyclerView topView = v.findViewById(R.id.recycle_top);
        adapter = new TopHomeAdapter(getActivity());
        topView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        topView.setAdapter(adapter);
        RecyclerView popup_two = v.findViewById(R.id.popup_two);
        adapters = new ErJiAdapter(getActivity());
        popup_two.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        popup_two.setAdapter(adapters);
        popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, -350);
        adapter.result(new TopHomeAdapter.Cicklistener() {
            @Override
            public void setonclicklisener(int index) {
                String id = topLasBean.getResult().get(index).getId();
                persenter.sendGet(Constant.ERJI + "?firstCategoryId=" + id, ErJi.class);
                adapters.result(new ErJiAdapter.Cicklistener() {
                    @Override
                    public void setonclicklisener(int index) {
                        String id1 = jiResult.get(index).getId();
                        persenter.sendGet(Constant.ZHAN + "?categoryId=" + id1 + "&page=1&count=8", ByIdBean.class);
                        backPage();
                    }
                });
            }
        });
        loadData();
    }

    private void loadData() {
        persenter.sendGet(Constant.YIJI, TopLasBean.class);
    }

    private void initData() {
        persenter = new PersenterImpl(this);
        persenter.sendGet(Constant.ZHU_BANNER, BannerBean.class);
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof BannerBean) {
            BannerBean bannerBean = (BannerBean) data;
            result = bannerBean.getResult();
            initPager();
            persenter.sendGet(Constant.ZHU_SHOPHTTP, HomeBean.class);
        }
        if (data instanceof TopLasBean) {
            topLasBean = (TopLasBean) data;
            adapter.setData(topLasBean.getResult());
        }
        if (data instanceof HomeBean) {
            final HomeBean homeBean = (HomeBean) data;
            hotAdapter.setData(homeBean.getResult().getRxxp().get(0).getCommodityList());
            moAdapter.setData(homeBean.getResult().getMlss().get(0).getCommodityList());
            pinAdapter.setData(homeBean.getResult().getPzsh().get(0).getCommodityList());
            hotAdapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    getGoods(homeBean.getResult().getRxxp().get(0).getCommodityList().get(index).getCommodityId());
                }
            });
            moAdapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    getGoods(homeBean.getResult().getMlss().get(0).getCommodityList().get(index).getCommodityId());
                }
            });
            pinAdapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    getGoods(homeBean.getResult().getPzsh().get(0).getCommodityList().get(index).getCommodityId());
                }
            });
        }
        if (data instanceof ByName) {
            final ByName byNames = (ByName) data;
            if (byNames.getResult().size()==0||byNames.getResult().equals("")){
            popupWindows = new PopupWindow(View.inflate(getActivity(),R.layout.popup_none,null),ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindows.setFocusable(true);
                popupWindows.setTouchable(true);
                popupWindows.setBackgroundDrawable(new BitmapDrawable());
                popupWindows.showAtLocation(View.inflate(getActivity(),R.layout.popup_none,null),Gravity.CENTER, 100, 0);
            }
            if (id=="1002"){
                res.setVisibility(View.VISIBLE);
            }else if (id=="1003"){
                mos.setVisibility(View.VISIBLE);
            }else if (id=="1004") {
                pins.setVisibility(View.VISIBLE);
            }
            scroll.setVisibility(View.GONE);
            byName.setVisibility(View.VISIBLE);
            byNameAdapter = new ByNameAdapter(getActivity());
            byName.setAdapter(byNameAdapter);
            byNameAdapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    getGoods(byNames.getResult().get(index).getCommodityId());
                }
            });
            byNameAdapter.setData(byNames.getResult());
            byNameAdapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    getGoods(byNames.getResult().get(index).getCommodityId());
                }
            });

            backPage();
        }
        if (data instanceof ErJi) {
            ErJi erJi = (ErJi) data;
            jiResult = erJi.getResult();
            adapters.setData(jiResult);
        }
        if (data instanceof ByIdBean) {
            scroll.setVisibility(View.GONE);
            byName.setVisibility(View.VISIBLE);
            ByIdAdapter adapter = new ByIdAdapter(getActivity());
            byName.setAdapter(adapter);
            final ByIdBean byIdBean = (ByIdBean) data;
            List<ByIdBean.ResultBean> result = byIdBean.getResult();
            adapter.setData(result);
            adapter.result(new TopHomeAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    getGoods(byIdBean.getResult().get(index).getCommodityId());
                }
            });
        }
        if (data instanceof GoodsBean){
            Intent intent=new Intent(getActivity(),MyInfoActivity.class);
            EventBus.getDefault().postSticky(new EventBean("goods",data));
            startActivity(intent);
        }

    }

    private long exitTime = 0;

    private void backPage() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                scroll.setVisibility(View.VISIBLE);
                byName.setVisibility(View.GONE);
                if (popupWindows!=null){
                    popupWindows.dismiss();
                }
                if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    scroll.setVisibility(View.VISIBLE);
                    byName.setVisibility(View.GONE);
                    res.setVisibility(View.GONE);
                    mos.setVisibility(View.GONE);
                    pins.setVisibility(View.GONE);
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        exitTime = System.currentTimeMillis();
                    } else {
                        //启动一个意图,回到桌面
                        Intent backHome = new Intent(Intent.ACTION_MAIN);
                        backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        backHome.addCategory(Intent.CATEGORY_HOME);
                        startActivity(backHome);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}