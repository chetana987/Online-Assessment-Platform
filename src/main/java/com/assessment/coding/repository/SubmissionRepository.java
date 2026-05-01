package com.assessment.coding.repository;

import com.assessment.coding.entity.Submission;
import com.assessment.coding.enums.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUserId(Long userId);
    List<Submission> findByQuestionId(Long questionId);
    List<Submission> findByUserIdAndQuestionId(Long userId, Long questionId);
    List<Submission> findByTestSessionId(Long testSessionId);
    Optional<Submission> findTopByUserIdAndQuestionIdOrderByCreatedAtDesc(Long userId, Long questionId);
}