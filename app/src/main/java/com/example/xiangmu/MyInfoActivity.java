package com.example.xiangmu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xiangmu.bean.EventBean;
import com.example.xiangmu.bean.GoodsBean;
import com.example.xiangmu.bean.ShopCarAddBean;
import com.example.xiangmu.bean.ShopResultBean;
import com.example.xiangmu.bean.ShowShoppingBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.CustomJiaJian;
import com.example.xiangmu.view.IView;
import com.example.xiangmu.view.MyCustomView;
import com.example.xiangmu.view.MyAlert;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInfoActivity extends AppCompatActivity implements IView {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.saleNum)
    TextView saleNum;
    @BindView(R.id.commodityName)
    TextView commodityName;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.shopAdd)
    ImageView shopAdd;
    @BindView(R.id.shopBuy)
    ImageView shopBuy;
    private GoodsBean goodsBean;
    private MyAlert myDialog;
    private PersenterImpl persenter;
    private int commodityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        persenter=new PersenterImpl(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEvent(EventBean evBean) {
        if (evBean.getName().equals("goods")){
            goodsBean = (GoodsBean) evBean.getO();
            commodityId = goodsBean.getResult().getCommodityId();
            load();
        }

    }

    @SuppressLint("JavascriptInterface")
    private void load() {
        String details = goodsBean.getResult().getDetails();
        String picture = goodsBean.getResult().getPicture();
        String[] split = picture.split(",");
        List<String> list = Arrays.asList(split);
        webview.loadDataWithBaseURL(null, details, "text/html", "utf-8", null);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(list);
        banner.start();
        price.setText("￥" + goodsBean.getResult().getPrice() + "");
        commodityName.setText(goodsBean.getResult().getCommodityName());
        weight.setText(goodsBean.getResult().getWeight() + "kg");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.shopAdd, R.id.shopBuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopAdd:
                //goodsBean.getResult().getCategoryId();
                View v=getLayoutInflater().inflate(R.layout.alert_goods,null);

                myDialog = new MyAlert(MyInfoActivity.this,0,0,v,R.style.DialogTheme);

                TextView recycle_price = v.findViewById(R.id.recycle_price);
                TextView recycle_title = v.findViewById(R.id.recycle_title);
                Button production=v.findViewById(R.id.production);
                Button cancel=v.findViewById(R.id.cancel);
                final CustomJiaJian customJiaJian = v.findViewById(R.id.customjiajian);
                final EditText count=customJiaJian.findViewById(R.id.count);
                String[] split = goodsBean.getResult().getPicture().split("\\|");
                recycle_title.setText(goodsBean.getResult().getCommodityName());
                recycle_price.setText("￥" + goodsBean.getResult().getPrice()+"");
                production.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String string = count.getText().toString();
                        int i = Integer.parseInt(string);
                        selShopCar();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.setCancelable(true);
                myDialog.show();

                break;
            case R.id.shopBuy:
                break;
            default:
                break;
        }
    }

    private void selShopCar() {
        persenter.sendGet(Constant.QUERYSHOP,ShowShoppingBean.class);
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof ShowShoppingBean){
            ShowShoppingBean carBean= (ShowShoppingBean) data;
            if(carBean.getMessage().equals("查询成功")){
                List<ShopResultBean> list=new ArrayList<>();
                List<ShowShoppingBean.ResuleBean> result = carBean.getResult();
                for(ShowShoppingBean.ResuleBean results:result){
                    list.add(new ShopResultBean(results.getCommodityId(),results.getCount()));
                }
                //把查到的数据 传给添加购物车方法 并判断
                addShopCar(list);
            }
        }
        if (data instanceof ShopCarAddBean){
            ShopCarAddBean addBean= (ShopCarAddBean) data;
            if(addBean.getMessage().equals("同步成功")){
                Toast.makeText(this, "已加入购物车,可点击查看", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //添加购物车
    private void addShopCar(List<ShopResultBean> list){
        String string="[";
        for (int i=0;i<list.size();i++){
            if(Integer.valueOf(commodityId)==list.get(i).getCommodityId()){
                int count = list.get(i).getCount();
                count++;
                list.get(i).setCount(count);
                break;
            }else if(i==list.size()-1){
                list.add(new ShopResultBean(Integer.valueOf(commodityId),1));
                break;
            }
        }
        for (ShopResultBean resultBean:list){
            string+="{\"commodityId\":"+resultBean.getCommodityId()+",\"count\":"+resultBean.getCount()+"},";
        }
        String substring = string.substring(0, string.length() - 1);
        substring+="]";
        Log.i("TAG",substring);
        Map<String,String> map=new HashMap<>();
        map.put("data",substring);
        persenter.onPutStartRequest(Constant.TOBUSHOP,map,ShopCarAddBean.class);
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}