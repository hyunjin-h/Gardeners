package com.example.gardeners;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;
import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements
        Robot.NlpListener,
        OnRobotReadyListener,
        Robot.ConversationViewAttachesListener,
        Robot.WakeupWordListener,
        Robot.ActivityStreamPublishListener,
        Robot.TtsListener,
        OnBeWithMeStatusChangedListener,
        OnGoToLocationStatusChangedListener,
        OnLocationsUpdatedListener {
    Menu menu;
    HomeFragment homeFragment;
    PlantFragment plantFragment;
    SearchFragment searchFragment;
    private static final int CAPTURE_IMAGE_REQUEST = 1;
    private CameraSurfaceView surfaceView;
    private Robot robot;
    final ArrayList<CommandData> arrayList = new ArrayList<>();
    private boolean isWork = false;
    private boolean isWatering = false;
    private boolean isGoToHome = false;
    private CommandData doingCommand;
    private Thread mainThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        robot = Robot.getInstance();
        robot.hideTopBar();
        homeFragment = new HomeFragment(this);
        plantFragment = new PlantFragment();
        searchFragment = new SearchFragment();
        surfaceView = findViewById(R.id.surfaceView);

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                if (arrayList.size() == 0) {
                    getCommandFromServer();
                }
            }
        },0,10000);

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
        NavigationBarView navigationBarView = findViewById(R.id.bottomNavigationView);
        menu = navigationBarView.getMenu();
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menuitem_bottombar_home:
                        item.setIcon(R.drawable.ic_checked_house);
                        menu.findItem(R.id.menuitem_bottombar_plant).setIcon(R.drawable.ic_leaf);
                        menu.findItem(R.id.menuitem_bottombar_search).setIcon(R.drawable.ic_magnifyingglass);
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                        return true;
                    case R.id.menuitem_bottombar_plant:
                        item.setIcon(R.drawable.ic_checked_leaf);
                        menu.findItem(R.id.menuitem_bottombar_home).setIcon(R.drawable.ic_house);
                        menu.findItem(R.id.menuitem_bottombar_search).setIcon(R.drawable.ic_magnifyingglass);
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, plantFragment).commit();
                        return true;
                    case R.id.menuitem_bottombar_search:
                        item.setIcon(R.drawable.ic_checked_magnifyingglass);
                        menu.findItem(R.id.menuitem_bottombar_home).setIcon(R.drawable.ic_house);
                        menu.findItem(R.id.menuitem_bottombar_plant).setIcon(R.drawable.ic_leaf);
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, searchFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private synchronized void getCommandFromServer() {
        Thread thread = new Thread(new Runnable() {
            JSONArray array;
            @Override
            public synchronized void run() {
                try {
                    String page = "http://cashup.iptime.org:5050/api/v1/core/1/command";
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
                                arrayList.add(new CommandData(Integer.parseInt(object.get("id").toString()), Integer.parseInt(object.get("profile_id").toString()), object.get("location").toString(), object.get("command").toString(), object.get("command_kor").toString(), Boolean.getBoolean(object.get("is_done").toString())));
                            }
                            parseCommand();
                        }
                        // 연결 끊기
                        conn.disconnect();
                    }
                } catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }
            }
        });
        thread.start();
    }

    private void parseCommand() {
        if (!isWork && !isWatering && !isGoToHome && arrayList.size() != 0) {
            for(int i = 0; i < arrayList.size(); i++) {
                CommandData command = arrayList.get(i);
                doingCommand = command;
                switch (command.getCommand()) {
                    case "move":
                    case "water":
                    case "home":
                        isWork = true;
                        robot.goTo(command.getLocation());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public Thread finishCommand(CommandData command) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    String page = "http://cashup.iptime.org:5050/api/v1/core/command/" + command.getCommandId() + "/done/";
                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    final StringBuilder sb = new StringBuilder();

                    if (conn != null) {
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("POST");

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
                        }
                        // 연결 끊기
                        conn.disconnect();
                        Thread.sleep(10000);
                    }
                } catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }
            }
        });
        thread.start();
        return thread;
    }

    public void waitForWater(CommandData command) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    Thread.sleep(10000);
                    String page = "http://cashup.iptime.org:5050/api/v1/gardens/1/water/2/";
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
                        }
                        JSONObject object = new JSONObject(sb.toString());
                        if (Integer.parseInt(object.get("is_water").toString()) == 0) {
                            isWatering = false;
                            finishCommand(command);
                        } else {
                            waitForWater(command);
                        }
                        // 연결 끊기
                        conn.disconnect();

                    }
                } catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }
            }
        });
        thread.start();
    }

    public void postTemiReadyCommand(CommandData command) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    String page = "http://cashup.iptime.org:5050/api/v1/core/command/" + command.getCommandId() + "/ready/";
                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    final StringBuilder sb = new StringBuilder();

                    if (conn != null) {
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("POST");

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
                        }
                        // 연결 끊기
                        conn.disconnect();
                        robot.speak(TtsRequest.create(doingCommand.getLocation() + "번 물주기 준비 완료했습니다."));
                        Thread.sleep(10000);
                        waitForWater(command);
                    }
                } catch (Exception e) {
                    Log.i("tag", "error :" + e);
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Robot.getInstance().addOnRobotReadyListener(this);
        Robot.getInstance().addNlpListener(this);
        Robot.getInstance().addOnBeWithMeStatusChangedListener(this);
        Robot.getInstance().addOnGoToLocationStatusChangedListener(this);
        Robot.getInstance().addConversationViewAttachesListenerListener(this);
        Robot.getInstance().addWakeupWordListener(this);
        Robot.getInstance().addTtsListener(this);
        Robot.getInstance().addOnLocationsUpdatedListener(this);
    }

    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            try {
                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                robot.onStart(activityInfo);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void onPublish(@NonNull ActivityStreamPublishMessage activityStreamPublishMessage) {

    }

    @Override
    public void onConversationAttaches(boolean b) {

    }

    @Override
    public void onNlpCompleted(@NonNull NlpResult nlpResult) {

    }

    @Override
    public void onTtsStatusChanged(@NonNull TtsRequest ttsRequest) {

    }

    @Override
    public void onWakeupWord(@NonNull String s, int i) {

    }

    @Override
    public void onBeWithMeStatusChanged(@NonNull String s) {

    }

    @Override
    public void onGoToLocationStatusChanged(@NonNull String s, @NonNull String s1, int i, @NonNull String s2) {
        if (s1.equals("complete")) {
            robot.turnBy(80, 1.2F);
            arrayList.remove(doingCommand);
            if (doingCommand.getCommand().equals("move")) {
                robot.speak(TtsRequest.create(doingCommand.getKorCommand()));
            } else if (doingCommand.getCommand().equals("water")) {
                isWatering = true;
                postTemiReadyCommand(doingCommand);
            } else if (doingCommand.getCommand().equals("home")) {
                robot.speak(TtsRequest.create("정상적으로 홈베이스로 이동하였습니다!"));
            }
            Thread finishThread = finishCommand(doingCommand);
            synchronized (finishThread) {
                isWork = false;
                parseCommand();
            }
        }
    }

    @Override
    public void onLocationsUpdated(@NonNull List<String> list) {
        for(String s: list) {
            Log.d("onLocation", s);
        }
    }
}