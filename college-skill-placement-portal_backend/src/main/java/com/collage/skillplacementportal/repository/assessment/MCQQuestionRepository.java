package com.collage.skillplacementportal.repository.assessment;

import com.collage.skillplacementportal.entity.assessment.question.MCQQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MCQQuestionRepository
        extends JpaRepository<MCQQuestion, Long> {

    Optional<MCQQuestion> findByQuestionId(Long questionId);
}