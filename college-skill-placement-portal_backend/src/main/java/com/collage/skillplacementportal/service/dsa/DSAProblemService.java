package com.collage.skillplacementportal.service.dsa;

import com.collage.skillplacementportal.entity.dsa.DSAProblem;
import com.collage.skillplacementportal.repository.dsa.DSAProblemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DSAProblemService {

    private final DSAProblemRepository dsaProblemRepository;

    public DSAProblemService(
            DSAProblemRepository dsaProblemRepository) {

        this.dsaProblemRepository = dsaProblemRepository;
    }

    // Create DSA problem
    public DSAProblem createProblem(DSAProblem problem) {
        return dsaProblemRepository.save(problem);
    }

    // Get all DSA problems
    public List<DSAProblem> getAllProblems() {
        return dsaProblemRepository.findAll();
    }

    // Get problem by ID
    public DSAProblem getProblemById(Long id) {
        return dsaProblemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "DSA problem not found with id: " + id
                        )
                );
    }

    // Get problems by platform
    public List<DSAProblem> getByPlatform(String platform) {
        return dsaProblemRepository.findByPlatform(
                platform.toUpperCase()
        );
    }

    // Get problems by topic
    public List<DSAProblem> getByTopic(String topic) {
        return dsaProblemRepository.findByTopic(
                topic.toUpperCase()
        );
    }

    // Get problems by difficulty
    public List<DSAProblem> getByDifficulty(
            String difficulty) {

        return dsaProblemRepository.findByDifficulty(
                difficulty.toUpperCase()
        );
    }

    // Get daily problems
    public List<DSAProblem> getDailyProblems() {
        return dsaProblemRepository
                .findByDailyProblemTrue();
    }

    // Get problems by platform and difficulty
    public List<DSAProblem> getByPlatformAndDifficulty(
            String platform,
            String difficulty) {

        return dsaProblemRepository
                .findByPlatformAndDifficulty(
                        platform.toUpperCase(),
                        difficulty.toUpperCase()
                );
    }

    // Update DSA problem
    public DSAProblem updateProblem(
            Long id,
            DSAProblem updatedProblem) {

        DSAProblem existingProblem =
                getProblemById(id);

        existingProblem.setTitle(
                updatedProblem.getTitle()
        );

        existingProblem.setDescription(
                updatedProblem.getDescription()
        );

        existingProblem.setPlatform(
                updatedProblem.getPlatform()
        );

        existingProblem.setProblemUrl(
                updatedProblem.getProblemUrl()
        );

        existingProblem.setTopic(
                updatedProblem.getTopic()
        );

        existingProblem.setDifficulty(
                updatedProblem.getDifficulty()
        );

        existingProblem.setPoints(
                updatedProblem.getPoints()
        );

        existingProblem.setDailyProblem(
                updatedProblem.getDailyProblem()
        );

        return dsaProblemRepository.save(
                existingProblem
        );
    }

    // Delete DSA problem
    public void deleteProblem(Long id) {

        if (!dsaProblemRepository.existsById(id)) {
            throw new RuntimeException(
                    "DSA problem not found with id: " + id
            );
        }

        dsaProblemRepository.deleteById(id);
    }
    public List<DSAProblem> getTodayProblems() {

        return dsaProblemRepository.findByDailyDate(
                LocalDate.now()
        );
    }
}