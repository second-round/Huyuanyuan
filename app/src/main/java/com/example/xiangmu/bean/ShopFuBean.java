package com.example.xiangmu.bean;

public class ShopFuBean {
    int commodityId;
    int amount;

    public ShopFuBean(int commodityId, int count) {
        this.commodityId = commodityId;
        this.amount = count;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return amount;
    }

    public void setCount(int count) {
        this.amount = count;
    }
}
