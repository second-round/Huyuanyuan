package com.example.kaoone.userbean;

import java.util.List;

public class TwoBean {
    private List<UserBean> data;

    public List<UserBean> getData() {
        return data;
    }

    public void setData(List<UserBean> data) {
        this.data = data;
    }

    public class UserBean{
        private String icon;
        private String name;
        private String cid;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public UserBean(String icon, String name, String cid) {
            this.icon = icon;
            this.name = name;
            this.cid = cid;

        }

    }


}
