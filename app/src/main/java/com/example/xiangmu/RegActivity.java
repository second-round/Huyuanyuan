package com.example.xiangmu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.bean.RegBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegActivity extends AppCompatActivity implements IView {
    private PersenterImpl persenter;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.getCode)
    TextView getCode;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.eyes)
    ImageView eyes;
    @BindView(R.id.reg)
    TextView reg;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        persenter=new PersenterImpl(this);
    }

    @OnClick({R.id.getCode, R.id.eyes, R.id.reg, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getCode:
                break;
            case R.id.eyes:
                if (password.getInputType() == 129) {
                    password.setInputType(128);
                } else {
                    password.setInputType(129);
                }
                break;
            case R.id.reg:
                startActivity(new Intent(RegActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.btn_login:
                Map<String,String> map=new HashMap<>();
                map.put("phone",phone.getText().toString());
                map.put("pwd",password.getText().toString());
                persenter.sendMessage(Constant.REG_PATH,map,RegBean.class);
                break;
        }
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof RegBean){
            RegBean regBean= (RegBean) data;
            if (regBean.getStatus().equals("0000")){
                startActivity(new Intent(RegActivity.this,MainActivity.class));
                finish();
            }


        }
    }
}
