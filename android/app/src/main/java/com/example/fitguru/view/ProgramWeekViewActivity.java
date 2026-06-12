package com.example.fitguru.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.adapters.DaysAdapter;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.dto.ProgramDayResponse;
import com.example.fitguru.program.model.DayItem;
import com.example.fitguru.repository.ProgramCreateRepository;
import com.example.fitguru.storage.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramWeekViewActivity extends AppCompatActivity {

    private Long weekId;

    private TextView tvWeekTitle;
    private Button btnBack;

    private RecyclerView rvDays;

    private ArrayList<DayItem> days;
    private DaysAdapter adapter;

    private SessionManager sessionManager;
    private ProgramCreateRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_week_view);

        weekId = getIntent().getLongExtra("weekId", -1);
        String weekName = getIntent().getStringExtra("weekName");

        tvWeekTitle = findViewById(R.id.tvWeekTitle);
        btnBack = findViewById(R.id.btnBack);
        rvDays = findViewById(R.id.rvDays);

        tvWeekTitle.setText(weekName);

        sessionManager = new SessionManager(this);

        repository = new ProgramCreateRepository(
                RetrofitClient.getInstance(sessionManager)
                        .create(ApiService.class)
        );

        days = new ArrayList<>();

        adapter = new DaysAdapter(
                this,
                days,
                item -> {

                    Intent intent =
                            new Intent(
                                    ProgramWeekViewActivity.this,
                                    ProgramDayViewActivity.class
                            );

                    intent.putExtra("dayId", item.getId());
                    intent.putExtra("dayName", item.getDay().name());

                    startActivity(intent);
                }
        );

        rvDays.setLayoutManager(new LinearLayoutManager(this));
        rvDays.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

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

                        if (!response.isSuccessful() || response.body() == null)
                            return;

                        days.clear();

                        for (ProgramDayResponse d : response.body()) {

                            days.add(new DayItem(
                                    d.getId(),
                                    d.getDay(),
                                    d.getPosition()
                            ));
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(
                            Call<List<ProgramDayResponse>> call,
                            Throwable t
                    ) {
                    }
                }
        );
    }
}
