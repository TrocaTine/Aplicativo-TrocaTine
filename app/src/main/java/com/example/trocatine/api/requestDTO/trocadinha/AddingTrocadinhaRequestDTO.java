package com.example.trocatine.api.requestDTO.trocadinha;

public class AddingTrocadinhaRequestDTO {
    private String email;
    private int amountTrocadinha;

    public AddingTrocadinhaRequestDTO(String email, int amountTrocadinha) {
        this.email = email;
        this.amountTrocadinha = amountTrocadinha;
    }

    public AddingTrocadinhaRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAmountTrocadinha() {
        return amountTrocadinha;
    }

    public void setAmountTrocadinha(int amountTrocadinha) {
        this.amountTrocadinha = amountTrocadinha;
    }
}
