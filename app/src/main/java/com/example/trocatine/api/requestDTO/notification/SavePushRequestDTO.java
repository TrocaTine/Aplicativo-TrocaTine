package com.example.trocatine.api.requestDTO.notification;

import java.util.List;

public class SavePushRequestDTO {
    private String title;

    private String description;

    private List<String> email;

    public SavePushRequestDTO(String title, String description, List<String> email) {
        this.title = title;
        this.description = description;
        this.email = email;
    }

    public SavePushRequestDTO() {
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

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }
}
