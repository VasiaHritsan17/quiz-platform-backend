package com.backend.demo.controller;

import com.backend.demo.entity.Role;
import com.backend.demo.entity.User;
import com.backend.demo.dto.UserRequest;
import com.backend.demo.dto.UserResponse;
import com.backend.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import com.backend.demo.dto.TestAttemptDTO;
import com.backend.demo.entity.TestAttempt;
import com.backend.demo.service.TestAttemptService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final TestAttemptService testAttemptService;

    public UserController(UserService userService, TestAttemptService testAttemptService) {
        this.userService = userService;
        this.testAttemptService = testAttemptService;
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        return userService.createUser(user);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        ));
    }

    @GetMapping("/me/attempts")
    public ResponseEntity<List<TestAttemptDTO>> getCurrentUserAttempts(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        User user = userService.getUserByUsername(principal.getName());
        List<TestAttempt> attempts = testAttemptService.getAttemptByStudentId(user.getId());

        List<TestAttemptDTO> attemptDTOs = attempts.stream()
                .filter(a -> a != null && a.getIsCompleted() != null && a.getIsCompleted())
                .map(attempt -> {
                    if (attempt.getTest() == null) return null;
                    int maxScore = attempt.getTest().getQuestions() != null ? attempt.getTest().getQuestions().size() : 0;
                    int percentage = maxScore > 0 ? (int) Math.round(((double) attempt.getScore() / maxScore) * 100) : 0;
                    return new TestAttemptDTO(
                            attempt.getId(),
                            attempt.getTest().getId(),
                            attempt.getTest().getTitle(),
                            attempt.getScore(),
                            maxScore,
                            percentage,
                            attempt.getIsCompleted(),
                            attempt.getStartTime(),
                            attempt.getEndTime()
                    );
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());

        return ResponseEntity.ok(attemptDTOs);
    }
}
