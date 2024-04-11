package com.example.to_do;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class PlannerActivity extends AppCompatActivity {

    private EditText editTextTaskName;
    private EditText editTextTaskDescription;
    private TimePicker timePicker;
    private Button buttonAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        timePicker = findViewById(R.id.timePicker);
        buttonAddTask = findViewById(R.id.buttonAddTask);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private void addTask() {
        String taskName = editTextTaskName.getText().toString().trim();
        String taskDescription = editTextTaskDescription.getText().toString().trim();

        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        String timeString = String.format("%02d:%02d", hour, minute); // Formátování času s dvěma číslicemi pro hodiny a minuty

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Přidání úkolu do seznamu naplánovaných úkolů
        String taskDetails = "Úkol: " + taskName + ", Popis: " + taskDescription + ", Čas: " + timeString;
        MainActivity.addTaskToList(taskDetails);

        // Zobrazení zprávy o úspěšném přidání úkolu
        Toast.makeText(this, "Úkol byl přidán.", Toast.LENGTH_SHORT).show();

        // Vytvoření notifikace v daném čase
        createNotification(this, calendar, taskName, taskDescription);

        // Přepnutí zpět na domovskou obrazovku
        finish();
    }

    private void createNotification(Context context, Calendar calendar, String taskName, String taskDescription) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("task_name", taskName);
        intent.putExtra("task_description", taskDescription);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}

