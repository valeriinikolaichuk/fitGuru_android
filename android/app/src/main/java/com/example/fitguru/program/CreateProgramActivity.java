package com.example.fitguru.program;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitguru.R;
import com.example.fitguru.adapters.WeekAdapter;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.program.dto.ProgramResponse;
import com.example.fitguru.program.dto.ProgramUpdateRequest;
import com.example.fitguru.program.dto.ProgramWeekCreateRequest;
import com.example.fitguru.program.dto.ProgramWeekResponse;
import com.example.fitguru.program.model.WeekItem;
import com.example.fitguru.repository.ProgramCreateRepository;
import com.example.fitguru.storage.SessionManager;

import android.util.Log;
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
    private EditText etProgramName;

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

        etProgramName = findViewById(R.id.etProgramName);
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

//        @Override
//        protected void onResume() {
//            super.onResume();
//            loadPrograms(clientId);
//        }

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

            Log.d("PROGRAM", "Weeks count = " + weeks.size());

            String programName =
                    etProgramName.getText().toString().trim();

            Log.d("PROGRAM", "Program name = " + programName);
            Log.d("PROGRAM", "Weeks count = " + weeks.size());

            ProgramUpdateRequest updateRequest =
                    new ProgramUpdateRequest();

            updateRequest.title = programName;

            repository.updateProgram(
                    programId,
                    updateRequest,
                    new Callback<ProgramResponse>() {

                        @Override
                        public void onResponse(
                                Call<ProgramResponse> call,
                                Response<ProgramResponse> response
                        ) {

                            Log.d("PROGRAM", "Program updated");
                        }

                        @Override
                        public void onFailure(
                                Call<ProgramResponse> call,
                                Throwable t
                        ) {

                            Log.e("PROGRAM", "Program update error", t);
                        }
                    }
            );

            for (WeekItem item : weeks) {

                if (item.getId() != null) {
                    continue;
                }

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

                                if (response.isSuccessful()
                                        && response.body() != null) {

                                    item.setId(response.body().getId());

                                    Log.d(
                                            "PROGRAM",
                                            "Week saved id = " + item.getId()
                                    );
                                }
                            }

                            @Override
                            public void onFailure(
                                    Call<ProgramWeekResponse> call,
                                    Throwable t
                            ) {
                                Log.e("PROGRAM", "Week error", t);
                            }
                        }
                );
            }
        });
    }
}

