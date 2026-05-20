package com.example.fitguru.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.auth.dto.LoginResponse;
import com.example.fitguru.main.MainActivity;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.R;
import com.example.fitguru.auth.dto.RegisterRequest;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.storage.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etPhone, etPassword;

    RadioGroup rgRole;
    Button btnRegister;
    SessionManager sessionManager;
    ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionManager =
                new SessionManager(this);

        api = RetrofitClient
                .getInstance(sessionManager)
                .create(ApiService.class);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        rgRole = findViewById(R.id.rgRole);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String role = "CLIENT";

            if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                        RegisterActivity.this,
                        "Fill all fields",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            int selectedId = rgRole.getCheckedRadioButtonId();

            if (selectedId == R.id.rbTrainer) {
                role = "TRAINER";
            }

            RegisterRequest request = new RegisterRequest(name, phone, password, role);

            api.register(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if (response.isSuccessful()) {

                        String token = response.body().getAccessToken();

                        SessionManager sessionManager =
                                new SessionManager(RegisterActivity.this);

                        sessionManager.saveAccessToken(token);
                        sessionManager.saveRole(response.body().getRole());

                        startActivity(
                                new Intent(
                                        RegisterActivity.this,
                                        MainActivity.class
                                )
                        );
                        finish();

                    } else {
                        Toast.makeText(
                                RegisterActivity.this,
                                "Server error: " + response.code(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this,
                            "Network error: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });

        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());
    }
}