package com.example.trocatine.api.requestDTO;

public class FindTrocadinhaCountRequestDTO {
    private String email;

    public FindTrocadinhaCountRequestDTO(String email) {
        this.email = email;
    }

    public FindTrocadinhaCountRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
