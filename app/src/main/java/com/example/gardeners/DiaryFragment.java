package com.example.gardeners;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DiaryFragment extends Fragment {
    private int id;
    public DiaryFragment(int id) {
        this.id = id;
        initDataset();
    }
    private ArrayList<DiaryData> arrayList=new ArrayList<>();
    private DiaryAdapter.OnDiaryListener onDiaryListener;
    private DiaryAdapter diaryAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.plant_main_page, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_diary);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.scrollToPosition(0);

        diaryAdapter = new DiaryAdapter(arrayList, onDiaryListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(diaryAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        back=(ImageButton) view.findViewById(R.id.imageButton2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void initDataset() {
        arrayList.add(new DiaryData(R.drawable.plant_1, "2022.02.02", "제목","다이어리 내용"));
        arrayList.add(new DiaryData(R.drawable.plant_1, "2022.02.02", "제목","다이어리 내용"));
        arrayList.add(new DiaryData(R.drawable.plant_1, "2022.02.02", "제목","다이어리 내용"));
        arrayList.add(new DiaryData(R.drawable.plant_1, "2022.02.02", "제목","다이어리 내용"));
        arrayList.add(new DiaryData(R.drawable.plant_1, "2022.02.02", "제목","다이어리 내용"));
    }

}