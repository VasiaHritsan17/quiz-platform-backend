package com.backend.demo.controller;

import com.backend.demo.dto.QuestionRequest;
import com.backend.demo.entity.Question;
import com.backend.demo.entity.QuestionType;
import com.backend.demo.entity.Test;
import com.backend.demo.service.QuestionService;
import com.backend.demo.service.TestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final TestService testService;

    public QuestionController(QuestionService questionService, TestService testService) {
        this.questionService = questionService;
        this.testService = testService;
    }

    @PostMapping
    public Question createQuestion(@RequestBody QuestionRequest request) {
        Test test = testService.getTestById(request.getTestId());

        Question question = new Question();
        question.setText(request.getText());
        question.setType(QuestionType.valueOf(request.getType().toUpperCase()));

        question.setTest(test);

        return questionService.createQuestion(question);
    }
}
