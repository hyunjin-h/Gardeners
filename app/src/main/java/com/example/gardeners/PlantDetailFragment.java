package com.example.gardeners;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlantDetailFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int id;
    private ImageButton back;
    private ImageView iv_plant;
    private TextView tv_plant_name,tv_flowerlan,tv_content_detail,tv_rasing_detail;
    private ArrayList<PlantDetailData> arrayList=new ArrayList<>();
    public PlantDetailFragment(int id) {
        this.id = id;

    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlantDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlantDetailFragment newInstance(String param1, String param2) {
        PlantDetailFragment fragment = new PlantDetailFragment(1);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        iv_plant.setImageResource(R.drawable.plant_1);
        tv_plant_name.setText("빈공간");
        tv_content_detail.setText("빈공간");
        tv_rasing_detail.setText("빈공간");
        tv_flowerlan.setText("빈공간");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragment searchFragment = new SearchFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.containers, searchFragment).commit();
            }
        });
        return view;
    }
//    private void initDataset(){
//        arrayList.add(new PlantDetailData(R.drawable.plant_1,"2","2","2","3"));
//    }
}