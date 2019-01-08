package com.example.xiangmu.pager;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class PagerTransformer implements ViewPager.PageTransformer {

    public static final float minScale = 0.9f;
    private static final float DEFAULT_MAX_ROTATE = 15f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;
    public static final float DEFAULT_CENTER = 0.4f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setPivotY(page.getHeight()/2);//旋转轴

        if (position < -1) {
//            view.setAlpha(mMinAlpha);
            page.setScaleY(minScale);
            page.setScaleX(minScale);
            page.setRotationY(-1 * mMaxRotate);
            page.setPivotX(page.getWidth());
        } else if (position <= 1) { // [-1,1]
            page.setRotationY(position * mMaxRotate);
            if (position < 0) //[0，-1]
            {
                float factor = minScale + (1 - minScale) * (1 + position);
                page.setScaleY(factor);
                page.setScaleX(factor);
//                page.setPivotX(page.getWidth() * (DEFAULT_CENTER + DEFAULT_CENTER * (-position)));
                page.setPivotX(page.getWidth());
            } else//[1，0]
            {
                float factor = minScale + (1 - minScale) * (1 - position);
                page.setScaleY(factor);
                page.setScaleX(factor);
//                page.setPivotX(page.getWidth() * DEFAULT_CENTER * (1 - position));
                page.setPivotX(0);
            }
        } else { // (1,+Infinity]
            page.setScaleY(minScale);
            page.setScaleX(minScale);
            page.setRotationY(1 * mMaxRotate);
            page.setPivotX(0);
        }

    }
}
