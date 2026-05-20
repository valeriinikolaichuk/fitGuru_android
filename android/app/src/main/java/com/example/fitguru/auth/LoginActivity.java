package com.example.fitguru.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.auth.dto.LoginRequest;
import com.example.fitguru.auth.dto.LoginResponse;
import com.example.fitguru.network.ApiService;
import com.example.fitguru.main.MainActivity;
import com.example.fitguru.R;
import com.example.fitguru.network.RetrofitClient;
import com.example.fitguru.storage.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etPhone, etPassword;
    Button btnLogin;
    Button btnRegister;
    SessionManager sessionManager;
    ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager =
                new SessionManager(this);

        api = RetrofitClient
                .getInstance(sessionManager)
                .create(ApiService.class);

        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {

            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (phone.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                        LoginActivity.this,
                        "Fill all fields",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            LoginRequest request =
                    new LoginRequest(phone, password);

            api.login(request).enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call,
                                       Response<LoginResponse> response) {

                    if (response.isSuccessful()) {

                        if (response.body() == null ||
                                response.body().getAccessToken() == null) {

                            Toast.makeText(
                                    LoginActivity.this,
                                    "Invalid server response",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        String accessToken =
                                response.body().getAccessToken();

                        String refreshToken =
                                response.body().getRefreshToken();

                        SessionManager sessionManager =
                                new SessionManager(LoginActivity.this);

                        sessionManager.saveAccessToken(accessToken);

                        sessionManager.saveRefreshToken(refreshToken);

                        sessionManager.saveRole(
                                response.body().getRole()
                        );

                        Toast.makeText(
                                LoginActivity.this,
                                "Login success",
                                Toast.LENGTH_SHORT
                        ).show();

                        startActivity(
                                new Intent(
                                        LoginActivity.this,
                                        MainActivity.class
                                )
                        );

                        finish();

                    } else {

                        Toast.makeText(
                                LoginActivity.this,
                                "Wrong credentials",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call,
                                      Throwable t) {

                    Toast.makeText(
                            LoginActivity.this,
                            "Error: " + t.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });

        btnRegister.setOnClickListener(v -> {

            startActivity(new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            ));

        });
    }
}