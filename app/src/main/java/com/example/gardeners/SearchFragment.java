package com.example.gardeners;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<PlantData> arrayList=new ArrayList<>();
    private PlantAdapter.OnPlantListener onPlantListener;
    private PlantAdapter plantAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        plantAdapter = new PlantAdapter(arrayList, onPlantListener);
        recyclerView.setAdapter(plantAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }

    private void initDataset() {
        //for Test
        ArrayList<PlantData> plantData = new ArrayList<>();
        plantData.add(new PlantData(R.drawable.plant_1, "가락지나물", "장미목 장미과에 속하는 관속식물이다. 낮은 지대의 습기 많은 곳에 자라는 여러해살이풀이다. 줄기는 땅 위로 뻗으며, 길이 20~60cm다. (더보기)"));
        plantData.add(new PlantData(R.drawable.plant_1, "가락지나물", "장미목 장미과에 속하는 관속식물이다. 낮은 지대의 습기 많은 곳에 자라는 여러해살이풀이다. 줄기는 땅 위로 뻗으며, 길이 20~60cm다. (더보기)"));
        plantData.add(new PlantData(R.drawable.plant_1, "가락지나물", "장미목 장미과에 속하는 관속식물이다. 낮은 지대의 습기 많은 곳에 자라는 여러해살이풀이다. 줄기는 땅 위로 뻗으며, 길이 20~60cm다. (더보기)"));
        plantData.add(new PlantData(R.drawable.plant_1, "가락지나물", "장미목 장미과에 속하는 관속식물이다. 낮은 지대의 습기 많은 곳에 자라는 여러해살이풀이다. 줄기는 땅 위로 뻗으며, 길이 20~60cm다. (더보기)"));
        plantData.add(new PlantData(R.drawable.plant_1, "가락지나물", "장미목 장미과에 속하는 관속식물이다. 낮은 지대의 습기 많은 곳에 자라는 여러해살이풀이다. 줄기는 땅 위로 뻗으며, 길이 20~60cm다. (더보기)"));
    }
}