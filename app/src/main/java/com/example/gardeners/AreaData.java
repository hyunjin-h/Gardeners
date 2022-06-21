package com.example.gardeners;

public class AreaData {
    private int iv_area;
    private String tv_area;


    public AreaData(int iv_area, String tv_area) {
        this.iv_area = iv_area;
        this.tv_area = tv_area;
    }

    public int getIv_area() { return iv_area; }

    public void setIv_area(int iv_area) { this.iv_area = iv_area; }

    public String getTv_area() { return tv_area; }

    public void setTv_area(String tv_area) { this.tv_area = tv_area; }
}
