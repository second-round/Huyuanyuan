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

public class CustomJiaJian extends RelativeLayout{
    private Context context;
    private EditText editText;
    private int mCount =1;
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
        jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content  = editText.getText().toString().trim();
                Integer count = Integer.valueOf(content);
                if (count>1){
                    mCount=count-1;
                    editText.setText(mCount+"");
                    //回调给adapter里面
                    if(customListener!=null){
                        customListener.jiajian(mCount);
                    }

                }
            }
        });
        jia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content  = editText.getText().toString().trim();
                int count = Integer.valueOf(content)+1;
                mCount = count;
                editText.setText(mCount+"");
                if(customListener!=null){
                    customListener.jiajian(mCount);
                }
            }
        });

        addView(view);
    }
    private void tosat(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
    CustomListener customListener;
    public void setCustomListener(CustomListener customListener){
        this.customListener = customListener;
    }
    //加减的接口
    public interface CustomListener{
        public void jiajian(int count);
        public void shuRuZhi(int count);
    }
    //这个方法是供recyadapter设置 数量时候调用的
    public void setEditText(int num) {
        if(editText !=null) {
            editText.setText(num + "");
        }
    }


}