package com.example.gardeners;

public class PlantDetailData {
    private int iv_plant;
    private String tv_plant_name;
    private String tv_flowerlan;
    private String tv_content_detail;
    private String tv_rasing_detail;



    public PlantDetailData(int iv_plant, String tv_plant_name, String tv_flowerlan, String tv_content_detail, String tv_rasing_detail) {

        this.iv_plant=iv_plant;
        this.tv_plant_name=tv_plant_name;
        this.tv_flowerlan=tv_flowerlan;
        this.tv_content_detail=tv_content_detail;
        this.tv_rasing_detail=tv_rasing_detail;
    }


    public int getIv_plant() {
        return iv_plant;
    }

    public void setIv_plant(int iv_plant) {
        this.iv_plant = iv_plant;
    }

    public String getTv_plant_name() {
        return tv_plant_name;
    }

    public void setTv_plant_name(String tv_plant_name) {
        this.tv_plant_name = tv_plant_name;
    }

    public String getTv_flowerlan() {
        return tv_flowerlan;
    }

    public void setTv_flowerlan(String tv_flowerlan) {
        this.tv_flowerlan = tv_flowerlan;
    }
    public String getTv_content_detail() {
        return tv_content_detail;
    }

    public void setTv_content_detail(String tv_content_detail) {
        this.tv_content_detail = tv_content_detail;
    }
    public String gettv_rasing_detail() {
        return tv_rasing_detail;
    }

    public void setTtv_rasing_detail(String tv_rasing_detail) {
        this.tv_rasing_detail= tv_rasing_detail;
    }
}
