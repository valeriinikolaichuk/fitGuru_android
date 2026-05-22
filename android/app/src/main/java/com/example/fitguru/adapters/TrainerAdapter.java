package com.example.fitguru.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fitguru.R;
import com.example.fitguru.trainer.dto.TrainerResponse;

import java.util.List;

public class TrainerAdapter extends ArrayAdapter<TrainerResponse> {


    public interface OnTrainerClickListener {
        void onSendRequest(TrainerResponse trainer);
    }

    private final OnTrainerClickListener listener;

    public TrainerAdapter(Context context,
                          List<TrainerResponse> trainers,
                          OnTrainerClickListener listener) {
        super(context, 0, trainers);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_trainer, parent, false);
        }

        TrainerResponse trainer = getItem(position);

        TextView name = convertView.findViewById(R.id.tvName);
        TextView phone = convertView.findViewById(R.id.tvPhone);
        Button btnAdd = convertView.findViewById(R.id.btnSendRequest);

        name.setText(trainer.getName());
        phone.setText(trainer.getPhone());

        btnAdd.setOnClickListener(v -> listener.onSendRequest(trainer));

        return convertView;
    }
}