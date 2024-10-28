package com.example.trocatine.api.requestDTO.user;

import java.time.LocalDate;

public class EditPersonalInformationRequestDTO {
    private String email;
    private String newEmail;
    private String number;
    private String nickname;
    private String firstName;
    private String lastName;
    private String cpf;
    private String birthDate;

    public EditPersonalInformationRequestDTO(String email, String newEmail, String number, String nickname, String firstName, String lastName, String cpf, String birthDate) {
        this.email = email;
        this.newEmail = newEmail;
        this.number = number;
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public EditPersonalInformationRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}
