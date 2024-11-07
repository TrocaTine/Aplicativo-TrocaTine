package com.example.trocatine.adapter.RecycleViewModels;

import java.math.BigDecimal;

public class CartProduct {
    private String title;
    private BigDecimal value;
    private int qualit;
    private long idProduct;

    public CartProduct(String title, BigDecimal value, int qualit, long idProduct) {
        this.title = title;
        this.value = value;
        this.qualit = qualit;
        this.idProduct = idProduct;
    }

    public CartProduct() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getQualit() {
        return qualit;
    }

    public void setQualit(int qualit) {
        this.qualit = qualit;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }
}
