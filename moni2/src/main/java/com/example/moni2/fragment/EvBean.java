package com.example.moni2.fragment;

class EvBean {
    private int num;
    private Object object;

    public EvBean(int num, Object object) {
        this.num = num;
        this.object = object;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
