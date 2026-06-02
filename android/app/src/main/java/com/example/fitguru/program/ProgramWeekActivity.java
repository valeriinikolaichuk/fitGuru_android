package com.example.fitguru.program;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitguru.R;

import java.util.ArrayList;

public class ProgramWeekActivity extends AppCompatActivity {
    private ArrayList<String> days;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_week);

        TextView tvWeekTitle = findViewById(R.id.tvWeekTitle);
        EditText etDay = findViewById(R.id.etDay);
        Button btnAddDay = findViewById(R.id.btnAddDay);
        ListView listDays = findViewById(R.id.listDays);

        String weekName = getIntent().getStringExtra("weekName");
        tvWeekTitle.setText(weekName);

        days = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                days
        );

        listDays.setAdapter(adapter);

        btnAddDay.setOnClickListener(v -> {
            String day = etDay.getText().toString().trim();

            if (!day.isEmpty()) {
                days.add(day);
                adapter.notifyDataSetChanged();
                etDay.setText("");
            }
        });

        listDays.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent =
                    new Intent(this, ProgramDayActivity.class);

            intent.putExtra("dayName", days.get(position));

            startActivity(intent);
        });
    }
}
