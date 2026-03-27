package com.backend.demo.dto;

public class StudentAnswerRequest {
    private Long attemptId;
    private Long questionId;
    private Long selectedOptionId;

    public Long getAttemptId() { return attemptId; }
    public Long getQuestionId() { return questionId; }
    public Long getSelectedOptionId() { return selectedOptionId; }

    public void setAttemptId(Long attemptId) { this.attemptId = attemptId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }
    public void setSelectedOptionId(Long selectedOptionId) { this.selectedOptionId = selectedOptionId; }
}