package com.collage.skillplacementportal.controller.assessment.attempt;

import com.collage.skillplacementportal.entity.assessment.attempt.AssessmentAttempt;
import com.collage.skillplacementportal.entity.assessment.attempt.StudentAnswer;
import com.collage.skillplacementportal.service.assessment.attempt.StudentAnswerService;
import com.collage.skillplacementportal.service.security.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/student-answers")
public class StudentAnswerController {

    private final StudentAnswerService studentAnswerService;
    private final AuthorizationService authorizationService;
    public StudentAnswerController(
            StudentAnswerService studentAnswerService,
            AuthorizationService authorizationService) {

        this.studentAnswerService = studentAnswerService;
        this.authorizationService = authorizationService;
    }

    // Submit answer
    @PostMapping
    public StudentAnswer submitAnswer(
            @RequestParam Long attemptId,
            @RequestParam Long questionId,
            @RequestParam String selectedAnswer) {

        AssessmentAttempt attempt =
                authorizationService
                        .getAssessmentAttempt(attemptId);

        if (!authorizationService
                .isStudentOwner(
                        attempt.getStudent().getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot submit an answer for another student's attempt"
            );
        }

        return studentAnswerService.submitAnswer(
                attemptId,
                questionId,
                selectedAnswer
        );
    }

    // Get all answers for an attempt
    @GetMapping("/attempt/{attemptId}")
    public List<StudentAnswer> getAttemptAnswers(
            @PathVariable Long attemptId) {

        AssessmentAttempt attempt =
                authorizationService
                        .getAssessmentAttempt(attemptId);

        if (!authorizationService
                .isStudentOwner(
                        attempt.getStudent().getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's answers"
            );
        }

        return studentAnswerService
                .getAttemptAnswers(attemptId);
    }

    // Get answer by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentAnswer> getAnswerById(
            @PathVariable Long id) {

        StudentAnswer answer;

        try {

            answer =
                    studentAnswerService
                            .getAnswerById(id);

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }

        if (!authorizationService
                .isStudentOwner(
                        answer.getAttempt()
                                .getStudent()
                                .getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's answer"
            );
        }

        return ResponseEntity.ok(answer);
    }
    @PostMapping("/coding/complete")
    public StudentAnswer markCodingCompleted(
            @RequestParam Long attemptId,
            @RequestParam Long questionId) {

        AssessmentAttempt attempt =
                authorizationService
                        .getAssessmentAttempt(attemptId);

        if (!authorizationService
                .isStudentOwner(
                        attempt.getStudent().getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot modify another student's answer"
            );
        }

        return studentAnswerService
                .markCodingCompleted(
                        attemptId,
                        questionId
                );
    }
}