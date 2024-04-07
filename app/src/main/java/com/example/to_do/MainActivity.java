package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout tasksLayout;
    private List<Task> tasks;

    private boolean isWhiteTheme = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksLayout = findViewById(R.id.tasks_layout);
        tasks = new ArrayList<>();

        Button addTaskButton = findViewById(R.id.add_task_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlannerActivity.class);
                startActivity(intent);
            }
        });

        Button changeThemeButton = findViewById(R.id.change_theme_button);
        changeThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTasks();
    }

    private void refreshTasks() {
        tasksLayout.removeAllViews();
        for (final Task task : tasks) {
            View taskView = getLayoutInflater().inflate(R.layout.task_item, null);

            TextView taskTextView = taskView.findViewById(R.id.task_text_view);
            taskTextView.setText(task.getTitle() + "\n" + task.getDescription() + "\n" + task.getTime());

            Button doneButton = taskView.findViewById(R.id.done_button);
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setDone(true);
                    refreshTasks();
                }
            });

            Button deleteButton = taskView.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tasks.remove(task);
                    refreshTasks();
                }
            });

            tasksLayout.addView(taskView);
        }
    }

    private void changeTheme() {
        isWhiteTheme = !isWhiteTheme;
        if (isWhiteTheme) {
            tasksLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        } else {
            tasksLayout.setBackgroundColor(getResources().getColor(android.R.color.black));
        }
    }

    // Method to add task from PlannerActivity
    public void addTask(Task task) {
        tasks.add(task);
        refreshTasks();
        scheduleNotification(task);
    }

    private void scheduleNotification(Task task) {
        // Schedule notification here using NotificationCompat and PendingIntent
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Nesplněný úkol")
                .setContentText(task.getTitle() + ": " + task.getDescription())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
