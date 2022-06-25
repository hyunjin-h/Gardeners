package com.example.gardeners;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.CustomViewHolder> {

    private ArrayList<DiaryData> arrayList;
    private final OnDiaryListener mOnDiaryListener;

    public void setFilteredList(ArrayList<DiaryData> filteredList){
        this.arrayList= filteredList;
        notifyDataSetChanged();

    }

    public DiaryAdapter(ArrayList<DiaryData> arrayList, OnDiaryListener onDiaryListener) {
        this.arrayList = arrayList;
        Log.d("array", String.valueOf(arrayList));
        this.mOnDiaryListener=onDiaryListener;
    }


    @NonNull
    @Override
    public DiaryAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_list,parent,false);

        CustomViewHolder holder =new CustomViewHolder(view,mOnDiaryListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.iv_diary_plant.setImageBitmap(arrayList.get(position).getIv_diary_plant());
        holder.diary_day.setText(arrayList.get(position).getDiary_day());
        holder.diary_name.setText(arrayList.get(position).getDiary_name());
        holder.diary_content.setText(arrayList.get(position).getDiary_content());

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageView iv_diary_plant;
        protected TextView diary_day;
        protected TextView diary_name;
        protected TextView diary_content;
        OnDiaryListener onDiaryListener;

        public CustomViewHolder(@NonNull View itemView,OnDiaryListener onDiaryListener) {
            super(itemView);
            this.iv_diary_plant=(ImageView) itemView.findViewById(R.id.iv_diary_plant);
            this.diary_day=(TextView) itemView.findViewById(R.id.diary_day);
            this.diary_name =(TextView) itemView.findViewById(R.id.diary_name);
            this.diary_content=(TextView) itemView.findViewById(R.id.diary_content);
            this.onDiaryListener=onDiaryListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
//            onDiaryListener.onDiaryClick(getAbsoluteAdapterPosition());
        }
    }
    public interface OnDiaryListener{
        void onDiaryClick(int position);
    }
}
