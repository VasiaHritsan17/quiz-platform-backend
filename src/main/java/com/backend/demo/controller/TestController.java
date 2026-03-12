package com.backend.demo.controller;

import com.backend.demo.dto.TestRequest;
import com.backend.demo.entity.Test;
import com.backend.demo.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tests")
public class TestController {

    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping
    public Test createTest(@RequestBody TestRequest request) {
        Test test = new Test();
        test.setTitle(request.getTitle());
        test.setDescription(request.getDescription());
        return testService.createTest(test);
    }

    @GetMapping
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable Long id) {
        try {
            Test test = testService.getTestById(id);
            return ResponseEntity.ok(test);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
