package com.example.trocatine.api.requestDTO.user;

import java.time.LocalDate;

public class CreateUserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String cpf;
    private String birthDate;
    private Boolean admin;
    private String nickname;
    private String password;
    private String street;
    private Integer number;
    private String city;
    private String state;
    private String neighborhood;
    private String complement;
    private String cep;
    private String numberPhone;


    public CreateUserRequestDTO(String firstName, String lastName, String email, String cpf, String birthDate, Boolean admin, String nickname, String password, String street, Integer number, String city, String state, String neighborhood, String complement, String cep, String numberPhone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.admin = admin;
        this.nickname = nickname;
        this.password = password;
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.cep = cep;
        this.numberPhone = numberPhone;
    }
    public CreateUserRequestDTO() {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumberPhon() {
        return numberPhone;
    }

    public void setNumberPhon(String numberPhon) {
        this.numberPhone = numberPhon;
    }

}
