package com.example.to_do;

public class Task {
    private String title;
    private String description;
    private String time;
    private boolean isDone;

    public Task(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.isDone = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
