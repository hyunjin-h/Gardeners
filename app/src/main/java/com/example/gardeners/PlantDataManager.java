package com.example.gardeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantDataManager {
    Map<String, ArrayList<PlantData>> map = new HashMap<>();
    private static final PlantDataManager instance = new PlantDataManager();

    private PlantDataManager() {
        map.put("", new ArrayList<>());
    }

    public static PlantDataManager getInstance() {
        return instance;
    }

    public ArrayList<PlantData> getMap(String key) {
        return map.get(key);
    }

    public void addMap(String key, ArrayList<PlantData> arrayList) {
        this.map.put(key, arrayList);
    }
}
