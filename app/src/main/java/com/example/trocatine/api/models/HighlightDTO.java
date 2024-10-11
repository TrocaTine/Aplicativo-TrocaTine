package com.example.trocatine.api.models;

import java.time.LocalDate;

public class HighlightDTO {
    private LocalDate expirantionAt;
    private boolean highlight;

    public HighlightDTO(LocalDate expirantionAt, boolean highlight) {
        this.expirantionAt = expirantionAt;
        this.highlight = highlight;
    }

    public HighlightDTO() {
    }

    public LocalDate getExpirantionAt() {
        return expirantionAt;
    }

    public void setExpirantionAt(LocalDate expirantionAt) {
        this.expirantionAt = expirantionAt;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    @Override
    public String toString() {
        return "HighlightDTO{" +
                "expirantionAt=" + expirantionAt +
                ", highlight=" + highlight +
                '}';
    }
}
