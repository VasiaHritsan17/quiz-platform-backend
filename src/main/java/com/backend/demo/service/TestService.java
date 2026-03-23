package com.backend.demo.service;

import com.backend.demo.entity.Test;
import com.backend.demo.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;

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
}
