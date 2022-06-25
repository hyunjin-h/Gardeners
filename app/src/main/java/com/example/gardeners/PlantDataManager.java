package com.example.gardeners;

import java.util.ArrayList;
import java.util.List;

public class PlantDataManager {
    private ArrayList<PlantData> arrayList =new ArrayList<>();
    private static final PlantDataManager instance = new PlantDataManager();

    private PlantDataManager() {}

    public static PlantDataManager getInstance() {
        return instance;
    }
    public ArrayList<PlantData> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<PlantData> arrayList) {
        this.arrayList = arrayList;
    }
}
