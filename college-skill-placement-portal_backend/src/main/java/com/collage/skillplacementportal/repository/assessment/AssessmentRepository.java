package com.collage.skillplacementportal.repository.assessment;

import com.collage.skillplacementportal.entity.assessment.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
