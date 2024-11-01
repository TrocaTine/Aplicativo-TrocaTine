package com.example.trocatine.api.requestDTO.order;

import java.math.BigDecimal;

public class FinishedOrderRequestDTO {
    private String email;
    private Long idProduct;
    private String numberCard;
    private String paymentType;
    private BigDecimal value;

    public FinishedOrderRequestDTO(String email, Long idProduct, String numberCard, String paymentType, BigDecimal value) {
        this.email = email;
        this.idProduct = idProduct;
        this.numberCard = numberCard;
        this.paymentType = paymentType;
        this.value = value;
    }

    public FinishedOrderRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
