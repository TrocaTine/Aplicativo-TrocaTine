package com.example.trocatine.adapter.RecycleViewModels;

public class MessageChat {
    private String mobile, name, message;

    public MessageChat(String mobile, String name, String message) {
        this.mobile = mobile;
        this.name = name;
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
