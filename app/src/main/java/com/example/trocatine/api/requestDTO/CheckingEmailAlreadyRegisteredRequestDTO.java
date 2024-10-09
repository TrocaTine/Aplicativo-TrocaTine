package com.example.trocatine.api.requestDTO;

public class CheckingEmailAlreadyRegisteredRequestDTO {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CheckingEmailAlreadyRegisteredRequestDTO(String email) {
        this.email = email;
    }

    public CheckingEmailAlreadyRegisteredRequestDTO() {
    }
}
