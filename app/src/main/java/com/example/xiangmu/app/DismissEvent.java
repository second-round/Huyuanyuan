package com.example.xiangmu.app;

import android.support.v4.app.Fragment;

public class DismissEvent {

    private Fragment DismissFragment;

    public DismissEvent(Fragment dismissFragment) {
        this.DismissFragment = dismissFragment;
    }

    public Fragment getDismissFragment() {
        return DismissFragment;
    }
}
