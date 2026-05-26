package com.backend.demo.dto;


public class TestAttemptRequest {
    private Long studentId;
    private Long testId;

    public Long getStudentId() {
        return studentId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }
}