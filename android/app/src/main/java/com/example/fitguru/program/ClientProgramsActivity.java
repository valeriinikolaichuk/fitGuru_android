package com.example.fitguru.program;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;

public class ClientProgramsActivity extends AppCompatActivity {

    Long clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_programs);

        clientId = getIntent().getLongExtra("clientId", -1);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnCreate = findViewById(R.id.btnCreateProgram);

        btnBack.setOnClickListener(v -> finish());

        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(
                    this,
                    CreateProgramActivity.class
            );
            intent.putExtra("clientId", clientId);
            startActivity(intent);
        });

        TextView tvClientName = findViewById(R.id.tvClientName);
        String clientName = getIntent().getStringExtra("clientName");
        tvClientName.setText(clientName);

        loadPrograms(clientId);
    }

    private void loadPrograms(Long clientId) {
        // поки заглушка
        System.out.println("Load programs for client: " + clientId);
    }
}
