package com.swc.onestop.MainActivity_Models;

public class Data_model {

    String title;
    String subtitle;
    String desc;
    String image;
    String dp;

    public Data_model(String title, String subtitle, String desc, String dp, String image) {
        this.title = title;
        this.subtitle = subtitle;
        this.desc = desc;
        this.image = image;
        this.dp = dp;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDesc() {
        return desc;
    }

    public String getdp() {
        return dp;
    }

    public String getImage() {
        return image;
    }
}