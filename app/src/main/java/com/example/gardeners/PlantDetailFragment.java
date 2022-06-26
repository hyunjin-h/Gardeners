package com.example.gardeners;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class PlantDetailFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int id;
    private Bitmap image;
    private ImageButton back;
    private ImageView iv_plant;
    private TextView tv_plant_name,tv_flowerlan,tv_content_detail,tv_rasing_detail;
    private View plant_detail;
    private ProgressBar progressBar;

    public PlantDetailFragment(int id) {
        this.id = id;
        initDataset();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_plant_detail, container, false);
        iv_plant=(ImageView) view.findViewById(R.id.iv_plant);
        back = (ImageButton) view.findViewById(R.id.imageButton2);
        tv_plant_name=(TextView) view.findViewById(R.id.tv_plant_name);
        tv_content_detail=(TextView)view.findViewById(R.id.tv_content_detail);
        tv_rasing_detail=(TextView)view.findViewById(R.id.tv_rasing_detail);
        tv_flowerlan=(TextView)view.findViewById(R.id.tv_flowerlan);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        plant_detail=(View)view.findViewById(R.id.plant_detail);
        iv_plant.setImageResource(R.drawable.plant_1);
        plant_detail.setVisibility(View.INVISIBLE);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragment searchFragment = new SearchFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.containers, searchFragment).commit();
            }
        });
        return view;
    }
    private void initDataset(){
        Thread thread = new Thread(new Runnable() {
            JSONObject object;
            @Override
            public void run() {
                try {
                    Log.d("detail fragment", String.valueOf(id));
                    String page = "http://www.smart-gardening.kro.kr:8000/api/v1/flowers/" + id + "/";
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

                            // 응답 Json 타입일 경우
                            object = new JSONObject(sb.toString());
                            URL urlConnection = new URL(object.get("main_image").toString());
                            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            image = BitmapFactory.decodeStream(input);
                            image = image.createScaledBitmap(image,200,200,true);
                            Log.d("detail fragment", String.valueOf(object));
                        }
                        // 연결 끊기
                        conn.disconnect();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                iv_plant.setImageBitmap(image);
                                tv_plant_name.setText(object.get("name").toString());
                                tv_flowerlan.setText(object.get("flower_language").toString());
                                tv_content_detail.setText(object.get("content").toString());
                                tv_rasing_detail.setText(object.get("growth").toString());
                                plant_detail.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }
            }
        });
        thread.start();
    }
}