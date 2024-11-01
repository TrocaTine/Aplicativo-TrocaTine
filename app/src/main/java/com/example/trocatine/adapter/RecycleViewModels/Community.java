package com.example.trocatine.adapter.RecycleViewModels;

public class Community {
    private String name;
    private int pendingMessageQuantity;

    public Community(String name, int pendingMessageQuantity) {
        this.name = name;
        this.pendingMessageQuantity = pendingMessageQuantity;
    }

    public Community() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPendingMessageQuantity() {
        return pendingMessageQuantity;
    }

    public void setPendingMessageQuantity(int pendingMessageQuantity) {
        this.pendingMessageQuantity = pendingMessageQuantity;
    }
}
