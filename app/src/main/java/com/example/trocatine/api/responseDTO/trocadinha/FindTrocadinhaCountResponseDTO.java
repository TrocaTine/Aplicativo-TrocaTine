package com.example.trocatine.api.responseDTO.trocadinha;

public class FindTrocadinhaCountResponseDTO {
    private String email;
    private int countTrocadinha;

    public FindTrocadinhaCountResponseDTO(String email, int countTrocadinha) {
        this.email = email;
        this.countTrocadinha = countTrocadinha;
    }

    public FindTrocadinhaCountResponseDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCountTrocadinha() {
        return countTrocadinha;
    }

    public void setCountTrocadinha(int countTrocadinha) {
        this.countTrocadinha = countTrocadinha;
    }
}
