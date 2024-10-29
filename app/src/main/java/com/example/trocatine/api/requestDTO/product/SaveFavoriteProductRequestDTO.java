package com.example.trocatine.api.requestDTO.product;

public class SaveFavoriteProductRequestDTO {
    private String email;
    private long id;

    public SaveFavoriteProductRequestDTO(String email, long id) {
        this.email = email;
        this.id = id;
    }

    public SaveFavoriteProductRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
