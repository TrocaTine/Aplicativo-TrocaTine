package com.example.trocatine.api.models;

public class TagDTO {
    private String name;
    private String type;

    public TagDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public TagDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
