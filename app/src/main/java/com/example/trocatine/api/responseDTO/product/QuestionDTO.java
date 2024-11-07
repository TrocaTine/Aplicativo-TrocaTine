package com.example.trocatine.api.responseDTO.product;

public class QuestionDTO {
    private String id;
    private String email_user;
    private String id_product;
    private String id_user;
    private String message;

    public QuestionDTO(String id, String id_product, String id_user, String message) {
        this.id = id;
        this.id_product = id_product;
        this.id_user = id_user;
        this.message = message;
    }

    public QuestionDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email_user;
    }

    public void setEmail(String email_user) {
        this.email_user = email_user;
    }
}
