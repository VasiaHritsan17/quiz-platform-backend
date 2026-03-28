package com.backend.demo.dto;


public class TestAttemptRequest {
    private Long studentId;
    private Long testId;

    // Геттери
    public Long getStudentId() {
        return studentId;
    }

    public Long getTestId() {
        return testId;
    }

    // Сеттери (саме завдяки їм Spring зможе прочитати JSON)
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }
}