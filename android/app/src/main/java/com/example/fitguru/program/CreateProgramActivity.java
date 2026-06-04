package com.example.fitguru.program;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.adapters.WeekAdapter;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.dto.ProgramWeekCreateRequest;
import com.example.fitguru.program.dto.ProgramWeekResponse;
import com.example.fitguru.program.model.WeekItem;
import com.example.fitguru.repository.ProgramCreateRepository;
import com.example.fitguru.storage.SessionManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProgramActivity extends AppCompatActivity {

    private Button btnBack;
    private Button btnAddWeek;
    private Button btnSave;

    private EditText etWeek;
    private RecyclerView rvWeeks;
    private ArrayList<WeekItem> weeks;

    private Long programId;

    private WeekAdapter adapter;

    private SessionManager sessionManager;
    private ProgramCreateRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_program);

        btnBack = findViewById(R.id.btnBack);
        btnAddWeek = findViewById(R.id.btnAddWeek);
        btnSave = findViewById(R.id.btnSave);

        etWeek = findViewById(R.id.etWeek);
        rvWeeks = findViewById(R.id.rvWeeks);

        weeks = new ArrayList<>();
        adapter = new WeekAdapter(this, weeks);

        programId = getIntent().getLongExtra("programId", -1);

        if (programId == -1) {
            Toast.makeText(
                    this,
                    "Invalid programId",
                    Toast.LENGTH_SHORT
            ).show();
            finish();
            return;
        }

        sessionManager = new SessionManager(this);

        repository = new ProgramCreateRepository(
                RetrofitClient.getInstance(sessionManager).create(ApiService.class)
        );

        rvWeeks.setLayoutManager(new LinearLayoutManager(this));
        rvWeeks.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        btnAddWeek.setOnClickListener(v -> {

            String weekName =
                    etWeek.getText().toString().trim();

            if (weekName.isEmpty()) {
                etWeek.setError("Enter week name");
                return;
            }

            weeks.add(new WeekItem(null, weekName, weeks.size() + 1));
            adapter.notifyItemInserted(weeks.size() - 1);
            etWeek.setText("");
        });

        btnSave.setOnClickListener(v -> {

            for (WeekItem item : weeks) {

                ProgramWeekCreateRequest request =
                        new ProgramWeekCreateRequest();

                request.programId = programId;
                request.title = item.getTitle();
                request.position = item.getPosition();

                repository.createWeek(
                        request,
                        new Callback<ProgramWeekResponse>() {

                            @Override
                            public void onResponse(
                                    Call<ProgramWeekResponse> call,
                                    Response<ProgramWeekResponse> response
                            ) {

                                if (response.isSuccessful()) {

                                    Toast.makeText(
                                            CreateProgramActivity.this,
                                            "Week saved",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }

                            @Override
                            public void onFailure(
                                    Call<ProgramWeekResponse> call,
                                    Throwable t
                            ) {

                                Toast.makeText(
                                        CreateProgramActivity.this,
                                        t.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
            }
        });
    }
}

