package com.example.gardeners;

import android.graphics.Bitmap;

public class DiaryData {
    private Bitmap iv_diary_plant;
    private String diary_day;
    private String diary_name;
    private String diary_content;


    public DiaryData(Bitmap iv_diary_plant, String diary_day, String diary_name,String diary_content) {
        this.iv_diary_plant=iv_diary_plant;
        this.diary_day=diary_day;
        this.diary_name=diary_name;
        this.diary_content=diary_content;
    }

    public Bitmap getIv_diary_plant() {
        return iv_diary_plant;
    }

    public void setIv_profile(Bitmap iv_profile) {
        this.iv_diary_plant = iv_diary_plant;
    }

    public String getDiary_day() {
        return diary_day;
    }

    public void setDiary_day(String diary_day) {
        this.diary_day=diary_day;
    }
    public String getDiary_name() {
        return diary_name;
    }

    public void setDiary_name(String diary_name) {
        this.diary_name=diary_name;
    }
    public String getDiary_content() {
        return diary_content;
    }

    public void setDiary_content(String diary_content) {
        this.diary_content=diary_content;
    }


}
