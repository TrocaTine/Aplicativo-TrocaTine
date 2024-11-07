package com.example.trocatine.api.requestDTO.cart;

public class AddProductShoppingCartResquestDTO {
    private long idProduct;
    private int quantity;
    private String email;

    public AddProductShoppingCartResquestDTO(long idProduct, int quantity, String email) {
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.email = email;
    }

    public AddProductShoppingCartResquestDTO() {
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
