package com.assessment.coding.entity;

import com.assessment.coding.enums.TestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TestStatus status = TestStatus.STARTED;

    @Column(name = "score")
    private Integer score;

    @Column(name = "total_marks")
    private Integer totalMarks;

    @PrePersist
    protected void onCreate() {
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
        if (status == null) {
            status = TestStatus.STARTED;
        }
    }

    public boolean isExpired() {
        if (endTime != null) {
            return LocalDateTime.now().isAfter(endTime);
        }
        if (test != null && test.getDuration() != null && startTime != null) {
            return LocalDateTime.now().isAfter(startTime.plusMinutes(test.getDuration()));
        }
        return false;
    }

    public void submit() {
        this.status = TestStatus.SUBMITTED;
        this.endTime = LocalDateTime.now();
    }

    public void expire() {
        this.status = TestStatus.EXPIRED;
        this.endTime = LocalDateTime.now();
    }
}