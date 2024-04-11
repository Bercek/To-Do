package com.example.to_do;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listViewTasks;
    private Button buttonAddTask;
    private Button buttonChangeTheme;

    private static ArrayList<String> tasks = new ArrayList<>();

    // pridani ukolu do seznamu
    public static void addTaskToList(String task) {
        tasks.add(task);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTasks = findViewById(R.id.listViewTasks);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        buttonChangeTheme = findViewById(R.id.buttonChangeTheme);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listViewTasks.setAdapter(adapter);

        // zobrazeni detailu ukolu
        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String task = tasks.get(position);

                Toast.makeText(MainActivity.this, "Detail úkolu: " + task, Toast.LENGTH_SHORT).show();
            }
        });

        // vymazani ukolu
        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                tasks.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Úkol byl označen jako hotový.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // prepnuti na PlannerActivity pri stisku tlacitka
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlannerActivity.class);
                startActivity(intent);
            }
        });

        // zmena designu aplikace
        buttonChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNightMode = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
                int nightMode = currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_NO ?
                        AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
                AppCompatDelegate.setDefaultNightMode(nightMode);
                recreate();
            }
        });
    }
}

