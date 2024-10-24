package com.example.trocatine.api.responseDTO;

public class FindRankingTrocadinhaResponseDTO {
    private String nickname;
    private int countTrocadinha;

    public FindRankingTrocadinhaResponseDTO(String nickname, int countTrocadinha) {
        this.nickname = nickname;
        this.countTrocadinha = countTrocadinha;
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
}
