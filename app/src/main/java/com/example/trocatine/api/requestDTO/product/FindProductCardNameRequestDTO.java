package com.example.trocatine.api.requestDTO.product;

public class FindProductCardNameRequestDTO {
    private String name;

    public FindProductCardNameRequestDTO(String name) {
        this.name = name;
    }

    public FindProductCardNameRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
