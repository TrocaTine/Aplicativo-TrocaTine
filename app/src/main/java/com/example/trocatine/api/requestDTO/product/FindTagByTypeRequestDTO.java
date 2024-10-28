package com.example.trocatine.api.requestDTO.product;

public class FindTagByTypeRequestDTO {
    private String type;

    public FindTagByTypeRequestDTO(String type) {
        this.type = type;
    }

    public FindTagByTypeRequestDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
