package com.assessment.coding.repository;

import com.assessment.coding.entity.TestSession;
import com.assessment.coding.enums.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {
    List<TestSession> findByTestId(Long testId);
    List<TestSession> findByStudentId(Long studentId);
    Optional<TestSession> findByTestIdAndStudentId(Long testId, Long studentId);
    List<TestSession> findByStatus(TestStatus status);
}