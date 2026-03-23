package com.backend.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {
    private String text;
    private String type;
    private Long testId;
}
