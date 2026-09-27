package com.collage.skillplacementportal.service.security;

import com.collage.skillplacementportal.entity.assessment.attempt.AssessmentAttempt;
import com.collage.skillplacementportal.entity.user.User;
import com.collage.skillplacementportal.repository.assessment.AssessmentAttemptRepository;
import com.collage.skillplacementportal.repository.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UserRepository userRepository;
    private final AssessmentAttemptRepository assessmentAttemptRepository;
    public AuthorizationService(
            UserRepository userRepository,
            AssessmentAttemptRepository assessmentAttemptRepository) {

        this.userRepository = userRepository;
        this.assessmentAttemptRepository = assessmentAttemptRepository;
    }

    public User getCurrentUser() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found"
                        )
                );
    }

    public boolean isStudentOwner(
            Long studentId) {

        User user = getCurrentUser();

        return user.getRole().name().equals("STUDENT")
                && studentId.equals(
                user.getStudentId()
        );
    }
    public AssessmentAttempt getAssessmentAttempt(
            Long attemptId) {

        return assessmentAttemptRepository
                .findById(attemptId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment attempt not found"
                        )
                );
    }

    public Long getCurrentStudentId() {

        User user = getCurrentUser();

        if (!user.getRole().name().equals("STUDENT")) {
            throw new RuntimeException("User is not a student");
        }

        if (user.getStudentId() == null) {
            throw new RuntimeException("Student profile not linked");
        }

        return user.getStudentId();
    }
}