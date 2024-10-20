package com.example.trocatine.api.responseDTO;

public class StandardResponseDTO <T>{
    private boolean error;
    private T data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public StandardResponseDTO(boolean error, T data) {
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
