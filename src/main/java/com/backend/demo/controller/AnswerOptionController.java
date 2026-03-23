package com.backend.demo.controller;

import com.backend.demo.dto.AnswerOptionRequest;
import com.backend.demo.entity.AnswerOption;
import com.backend.demo.entity.Question;
import com.backend.demo.service.AnswerOptionService;
import com.backend.demo.service.QuestionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class AnswerOptionController {
    private final  AnswerOptionService answerOptionService;
    private final  QuestionService questionService;

    public AnswerOptionController(AnswerOptionService answerOptionService, QuestionService questionService) {
        this.answerOptionService = answerOptionService;
        this.questionService = questionService;
    }

    @PostMapping
    public AnswerOption createAnswerOption(@RequestBody AnswerOptionRequest request) {
        Question question = questionService.getQuestionById(request.getQuestionId());

        AnswerOption answerOption = new AnswerOption();
        answerOption.setText(request.getText());
        answerOption.setCorrect(request.isCorrect());
        answerOption.setQuestion(question);

        return answerOptionService.createAnswerOption(answerOption);
    }
}
