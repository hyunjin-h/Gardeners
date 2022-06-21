package com.example.gardeners;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiaryFragment extends Fragment {
    private int id;
    public DiaryFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.plant_main_page, container, false);

//        recyclerView = (RecyclerView) view.findViewById(R.id.my_plant1);
//
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
//
//        recyclerView.scrollToPosition(0);
//
//        areaAdapter = new AreaAdapter(arrayList, onAreaListener);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(areaAdapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}