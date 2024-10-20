package com.example.trocatine.api.responseDTO;

public class TesteResponseDTO {
    private boolean error;
    private FindPersonalInformationResponseDTO findPersonalInformationResponseDTO;

    public TesteResponseDTO(boolean error, FindPersonalInformationResponseDTO findPersonalInformationResponseDTO) {
        this.error = error;
        this.findPersonalInformationResponseDTO = findPersonalInformationResponseDTO;
    }

    public TesteResponseDTO() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public FindPersonalInformationResponseDTO getFindPersonalInformationResponseDTO() {
        return findPersonalInformationResponseDTO;
    }

    public void setFindPersonalInformationResponseDTO(FindPersonalInformationResponseDTO findPersonalInformationResponseDTO) {
        this.findPersonalInformationResponseDTO = findPersonalInformationResponseDTO;
    }
}
