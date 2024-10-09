package com.example.trocatine.api.responseDTO;

public class CreateUserResponseDTO {
    private boolean create;

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public CreateUserResponseDTO(boolean create) {
        this.create = create;
    }

    public CreateUserResponseDTO() {
    }
}
