package com.backend.demo.dto;

import java.util.List;

public class StudentAnswerRequest {
    private Long attemptId;
    private Long questionId;
    private Long selectedOptionId;
    private List<Long> selectedOptionIds;
    private String enteredText;

    public Long getAttemptId() { return attemptId; }
    public Long getQuestionId() { return questionId; }
    public Long getSelectedOptionId() { return selectedOptionId; }
    public List<Long> getSelectedOptionIds() { return selectedOptionIds; }
    public String getEnteredText() { return enteredText; }

    public void setAttemptId(Long attemptId) { this.attemptId = attemptId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }
    public void setSelectedOptionId(Long selectedOptionId) { this.selectedOptionId = selectedOptionId; }
    public void setSelectedOptionIds(List<Long> selectedOptionIds) { this.selectedOptionIds = selectedOptionIds; }
    public void setEnteredText(String enteredText) { this.enteredText = enteredText; }
}