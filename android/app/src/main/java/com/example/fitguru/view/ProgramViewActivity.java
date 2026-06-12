package com.example.fitguru.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.adapters.WeekAdapter;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.CreateProgramActivity;
import com.example.fitguru.program.dto.ProgramResponse;
import com.example.fitguru.program.dto.ProgramWeekResponse;
import com.example.fitguru.program.model.WeekItem;
import com.example.fitguru.repository.ProgramCreateRepository;
import com.example.fitguru.storage.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramViewActivity extends AppCompatActivity {

    private Long programId;

    private TextView tvProgramTitle;
    private TextView tvStatus;

    private Button btnBack;
    private Button btnEdit;
    private String mode;
    private Button btnChangeStatus;

    private RecyclerView rvWeeks;

    private ArrayList<WeekItem> weeks;
    private WeekAdapter adapter;

    private SessionManager sessionManager;
    private ProgramCreateRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_view);

        programId = getIntent().getLongExtra("programId", -1);

        tvProgramTitle = findViewById(R.id.tvProgramTitle);
        tvStatus = findViewById(R.id.tvStatus);

        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        btnChangeStatus = findViewById(R.id.btnChangeStatus);

        mode = getIntent().getStringExtra("mode");

        if ("CLIENT".equals(mode)) {
            btnEdit.setVisibility(View.GONE);
        }

        rvWeeks = findViewById(R.id.rvWeeks);

        sessionManager = new SessionManager(this);

        repository = new ProgramCreateRepository(
                RetrofitClient.getInstance(sessionManager)
                        .create(ApiService.class)
        );

        weeks = new ArrayList<>();

        adapter = new WeekAdapter(
                this,
                weeks,
                week -> {

                    Intent intent = new Intent(
                            ProgramViewActivity.this,
                            ProgramWeekViewActivity.class
                    );

                    intent.putExtra("weekId", week.getId());
                    intent.putExtra("weekName", week.getTitle());

                    startActivity(intent);
                }
        );

        rvWeeks.setLayoutManager(new LinearLayoutManager(this));

        rvWeeks.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        btnEdit.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProgramViewActivity.this,
                            CreateProgramActivity.class
                    );

            intent.putExtra("programId", programId);

            startActivity(intent);
        });

        btnChangeStatus.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "TODO Change Status",
                    Toast.LENGTH_SHORT
            ).show();
        });

        loadProgram();
        loadWeeks();
    }

    private void loadProgram() {

        repository.getProgram(
                programId,
                new Callback<ProgramResponse>() {

                    @Override
                    public void onResponse(
                            Call<ProgramResponse> call,
                            Response<ProgramResponse> response
                    ) {

                        if (!response.isSuccessful()
                                || response.body() == null) {
                            return;
                        }

                        ProgramResponse program = response.body();

                        tvProgramTitle.setText(program.getTitle());

                        tvStatus.setText(
                                "Status: " + program.getStatus()
                        );
                    }

                    @Override
                    public void onFailure(
                            Call<ProgramResponse> call,
                            Throwable t
                    ) {
                    }
                }
        );
    }

    private void loadWeeks() {

        repository.getWeeksByProgram(
                programId,
                new Callback<List<ProgramWeekResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<ProgramWeekResponse>> call,
                            Response<List<ProgramWeekResponse>> response
                    ) {

                        if (!response.isSuccessful()
                                || response.body() == null) {
                            return;
                        }

                        weeks.clear();

                        for (ProgramWeekResponse week : response.body()) {

                            weeks.add(
                                    new WeekItem(
                                            week.getId(),
                                            week.getTitle(),
                                            week.getPosition()
                                    )
                            );
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(
                            Call<List<ProgramWeekResponse>> call,
                            Throwable t
                    ) {
                    }
                }
        );
    }
}
