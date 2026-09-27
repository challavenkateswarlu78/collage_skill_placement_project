package com.collage.skillplacementportal.controller.assessment.question;

import com.collage.skillplacementportal.entity.assessment.question.Question;
import com.collage.skillplacementportal.service.assessment.question.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // Create question
    @PostMapping
    public Question createQuestion(
            @RequestBody Question question,
            @RequestParam Long assessmentId) {

        return questionService.createQuestion(
                question,
                assessmentId
        );
    }

    // Get all questions
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // Get questions for an assessment
    @GetMapping("/assessment/{assessmentId}")
    public List<Question> getQuestionsByAssessment(
            @PathVariable Long assessmentId) {

        return questionService.getQuestionsByAssessment(
                assessmentId
        );
    }

    // Get only MCQ questions
    @GetMapping("/assessment/{assessmentId}/mcq")
    public List<Question> getMCQQuestions(
            @PathVariable Long assessmentId) {

        return questionService.getMCQQuestions(
                assessmentId
        );
    }

    // Get only coding questions
    @GetMapping("/assessment/{assessmentId}/coding")
    public List<Question> getCodingQuestions(
            @PathVariable Long assessmentId) {

        return questionService.getCodingQuestions(
                assessmentId
        );
    }

    // Get question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(
            @PathVariable Long id) {

        try {
            return ResponseEntity.ok(
                    questionService.getQuestionById(id)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id) {

        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}