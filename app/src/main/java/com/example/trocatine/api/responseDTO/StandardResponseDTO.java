package com.example.trocatine.api.responseDTO;

public class StandardResponseDTO {
    private boolean error;
    private Object data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public StandardResponseDTO(boolean error, Object data) {
        this.error = error;
        this.data = data;
    }

    public StandardResponseDTO() {
    }

    @Override
    public String toString() {
        return "StandardResponseDTO{" +
                "error=" + error +
                ", data=" + data +
                '}';
    }
}
