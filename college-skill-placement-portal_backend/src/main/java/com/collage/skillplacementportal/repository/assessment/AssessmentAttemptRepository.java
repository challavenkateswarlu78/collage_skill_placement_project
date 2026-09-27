package com.collage.skillplacementportal.repository.assessment;

import com.collage.skillplacementportal.entity.assessment.attempt.AssessmentAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssessmentAttemptRepository
        extends JpaRepository<AssessmentAttempt, Long> {

    List<AssessmentAttempt> findByStudentId(Long studentId);

    List<AssessmentAttempt> findByAssessmentId(Long assessmentId);

    Optional<AssessmentAttempt> findByStudentIdAndAssessmentIdAndStatus(
            Long studentId,
            Long assessmentId,
            String status
    );
}