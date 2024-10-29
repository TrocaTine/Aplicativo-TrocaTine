package com.example.trocatine.api.requestDTO.product;

public class FindProductFavoriteRequestDTO {
    private String email;

    public FindProductFavoriteRequestDTO(String email) {
        this.email = email;
    }

    public FindProductFavoriteRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
