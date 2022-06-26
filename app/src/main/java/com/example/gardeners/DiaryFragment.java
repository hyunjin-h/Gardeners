package com.example.gardeners;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DiaryFragment extends Fragment {
    private int id;

    public DiaryFragment(int id) {
        this.id = id;
        initDataset();
        initGardenData();

    }

    private ImageView imageView;
    private TextView plantName, period, humidity, section;
    private Bitmap image;
    private ArrayList<DiaryData> arrayList = new ArrayList<>();
    private DiaryAdapter.OnDiaryListener onDiaryListener;
    private DiaryAdapter diaryAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private View plantInfo;
    private ImageButton back;
    private ProgressBar progressBar;


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

        imageView = (ImageView) view.findViewById(R.id.vlog_image);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);

        plantName = (TextView) view.findViewById(R.id.vlog_plant_name);
        humidity = (TextView) view.findViewById(R.id.plant_humidity);
        period=(TextView)view.findViewById(R.id.vlog_period);
        section=(TextView)view.findViewById(R.id.textView2);
        plantInfo=(View)view.findViewById(R.id.plant_info);



        back = (ImageButton) view.findViewById(R.id.imageButton2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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


    private void initGardenData() {
        Thread thread = new Thread(new Runnable() {
            JSONObject object;

            @Override
            public void run() {

                try {
                    progressBar.setVisibility(View.VISIBLE);

                    String page = "http://www.smart-gardening.kro.kr:8000/api/v1/gardens/" + id + "/";
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
                            object = new JSONObject(sb.toString());
                            URL urlConnection = new URL(object.get("image").toString());
                            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            Bitmap myBitmap = BitmapFactory.decodeStream(input);
                            myBitmap = myBitmap.createScaledBitmap(myBitmap, 100, 100, true);
                            image = myBitmap;
                        }
                        // 연결 끊기
                        conn.disconnect();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                imageView.setImageBitmap(image);
                                section.setText(object.get("section").toString() + "구역");
//                                period.setText(object.get("period").toString() + "일");
                                plantName.setText(object.get("name").toString());
                                humidity.setText(object.get("humidity").toString() + "%RH");



                            } catch (JSONException e) {
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

    private void initDataset() {
        Thread thread = new Thread(new Runnable() {
            JSONArray array;

            @Override
            public void run() {
                try {

                    String page = "http://www.smart-gardening.kro.kr:8000/api/v1/gardens/" + id + "/diary/";
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
                            array = new JSONArray(sb.toString());
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                URL urlConnection = new URL(object.get("image").toString());
                                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                                connection.setDoInput(true);
                                connection.connect();
                                InputStream input = connection.getInputStream();
                                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                                myBitmap = myBitmap.createScaledBitmap(myBitmap, 200, 200, true);
                                arrayList.add(new DiaryData(myBitmap, object.get("created_at").toString().substring(0, 10), object.get("title").toString(), object.get("description").toString()));
                            }
                            Log.d("array", String.valueOf(arrayList));
                        }
                        // 연결 끊기
                        conn.disconnect();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                diaryAdapter.setFilteredList(arrayList);
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