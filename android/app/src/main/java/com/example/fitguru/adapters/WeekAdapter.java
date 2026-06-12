package com.example.fitguru.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.program.ProgramWeekActivity;
import com.example.fitguru.program.model.WeekItem;

import java.util.List;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.WeekViewHolder> {

    public interface OnWeekClick {
        void onClick(WeekItem week);
    }

    private final Context context;
    private final List<WeekItem> weeks;
    private final OnWeekClick listener;

    public WeekAdapter(
            Context context,
            List<WeekItem> weeks,
            OnWeekClick listener
    ) {
        this.context = context;
        this.weeks = weeks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_week, parent, false);

        return new WeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder holder, int position) {

        WeekItem week = weeks.get(position);

        holder.tvWeek.setText(week.getTitle());

        holder.tvWeek.setOnClickListener(v -> {

            if (week.getId() == null) {

                Toast.makeText(
                        context,
                        "Week not saved yet",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            listener.onClick(week);
        });
    }

    @Override
    public int getItemCount() {
        return weeks.size();
    }

    public static class WeekViewHolder extends RecyclerView.ViewHolder {

        TextView tvWeek;

        public WeekViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWeek = itemView.findViewById(R.id.tvWeek);
        }
    }
}