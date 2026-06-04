package com.example.fitguru.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DayVH> {
    private ArrayList<String> days;
    private OnItemClick listener;

    public interface OnItemClick {
        void onClick(int position);
    }

    public DaysAdapter(ArrayList<String> days, OnItemClick listener) {
        this.days = days;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new DayVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayVH holder, int position) {
        String day = days.get(position);
        holder.text.setText(day);

        holder.itemView.setOnClickListener(v -> listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    static class DayVH extends RecyclerView.ViewHolder {
        TextView text;

        public DayVH(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(android.R.id.text1);
        }
    }
}
