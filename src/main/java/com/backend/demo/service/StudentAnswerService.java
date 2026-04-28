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

        StudentAnswer answer = new StudentAnswer();
        answer.setTestAttempt(attempt);
        answer.setQuestion(question);
        
        if (request.getSelectedOptionId() != null) {
            AnswerOption selectedOption = optionService.getOptionById(request.getSelectedOptionId());
            answer.setSelectedOption(selectedOption);
            answer.setIsCorrect(selectedOption.getIsCorrect());
        }
        
        if (request.getEnteredText() != null) {
            answer.setEnteredText(request.getEnteredText());
        }

        return studentAnswerRepository.save(answer);
    }

    public java.util.List<StudentAnswer> getAnswersByAttempt(Long attemptId) {
        return studentAnswerRepository.findByTestAttemptId(attemptId);
    }

    public StudentAnswer gradeAnswer(Long answerId, Boolean isCorrect) {
        StudentAnswer answer = studentAnswerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Відповідь не знайдено"));
        
        Boolean previousGrade = answer.getIsCorrect();
        answer.setIsCorrect(isCorrect);
        studentAnswerRepository.save(answer);

        if (previousGrade == null || !previousGrade.equals(isCorrect)) {
            TestAttempt attempt = answer.getTestAttempt();
            int scoreChange = 0;
            if (Boolean.TRUE.equals(isCorrect)) scoreChange = 1;
            if (Boolean.TRUE.equals(previousGrade)) scoreChange = -1;
            
            if (scoreChange != 0) {
                attempt.setScore(attempt.getScore() + scoreChange);
                attemptService.saveAttempt(attempt);
            }
        }
        return answer;
    }
}