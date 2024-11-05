package com.example.trocatine.adapter.RecycleViewModels;

import android.net.Uri;

public class Community {
    private String name;
    private String photo;
    private boolean community;


    public Community(String name, String photo, boolean community) {
        this.name = name;
        this.photo = photo;
        this.community = community;

    }

    public Community() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean getCommunity() {
        return community;
    }

    public void setCommunity(boolean community) {
        this.community = community;
    }
}
