package com.example.fitguru.program;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateProgramActivity extends AppCompatActivity {

    private ArrayList<String> weeks;
    private ArrayAdapter<String> adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_program);

            Button btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(v -> finish());

            EditText etWeek = findViewById(R.id.etWeek);
            Button btnAddWeek = findViewById(R.id.btnAddWeek);
            ListView listWeeks = findViewById(R.id.listWeeks);

            weeks = new ArrayList<>();

            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    weeks
            );

            listWeeks.setAdapter(adapter);

            btnAddWeek.setOnClickListener(v -> {

                String weekName = etWeek.getText().toString().trim();

                if (weekName.isEmpty()) {
                    etWeek.setError("Enter week name");
                    return;
                }

                weeks.add(weekName);
                adapter.notifyDataSetChanged();

                etWeek.setText("");
            });








            listWeeks.setOnItemClickListener((parent, view, position, id) -> {

                String weekName = weeks.get(position);

                Intent intent =
                        new Intent(this, CreateProgramDayActivity.class);

                intent.putExtra("weekName", weekName);

                startActivity(intent);

            });

        }
}

