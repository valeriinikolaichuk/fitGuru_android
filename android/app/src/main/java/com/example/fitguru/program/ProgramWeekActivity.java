package com.example.fitguru.program;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.adapters.DaysAdapter;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.dto.ProgramDayCreateRequest;
import com.example.fitguru.program.model.enums.TrainingDay;
import com.example.fitguru.repository.ProgramCreateRepository;
import com.example.fitguru.storage.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramWeekActivity extends AppCompatActivity {

    private DaysAdapter adapter;

    private Spinner spinnerDays;
    private Button btnAddDay;
    private Button btnSave;

    private ArrayList<TrainingDay> days;

    private RecyclerView rvDays;

    private Long weekId;

    private SessionManager sessionManager;
    private ProgramCreateRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_week);

        TextView tvWeekTitle = findViewById(R.id.tvWeekTitle);

        spinnerDays = findViewById(R.id.spinnerDays);
        btnAddDay = findViewById(R.id.btnAddDay);
        btnSave = findViewById(R.id.btnSave);
        rvDays = findViewById(R.id.rvDays);

        days = new ArrayList<>();

        weekId = getIntent().getLongExtra("weekId", -1);
        String weekName = getIntent().getStringExtra("weekName");
        tvWeekTitle.setText(weekName);

        // Spinner
        List<TrainingDay> availableDays = Arrays.asList(TrainingDay.values());

        ArrayAdapter<TrainingDay> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                availableDays
        );

        spinnerDays.setAdapter(spinnerAdapter);

        // RecyclerView adapter
        adapter = new DaysAdapter(
                new ArrayList<>(),
                position -> {

                    TrainingDay day = days.get(position);

                    Intent intent = new Intent(
                            this,
                            ProgramDayActivity.class
                    );
                    intent.putExtra("dayName", day.name());
                    startActivity(intent);
        });

        rvDays.setLayoutManager(new LinearLayoutManager(this));
        rvDays.setAdapter(adapter);

        btnAddDay.setOnClickListener(v -> {

            TrainingDay selectedDay =
                    (TrainingDay) spinnerDays.getSelectedItem();

            if (!days.contains(selectedDay)) {
                days.add(selectedDay);
                adapter.notifyItemInserted(days.size() - 1);
            }
        });

        sessionManager = new SessionManager(this);
        repository = new ProgramCreateRepository(
                RetrofitClient.getInstance(sessionManager).create(ApiService.class)
        );

        btnSave.setOnClickListener(v -> {

            List<ProgramDayCreateRequest> requests = new ArrayList<>();

            for (int i = 0; i < days.size(); i++) {

                ProgramDayCreateRequest req = new ProgramDayCreateRequest();

                req.weekId = weekId;
                req.day = days.get(i);
                req.position = i;

                requests.add(req);
            }

            repository.createDay(requests, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(ProgramWeekActivity.this,
                            "Days saved", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ProgramWeekActivity.this,
                            t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
