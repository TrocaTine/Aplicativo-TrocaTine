package com.example.trocatine.api.requestDTO.product;

import com.example.trocatine.api.models.TagDTO;

import java.math.BigDecimal;
import java.util.List;

public class SaveProductRequestDTO {
    private String emailUser;
    private String name;
    private String description;
    private BigDecimal value;
    private long stock;
    private boolean flagTrade;
    private List<TagDTO> tags;
    private List<String> categories;

    public SaveProductRequestDTO(String emailUser, String name, String description, BigDecimal value, long stock, boolean flagTrade, List<TagDTO> tags, List<String> categories) {
        this.emailUser = emailUser;
        this.name = name;
        this.description = description;
        this.value = value;
        this.stock = stock;
        this.flagTrade = flagTrade;
        this.tags = tags;
        this.categories = categories;
    }

    public SaveProductRequestDTO() {
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public boolean isFlagTrade() {
        return flagTrade;
    }

    public void setFlagTrade(boolean flagTrade) {
        this.flagTrade = flagTrade;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "SaveProductRequestDTO{" +
                "emailUser='" + emailUser + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", stock=" + stock +
                ", flagTrade=" + flagTrade +
                ", tags=" + tags +
                ", categories='" + categories + '\'' +
                '}';
    }
}
