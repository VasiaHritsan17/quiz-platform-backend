package com.backend.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestAttemptDTO {
    private Long id;
    private Long testId;
    private String testTitle;
    private Integer score;
    private Integer maxScore;
    private Integer percentage;
    private Boolean isCompleted;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
