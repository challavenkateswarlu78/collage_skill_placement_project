package com.collage.skillplacementportal.controller.dsa;

import com.collage.skillplacementportal.entity.dsa.DSAProblem;
import com.collage.skillplacementportal.service.dsa.DSAProblemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dsa/problems")
public class DSAProblemController {

    private final DSAProblemService dsaProblemService;

    public DSAProblemController(
            DSAProblemService dsaProblemService) {

        this.dsaProblemService = dsaProblemService;
    }

    // Create problem
    @PostMapping
    public DSAProblem createProblem(
            @RequestBody DSAProblem problem) {

        return dsaProblemService.createProblem(problem);
    }

    // Get all problems
    @GetMapping
    public List<DSAProblem> getAllProblems() {

        return dsaProblemService.getAllProblems();
    }

    // Get problem by ID
    @GetMapping("/{id}")
    public ResponseEntity<DSAProblem> getProblemById(
            @PathVariable Long id) {

        try {
            return ResponseEntity.ok(
                    dsaProblemService.getProblemById(id)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get problems by platform
    @GetMapping("/platform/{platform}")
    public List<DSAProblem> getByPlatform(
            @PathVariable String platform) {

        return dsaProblemService.getByPlatform(platform);
    }

    // Get problems by topic
    @GetMapping("/topic/{topic}")
    public List<DSAProblem> getByTopic(
            @PathVariable String topic) {

        return dsaProblemService.getByTopic(topic);
    }

    // Get problems by difficulty
    @GetMapping("/difficulty/{difficulty}")
    public List<DSAProblem> getByDifficulty(
            @PathVariable String difficulty) {

        return dsaProblemService.getByDifficulty(
                difficulty
        );
    }

    // Get daily problems
    @GetMapping("/daily")
    public List<DSAProblem> getDailyProblems() {

        return dsaProblemService.getDailyProblems();
    }

    // Platform + difficulty
    @GetMapping("/filter")
    public List<DSAProblem> getByPlatformAndDifficulty(
            @RequestParam String platform,
            @RequestParam String difficulty) {

        return dsaProblemService
                .getByPlatformAndDifficulty(
                        platform,
                        difficulty
                );
    }

    // Update problem
    @PutMapping("/{id}")
    public ResponseEntity<DSAProblem> updateProblem(
            @PathVariable Long id,
            @RequestBody DSAProblem problem) {

        try {
            return ResponseEntity.ok(
                    dsaProblemService.updateProblem(
                            id,
                            problem
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete problem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProblem(
            @PathVariable Long id) {

        try {
            dsaProblemService.deleteProblem(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/daily/today")
    public List<DSAProblem> getTodayProblems() {

        return dsaProblemService.getTodayProblems();
    }
}