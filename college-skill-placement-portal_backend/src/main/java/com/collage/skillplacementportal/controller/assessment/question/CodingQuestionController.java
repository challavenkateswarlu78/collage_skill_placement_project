package com.collage.skillplacementportal.controller.assessment.question;

import com.collage.skillplacementportal.entity.assessment.question.CodingQuestion;
import com.collage.skillplacementportal.service.assessment.question.CodingQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coding-questions")
public class CodingQuestionController {

    private final CodingQuestionService codingQuestionService;

    public CodingQuestionController(
            CodingQuestionService codingQuestionService) {

        this.codingQuestionService = codingQuestionService;
    }

    // Create coding question
    @PostMapping
    public CodingQuestion createCodingQuestion(
            @RequestBody CodingQuestion codingQuestion,
            @RequestParam Long questionId) {

        return codingQuestionService.createCodingQuestion(
                codingQuestion,
                questionId
        );
    }

    // Get all coding questions
    @GetMapping
    public List<CodingQuestion> getAllCodingQuestions() {
        return codingQuestionService.getAllCodingQuestions();
    }

    // Get coding question by ID
    @GetMapping("/{id}")
    public ResponseEntity<CodingQuestion> getCodingQuestionById(
            @PathVariable Long id) {

        try {
            return ResponseEntity.ok(
                    codingQuestionService.getCodingQuestionById(id)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get coding question by Question ID
    @GetMapping("/question/{questionId}")
    public ResponseEntity<CodingQuestion> getByQuestionId(
            @PathVariable Long questionId) {

        try {
            return ResponseEntity.ok(
                    codingQuestionService.getByQuestionId(questionId)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update coding question
    @PutMapping("/{id}")
    public ResponseEntity<CodingQuestion> updateCodingQuestion(
            @PathVariable Long id,
            @RequestBody CodingQuestion details) {

        try {
            return ResponseEntity.ok(
                    codingQuestionService.updateCodingQuestion(
                            id,
                            details
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete coding question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCodingQuestion(
            @PathVariable Long id) {

        try {
            codingQuestionService.deleteCodingQuestion(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}