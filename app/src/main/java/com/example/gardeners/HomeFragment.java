package com.example.gardeners;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView temperatureText, humidityText, co2Text;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAPTURE_IMAGE_REQUEST = 1;
    private String mCurrentPhotoPath;
    private File photoFile;
    private ImageView imageView;
    private CameraSurfaceView surfaceView;
    Camera cam = null;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AppCompatActivity activity;

    public HomeFragment(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void captureCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void getCoreData() {
        Thread thread = new Thread(new Runnable() {
            JSONObject json;
            @Override
            public void run() {
                try {
                    String page = "http://www.smart-gardening.kro.kr:8000/api/v1/core/1/";
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
                            json = new JSONObject(sb.toString());
                        }
                        // 연결 끊기
                        conn.disconnect();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                temperatureText.setText(json.get("temperature").toString());
                                humidityText.setText(json.get("humidity").toString() + "%");
                                co2Text.setText(json.get("co2").toString() + "ppm");
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        temperatureText = view.findViewById(R.id.temperatures_tv);
        humidityText = view.findViewById(R.id.humidity_tv);
        co2Text = view.findViewById(R.id.co2_tv);
        imageView = view.findViewById(R.id.rect_iv);
        surfaceView = view.findViewById(R.id.surfaceView);
        temperatureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });
        getCoreData();
        return view;
    }

    private void capture() {
        Log.d("image", "click1");
        surfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.d("image", "click2");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                imageView.setImageBitmap(bitmap);
                // 사진을 찍게 되면 미리보기가 중지된다. 다시 미리보기를 시작하려면...
                camera.startPreview();
            }
        });
    }
}