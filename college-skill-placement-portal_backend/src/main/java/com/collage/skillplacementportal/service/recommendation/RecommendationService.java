package com.collage.skillplacementportal.service.recommendation;

import com.collage.skillplacementportal.dto.recommendation.RecommendationDTO;
import com.collage.skillplacementportal.entity.dsa.DSAProblem;
import com.collage.skillplacementportal.entity.dsa.SkillTopicMapping;
import com.collage.skillplacementportal.entity.dsa.StudentDSAProgress;
import com.collage.skillplacementportal.entity.skill.StudentSkill;
import com.collage.skillplacementportal.repository.dsa.DSAProblemRepository;
import com.collage.skillplacementportal.repository.dsa.SkillTopicMappingRepository;
import com.collage.skillplacementportal.repository.dsa.StudentDSAProgressRepository;
import com.collage.skillplacementportal.repository.skill.StudentSkillRepository;

import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecommendationService {

    private final DSAProblemRepository dsaProblemRepository;
    private final StudentDSAProgressRepository progressRepository;
    private final StudentSkillRepository studentSkillRepository;

    private final SkillTopicMappingRepository mappingRepository;

    public RecommendationService(
            DSAProblemRepository dsaProblemRepository,
            StudentDSAProgressRepository progressRepository,
            StudentSkillRepository studentSkillRepository,
            SkillTopicMappingRepository mappingRepository) {

        this.dsaProblemRepository = dsaProblemRepository;
        this.progressRepository = progressRepository;
        this.studentSkillRepository = studentSkillRepository;
        this.mappingRepository = mappingRepository;
    }

    public List<RecommendationDTO> getRecommendations(
            Long studentId,
            String topic) {

        List<StudentDSAProgress> solved =
                progressRepository
                        .findByStudentIdAndSolvedTrue(
                                studentId
                        );

        Set<Long> solvedProblemIds =
                new HashSet<>();

        for (StudentDSAProgress progress : solved) {
            solvedProblemIds.add(
                    progress.getProblem().getId()
            );
        }

        List<DSAProblem> problems =
                dsaProblemRepository.findByTopic(
                        topic.toUpperCase()
                );

        List<RecommendationDTO> result =
                new ArrayList<>();

        for (DSAProblem problem : problems) {

            if (solvedProblemIds.contains(
                    problem.getId())) {
                continue;
            }

            result.add(
                    new RecommendationDTO(
                            problem.getId(),
                            problem.getTitle(),
                            problem.getPlatform(),
                            problem.getTopic(),
                            problem.getDifficulty(),
                            problem.getPoints(),
                            problem.getProblemUrl()
                    )
            );
        }

        return result;
    }
    public List<RecommendationDTO> getAutomaticRecommendations(
            Long studentId) {

        List<StudentSkill> studentSkills =
                studentSkillRepository.findByStudentId(
                        studentId
                );

        List<StudentDSAProgress> solved =
                progressRepository
                        .findByStudentIdAndSolvedTrue(
                                studentId
                        );

        Set<Long> solvedProblemIds =
                new HashSet<>();

        for (StudentDSAProgress progress : solved) {

            solvedProblemIds.add(
                    progress.getProblem().getId()
            );
        }

        List<RecommendationDTO> result =
                new ArrayList<>();

        Set<Long> addedProblemIds =
                new HashSet<>();

        for (StudentSkill studentSkill : studentSkills) {

            double level =
                    studentSkill.getSkillLevel();

            // Only weak / needs-improvement skills
            if (level >= 60) {
                continue;
            }

            Long skillId =
                    studentSkill.getSkill().getId();

            List<SkillTopicMapping> mappings =
                    mappingRepository.findBySkillId(
                            skillId
                    );

            for (SkillTopicMapping mapping : mappings) {

                String topic =
                        mapping.getTopic();

                List<DSAProblem> problems =
                        dsaProblemRepository.findByTopic(
                                topic.toUpperCase()
                        );

                for (DSAProblem problem : problems) {

                    // Already solved
                    if (solvedProblemIds.contains(
                            problem.getId())) {
                        continue;
                    }

                    // Already recommended
                    if (addedProblemIds.contains(
                            problem.getId())) {
                        continue;
                    }

                    result.add(
                            new RecommendationDTO(
                                    problem.getId(),
                                    problem.getTitle(),
                                    problem.getPlatform(),
                                    problem.getTopic(),
                                    problem.getDifficulty(),
                                    problem.getPoints(),
                                    problem.getProblemUrl()
                            )
                    );

                    addedProblemIds.add(
                            problem.getId()
                    );
                }
            }
        }
        result.sort(
                Comparator.comparingInt(
                        recommendation ->
                                getDifficultyOrder(
                                        recommendation.getDifficulty()
                                )
                )
        );

        if (result.size() > 5) {
            return result.subList(0, 5);
        }

        return result;
    }

    private int getDifficultyOrder(String difficulty) {

        if (difficulty == null) {
            return 99;
        }

        switch (difficulty.toUpperCase()) {

            case "EASY":
                return 1;

            case "MEDIUM":
                return 2;

            case "HARD":
                return 3;

            default:
                return 99;
        }
    }
}