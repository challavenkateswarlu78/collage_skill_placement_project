package com.collage.skillplacementportal.controller.dsa;

import com.collage.skillplacementportal.dto.dsa.*;
import com.collage.skillplacementportal.entity.dsa.StudentDSAProgress;
import com.collage.skillplacementportal.service.dsa.StudentDSAProgressService;
import com.collage.skillplacementportal.service.security.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/dsa/progress")
public class StudentDSAProgressController {

    private final StudentDSAProgressService progressService;
    private final AuthorizationService authorizationService;
    public StudentDSAProgressController(
            StudentDSAProgressService progressService,
            AuthorizationService authorizationService) {

        this.progressService = progressService;
        this.authorizationService = authorizationService;
    }

    // Mark problem as solved
    @PostMapping("/solve")
    public StudentDSAProgress markAsSolved(
            @RequestParam Long studentId,
            @RequestParam Long problemId) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot update another student's DSA progress"
            );
        }

        return progressService.markAsSolved(
                studentId,
                problemId
        );
    }

    // Get all progress of a student
    @GetMapping("/student/{studentId}")
    public List<StudentDSAProgress> getStudentProgress(
            @PathVariable Long studentId) {
        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's DSA progress"
            );
        }
        return progressService.getStudentProgress(
                studentId
        );
    }

    // Get solved problems of a student
    @GetMapping("/student/{studentId}/solved")
    public List<StudentDSAProgress> getSolvedProblems(
            @PathVariable Long studentId) {

        return progressService.getSolvedProblems(
                studentId
        );
    }

    // Get students who solved a particular problem
    @GetMapping("/problem/{problemId}")
    public List<StudentDSAProgress> getProblemProgress(
            @PathVariable Long problemId) {

        return progressService.getProblemProgress(
                problemId
        );
    }
    @GetMapping("/student/{studentId}/points")
    public int getTotalPoints(
            @PathVariable Long studentId) {

        return progressService.getTotalPoints(studentId);
    }
    @GetMapping("/leaderboard")
    public List<LeaderboardDTO> getLeaderboard() {

        return progressService.getLeaderboard();
    }
    @GetMapping("/student/{studentId}/streak")
    public int getCurrentStreak(
            @PathVariable Long studentId) {

        return progressService.getCurrentStreak(studentId);
    }
    @GetMapping("/student/{studentId}/summary")
    public DSAProgressDTO getStudentSummary(
            @PathVariable Long studentId) {

        return progressService.getStudentSummary(
                studentId
        );
    }
    @GetMapping("/student/{studentId}/topics")
    public List<DSATopicProgressDTO> getTopicProgress(
            @PathVariable Long studentId) {

        return progressService.getTopicProgress(
                studentId
        );
    }
    @GetMapping("/student/{studentId}/difficulty")
    public List<DSADifficultyProgressDTO> getDifficultyProgress(
            @PathVariable Long studentId) {

        return progressService.getDifficultyProgress(
                studentId
        );
    }
    @GetMapping("/student/{studentId}/platforms")
    public List<DSAPlatformProgressDTO> getPlatformProgress(
            @PathVariable Long studentId) {

        return progressService.getPlatformProgress(
                studentId
        );
    }
}