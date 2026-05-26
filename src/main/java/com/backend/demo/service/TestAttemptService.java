package com.backend.demo.service;

import com.backend.demo.entity.*;
import com.backend.demo.entity.Test;
import com.backend.demo.repository.StudentAnswerRepository;
import com.backend.demo.repository.TestAttemptRepository;
import com.backend.demo.repository.TestRepository;
import com.backend.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public TestAttempt saveAttempt(TestAttempt attempt) {
        return testAttemptRepository.save(attempt);
    }

    public TestAttempt finishAttempt(Long attemptId) {
        TestAttempt attempt = getAttemptById(attemptId);
        Test test = attempt.getTest();

        List<StudentAnswer> allAnswers = studentAnswerRepository.findByTestAttemptId(attemptId);

        // Групуємо відповіді по питанню
        Map<Long, List<StudentAnswer>> answersByQuestion = allAnswers.stream()
                .collect(Collectors.groupingBy(a -> a.getQuestion().getId()));

        int score = 0;

        for (Question question : test.getQuestions()) {
            List<StudentAnswer> studentAnswers = answersByQuestion.getOrDefault(question.getId(), Collections.emptyList());

            if (studentAnswers.isEmpty()) {
                continue; // Немає відповіді — 0 балів
            }

            if (question.getType() == QuestionType.TEXT) {
                // Текстові відповіді потребують ручної перевірки, пропускаємо
                continue;
            }

            if (question.getType() == QuestionType.SINGLE_CHOICE) {
                // Для одиночного вибору — правильно, якщо обрана правильна опція
                if (studentAnswers.size() == 1 && Boolean.TRUE.equals(studentAnswers.get(0).getIsCorrect())) {
                    score++;
                }
            } else if (question.getType() == QuestionType.MULTIPLE_CHOICE) {
                // Для множинного вибору — правильно тільки якщо обрані ВСІ правильні
                // і НЕ обрані неправильні варіанти
                List<AnswerOption> allOptions = question.getOptions();
                if (allOptions == null) continue;

                Set<Long> correctOptionIds = allOptions.stream()
                        .filter(AnswerOption::getIsCorrect)
                        .map(AnswerOption::getId)
                        .collect(Collectors.toSet());

                Set<Long> selectedOptionIds = studentAnswers.stream()
                        .filter(a -> a.getSelectedOption() != null)
                        .map(a -> a.getSelectedOption().getId())
                        .collect(Collectors.toSet());

                // Питання правильне тільки якщо обрані опції точно = правильним опціям
                if (selectedOptionIds.equals(correctOptionIds)) {
                    score++;
                }
            } else if (question.getType() == QuestionType.MATCHING) {
                // Для встановлення відповідностей (MATCHING)
                if (studentAnswers.size() == 1) {
                    StudentAnswer answer = studentAnswers.get(0);
                    String enteredText = answer.getEnteredText();
                    if (enteredText != null && !enteredText.isEmpty()) {
                        try {
                            String[] pairs = enteredText.split(",");
                            int matchedCount = 0;
                            int totalOptions = question.getOptions().size();
                            for (String pair : pairs) {
                                String[] parts = pair.split(":", 2);
                                if (parts.length == 2) {
                                    Long optionId = Long.parseLong(parts[0].trim());
                                    String chosenRight = parts[1].trim();

                                    AnswerOption opt = question.getOptions().stream()
                                            .filter(o -> o.getId().equals(optionId))
                                            .findFirst()
                                            .orElse(null);

                                    if (opt != null) {
                                        String[] origParts = opt.getText().split("::", 2);
                                        String correctRight = origParts.length == 2 ? origParts[1].trim() : "";
                                        if (chosenRight.equalsIgnoreCase(correctRight)) {
                                            matchedCount++;
                                        }
                                    }
                                }
                            }
                            if (matchedCount == totalOptions) {
                                score++;
                            }
                        } catch (Exception e) {

                        }
                    }
                }
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
