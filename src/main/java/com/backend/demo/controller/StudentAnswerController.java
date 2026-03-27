package com.backend.demo.controller;

import com.backend.demo.dto.StudentAnswerRequest;
import com.backend.demo.entity.StudentAnswer;
import com.backend.demo.service.StudentAnswerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student-answers")
public class StudentAnswerController {

    private final StudentAnswerService studentAnswerService;

    public StudentAnswerController(StudentAnswerService studentAnswerService) {
        this.studentAnswerService = studentAnswerService;
    }

    @PostMapping
    public StudentAnswer SaveAnswer(@RequestBody StudentAnswerRequest request) {
        return studentAnswerService.saveAnswer(request);
    }
}
