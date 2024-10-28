package com.example.trocatine.api.requestDTO.user;

public class FindPersonalInformationRequestDTO {
    private String email;

    public FindPersonalInformationRequestDTO(String email) {
        this.email = email;
    }

    public FindPersonalInformationRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
