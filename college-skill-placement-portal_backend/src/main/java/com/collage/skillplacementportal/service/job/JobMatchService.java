package com.collage.skillplacementportal.service.job;

import com.collage.skillplacementportal.dto.job.JobMatchDTO;
import com.collage.skillplacementportal.dto.job.JobMatchSummaryDTO;
import com.collage.skillplacementportal.dto.job.JobSkillMatchDTO;
import com.collage.skillplacementportal.entity.job.Job;
import com.collage.skillplacementportal.entity.job.JobSkillRequirement;
import com.collage.skillplacementportal.entity.skill.StudentSkill;
import com.collage.skillplacementportal.repository.job.JobRepository;
import com.collage.skillplacementportal.repository.job.JobSkillRequirementRepository;
import com.collage.skillplacementportal.repository.skill.StudentSkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class JobMatchService {

    private final JobRepository jobRepository;

    private final JobSkillRequirementRepository requirementRepository;

    private final StudentSkillRepository studentSkillRepository;

    public JobMatchService(
            JobRepository jobRepository,
            JobSkillRequirementRepository requirementRepository,
            StudentSkillRepository studentSkillRepository) {

        this.jobRepository = jobRepository;
        this.requirementRepository = requirementRepository;
        this.studentSkillRepository = studentSkillRepository;
    }

    // Calculate match between a student and a job
    public JobMatchDTO calculateMatch(
            Long studentId,
            Long jobId) {

        // Find job
        Job job =
                jobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found with id: "
                                                + jobId
                                )
                        );

        // Get skills required by the job
        List<JobSkillRequirement> requirements =
                requirementRepository.findByJobId(jobId);

        // Get skills of the student
        List<StudentSkill> studentSkills =
                studentSkillRepository.findByStudentId(studentId);

        // Total number of skills required by the job
        int totalRequiredSkills =
                requirements.size();

        // Number of skills where student meets minimum level
        int matchedSkills = 0;

        // Total score obtained across all required skills
        double totalSkillScore = 0;

        // Compare each job requirement with student's skills
        for (JobSkillRequirement requirement :
                requirements) {

            // Skill required by the job
            Long requiredSkillId =
                    requirement.getSkill().getId();

            // Minimum skill level required by the job
            double minimumLevel =
                    requirement.getMinimumLevel();

            // Student's level for this skill
            double studentLevel = 0;

            // Find matching skill in student's skills
            for (StudentSkill studentSkill :
                    studentSkills) {

                if (studentSkill.getSkill().getId()
                        .equals(requiredSkillId)) {

                    studentLevel =
                            studentSkill.getSkillLevel();

                    break;
                }
            }

            // Calculate score for this individual skill
            double skillScore = 0;

            if (minimumLevel > 0) {

                skillScore =
                        (studentLevel / minimumLevel) * 100;

                // Maximum score for one skill is 100
                if (skillScore > 100) {
                    skillScore = 100;
                }
            }

            // Add this skill's score to total
            totalSkillScore += skillScore;

            // Check whether student meets minimum requirement
            if (studentLevel >= minimumLevel) {
                matchedSkills++;
            }
        }

        // Calculate overall match percentage
        double matchPercentage = 0;

        if (totalRequiredSkills > 0) {

            matchPercentage =
                    totalSkillScore
                            / totalRequiredSkills;
        }

        // Determine match status
        String matchStatus;

        if (matchPercentage >= 90) {

            matchStatus = "EXCELLENT_MATCH";

        } else if (matchPercentage >= 75) {

            matchStatus = "GOOD_MATCH";

        } else if (matchPercentage >= 60) {

            matchStatus = "POSSIBLE_MATCH";

        } else {

            matchStatus = "LOW_MATCH";
        }

        // Return result
        return new JobMatchDTO(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                matchPercentage,
                totalRequiredSkills,
                matchedSkills,
                matchStatus
        );
    }

    // Get all job matches for a student
    public List<JobMatchDTO> getMatchesForStudent(
            Long studentId) {

        // Get all active jobs
        List<Job> jobs =
                jobRepository.findByActiveTrue();

        List<JobMatchDTO> result =
                new ArrayList<>();

        // Calculate match for every active job
        for (Job job : jobs) {

            JobMatchDTO match =
                    calculateMatch(
                            studentId,
                            job.getId()
                    );

            result.add(match);
        }

        // Sort jobs by highest match percentage
        result.sort(
                Comparator.comparingDouble(
                        JobMatchDTO::getMatchPercentage
                ).reversed()
        );

        return result;
    }
    public List<JobMatchDTO> getRecommendedJobs(
            Long studentId) {

        List<JobMatchDTO> allMatches =
                getMatchesForStudent(studentId);

        List<JobMatchDTO> recommended =
                new ArrayList<>();

        for (JobMatchDTO match : allMatches) {

            if (match.getMatchPercentage() >= 75) {
                recommended.add(match);
            }
        }

        return recommended;
    }
    public List<JobSkillMatchDTO> getSkillMatches(
            Long studentId,
            Long jobId) {

        if (!jobRepository.existsById(jobId)) {
            throw new RuntimeException(
                    "Job not found with id: " + jobId
            );
        }

        List<JobSkillRequirement> requirements =
                requirementRepository.findByJobId(
                        jobId
                );

        List<StudentSkill> studentSkills =
                studentSkillRepository.findByStudentId(
                        studentId
                );

        List<JobSkillMatchDTO> result =
                new ArrayList<>();

        for (JobSkillRequirement requirement :
                requirements) {

            Long skillId =
                    requirement.getSkill().getId();

            String skillName =
                    requirement.getSkill().getName();

            double requiredLevel =
                    requirement.getMinimumLevel();

            double studentLevel = 0;

            for (StudentSkill studentSkill :
                    studentSkills) {

                if (studentSkill.getSkill().getId()
                        .equals(skillId)) {

                    studentLevel =
                            studentSkill.getSkillLevel();

                    break;
                }
            }

            double skillMatchPercentage = 0;

            if (requiredLevel > 0) {

                skillMatchPercentage =
                        (studentLevel / requiredLevel)
                                * 100;

                if (skillMatchPercentage > 100) {
                    skillMatchPercentage = 100;
                }
            }

            boolean matched =
                    studentLevel >= requiredLevel;

            result.add(
                    new JobSkillMatchDTO(
                            skillId,
                            skillName,
                            studentLevel,
                            requiredLevel,
                            skillMatchPercentage,
                            matched
                    )
            );
        }

        return result;
    }
    public JobMatchSummaryDTO getJobMatchSummary(Long studentId) {

        List<JobMatchDTO> matches = getMatchesForStudent(studentId);

        if (matches.isEmpty()) {
            return new JobMatchSummaryDTO(
                    0,
                    0.0,
                    0.0
            );
        }

        int recommendedJobs = 0;
        double bestMatchPercentage = 0.0;
        double totalMatchPercentage = 0.0;

        for (JobMatchDTO match : matches) {

            double percentage = match.getMatchPercentage();

            if (percentage >= 75) {
                recommendedJobs++;
            }

            if (percentage > bestMatchPercentage) {
                bestMatchPercentage = percentage;
            }

            totalMatchPercentage += percentage;
        }

        double averageMatchPercentage =
                totalMatchPercentage / matches.size();

        return new JobMatchSummaryDTO(
                recommendedJobs,
                bestMatchPercentage,
                averageMatchPercentage
        );
    }
}