package com.example.xiangmu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.bean.LoginBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.util.SharedPreferencesUtil;
import com.example.xiangmu.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.check_pass)
    CheckBox check_pass;
    @BindView(R.id.reg)
    TextView reg;
    PersenterImpl persenter;
    @BindView(R.id.eyes)
    ImageView eyes;
    SharedPreferences sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
    private SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        edit = sharedPreferences.edit();
        persenter=new PersenterImpl(this);
        phone.setText(SharedPreferencesUtil.getString("phoneNum",null));
        if (SharedPreferencesUtil.getBoolean("loginCheck",false)){
            password.setText(SharedPreferencesUtil.getString("password",null));
            check_pass.setChecked(SharedPreferencesUtil.getBoolean("loginCheck",false));
        }else {
        }
    }

    @OnClick({R.id.btn_login,R.id.check_pass,R.id.reg,R.id.eyes})
    public void check(View view){
        switch (view.getId()){
            case R.id.btn_login:
                if (check_pass.isChecked()){
                    SharedPreferencesUtil.put("phoneNum",phone.getText().toString());
                    SharedPreferencesUtil.put("password",password.getText().toString());
                    SharedPreferencesUtil.put("loginCheck",check_pass.isChecked());
                }else {
                    SharedPreferencesUtil.remove("password");
                    SharedPreferencesUtil.remove("loginCheck");
                }
                Map<String,String> map=new HashMap<>();
                map.put("phone",phone.getText().toString());
                map.put("pwd",password.getText().toString());
                persenter.sendMessage(Constant.LOGIN_PATH,map,LoginBean.class);
                break;
            case R.id.check_pass:
                break;
            case R.id.reg:
                startActivity(new Intent(MainActivity.this,RegActivity.class));
                finish();
                break;
            case R.id.eyes:
            //129是密码不可见(隐藏密码) 128是密码可见(明文密码)
                if (password.getInputType() == 129) {
                    password.setInputType(128);
                } else {
                    password.setInputType(129);
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof LoginBean){
            LoginBean loginBean= (LoginBean) data;
            if (loginBean.getStatus().equals("0000")){
                Toast.makeText(MainActivity.this,loginBean.getMessage(),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        }
    }
}