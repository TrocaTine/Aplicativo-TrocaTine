package com.example.trocatine.api.responseDTO;

public class SaveInfoProductResponseDTO{
    private Long idUserProduct;
    private String nicknameProduct;
    private Long idUser;
    private String nicknameUser;

    public Long getIdUserProduct() {
        return idUserProduct;
    }

    public void setIdUserProduct(Long idUserProduct) {
        this.idUserProduct = idUserProduct;
    }

    public String getNicknameProduct() {
        return nicknameProduct;
    }

    public void setNicknameProduct(String nicknameProduct) {
        this.nicknameProduct = nicknameProduct;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNicknameUser() {
        return nicknameUser;
    }

    public void setNicknameUser(String nicknameUser) {
        this.nicknameUser = nicknameUser;
    }

    public SaveInfoProductResponseDTO() {
    }

    public SaveInfoProductResponseDTO(Long idUserProduct, String nicknameProduct, Long idUser, String nicknameUser) {
        this.idUserProduct = idUserProduct;
        this.nicknameProduct = nicknameProduct;
        this.idUser = idUser;
        this.nicknameUser = nicknameUser;
    }
}
