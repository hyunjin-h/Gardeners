package com.example.gardeners;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;

public class PlantViewActivity extends AppCompatActivity implements PlantAdapter.OnPlantListener {
    private static final String TAG="PlantViewActivity";

    private ArrayList<PlantData> arrayList=new ArrayList<>();
    private PlantAdapter plantAdapter;
    private RecyclerView recyclerView;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_plant);


        searchView=findViewById(R.id.searchView);
        searchView.setQuery("",false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });




        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);

        initRecyclerView();
        insertFakePlants();


        plantAdapter=new PlantAdapter(arrayList,this);
    }

    private void filterList(String text) {
        ArrayList<PlantData> filteredList=new ArrayList<>();
        for(PlantData plant:arrayList){
            if(plant.getTv_name().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(plant);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this,"NO DATA",Toast.LENGTH_SHORT).show();
        }else{
            plantAdapter.setFilteredList(filteredList);
        }
    }

    private void insertFakePlants(){
        for(int i=0;i<10;i++){
            PlantData plant=new PlantData(R.drawable.plant_1,(i+10)+"번",i+"번 내용");
            arrayList.add(plant);
        }
        plantAdapter.notifyDataSetChanged();
    }
    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        plantAdapter=new PlantAdapter(arrayList,this);
        recyclerView.setAdapter(plantAdapter);

    }
    @Override
    public void onPlantClick(int position) {
        Log.d(TAG, "onPlantClick: "+position);

    }
}