package com.example.xiangmu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiangmu.MyInfosActivity;
import com.example.xiangmu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentMine extends Fragment {
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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
                break;
            case R.id.footprint:
                break;
            case R.id.Wallet:
                break;
            case R.id.shippingaddress:
                break;
            case R.id.mine_icon:
                break;
            default:
                break;
        }
    }
}