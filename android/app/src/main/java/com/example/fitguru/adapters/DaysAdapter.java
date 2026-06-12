package com.example.fitguru.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.program.model.DayItem;

import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DayViewHolder> {

    public interface OnItemClick {
        void onClick(DayItem item);
    }

    private final Context context;
    private final List<DayItem> days;
    private final OnItemClick listener;

    public DaysAdapter(
            Context context,
            List<DayItem> days,
            OnItemClick listener
    ) {
        this.context = context;
        this.days = days;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {

        DayItem item = days.get(position);

        holder.tvDay.setText(item.getDay().name());

        holder.itemView.setOnClickListener(v ->
                listener.onClick(item)
        );
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay;
        TextView arrow;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            arrow = itemView.findViewById(R.id.tvArrow);
        }
    }
}