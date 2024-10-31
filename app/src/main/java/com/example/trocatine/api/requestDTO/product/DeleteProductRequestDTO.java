package com.example.trocatine.api.requestDTO.product;

public class DeleteProductRequestDTO {
    private Long idProduct;

    public DeleteProductRequestDTO(Long idProduct) {
        this.idProduct = idProduct;
    }

    public DeleteProductRequestDTO() {
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }
}
