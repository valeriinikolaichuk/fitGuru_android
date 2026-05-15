package com.example.fitguru.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fitguru.R;
import com.example.fitguru.trainer.dto.ClientResponse;
import com.example.fitguru.trainer.dto.TrainerResponse;

import java.util.List;

public class UserAdapter extends ArrayAdapter<Object> {

    public UserAdapter(Context context, List<Object> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_user, parent, false);
        }

        Object item = getItem(position);

        TextView name = view.findViewById(R.id.tvName);
        TextView phone = view.findViewById(R.id.tvPhone);

        if (item instanceof ClientResponse) {
            ClientResponse c = (ClientResponse) item;
            name.setText(c.name);
            phone.setText(c.phone);
        } else if (item instanceof TrainerResponse) {
            TrainerResponse t = (TrainerResponse) item;
            name.setText(t.name);
            phone.setText(t.phone);
        }

        return view;
    }
}
