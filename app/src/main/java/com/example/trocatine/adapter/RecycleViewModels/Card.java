package com.example.trocatine.adapter.RecycleViewModels;

import java.time.LocalDate;

public class Card {
    private long idCard;
    private long idUser;
    private String cardNumber;
    private LocalDate expirationDate;
    private int cvv;
    private String fullName;

    public Card(long idCard, long idUser, String cardNumber, LocalDate expirationDate, int cvv, String fullName) {
        this.idCard = idCard;
        this.idUser = idUser;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.fullName = fullName;
    }

    public Card() {
    }

    public long getIdCard() {
        return idCard;
    }

    public void setIdCard(long idCard) {
        this.idCard = idCard;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
