package com.example.trocatine.api.responseDTO.user;

public class EditPersonalInformationResponseDTO {
    private boolean edited;

    public EditPersonalInformationResponseDTO(boolean edited) {
        this.edited = edited;
    }

    public EditPersonalInformationResponseDTO() {
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
