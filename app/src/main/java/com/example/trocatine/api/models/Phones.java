package com.example.trocatine.api.models;

public class Phones {

    private Long idPhone;


    private String number;

    private Users user;


    public Phones(String number, Users user){
        this.number = number;
        this.user = user;
    }
}
