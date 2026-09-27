package com.collage.skillplacementportal.controller.assessment;

import com.collage.skillplacementportal.entity.assessment.Assessment;
import com.collage.skillplacementportal.service.assessment.AssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    // Create assessment
    @PostMapping
    public Assessment createAssessment(
            @RequestBody Assessment assessment,
            @RequestParam Long skillId) {

        return assessmentService.createAssessment(
                assessment,
                skillId
        );
    }

    // Get all assessments
    @GetMapping
    public List<Assessment> getAllAssessments() {
        return assessmentService.getAllAssessments();
    }

    // Get assessment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Assessment> getAssessmentById(
            @PathVariable Long id) {

        try {
            return ResponseEntity.ok(
                    assessmentService.getAssessmentById(id)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update assessment
    @PutMapping("/{id}")
    public ResponseEntity<Assessment> updateAssessment(
            @PathVariable Long id,
            @RequestBody Assessment assessment,
            @RequestParam Long skillId) {

        try {
            return ResponseEntity.ok(
                    assessmentService.updateAssessment(
                            id,
                            assessment,
                            skillId
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete assessment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(
            @PathVariable Long id) {

        try {
            assessmentService.deleteAssessment(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}