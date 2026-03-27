package com.backend.demo.controller;

import com.backend.demo.dto.TestAttemptRequest;
import com.backend.demo.entity.TestAttempt;
import com.backend.demo.service.TestAttemptService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attempts")
public class TestAttemptController {

    private final TestAttemptService testAttemptService;

    public TestAttemptController(TestAttemptService testAttemptService) {
        this.testAttemptService = testAttemptService;
    }

    @PostMapping("/start")
    public TestAttempt StartTestAttempt(@RequestBody TestAttemptRequest request) {
        return testAttemptService.startAttempt(request.getStudentId(), request.getTestId());
    }

    @PostMapping("/{id}/finish")
    public TestAttempt finistTestAttempt(@PathVariable Long id) {
        return testAttemptService.finishAttempt(id);
    }
}
