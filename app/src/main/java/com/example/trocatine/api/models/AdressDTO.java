package com.example.trocatine.api.models;

public class AdressDTO {
    private String street;
    private Integer number;
    private String city;
    private String state;
    private String complement;
    private String cep;

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

    public AdressDTO(String street, Integer number, String city, String state, String complement, String cep) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.complement = complement;
        this.cep = cep;
    }

    public AdressDTO() {
    }

    @Override
    public String toString() {
        return "AdressDTO{" +
                "street='" + street + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", complement='" + complement + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }
}
