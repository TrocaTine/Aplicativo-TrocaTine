package com.example.trocatine.api.models;

import com.example.trocatine.adapter.RecycleViewModels.Product;

import java.time.LocalDate;

public class Users {
    private Long idUser;

    private String firstName;

    private String lastName;

    private String email;

    private String cpf;

    private LocalDate birthDate;

    private Boolean admin;

    private String nickname;

    private String password;

    private Product product;
    private Phones phones;

    public Users(Long idUser, String firstName, String lastName, String email, String cpf, LocalDate birthDate, Boolean admin, String nickname, String password, Product product, Phones phones) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.admin = admin;
        this.nickname = nickname;
        this.password = password;
        this.product = product;
        this.phones = phones;
    }

    public Users() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Phones getPhones() {
        return phones;
    }

    public void setPhones(Phones phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Users{" +
                "idUser=" + idUser +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", birthDate=" + birthDate +
                ", admin=" + admin +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", product=" + product +
                ", phones=" + phones +
                '}';
    }
}
