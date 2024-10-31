package com.example.trocatine.api.requestDTO.product;

import com.example.trocatine.api.models.TagDTO;

import java.math.BigDecimal;
import java.util.List;

public class EditProductRequestDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal value;
    private Long stock;
    private Boolean flagTrade;
    private List<TagDTO> tags;
    private List<String> categories;

    public EditProductRequestDTO(Long id, String name, String description, BigDecimal value, Long stock, Boolean flagTrade, List<TagDTO> tags, List<String> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.stock = stock;
        this.flagTrade = flagTrade;
        this.tags = tags;
        this.categories = categories;
    }

    public EditProductRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Boolean getFlagTrade() {
        return flagTrade;
    }

    public void setFlagTrade(Boolean flagTrade) {
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
}
