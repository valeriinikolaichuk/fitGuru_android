package com.example.fitguru.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.program.ProgramDayActivity;
import com.example.fitguru.program.model.WeekItem;

import java.util.List;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.WeekViewHolder> {

    private final Context context;
    private final List<WeekItem> weeks;

    public WeekAdapter(Context context, List<WeekItem> weeks) {
        this.context = context;
        this.weeks = weeks;
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_week, parent, false);

        return new WeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull WeekViewHolder holder,
            int position
    ) {

        WeekItem week = weeks.get(position);

        holder.tvWeek.setText(week.getTitle());

        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(context,
                            ProgramDayActivity.class);

            intent.putExtra("weekName", week.getTitle());

            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {

            weeks.remove(holder.getAdapterPosition());

            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return weeks.size();
    }

    static class WeekViewHolder extends RecyclerView.ViewHolder {

        TextView tvWeek;
        Button btnDelete;

        public WeekViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWeek = itemView.findViewById(R.id.tvWeek);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
