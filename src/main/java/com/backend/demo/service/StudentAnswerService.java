package com.backend.demo.service;

import com.backend.demo.entity.*;
import com.backend.demo.repository.StudentAnswerRepository;
import org.springframework.stereotype.Service;
import com.backend.demo.dto.StudentAnswerRequest;

@Service
public class StudentAnswerService {

    private final StudentAnswerRepository studentAnswerRepository;
    private final TestAttemptService attemptService;
    private final QuestionService questionService;
    private final AnswerOptionService optionService;

    public StudentAnswerService(StudentAnswerRepository studentAnswerRepository,
                                TestAttemptService attemptService,
                                QuestionService questionService,
                                AnswerOptionService optionService) {
        this.studentAnswerRepository = studentAnswerRepository;
        this.attemptService = attemptService;
        this.questionService = questionService;
        this.optionService = optionService;
    }

    public StudentAnswer saveAnswer(StudentAnswerRequest request) {
        TestAttempt attempt = attemptService.getAttemptById(request.getAttemptId());
        Question question = questionService.getQuestionById(request.getQuestionId());
        AnswerOption selectedOption = optionService.getOptionById(request.getSelectedOptionId());

        StudentAnswer answer = new StudentAnswer();
        answer.setTestAttempt(attempt);
        answer.setQuestion(question);
        answer.setSelectedOption(selectedOption);

        return studentAnswerRepository.save(answer);
    }
}