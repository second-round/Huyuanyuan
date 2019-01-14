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

public class MyCustomView extends RelativeLayout implements View.OnClickListener{
    private Context context;
    private EditText editText;
    private List<ShowShoppingBean.ResuleBean> listBeans=new ArrayList<>();
    private int position;
    private MyShoppingAdapter shoppingAdapter;

    public MyCustomView(Context context) {
        super(context);
        init(context);
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.context=context;
        View view=View.inflate(context,R.layout.custom_jiajian,null);
        Button jia=view.findViewById(R.id.add);
        Button jian=view.findViewById(R.id.reverse);
        editText = view.findViewById(R.id.count);
        jia.setOnClickListener(this);
        jian.setOnClickListener(this);
        addView(view);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private  int num;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                num++;
                editText.setText(num+"");
                listBeans.get(position).setCount(num);
                callbacklistener.callback(num);
                shoppingAdapter.notifyItemChanged(position);
                break;
            case R.id.reverse:
                if (num>1){
                    num--;
                }else {
                    tosat("数量不能小于1");
                }
                editText.setText(num+"");
                listBeans.get(position).setCount(num);
                callbacklistener.callback(num);
                shoppingAdapter.notifyItemChanged(position);
                break;
            default:break;
        }

    }
    private void tosat(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
    public void setDAta(MyShoppingAdapter shoppingAdapter,List<ShowShoppingBean.ResuleBean> list,int i){
        listBeans=list;
        this.shoppingAdapter=shoppingAdapter;
        position=i;
        num=list.get(i).getCount();
        editText.setText(num+"");
    }
    private Callbacklistener callbacklistener;
    public void setCallbacklistener(Callbacklistener callbacklistener){
        this.callbacklistener=callbacklistener;
    }
    public interface Callbacklistener{
        void callback(int num);
    }
}