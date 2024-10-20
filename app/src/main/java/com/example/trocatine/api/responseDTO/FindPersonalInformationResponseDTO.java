package com.example.trocatine.api.responseDTO;

import com.example.trocatine.api.models.AdressDTO;

import java.time.LocalDate;
import java.util.Set;

public class FindPersonalInformationResponseDTO {
    private Set<String> phone;
    private String cpf;
    private String birthDate;
    private Set<AdressDTO> addresses;
    private String fullName;
    private String nickname;
    private String email;

    public Set<String> getPhone() {
        return phone;
    }

    public void setPhone(Set<String> phone) {
        this.phone = phone;
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

    public Set<AdressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<AdressDTO> addresses) {
        this.addresses = addresses;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FindPersonalInformationResponseDTO(Set<String> phone, String cpf, String birthDate, Set<AdressDTO> addresses, String fullName, String nickname, String email) {
        this.phone = phone;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.addresses = addresses;
        this.fullName = fullName;
        this.nickname = nickname;
        this.email = email;
    }

    public FindPersonalInformationResponseDTO() {
    }
}
