package com.example.xiangmu.bean;

import java.util.ArrayList;
import java.util.List;

public class ShowShoppingBean {
    private String status;
    private String message;
    private List<ResuleBean> result=new ArrayList<>();
    private final String SUCCESS_CODE="0000";
    public boolean isSuceess(){
        return status.equals(SUCCESS_CODE);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResuleBean> getResult() {
        return result;
    }

    public void setResult(List<ResuleBean> result) {
        this.result = result;
    }
    public void addResult(ResuleBean result) {
        this.result.add(result);
    }

    public static class ResuleBean{
        private int commodityId;
        private String commodityName;
        private String pic;
        private double price;
        private int count;
        private boolean item_check;

        public boolean isItem_check() {
            return item_check;
        }

        public void setItem_check(boolean item_check) {
            this.item_check = item_check;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
