package com.assessment.coding.repository;

import com.assessment.coding.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByAccessCode(String accessCode);
    List<Test> findByIsActiveTrue();
    List<Test> findByCreatedBy(Long createdBy);
}