package com.example.gardeners;

import android.graphics.Bitmap;

public class PlantData {
    private int id;
    private Bitmap iv_profile;
    private String tv_name;
    private String tv_content;



    public PlantData(int id, Bitmap iv_profile, String tv_name, String tv_content) {
        this.id = id;
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
        this.tv_content = tv_content;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(Bitmap iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_content() {
        return tv_content;
    }

    public void setTv_content(String tv_content) {
        this.tv_content = tv_content;
    }



}
