package com.backend.demo.service;

import com.backend.demo.entity.*;
import com.backend.demo.repository.StudentAnswerRepository;
import org.springframework.stereotype.Service;
import com.backend.demo.dto.StudentAnswerRequest;

import java.util.ArrayList;
import java.util.List;

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

    public List<StudentAnswer> saveAnswer(StudentAnswerRequest request) {
        TestAttempt attempt = attemptService.getAttemptById(request.getAttemptId());
        Question question = questionService.getQuestionById(request.getQuestionId());

        List<StudentAnswer> savedAnswers = new ArrayList<>();

        // Видаляємо попередні відповіді на це питання для цієї спроби
        List<StudentAnswer> existing = studentAnswerRepository.findByTestAttemptIdAndQuestionId(
                request.getAttemptId(), request.getQuestionId());
        if (!existing.isEmpty()) {
            studentAnswerRepository.deleteAll(existing);
        }

        if (request.getSelectedOptionIds() != null && !request.getSelectedOptionIds().isEmpty()) {
            // MULTIPLE_CHOICE або SINGLE_CHOICE — зберігаємо всі обрані варіанти
            for (Long optionId : request.getSelectedOptionIds()) {
                AnswerOption selectedOption = optionService.getOptionById(optionId);
                StudentAnswer answer = new StudentAnswer();
                answer.setTestAttempt(attempt);
                answer.setQuestion(question);
                answer.setSelectedOption(selectedOption);
                answer.setIsCorrect(selectedOption.getIsCorrect());
                savedAnswers.add(studentAnswerRepository.save(answer));
            }
        } else if (request.getSelectedOptionId() != null) {
            // Зворотна сумісність: один обраний варіант
            AnswerOption selectedOption = optionService.getOptionById(request.getSelectedOptionId());
            StudentAnswer answer = new StudentAnswer();
            answer.setTestAttempt(attempt);
            answer.setQuestion(question);
            answer.setSelectedOption(selectedOption);
            answer.setIsCorrect(selectedOption.getIsCorrect());
            savedAnswers.add(studentAnswerRepository.save(answer));
        } else if (request.getEnteredText() != null) {
            // Текстова відповідь
            StudentAnswer answer = new StudentAnswer();
            answer.setTestAttempt(attempt);
            answer.setQuestion(question);
            answer.setEnteredText(request.getEnteredText());
            savedAnswers.add(studentAnswerRepository.save(answer));
        }

        return savedAnswers;
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