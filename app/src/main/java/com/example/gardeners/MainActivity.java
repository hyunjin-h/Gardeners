package com.example.gardeners;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
}