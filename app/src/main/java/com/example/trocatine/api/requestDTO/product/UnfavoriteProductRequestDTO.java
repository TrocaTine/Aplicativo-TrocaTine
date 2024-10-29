package com.example.trocatine.api.requestDTO.product;

public class UnfavoriteProductRequestDTO {
    private long id;
    private String email;

    public UnfavoriteProductRequestDTO(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public UnfavoriteProductRequestDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
