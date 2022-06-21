package com.example.gardeners;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AreaViewActivity extends AppCompatActivity implements AreaAdapter.OnAreaListener {
    private static final String TAG="AreaViewActivity";

    private ArrayList<AreaData> arrayList = new ArrayList<>();
    private AreaAdapter areaAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_plant);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);

        initRecyclerView();

        areaAdapter = new AreaAdapter(arrayList,this, getSupportFragmentManager());
    }

    private void filterList(String text) {
        ArrayList<AreaData> filteredList=new ArrayList<>();
        for(AreaData area:arrayList){
            if(area.getTv_area().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(area);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this,"NO DATA",Toast.LENGTH_SHORT).show();
        }else{
            areaAdapter.setFilteredList(filteredList);
        }
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        areaAdapter=new AreaAdapter(arrayList,this, getSupportFragmentManager());
        recyclerView.setAdapter(areaAdapter);

    }
    @Override
    public void onAreaClick(int position) {
        Log.d(TAG, "onAreaClick: "+position);
    }
}
