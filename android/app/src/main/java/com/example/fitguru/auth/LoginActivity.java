package com.example.fitguru.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.auth.dto.LoginRequest;
import com.example.fitguru.auth.dto.LoginResponse;
import com.example.fitguru.auth.network.ApiService;
import com.example.fitguru.main.MainActivity;
import com.example.fitguru.R;
import com.example.fitguru.auth.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etPhone, etPassword;
    Button btnLogin;

    ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        api = RetrofitClient.getInstance().create(ApiService.class);

        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {

            String phone = etPhone.getText().toString();
            String password = etPassword.getText().toString();

            LoginRequest request = new LoginRequest(phone, password);

            api.login(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if (response.isSuccessful()) {

                        String token = response.body().token;

                        getSharedPreferences("app", MODE_PRIVATE)
                                .edit()
                                .putString("token", token)
                                .apply();

                        Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}