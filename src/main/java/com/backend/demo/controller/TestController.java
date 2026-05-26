package com.backend.demo.controller;

import com.backend.demo.dto.TestRequest;
import com.backend.demo.entity.Test;
import com.backend.demo.entity.User;
import com.backend.demo.service.TestService;
import com.backend.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final TestService testService;

    private final UserService userService;

    public TestController(TestService testService, UserService userService) {
        this.testService = testService;
        this.userService = userService;
    }

    @PostMapping
    public Test createTest(@RequestBody TestRequest request) {
        User creator = userService.getUserById(request.getCreatorId());

        Test test = new Test();
        test.setTitle(request.getTitle());
        test.setDescription(request.getDescription());
        test.setTimeLimitMinutes(request.getTimeLimitMinutes());
        test.setMaxAttempts(request.getMaxAttempts());
        test.setCreator(creator);

        return testService.createTest(test);
    }

    @GetMapping
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/creator/{creatorId}")
    public List<Test> getTestsByCreator(@PathVariable Long creatorId) {
        return testService.getTestsByCreator(creatorId);
    }

    @GetMapping("/{id}")
    public Test getTest(@PathVariable Long id) {
      return testService.getTestById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllTests() {
        testService.deleteAllTests();
        return ResponseEntity.noContent().build();
    }
}
