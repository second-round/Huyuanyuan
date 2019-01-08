package com.example.xiangmu.app;

import android.support.v4.app.Fragment;

public class Addevent {
    private Fragment addFragment;

    public Addevent(Fragment addFragment) {
        this.addFragment = addFragment;
    }

    public Fragment getAddFragment() {
        return addFragment;
    }
}
