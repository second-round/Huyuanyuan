package com.example.xiangmu.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiangmu.CityListActivity;
import com.example.xiangmu.MainFootActivity;
import com.example.xiangmu.MyGroupActivity;
import com.example.xiangmu.MyInfosActivity;
import com.example.xiangmu.MyWalletActivity;
import com.example.xiangmu.R;
import com.example.xiangmu.bean.EventBean;
import com.example.xiangmu.bean.LoginBean;
import com.example.xiangmu.bean.UserInfoBean;
import com.example.xiangmu.persenter.PersenterImpl;
import com.example.xiangmu.util.Constant;
import com.example.xiangmu.view.IView;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//圆角， 圆形
//        fresco实现
//glide实现
//
//需要自己实现圆角，继承自BitmapTransformation操作bitmap对象实现(圆形同理)：

//Fresco缓存也是一大亮点， 三级缓存，分别是 Bitmap缓存，未解码图片缓存， 文件缓存

//加载图片速度-在不考虑缓存的情况下， Fresco也比Glide快很多====Fresco需要3s左右， Glide需要5s左右







public class FragmentMine extends Fragment implements IView {
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.personaldata)
    TextView personaldata;
    @BindView(R.id.mycircle)
    TextView mycircle;
    @BindView(R.id.footprint)
    TextView footprint;
    @BindView(R.id.Wallet)
    TextView Wallet;
    @BindView(R.id.shippingaddress)
    TextView shippingaddress;
    @BindView(R.id.mine_icon)
    ImageView mineIcon;
    Unbinder unbinder;
    private LoginBean loginBean;
    private PersenterImpl persenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        persenter=new PersenterImpl(this);
        persenter.sendGet(Constant.GETUSERBYID,UserInfoBean.class);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.nickname, R.id.personaldata, R.id.mycircle, R.id.footprint, R.id.Wallet, R.id.shippingaddress, R.id.mine_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nickname:
                break;
            case R.id.personaldata:
                getActivity().startActivity(new Intent(getActivity(),MyInfosActivity.class));
                break;
            case R.id.mycircle:
                Intent intent1=new Intent(getActivity(),MyGroupActivity.class);
                startActivity(intent1);
                break;
            case R.id.footprint:
                startActivity(new Intent(getActivity(),MainFootActivity.class));
                break;
            case R.id.Wallet:
                startActivity(new Intent(getActivity(),MyWalletActivity.class));
                break;
            case R.id.shippingaddress:
                Intent intent5=new Intent(getActivity(),CityListActivity.class);
                startActivity(intent5);
                break;
            case R.id.mine_icon:
                break;
            default:
                break;
        }
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof UserInfoBean){
            UserInfoBean userInfoBean= (UserInfoBean) data;
            String headPic = userInfoBean.getResult().getHeadPic();
            Uri uri=Uri.parse(headPic);
            mineIcon.setImageURI(uri);
            nickname.setText(userInfoBean.getResult().getNickName());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEvent(EventBean evBean) {
        if (evBean.getName().equals("main")){
            loginBean = (LoginBean) evBean.getO();
            Glide.with(getActivity()).load(loginBean.getResult().getHeadPic()).into(mineIcon);
            nickname.setText(loginBean.getResult().getNickName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        persenter.sendGet(Constant.GETUSERBYID,UserInfoBean.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}