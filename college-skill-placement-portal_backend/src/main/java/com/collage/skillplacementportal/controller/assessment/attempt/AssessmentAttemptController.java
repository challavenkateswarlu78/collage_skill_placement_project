package com.collage.skillplacementportal.controller.assessment.attempt;

import com.collage.skillplacementportal.dto.assessment.AssessmentResultDTO;
import com.collage.skillplacementportal.entity.assessment.attempt.AssessmentAttempt;
import com.collage.skillplacementportal.service.assessment.attempt.AssessmentAttemptService;
import com.collage.skillplacementportal.service.security.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/assessment-attempts")
public class AssessmentAttemptController {

    private final AssessmentAttemptService attemptService;
    private final AuthorizationService authorizationService;
    public AssessmentAttemptController(
            AssessmentAttemptService attemptService,
            AuthorizationService authorizationService) {

        this.attemptService = attemptService;
        this.authorizationService = authorizationService;
    }

    // Start assessment
    @PostMapping("/start")
    public AssessmentAttempt startAssessment(
            @RequestParam Long studentId,
            @RequestParam Long assessmentId) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot start an assessment for another student"
            );
        }

        return attemptService.startAssessment(
                studentId,
                assessmentId
        );
    }

    // Get attempt by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssessmentAttempt> getAttemptById(
            @PathVariable Long id) {

        AssessmentAttempt attempt;

        try {

            attempt =
                    attemptService.getAttemptById(id);

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }

        if (!authorizationService
                .isStudentOwner(
                        attempt.getStudent().getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's attempt"
            );
        }

        return ResponseEntity.ok(attempt);
    }

    // Get all attempts of a student
    @GetMapping("/student/{studentId}")
    public List<AssessmentAttempt> getStudentAttempts(
            @PathVariable Long studentId) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's attempts"
            );
        }

        return attemptService.getStudentAttempts(
                studentId
        );
    }

    // Get all attempts of an assessment
    @GetMapping("/assessment/{assessmentId}")
    public List<AssessmentAttempt> getAssessmentAttempts(
            @PathVariable Long assessmentId) {

        return attemptService.getAssessmentAttempts(
                assessmentId
        );
    }
    // Submit assessment
    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<AssessmentAttempt> submitAssessment(
            @PathVariable Long attemptId) {

        AssessmentAttempt attempt;

        try {

            attempt =
                    attemptService.getAttemptById(
                            attemptId
                    );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }

        if (!authorizationService
                .isStudentOwner(
                        attempt.getStudent().getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot submit another student's assessment"
            );
        }

        try {

            return ResponseEntity.ok(
                    attemptService.submitAssessment(
                            attemptId
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{attemptId}/result")
    public ResponseEntity<AssessmentResultDTO> getAssessmentResult(
            @PathVariable Long attemptId) {

        AssessmentAttempt attempt;

        try {

            attempt =
                    attemptService.getAttemptById(
                            attemptId
                    );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }

        if (!authorizationService
                .isStudentOwner(
                        attempt.getStudent().getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's result"
            );
        }

        return ResponseEntity.ok(
                attemptService.getAssessmentResult(
                        attemptId
                )
        );
    }
}