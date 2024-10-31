package com.example.trocatine.api.requestDTO.product;

public class FindProductByUserRequestDTO {
    private String email;

    public FindProductByUserRequestDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FindProductByUserRequestDTO() {
    }
}
