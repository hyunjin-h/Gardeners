package com.example.gardeners;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PlantDataManager {
    private static final Map<String, ArrayList<PlantData>> map = new HashMap<String, ArrayList<PlantData>>(){{ put("", new ArrayList<>()); }};
    private static final PlantDataManager instance = new PlantDataManager();

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
