package com.example.trocatine.api.requestDTO.product;

public class SaveInformactionCardRequestDTO {
    private String email;
    private String cardNumber;
    private String expirationDate;
    private int cvv;

    public SaveInformactionCardRequestDTO(String email, String cardNumber, String expirationDate, int cvv) {
        this.email = email;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public SaveInformactionCardRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
}
