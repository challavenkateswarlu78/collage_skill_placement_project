package com.collage.skillplacementportal.repository.assessment;

import com.collage.skillplacementportal.entity.assessment.question.CodingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodingQuestionRepository
        extends JpaRepository<CodingQuestion, Long> {

    Optional<CodingQuestion> findByQuestionId(Long questionId);
}