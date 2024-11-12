package com.example.trocatine.api.responseDTO.trocadinha;

public class FindRankingTrocadinhaResponseDTO {
    private String email;
    private String nickname;
    private int countTrocadinha;

    public FindRankingTrocadinhaResponseDTO(String nickname, int countTrocadinha, String email) {
        this.nickname = nickname;
        this.countTrocadinha = countTrocadinha;
        this.email = email;
    }

    public FindRankingTrocadinhaResponseDTO() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCountTrocadinha() {
        return countTrocadinha;
    }

    public void setCountTrocadinha(int countTrocadinha) {
        this.countTrocadinha = countTrocadinha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
