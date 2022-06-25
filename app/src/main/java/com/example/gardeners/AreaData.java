package com.example.gardeners;

import android.graphics.Bitmap;

public class AreaData {
    private int area_num;
    private Bitmap iv_area;
    private String tv_area;


    public AreaData(int area_num, Bitmap iv_area, String tv_area) {
        this.area_num = area_num;
        this.iv_area = iv_area;
        this.tv_area = tv_area;
    }

    public int getArea_num() {
        return area_num;
    }

    public void setArea_num(int area_num) {
        this.area_num = area_num;
    }

    public Bitmap getIv_area() { return iv_area; }

    public void setIv_area(Bitmap iv_area) { this.iv_area = iv_area; }

    public String getTv_area() { return tv_area; }

    public void setTv_area(String tv_area) { this.tv_area = tv_area; }
}
