package com.example.trocatine.adapter.RecycleViewModels;

public class ChatCommunity {
    private String nickname;
    private String photo;
    private String message;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public ChatCommunity() {
    }

    public ChatCommunity(String nickname, String photo, String message) {
        this.nickname = nickname;
        this.photo = photo;
        this.message = message;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
