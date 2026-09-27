package com.collage.skillplacementportal.repository.assessment;

import com.collage.skillplacementportal.entity.assessment.question.Question;
import com.collage.skillplacementportal.entity.assessment.question.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByAssessmentId(Long assessmentId);

    List<Question> findByAssessmentIdAndQuestionType(
            Long assessmentId,
            QuestionType questionType
    );
}
