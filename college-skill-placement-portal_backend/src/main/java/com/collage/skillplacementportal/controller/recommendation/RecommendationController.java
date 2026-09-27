package com.collage.skillplacementportal.controller.recommendation;

import com.collage.skillplacementportal.dto.recommendation.RecommendationDTO;
import com.collage.skillplacementportal.service.recommendation.RecommendationService;
import com.collage.skillplacementportal.service.security.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final AuthorizationService authorizationService;
    public RecommendationController(
            RecommendationService recommendationService,
            AuthorizationService authorizationService) {

        this.recommendationService =
                recommendationService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/student/{studentId}")
    public List<RecommendationDTO> getRecommendations(
            @PathVariable Long studentId,
            @RequestParam String topic) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's recommendations"
            );
        }

        return recommendationService
                .getRecommendations(
                        studentId,
                        topic
                );
    }
    @GetMapping("/student/{studentId}/automatic")
    public List<RecommendationDTO> getAutomaticRecommendations(
            @PathVariable Long studentId) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's recommendations"
            );
        }

        return recommendationService
                .getAutomaticRecommendations(studentId);
    }
}