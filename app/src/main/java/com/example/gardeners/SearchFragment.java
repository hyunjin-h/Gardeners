package com.example.gardeners;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PlantAdapter.OnPlantListener onPlantListener;
    private PlantAdapter plantAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PlantDataManager manager;
    private SearchView searchView;
    private ProgressBar progressBar;

    public SearchFragment() {
        manager = PlantDataManager.getInstance();
        if (manager.getMap("").size() == 0) {
            initDataset();
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar3);
        if (manager.getMap("").size() != 0) {
            progressBar.setVisibility(View.INVISIBLE);
        }
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        plantAdapter = new PlantAdapter(manager.getMap(""), onPlantListener, getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(plantAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFlowerByWord(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public synchronized void run() {
                            try {
                                if(manager.getMap("").size() != 0) {
                                    plantAdapter.setFilteredList(manager.getMap(""));
                                } else {
                                    progressBar.setVisibility(View.VISIBLE);
                                    initDataset();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private synchronized void searchFlowerByWord(String word) {
        Thread thread = new Thread(new Runnable() {
            JSONArray array;
            final ArrayList<PlantData> arrayList = new ArrayList<>();

            @Override
            public synchronized void run() {
                try {

                    String page = "http://cashup.iptime.org:5050/api/v1/flowers/?word=" + word;
                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    final StringBuilder sb = new StringBuilder();

                    if (conn != null) {
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");

                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
                            JSONObject temp = new JSONObject(sb.toString());
                            array = temp.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                URL urlConnection = new URL(object.get("main_image").toString());
                                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                                connection.setDoInput(true);
                                connection.connect();
                                InputStream input = connection.getInputStream();
                                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                                myBitmap = myBitmap.createScaledBitmap(myBitmap,200,200,true);
                                arrayList.add(new PlantData(Integer.parseInt(object.get("id").toString()), myBitmap, object.get("name").toString(), object.get("content").toString()));
                            }
                            manager.addMap(word, arrayList);

                        }
                        // 연결 끊기
                        conn.disconnect();
                    }
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public synchronized void run() {
                            try {
                                plantAdapter.setFilteredList(manager.getMap(word));
                                progressBar.setVisibility(View.INVISIBLE);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }

            }
        });
        thread.start();
    }

    private synchronized void initDataset() {
        Thread thread = new Thread(new Runnable() {
            JSONArray array;
            final ArrayList<PlantData> arrayList = new ArrayList<>();

            @Override
            public synchronized void run() {
                try {
                    String page = "http://cashup.iptime.org:5050/api/v1/flowers/";
                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    final StringBuilder sb = new StringBuilder();

                    if (conn != null) {
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");

                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
                            JSONObject temp = new JSONObject(sb.toString());
                            array = temp.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                URL urlConnection = new URL(object.get("main_image").toString());
                                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                                connection.setDoInput(true);
                                connection.connect();
                                InputStream input = connection.getInputStream();
                                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                                myBitmap = myBitmap.createScaledBitmap(myBitmap, 200, 200, true);
                                arrayList.add(new PlantData(Integer.parseInt(object.get("id").toString()), myBitmap, object.get("name").toString(), object.get("content").toString()));
                            }
                            manager.addMap("", arrayList);
                    }
                        // 연결 끊기
                        conn.disconnect();

                    }
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public synchronized void run() {
                            try {
                                plantAdapter.setFilteredList(manager.getMap(""));
                                progressBar.setVisibility(View.INVISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    });

                } catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }

            }

        });

        thread.start();
    }

}