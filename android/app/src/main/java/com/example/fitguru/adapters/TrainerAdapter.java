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
import com.example.fitguru.client.dto.AvailableTrainerResponse;

import java.util.List;

public class TrainerAdapter extends ArrayAdapter<AvailableTrainerResponse> {


    public interface OnTrainerClickListener {
        void onSendRequest(AvailableTrainerResponse trainer, int position);
        void onCancelRequest(AvailableTrainerResponse trainer, int position);
    }

    private final OnTrainerClickListener listener;

    public TrainerAdapter(Context context,
                          List<AvailableTrainerResponse> trainers,
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

        AvailableTrainerResponse trainer = getItem(position);

        TextView name = convertView.findViewById(R.id.tvName);
        TextView phone = convertView.findViewById(R.id.tvPhone);
        Button btnAdd = convertView.findViewById(R.id.btnSendRequest);

        name.setText(trainer.getName());
        phone.setText(trainer.getPhone());

        String status = trainer.getRequestStatus();

        if (status == null || status.equals("NONE")) {

            btnAdd.setText("Send Request");

            btnAdd.setEnabled(true);

            btnAdd.setOnClickListener(v ->
                    listener.onSendRequest(trainer, position));

        } else if (status.equals("PENDING")) {

            btnAdd.setText("Cancel Request");

            btnAdd.setEnabled(true);

            btnAdd.setOnClickListener(v ->
                    listener.onCancelRequest(trainer, position));

        } else if (status.equals("ACCEPTED")) {

            btnAdd.setText("Connected");
            btnAdd.setEnabled(false);
            btnAdd.setOnClickListener(null);
        }

        return convertView;
    }
}