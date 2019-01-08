package com.example.xiangmu.bean;

public class EventBean {
    Object o;
    private String name;
    private int num;


    public EventBean( String name,Object o) {
        this.o = o;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
