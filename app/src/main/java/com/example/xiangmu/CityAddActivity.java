package com.example.xiangmu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.bean.ShopCarAddBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityAddActivity extends AppCompatActivity implements IView {

    @BindView(R.id.adresss)
    TextView adresss;
    @BindView(R.id.youxiao)
    TextView youxiao;
    @BindView(R.id.email_num)
    TextView emailNum;
    @BindView(R.id.sure)
    TextView sure;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.shu)
    EditText shu;
    private CityPicker cityPicker;
    private PersenterImpl persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_add);
        persenter = new PersenterImpl(this);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        shu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容
                    shu.setVisibility(View.GONE);
                    youxiao.setVisibility(View.VISIBLE);
                    youxiao.setText(shu.getText().toString());
                }
            }
        });
    }
    private void initCityPicker() {
        //滚轮文字的大小
        //滚轮文字的颜色
        //省份滚轮是否循环显示
        //城市滚轮是否循环显示
        //地区（县）滚轮是否循环显示
        //滚轮显示的item个数
        //滚轮item间距
        cityPicker = new CityPicker.Builder(CityAddActivity.this)
                //滚轮文字的大小
                .textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#0CB6CA")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("xx省")
                .city("xx市")
                .district("xx区")
                //滚轮文字的颜色
                .textColor(Color.parseColor("#000000"))
                //省份滚轮是否循环显示
                .provinceCyclic(true)
                //城市滚轮是否循环显示
                .cityCyclic(false)
                //地区（县）滚轮是否循环显示
                .districtCyclic(false)
                //滚轮显示的item个数
                .visibleItemsCount(7)
                //滚轮item间距
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                adresss.setText(province + city + district);
//                youxiao.setText(code);
                emailNum.setText(code);
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @OnClick({R.id.adresss, R.id.sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.adresss:
                initCityPicker();
                cityPicker.show();
                break;
            case R.id.youxiao:
                youxiao.setVisibility(View.GONE);
                shu.setVisibility(View.VISIBLE);
                break;
            case R.id.sure:
                Map<String, String> map = new HashMap();
                map.put("realName", name.getText().toString());
                map.put("phone", phone.getText().toString());
                map.put("address", adresss.getText().toString() + " " + youxiao.getText().toString());
                map.put("zipCode", emailNum.getText().toString());
                persenter.sendMessage(Constant.MYADDRESS1, map, ShopCarAddBean.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof ShopCarAddBean) {
            ShopCarAddBean shopCarAddBean = (ShopCarAddBean) data;
            Toast.makeText(this, shopCarAddBean.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, TJDDActivity.class));
            finish();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获取当前焦点所在的控件；
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int) ev.getRawX();
                int rawY = (int) ev.getRawY();
                // 判断点击的点是否落在当前焦点所在的 view 上；
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
