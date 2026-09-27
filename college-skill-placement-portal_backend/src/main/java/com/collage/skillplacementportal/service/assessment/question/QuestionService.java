package com.collage.skillplacementportal.service.assessment.question;

import com.collage.skillplacementportal.entity.assessment.Assessment;
import com.collage.skillplacementportal.entity.assessment.question.Question;
import com.collage.skillplacementportal.entity.assessment.question.QuestionType;
import com.collage.skillplacementportal.repository.assessment.AssessmentRepository;
import com.collage.skillplacementportal.repository.assessment.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AssessmentRepository assessmentRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            AssessmentRepository assessmentRepository) {

        this.questionRepository = questionRepository;
        this.assessmentRepository = assessmentRepository;
    }

    // Create question
    public Question createQuestion(
            Question question,
            Long assessmentId) {

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment not found with id: " + assessmentId
                        )
                );

        question.setAssessment(assessment);

        return questionRepository.save(question);
    }

    // Get all questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Get questions of an assessment
    public List<Question> getQuestionsByAssessment(
            Long assessmentId) {

        if (!assessmentRepository.existsById(assessmentId)) {
            throw new RuntimeException(
                    "Assessment not found with id: " + assessmentId
            );
        }

        return questionRepository.findByAssessmentId(assessmentId);
    }

    // Get MCQ questions
    public List<Question> getMCQQuestions(Long assessmentId) {

        return questionRepository
                .findByAssessmentIdAndQuestionType(
                        assessmentId,
                        QuestionType.MCQ
                );
    }

    // Get Coding questions
    public List<Question> getCodingQuestions(Long assessmentId) {

        return questionRepository
                .findByAssessmentIdAndQuestionType(
                        assessmentId,
                        QuestionType.CODING
                );
    }

    // Get question by ID
    public Question getQuestionById(Long id) {

        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + id
                        )
                );
    }

    // Delete question
    public void deleteQuestion(Long id) {

        if (!questionRepository.existsById(id)) {
            throw new RuntimeException(
                    "Question not found with id: " + id
            );
        }

        questionRepository.deleteById(id);
    }
}