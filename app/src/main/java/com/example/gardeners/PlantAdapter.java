package com.example.gardeners;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.CustomViewHolder> {

    private ArrayList<PlantData> arrayList;
    private final OnPlantListener mOnPlantListener;

    public void setFilteredList(ArrayList<PlantData> filteredList){
        this.arrayList= filteredList;
        notifyDataSetChanged();

    }

    public PlantAdapter(ArrayList<PlantData> arrayList,OnPlantListener onPlantListener) {
        this.arrayList = arrayList;
        this.mOnPlantListener=onPlantListener;
    }


    @NonNull
    @Override
    public PlantAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        return new CustomViewHolder(view, mOnPlantListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.CustomViewHolder holder, int position) {
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_content.setText(arrayList.get(position).getTv_content());

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageView iv_profile;
        protected TextView tv_name;
        protected TextView tv_content;
        OnPlantListener onPlantListener;

        public CustomViewHolder(@NonNull View itemView,OnPlantListener onPlantListener) {
            super(itemView);
            this.iv_profile=(ImageView) itemView.findViewById(R.id.iv_profile);
            this.tv_name=(TextView) itemView.findViewById(R.id.tv_name);
            this.tv_content=(TextView) itemView.findViewById(R.id.tv_content);
            this.onPlantListener=onPlantListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onPlantListener.onPlantClick(getAdapterPosition());
        }
    }
    public interface OnPlantListener{
        void onPlantClick(int position);
    }
}
