package com.backend.demo.repository;

import com.backend.demo.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
    List<StudentAnswer> findByTestAttemptId(Long attemptId);
    List<StudentAnswer> findByTestAttemptIdAndQuestionId(Long attemptId, Long questionId);
}
