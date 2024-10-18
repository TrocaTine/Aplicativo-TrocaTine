package com.example.trocatine.api.responseDTO;

public class SaveProductResponseDTO {
    private boolean saved;

    public SaveProductResponseDTO(boolean saved) {
        this.saved = saved;
    }

    public SaveProductResponseDTO() {
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    @Override
    public String toString() {
        return "SaveProductResponseDTO{" +
                "saved=" + saved +
                '}';
    }
}
