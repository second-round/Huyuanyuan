package com.example.xiangmu;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.adapter.OrderAdapter;
import com.example.xiangmu.adapter.PopupAddrAdapter;
import com.example.xiangmu.bean.AddressListBean;
import com.example.xiangmu.bean.EventBean;
import com.example.xiangmu.bean.RegBean;
import com.example.xiangmu.bean.ShopFuBean;
import com.example.xiangmu.bean.ShopResultBean;
import com.example.xiangmu.bean.ShowShoppingBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TJDDActivity extends AppCompatActivity implements IView {

    @BindView(R.id.allordersRecycle)
    RecyclerView recyclerView;
    @BindView(R.id.addAddress)
    Button addAddress;
    @BindView(R.id.allordersPrice)
    TextView allordersPrice;
    @BindView(R.id.allordersCount)
    TextView allordersCount;
    @BindView(R.id.qujiesuan)
    TextView qujiesuan;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.addr)
    TextView addr;
    @BindView(R.id.popup_two)
    LinearLayout popupTwo;
    @BindView(R.id.saddAddress)
    LinearLayout saddAddress;
    private OrderAdapter adapter;
    private ShowShoppingBean list;
    private ShowShoppingBean list2;
    private PersenterImpl persenter;
    private PopupWindow popupWindows;
    private TextView add;
    private RecyclerView recyclePopup;
    private PopupAddrAdapter addrAdapter;
    private List<AddressListBean.ResuleBean> result;
    private AddressListBean.ResuleBean resuleBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjdd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        addrAdapter = new PopupAddrAdapter(this);
        persenter = new PersenterImpl(this);
        persenter.sendGet(Constant.MYADDRESS, AddressListBean.class);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new OrderAdapter(TJDDActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.setShopCarListener(new OrderAdapter.ShopCarListener() {
            @Override
            public void callBack(List<ShowShoppingBean.ResuleBean> mlist) {
                list.getResult().clear();
                list.getResult().addAll(mlist);
                totalMoney();
            }
        });
        popupTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persenter.sendGet(Constant.MYADDRESS, AddressListBean.class);
                popupWindows.showAsDropDown(view);
            }
        });
        View view = View.inflate(TJDDActivity.this, R.layout.popup_addr, null);
        popupWindows = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindows.setFocusable(true);
        popupWindows.setTouchable(true);
        add = view.findViewById(R.id.addAddress);
        recyclePopup = view.findViewById(R.id.recycle);
        popupWindows.setBackgroundDrawable(new BitmapDrawable());
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclePopup.setLayoutManager(manager1);
        recyclePopup.setAdapter(addrAdapter);
        addrAdapter.setAddrClickListener(new PopupAddrAdapter.AddrClickListener() {
            @Override
            public void callBack(int i) {
                resuleBean = result.get(i);
                Map<String,String> map=new HashMap<>();
                map.put("id",resuleBean.getId());
                persenter.sendMessage(Constant.MORENDIZHI,map,RegBean.class);
                popupWindows.dismiss();
            }
        });
        popupClick();
    }

    private void setaddr(AddressListBean.ResuleBean resuleBean) {
        name.setText(resuleBean.getRealName());
        phone.setText(resuleBean.getPhone() + "");
        addr.setText(resuleBean.getAddress());
        saddAddress.setVisibility(View.VISIBLE);
        addAddress.setVisibility(View.INVISIBLE);
    }

    private void popupClick() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindows.dismiss();
                Intent intent = new Intent(TJDDActivity.this, CityAddActivity.class);
                intent.putExtra("name","1");
                startActivity(intent);
                finish();
            }
        });
    }

    private void totalMoney() {
        int money = 0;
        int count = 0;
        for (int i = 0; i < list.getResult().size(); i++) {
            if (list.getResult().get(i).isItem_check()) {
                count += list.getResult().get(i).getCount();
                money += list.getResult().get(i).getPrice() * list.getResult().get(i).getCount();
            }
        }
        allordersPrice.setText("" + money + "");
        allordersCount.setText(count + "");
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEvent(EventBean evBean) {
        if (evBean.getName().equals("list")) {
            list = (ShowShoppingBean) evBean.getO();
            adapter.setList(list.getResult());
            totalMoney();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.addAddress, R.id.qujiesuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addAddress:
                persenter.sendGet(Constant.MYADDRESS, AddressListBean.class);
                popupWindows.showAsDropDown(view);
                break;
            case R.id.qujiesuan:
                Map<String,String> map=new HashMap<>();
                List<ShopFuBean> lists=new ArrayList<>();
                list2=list;
                for (int i = 0; i < list2.getResult().size(); i++) {
                    if (list2.getResult().get(i).isItem_check()){
                        int commodityId = list2.getResult().get(i).getCommodityId();
                        int count = list2.getResult().get(i).getCount();
                        lists.add(new ShopFuBean(commodityId,count));
                        list.getResult().remove(i);
                    }
                }
                String s = new Gson().toJson(lists);
                map.put("orderInfo",s);
                map.put("totalPrice",allordersPrice.getText().toString());
                map.put("addressId",resuleBean.getId());
                persenter.sendMessage(Constant.DINGDAN,map,RegBean.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof AddressListBean) {
            AddressListBean bean = (AddressListBean) data;
            result = bean.getResult();
            for (int i = 0; i < result.size(); i++) {
                String whetherDefault = result.get(i).getWhetherDefault();
                if (Integer.parseInt(whetherDefault)==1){
                    resuleBean = result.get(i);
                    setaddr(resuleBean);
                }
            }
            addrAdapter.setList(result);
        }
        if (data instanceof RegBean){
            RegBean regBean= (RegBean) data;
            Toast.makeText(TJDDActivity.this,regBean.getMessage(),Toast.LENGTH_SHORT).show();
            if (regBean.getStatus().equals("0000")){
                setaddr(resuleBean);
            }
            if (regBean.getMessage().equals("创建订单成功")){
                adapter.setList(list.getResult());
//                totalMoney();
            }
        }

    }
}
