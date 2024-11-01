package com.example.trocatine.adapter.RecycleViewModels;

import com.google.gson.annotations.Expose;

public class Product {
    private long id;
    private int idUser;
    private String name;
    private String description;
    private double value;
    private long stock;
    private String createdAt;
    private boolean flagTrade;
    @Expose(serialize = false, deserialize = false)

    private String imageUrl;

    public Product(int id, int idUser, String name, String description, double value, long stock, String createdAt, boolean flagTrade) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.description = description;
        this.value = value;
        this.stock = stock;
        this.createdAt = createdAt;
        this.flagTrade = flagTrade;
    }

    public Product() {
    }

    public long getid() {
        return id;
    }

    public long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getValue() {
        return value;
    }

    public long getStock() {
        return stock;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean getFlagTrade() {
        return flagTrade;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public void setFlagTrade(boolean flagTrade) {
        this.flagTrade = flagTrade;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", stock=" + stock +
                ", createdAt='" + createdAt + '\'' +
                ", flagTroca=" + flagTrade +
                '}';
    }
}
