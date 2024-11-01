package com.example.trocatine.api.models.RecycleViewModels;

public class Question {
    private String email;
    private String userName;
    private String text;

    public Question(String userName, String text, String email) {
        this.userName = userName;
        this.text = text;
        this.email = email;
    }

    public Question() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
