package com.example.xiangmu;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.bean.RegBean;
import com.example.xiangmu.bean.UserInfoBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInfosActivity extends AppCompatActivity implements IView {

    @BindView(R.id.myIcon)
    SimpleDraweeView myIcon;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.changeName)
    EditText changeName;
    @BindView(R.id.changePhone)
    EditText changePhone;
    private PersenterImpl persenter;
    private UserInfoBean bean;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_infos);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        edit = sharedPreferences.edit();
        persenter = new PersenterImpl(this);
        persenter.sendGet(Constant.GETUSERBYID, UserInfoBean.class);
        changeName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容
                    changeName.setVisibility(View.GONE);
                    name.setVisibility(View.VISIBLE);
                    name.setText(changeName.getText().toString());
                    Map<String,String> map=new HashMap<>();
                    map.put("nickName",changeName.getText().toString());
                    persenter.onPutStartRequest(Constant.UPDATENICEHNG,map,RegBean.class);
                }
            }
        });
        changePhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容
                    changePhone.setVisibility(View.GONE);
                    phone.setVisibility(View.VISIBLE);
                    phone.setText(changePhone.getText().toString());
                    Map<String,String> map=new HashMap<>();
                    map.put("oldPwd",sharedPreferences.getString("password",null));
                    map.put("newPwd",changePhone.getText().toString());
                    persenter.onPutStartRequest(Constant.MIMA,map,RegBean.class);
                }
            }
        });
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

    @Override
    public void requesta(Object data) {
        if (data instanceof UserInfoBean) {
            bean = (UserInfoBean) data;
            String headPic = bean.getResult().getHeadPic();
            Uri uri = Uri.parse(headPic);
            myIcon.setImageURI(uri);
            name.setText(bean.getResult().getNickName());
            phone.setText(sharedPreferences.getString("password",null));
        }
        if (data instanceof RegBean){
            RegBean regBean= (RegBean) data;
            if (regBean.getStatus().equals("0000")){
                edit.putString("password",phone.getText().toString());
            }
            Toast.makeText(MyInfosActivity.this,regBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.name, R.id.phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.name:
                name.setVisibility(View.GONE);
                changeName.setVisibility(View.VISIBLE);
                changeName.setText(name.getText().toString());
                break;
            case R.id.phone:
                phone.setVisibility(View.GONE);
                changePhone.setVisibility(View.VISIBLE);
                changePhone.setText(phone.getText().toString());
                break;
            default:
                break;
        }
    }
}
