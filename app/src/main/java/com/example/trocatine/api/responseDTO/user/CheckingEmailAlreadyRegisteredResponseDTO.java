package com.example.trocatine.api.responseDTO.user;

public class CheckingEmailAlreadyRegisteredResponseDTO {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CheckingEmailAlreadyRegisteredResponseDTO(String email) {
        this.email = email;
    }

    public CheckingEmailAlreadyRegisteredResponseDTO() {
    }
}
