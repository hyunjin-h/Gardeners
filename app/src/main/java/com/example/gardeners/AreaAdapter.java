package com.example.gardeners;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.CustomViewHolder> {
    private int id;
    private ArrayList<AreaData> arrayList;
    private final OnAreaListener mOnAreaListener;
    private FragmentManager manager;

    public void setFilteredList(ArrayList<AreaData> filteredList){
        this.arrayList= filteredList;
        notifyDataSetChanged();
    }

    public AreaAdapter(ArrayList<AreaData> arrayList, AreaAdapter.OnAreaListener onAreaListener, FragmentManager manager) {
        this.arrayList = arrayList;
        this.mOnAreaListener=onAreaListener;
        this.manager = manager;
    }

    @NonNull
    @Override
    public AreaAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view,mOnAreaListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AreaAdapter.CustomViewHolder holder, int position) {
        id = arrayList.get(position).getArea_num();
        holder.iv_area.setImageBitmap(arrayList.get(position).getIv_area());
        holder.tv_area.setText(arrayList.get(position).getTv_area());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() { return arrayList.size(); }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView iv_area;
        protected TextView tv_area;
        OnAreaListener onAreaListener;

        public CustomViewHolder(@NonNull View areaView, OnAreaListener onAreaListener) {
            super(areaView);
            this.iv_area = (ImageView) areaView.findViewById(R.id.iv_area);
            this.tv_area = (TextView) areaView.findViewById(R.id.tv_area);
            this.onAreaListener = onAreaListener;
            areaView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Fragment diaryFragment = new DiaryFragment(id);
            manager.beginTransaction().replace(R.id.containers, diaryFragment).addToBackStack(null).commit();
        }
    }

    public interface OnAreaListener {
        void onAreaClick(int position);
    }
}

