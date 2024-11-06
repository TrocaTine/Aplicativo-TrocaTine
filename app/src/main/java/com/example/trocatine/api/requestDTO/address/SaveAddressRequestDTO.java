package com.example.trocatine.api.requestDTO.address;

import com.example.trocatine.api.models.AdressDTO;

public class SaveAddressRequestDTO {
    private String email;
    private AdressDTO address;

    public SaveAddressRequestDTO(String email, AdressDTO address) {
        this.email = email;
        this.address = address;
    }

    public SaveAddressRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdressDTO getAdressDTO() {
        return address;
    }

    public void setAdressDTO(AdressDTO address) {
        this.address = address;
    }
}
