package com.example.fitguru.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.program.dto.ProgramExerciseResponse;

import java.util.List;
import java.util.Locale;

public class ExerciseViewAdapter
        extends RecyclerView.Adapter<ExerciseViewAdapter.VH> {

    private final List<ProgramExerciseResponse> exercises;

    public ExerciseViewAdapter(
            List<ProgramExerciseResponse> exercises
    ) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_program_exercise,
                        parent,
                        false
                );

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull VH holder,
            int position
    ) {

        ProgramExerciseResponse item =
                exercises.get(position);

        holder.tvExercise.setText(
                (position + 1) + ". " + item.getExerciseName()
        );

        holder.tvDetails.setText(
                String.format(
                        Locale.getDefault(),
                        "%.0f x %d x %d",
                        item.weight,
                        item.sets,
                        item.reps
                )
        );
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        TextView tvExercise;
        TextView tvDetails;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvExercise = itemView.findViewById(R.id.tvExercise);
            tvDetails = itemView.findViewById(R.id.tvInfo);
        }
    }
}

