package com.example.xiangmu.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xiangmu.R;
import com.example.xiangmu.adapter.MyShoppingAdapter;
import com.example.xiangmu.bean.ShowShoppingBean;

import java.util.ArrayList;
import java.util.List;

public class CustomJiaJian extends RelativeLayout implements View.OnClickListener{
    private Context context;
    private EditText editText;
    private List<ShowShoppingBean.ResuleBean> listBeans=new ArrayList<>();
    private int position;
    private MyShoppingAdapter shoppingAdapter;

    public CustomJiaJian(Context context) {
        super(context);
        init(context);
    }

    public CustomJiaJian(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomJiaJian(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.context=context;
        View view=View.inflate(context,R.layout.custom_jiajian,null);
        Button jia=view.findViewById(R.id.add);
        Button jian=view.findViewById(R.id.reverse);
        editText = view.findViewById(R.id.count);
        addView(view);
    }
    private  int num;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reverse:
                num++;
                editText.setText(num+"");
                break;
            case R.id.add:
                if (num>1){
                    num--;
                }else {
                    tosat("数量不能小于1");
                }
                editText.setText(num+"");
                break;
            default:break;
        }

    }
    private void tosat(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}