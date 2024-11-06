package com.example.trocatine.adapter.RecycleViewModels;

import android.icu.text.CaseMap;

public class Notification {
    private String title;
    private String description;

    public Notification(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Notification() {
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
}
