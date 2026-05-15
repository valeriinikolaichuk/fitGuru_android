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
import com.example.fitguru.main.dto.TrainingRequestResponse;
import com.example.fitguru.repository.UserRepository;
import com.example.fitguru.storage.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdapter extends ArrayAdapter<TrainingRequestResponse> {

    UserRepository repository;
    SessionManager sessionManager;

    public RequestAdapter(
            Context context,
            List<TrainingRequestResponse> items
    ) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(
            int position,
            View convertView,
            @NonNull ViewGroup parent
    ) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_request, parent, false);
        }

        TrainingRequestResponse request = getItem(position);

        TextView tvName =
                convertView.findViewById(R.id.tvName);

        TextView tvPhone =
                convertView.findViewById(R.id.tvPhone);

        Button btnAccept =
                convertView.findViewById(R.id.btnAccept);

        Button btnReject =
                convertView.findViewById(R.id.btnReject);

        tvName.setText(request.clientName);
        tvPhone.setText(request.clientPhone);

        btnAccept.setOnClickListener(v -> {

            repository.acceptRequest(
                    "Bearer " + sessionManager.getToken(),
                    request.id,
                    new Callback<Void>() {

                        @Override
                        public void onResponse(
                                Call<Void> call,
                                Response<Void> response
                        ) {

                            if (response.isSuccessful()) {

                                remove(request);
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<Void> call,
                                Throwable t
                        ) {

                        }
                    }
            );
        });

        btnReject.setOnClickListener(v -> {

            repository.rejectRequest(
                    "Bearer " + sessionManager.getToken(),
                    request.id,
                    new Callback<Void>() {

                        @Override
                        public void onResponse(
                                Call<Void> call,
                                Response<Void> response
                        ) {

                            if (response.isSuccessful()) {

                                remove(request);
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<Void> call,
                                Throwable t
                        ) {

                        }
                    }
            );
        });

        return convertView;
    }
}
