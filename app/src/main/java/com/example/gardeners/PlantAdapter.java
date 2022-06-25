package com.example.gardeners;

import android.provider.ContactsContract;
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

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.CustomViewHolder> {

    private ArrayList<PlantData> arrayList;
    private final OnPlantListener mOnPlantListener;
    private FragmentManager manager;



    public void setFilteredList(ArrayList<PlantData> filteredList){
        this.arrayList= filteredList;
        notifyDataSetChanged();

    }

    public PlantAdapter(ArrayList<PlantData> arrayList,OnPlantListener onPlantListener, FragmentManager manager) {
        this.arrayList = arrayList;
        this.mOnPlantListener=onPlantListener;
        this.manager = manager;
    }


    @NonNull
    @Override
    public PlantAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        return new CustomViewHolder(view,mOnPlantListener, this.manager);
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
        protected ImageView iv_plant;
        protected TextView tv_plant_name,tv_flowerlan,tv_content_detail,tv_rasing_detail;
        OnPlantListener onPlantListener;
        private final FragmentManager manager;

        public CustomViewHolder(@NonNull View itemView,OnPlantListener onPlantListener, FragmentManager manager) {
            super(itemView);
            this.iv_profile=(ImageView) itemView.findViewById(R.id.iv_profile);
            this.tv_name=(TextView) itemView.findViewById(R.id.tv_name);
            this.tv_content=(TextView) itemView.findViewById(R.id.tv_content);
            this.manager = manager;
            this.onPlantListener=onPlantListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //onPlantListener.onPlantClick(getAbsoluteAdapterPosition());
            Fragment plantDetailFragment=new PlantDetailFragment(1);
            manager.beginTransaction().replace(R.id.containers, plantDetailFragment).commit();

        }
    }
    public interface OnPlantListener{
        void onPlantClick(int position);
    }
}
