package com.example.gardeners;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantFragment#} factory method to
 * create an instance of this fragment.
 */
public class PlantFragment extends Fragment {
    private ArrayList<AreaData> arrayList=new ArrayList<>();
    private AreaAdapter.OnAreaListener onAreaListener;
    private AreaAdapter areaAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public PlantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plant, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_plant1);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.scrollToPosition(0);

        areaAdapter = new AreaAdapter(arrayList, onAreaListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(areaAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }

    private void initDataset() {
        //for Test
        ArrayList<AreaData> areaData = new ArrayList<>();
        areaData.add(new AreaData(R.drawable.plant_1, "구역A-이름"));
        areaData.add(new AreaData(R.drawable.plant_1, "구역B-이름"));
        areaData.add(new AreaData(R.drawable.plant_1, "구역C-이름"));
        areaData.add(new AreaData(R.drawable.plant_1, "구역D-이름"));
        areaData.add(new AreaData(R.drawable.plant_1, "구역E-이름"));
    }
}