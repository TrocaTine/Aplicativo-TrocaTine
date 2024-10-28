package com.example.trocatine.api.responseDTO.product;

import com.example.trocatine.api.models.HighlightDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FindProductCardNameResponseDTO {
    private Long id;
    private BigDecimal value;
    private LocalDate createdAt;
    private String name;
    private String description;
    private List<String> tags;
    private List<HighlightDTO> highlight;
    private boolean flagTrade;

    public FindProductCardNameResponseDTO(Long id, BigDecimal value, LocalDate createdAt, String name, String description, List<String> tags, List<HighlightDTO> highlight, boolean flagTrade) {
        this.id = id;
        this.value = value;
        this.createdAt = createdAt;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.highlight = highlight;
        this.flagTrade = flagTrade;
    }

    public FindProductCardNameResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<HighlightDTO> getHighlight() {
        return highlight;
    }

    public void setHighlight(List<HighlightDTO> highlight) {
        this.highlight = highlight;
    }

    public boolean isFlagTrade() {
        return flagTrade;
    }

    public void setFlagTrade(boolean flagTrade) {
        this.flagTrade = flagTrade;
    }

    @Override
    public String toString() {
        return "FindProductCardNameResponseDTO{" +
                "id=" + id +
                ", value=" + value +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", highlight=" + highlight +
                ", flagTrade=" + flagTrade +
                '}';
    }
}
