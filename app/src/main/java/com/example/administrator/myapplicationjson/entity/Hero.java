package com.example.administrator.myapplicationjson.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/12/1.
 */

public class Hero {

    private String name;
    private String des;
    private Drawable Icon;

    public Hero(String name, String des, Drawable icon) {
        this.name = name;
        this.des = des;
       this.Icon = icon;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "des='" + des + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Drawable getIcon() {
        return Icon;
    }

    public void setIcon(Drawable icon) {
        Icon = icon;
    }
}
