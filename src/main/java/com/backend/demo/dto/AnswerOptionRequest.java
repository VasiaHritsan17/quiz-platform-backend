package com.backend.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerOptionRequest {
    private String test;
    private boolean isCorrect;
    private Long questionId;
}
