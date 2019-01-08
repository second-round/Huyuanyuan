package com.example.xiangmu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiangmu.bean.EventBean;
import com.example.xiangmu.bean.GoodsBean;
import com.example.xiangmu.view.CustomJiaJian;
import com.example.xiangmu.view.MyAlert;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInfoActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
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

                        EventBean mDialog = new EventBean("myDialog", goodsBean);
                        String s = count.getText().toString();
                        mDialog.setNum(Integer.parseInt(s));
                        EventBus.getDefault().postSticky(myDialog);
                        myDialog.dismiss();
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

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}