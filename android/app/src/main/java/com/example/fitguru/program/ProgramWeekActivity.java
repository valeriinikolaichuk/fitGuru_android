package com.example.fitguru.program;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
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
import com.example.fitguru.program.dto.ProgramDayResponse;
import com.example.fitguru.program.model.DayItem;
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
    private Button btnDeleteWeek;
    private Button btnBack;

    private ArrayList<DayItem> days;

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
        btnDeleteWeek = findViewById(R.id.btnDeleteWeek);
        btnBack = findViewById(R.id.btnBack);

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
                this,
                days,
                item -> {

                    Intent intent = new Intent(
                            ProgramWeekActivity.this,
                            ProgramDayActivity.class
                    );

                    intent.putExtra("dayName", item.getDay().name());
                    intent.putExtra("dayId", item.getId());

                    startActivity(intent);
                }
        );

        rvDays.setLayoutManager(new LinearLayoutManager(this));
        rvDays.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        btnDeleteWeek.setOnClickListener(v -> {

            repository.deleteWeek(
                    weekId,
                    new Callback<Void>() {

                        @Override
                        public void onResponse(
                                Call<Void> call,
                                Response<Void> response
                        ) {

                            if (response.isSuccessful()) {

                                Toast.makeText(
                                        ProgramWeekActivity.this,
                                        "Week deleted",
                                        Toast.LENGTH_SHORT
                                ).show();

                                finish();
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<Void> call,
                                Throwable t
                        ) {
                            Toast.makeText(
                                    ProgramWeekActivity.this,
                                    t.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );
        });

        btnAddDay.setOnClickListener(v -> {

            TrainingDay selectedDay =
                    (TrainingDay) spinnerDays.getSelectedItem();

            boolean exists = false;

            for (DayItem item : days) {
                if (item.getDay() == selectedDay) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                days.add(new DayItem(
                        null,
                        selectedDay,
                        days.size())
                );
                adapter.notifyItemInserted(days.size() - 1);
            }
        });

        sessionManager = new SessionManager(this);
        repository = new ProgramCreateRepository(
                RetrofitClient.getInstance(sessionManager).create(ApiService.class)
        );

        btnSave.setOnClickListener(v -> {

            List<ProgramDayCreateRequest> requests = new ArrayList<>();

            for (DayItem item : days) {

                if (item.getId() != null) {
                    continue;
                }

                ProgramDayCreateRequest req =
                        new ProgramDayCreateRequest();

                req.weekId = weekId;
                req.day = item.getDay();
                req.position = item.getPosition();

                requests.add(req);
            }

            if (requests.isEmpty()) {

                Toast.makeText(
                        ProgramWeekActivity.this,
                        "Nothing to save",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            repository.createDay(
                    requests,
                    new Callback<Void>() {

                        @Override
                        public void onResponse(
                                Call<Void> call,
                                Response<Void> response
                        ) {

                            if (!response.isSuccessful()) {

                                Toast.makeText(
                                        ProgramWeekActivity.this,
                                        "Save failed",
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            Toast.makeText(
                                    ProgramWeekActivity.this,
                                    "Days saved",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadDays();
                        }

                        @Override
                        public void onFailure(
                                Call<Void> call,
                                Throwable t
                        ) {

                            Toast.makeText(
                                    ProgramWeekActivity.this,
                                    t.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadDays();
    }

    private void loadDays() {

        repository.getDaysByWeek(
                weekId,
                new Callback<List<ProgramDayResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<ProgramDayResponse>> call,
                            Response<List<ProgramDayResponse>> response
                    ) {

                        if (!response.isSuccessful()
                                || response.body() == null) {
                            return;
                        }

                        days.clear();

                        for (ProgramDayResponse day : response.body()) {

                            days.add(
                                    new DayItem(
                                            day.getId(),
                                            day.getDay(),
                                            day.getPosition()
                                    )
                            );
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(
                            Call<List<ProgramDayResponse>> call,
                            Throwable t
                    ) {
                        Log.e("PROGRAM", "Load days error", t);
                    }
                }
        );
    }
}
