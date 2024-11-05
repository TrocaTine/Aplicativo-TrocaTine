package com.example.trocatine.api.responseDTO.product;

import java.util.List;

public class FindQuestionsByProductResponseDTO {
    private List<QuestionDTO> questionDTOList;

    public FindQuestionsByProductResponseDTO(List<QuestionDTO> questionDTOList) {
        this.questionDTOList = questionDTOList;
    }

    public List<QuestionDTO> getQuestionDTOList() {
        return questionDTOList;
    }

    public void setQuestionDTOList(List<QuestionDTO> questionDTOList) {
        this.questionDTOList = questionDTOList;
    }

    public FindQuestionsByProductResponseDTO() {
    }
}
