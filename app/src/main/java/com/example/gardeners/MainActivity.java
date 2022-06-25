package com.example.gardeners;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Menu menu;
    HomeFragment homeFragment;
    PlantFragment plantFragment;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        if (!hasPermissions(PERMISSIONS)) {
            //퍼미션 허가 안되어있다면 사용자에게 요청
            requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        }

        homeFragment = new HomeFragment();
        plantFragment = new PlantFragment();
        searchFragment = new SearchFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottomNavigationView);
        menu=navigationBarView.getMenu();
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

    static final int PERMISSIONS_REQUEST_CODE = 1000;
    String[] PERMISSIONS  = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private boolean hasPermissions(String[] permissions) {
        int result;

        for (String perms : permissions){
            result = ContextCompat.checkSelfPermission(this, perms);
            if (result == PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean cameraPermissionAccepted = grantResults[0]
                        == PackageManager.PERMISSION_GRANTED;
                boolean diskPermissionAccepted = grantResults[1]
                        == PackageManager.PERMISSION_GRANTED;

                if (!cameraPermissionAccepted || !diskPermissionAccepted)
                    showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.create().show();
    }
}