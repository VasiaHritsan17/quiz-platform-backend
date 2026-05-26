package com.backend.demo.service;

import com.backend.demo.entity.Test;
import com.backend.demo.repository.TestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    public Test getTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Тест з таким  id не знайдено!"));
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public List<Test> getTestsByCreator(Long creatorId) {
        return testRepository.findByCreatorId(creatorId);
    }

    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllTests() {
        entityManager.createNativeQuery("TRUNCATE TABLE student_answers, test_attempts, answer_options, questions, tests CASCADE").executeUpdate();
    }
}
