package com.backend.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestRequest {
    private String title;
    private String description;
    private Integer timeLimitMinutes;
    private Integer maxAttempts;
    private Long creatorId;
}
