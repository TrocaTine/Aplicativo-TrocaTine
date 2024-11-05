package com.example.trocatine.api.requestDTO.product;

public class SaveQuestionsProductRequestDTO {
    private String email;
    private String password;
    private Long idProduct;
    private String message;

    public SaveQuestionsProductRequestDTO(String email, String password, Long idProduct, String message) {
        this.email = email;
        this.password = password;
        this.idProduct = idProduct;
        this.message = message;
    }

    public SaveQuestionsProductRequestDTO() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
