package com.backend.demo.service;

import com.backend.demo.entity.*;
import com.backend.demo.entity.Test;
import com.backend.demo.repository.StudentAnswerRepository;
import com.backend.demo.repository.TestAttemptRepository;
import com.backend.demo.repository.TestRepository;
import com.backend.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestAttemptService {
    private final TestAttemptRepository testAttemptRepository;
    private final UserService userService;
    private final TestService testService;
    private final StudentAnswerRepository studentAnswerRepository;


    public TestAttemptService(TestAttemptRepository testAttemptRepository,
                              UserService userService,
                              TestService testService,
                              StudentAnswerRepository studentAnswerRepository) {
        this.testAttemptRepository = testAttemptRepository;
        this.userService = userService;
        this.testService = testService;
        this.studentAnswerRepository = studentAnswerRepository;
    }

    public TestAttempt startAttempt(Long StudentId, Long TestId) {
        User student = userService.getUserById(StudentId);
        Test test = testService.getTestById(TestId);

        TestAttempt attempt = new TestAttempt();
        attempt.setStudent(student);
        attempt.setTest(test);
        attempt.setScore(0);
        attempt.setStartTime(java.time.LocalDateTime.now());

        return testAttemptRepository.save(attempt);
    }

    public TestAttempt getAttemptById(Long id) {
        return testAttemptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Спробу не знайдено"));
    }

    public TestAttempt finishAttempt(Long attemptId) {
        TestAttempt attempt = getAttemptById(attemptId);

        List<StudentAnswer> answers = studentAnswerRepository.findByTestAttemptId(attemptId);
        int score = 0;

        for (StudentAnswer answer : answers) {
            if (answer.getSelectedOption().getIsCorrect()) {
                score++;
            }
        }

        attempt.setEndTime(java.time.LocalDateTime.now());
        attempt.setIsCompleted(true);
        attempt.setScore(score);


        return testAttemptRepository.save(attempt);
    }

    public List<TestAttempt> getAttemptByTestId(Long testId) {
        return testAttemptRepository.findByTestId(testId);
    }

    public List<TestAttempt> getAttemptByStudentId(Long studentId) {
        return testAttemptRepository.findByStudentId(studentId);
    }
}
