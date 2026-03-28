package com.backend.demo.repository;

import com.backend.demo.entity.TestAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestAttemptRepository extends JpaRepository<TestAttempt,Long> {
    List<TestAttempt> findByTestId(Long testId);

    List<TestAttempt> findByStudentId(Long studentId);
}
