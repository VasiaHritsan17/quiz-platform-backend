package com.backend.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerOptionRequest {
    private String text;
    @JsonProperty("isCorrect")
    private boolean isCorrect;
    private Long questionId;
}
