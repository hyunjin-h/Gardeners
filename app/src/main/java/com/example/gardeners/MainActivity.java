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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Menu menu;
    HomeFragment homeFragment;
    PlantFragment plantFragment;
    SearchFragment searchFragment;
    private static final int CAPTURE_IMAGE_REQUEST = 1;
    private CameraSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        homeFragment = new HomeFragment(this);
        plantFragment = new PlantFragment();
        searchFragment = new SearchFragment();
        surfaceView = findViewById(R.id.surfaceView);

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


}