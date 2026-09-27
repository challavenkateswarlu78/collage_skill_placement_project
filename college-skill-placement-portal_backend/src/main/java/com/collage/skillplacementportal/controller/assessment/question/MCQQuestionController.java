package com.collage.skillplacementportal.controller.assessment.question;

import com.collage.skillplacementportal.entity.assessment.question.MCQQuestion;
import com.collage.skillplacementportal.service.assessment.question.MCQQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mcq-questions")
public class MCQQuestionController {

    private final MCQQuestionService mcqQuestionService;

    public MCQQuestionController(
            MCQQuestionService mcqQuestionService) {

        this.mcqQuestionService = mcqQuestionService;
    }

    // Create MCQ details
    @PostMapping
    public MCQQuestion createMCQ(
            @RequestBody MCQQuestion mcqQuestion,
            @RequestParam Long questionId) {

        return mcqQuestionService.createMCQ(
                mcqQuestion,
                questionId
        );
    }

    // Get all MCQs
    @GetMapping
    public List<MCQQuestion> getAllMCQs() {
        return mcqQuestionService.getAllMCQs();
    }

    // Get MCQ by ID
    @GetMapping("/{id}")
    public ResponseEntity<MCQQuestion> getMCQById(
            @PathVariable Long id) {

        try {
            return ResponseEntity.ok(
                    mcqQuestionService.getMCQById(id)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get MCQ by Question ID
    @GetMapping("/question/{questionId}")
    public ResponseEntity<MCQQuestion> getMCQByQuestionId(
            @PathVariable Long questionId) {

        try {
            return ResponseEntity.ok(
                    mcqQuestionService
                            .getMCQByQuestionId(questionId)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update MCQ
    @PutMapping("/{id}")
    public ResponseEntity<MCQQuestion> updateMCQ(
            @PathVariable Long id,
            @RequestBody MCQQuestion mcqDetails) {

        try {
            return ResponseEntity.ok(
                    mcqQuestionService.updateMCQ(
                            id,
                            mcqDetails
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete MCQ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMCQ(
            @PathVariable Long id) {

        try {
            mcqQuestionService.deleteMCQ(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}