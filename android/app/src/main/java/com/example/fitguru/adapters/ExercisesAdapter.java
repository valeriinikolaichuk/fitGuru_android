package com.example.fitguru.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.program.dto.ProgramExerciseCreateRequest;

import java.util.ArrayList;
import java.util.Locale;

public class ExercisesAdapter
        extends RecyclerView.Adapter<ExercisesAdapter.VH> {

    private final ArrayList<ProgramExerciseCreateRequest> exercises;
    private final OnClick listener;

    public interface OnClick {
        void onClick(int position);
    }

    public ExercisesAdapter(
            ArrayList<ProgramExerciseCreateRequest> exercises,
            OnClick listener
    ) {
        this.exercises = exercises;
        this.listener = listener;
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

        ProgramExerciseCreateRequest item =
                exercises.get(position);

        holder.tvExercise.setText(
                (position + 1) + ". " + item.exerciseName
        );

        holder.tvInfo.setText(
                String.format(
                        Locale.getDefault(),
                        "%.0f x %d x %d",
                        item.weight,
                        item.sets,
                        item.reps
                )
        );

        holder.itemView.setOnClickListener(
                v -> listener.onClick(position)
        );
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvExercise;
        TextView tvInfo;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvExercise = itemView.findViewById(R.id.tvExercise);
            tvInfo = itemView.findViewById(R.id.tvInfo);
        }
    }
}
