package com.example.gardeners;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantFragment#} factory method to
 * create an instance of this fragment.
 */
public class PlantFragment extends Fragment {
    private ArrayList<AreaData> arrayList = new ArrayList<>();
    private AreaAdapter.OnAreaListener onAreaListener;
    private AreaAdapter areaAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public PlantFragment() {
        // Required empty public constructor
        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plant, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_plant1);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);

        recyclerView.scrollToPosition(0);

        areaAdapter = new AreaAdapter(arrayList, onAreaListener, getActivity().getSupportFragmentManager());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(areaAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initDataset() {
        Thread thread = new Thread(new Runnable() {
            JSONArray array;
            @Override
            public void run() {
                try {
                    String page = "http://www.smart-gardening.kro.kr:8000/api/v1/gardens/";
                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    final StringBuilder sb = new StringBuilder();

                    if(conn != null) {
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");

                        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            // 결과 값 읽어오는 부분
                            BufferedReader br = new BufferedReader(new InputStreamReader(
                                    conn.getInputStream(), "utf-8"
                            ));
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line);
                            }
                            // 버퍼리더 종료
                            br.close();
                            array = new JSONArray(sb.toString());
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                arrayList.add(new AreaData(R.drawable.plant_1, object.get("name").toString()));
                            }
                        }
                        // 연결 끊기
                        conn.disconnect();
                    }
                }
                catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }
            }
        });
        thread.start();
    }
}