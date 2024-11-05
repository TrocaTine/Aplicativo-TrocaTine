package com.example.trocatine.api.requestDTO.product;

public class FindQuestionsByProductRequestDTO {
    private String email;
    private String password;
    private Long idProduct;

    public FindQuestionsByProductRequestDTO(String email, String password, Long idProduct) {
        this.email = email;
        this.password = password;
        this.idProduct = idProduct;
    }

    public FindQuestionsByProductRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }
}
