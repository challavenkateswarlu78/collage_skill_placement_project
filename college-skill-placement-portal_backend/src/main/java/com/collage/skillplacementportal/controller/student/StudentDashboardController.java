package com.collage.skillplacementportal.controller.student;

import com.collage.skillplacementportal.dto.assessment.AssessmentProgressDTO;
import com.collage.skillplacementportal.dto.assessment.AssessmentSummaryDTO;
import com.collage.skillplacementportal.dto.dsa.DSAProgressDTO;
import com.collage.skillplacementportal.dto.job.ApplicationSummaryDTO;
import com.collage.skillplacementportal.dto.job.JobMatchDTO;
import com.collage.skillplacementportal.dto.job.JobMatchSummaryDTO;
import com.collage.skillplacementportal.dto.recommendation.RecommendationDTO;
import com.collage.skillplacementportal.dto.skill.SkillGapDTO;
import com.collage.skillplacementportal.dto.student.PlacementReadinessDTO;
import com.collage.skillplacementportal.dto.student.StudentDashboardDTO;
import com.collage.skillplacementportal.entity.assessment.attempt.AssessmentAttempt;
import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.entity.skill.StudentSkill;
import com.collage.skillplacementportal.repository.skill.StudentSkillRepository;
import com.collage.skillplacementportal.repository.student.StudentRepository;
import com.collage.skillplacementportal.service.assessment.attempt.AssessmentAttemptService;
import com.collage.skillplacementportal.service.dsa.StudentDSAProgressService;
import com.collage.skillplacementportal.service.job.JobApplicationService;
import com.collage.skillplacementportal.service.job.JobMatchService;
import com.collage.skillplacementportal.service.recommendation.RecommendationService;
import com.collage.skillplacementportal.service.security.AuthorizationService;

import com.collage.skillplacementportal.service.skill.SkillGapService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentDashboardController {

    private final StudentRepository studentRepository;
    private final StudentSkillRepository studentSkillRepository;
    private final AuthorizationService authorizationService;
    private final SkillGapService skillGapService;
    private final RecommendationService recommendationService;
    private final StudentDSAProgressService studentDSAProgressService;
    private final AssessmentAttemptService assessmentAttemptService;
    private final JobApplicationService jobApplicationService;
    private final JobMatchService jobMatchService;

    public StudentDashboardController(
            StudentRepository studentRepository,
            StudentSkillRepository studentSkillRepository,
            AuthorizationService authorizationService,
            SkillGapService skillGapService,
            RecommendationService recommendationService,
            StudentDSAProgressService studentDSAProgressService,
            AssessmentAttemptService assessmentAttemptService,
            JobApplicationService jobApplicationService,
            JobMatchService jobMatchService) {

        this.studentRepository = studentRepository;
        this.studentSkillRepository = studentSkillRepository;
        this.authorizationService = authorizationService;
        this.skillGapService = skillGapService;
        this.recommendationService = recommendationService;
        this.studentDSAProgressService = studentDSAProgressService;
        this.assessmentAttemptService = assessmentAttemptService;
        this.jobApplicationService = jobApplicationService;
        this.jobMatchService = jobMatchService;
    }

    @GetMapping("/dashboard")
    public StudentDashboardDTO getDashboard() {

        Long studentId =
                authorizationService.getCurrentStudentId();

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student profile not found"
                                )
                        );


        List<StudentSkill> skills =
                studentSkillRepository.findByStudentId(studentId);
        List<SkillGapDTO> skillGaps =
                skillGapService.getSkillGaps(studentId);
        int skillsNeedingImprovement = 0;

        for (SkillGapDTO skillGap : skillGaps) {

            if (skillGap.getStatus() != null
                    && skillGap.getStatus()
                    .equalsIgnoreCase("NEEDS_IMPROVEMENT")) {

                skillsNeedingImprovement++;
            }
        }
        List<RecommendationDTO> recommendations =
                recommendationService.getAutomaticRecommendations(studentId);
        DSAProgressDTO dsaProgress =
                studentDSAProgressService.getStudentSummary(studentId);
        List<AssessmentAttempt> attempts =
                assessmentAttemptService.getStudentAttempts(studentId);


        List<AssessmentProgressDTO> assessmentProgress =
                new ArrayList<>();
        for (AssessmentAttempt attempt : attempts) {

            double percentage = 0;

            if (attempt.getTotalMarks() != null
                    && attempt.getTotalMarks() > 0
                    && attempt.getScore() != null) {

                percentage =
                        ((double) attempt.getScore()
                                / attempt.getTotalMarks()) * 100;
            }

            assessmentProgress.add(
                    new AssessmentProgressDTO(
                            attempt.getAssessment().getId(),
                            attempt.getStatus(),
                            attempt.getScore(),
                            attempt.getTotalMarks(),
                            percentage
                    )
            );
        }
        ApplicationSummaryDTO applicationSummary =
                jobApplicationService.getApplicationSummary(studentId);
        List<JobMatchDTO> recommendedJobs =
                jobMatchService.getRecommendedJobs(studentId);
        JobMatchSummaryDTO jobMatchSummary =
                jobMatchService.getJobMatchSummary(studentId);
        AssessmentSummaryDTO assessmentSummary =
                assessmentAttemptService.getAssessmentSummary(
                        studentId
                );
        double dsaProgressPercentage =
                studentDSAProgressService.getDSAProgressPercentage(
                        studentId
                );

        PlacementReadinessDTO placementReadiness =
                calculatePlacementReadiness(
                        assessmentSummary,
                        jobMatchSummary,
                        dsaProgressPercentage,
                        skillsNeedingImprovement
                );

        return new StudentDashboardDTO(
                student,
                skills,
                skillGaps,
                recommendations,
                dsaProgress,
                assessmentProgress,
                applicationSummary,
                recommendedJobs,
                assessmentSummary,
                jobMatchSummary,
                placementReadiness
        );
    }
    private PlacementReadinessDTO calculatePlacementReadiness(
            AssessmentSummaryDTO assessmentSummary,
            JobMatchSummaryDTO jobMatchSummary,
            double dsaProgressPercentage,
            int skillsNeedingImprovement) {

        double totalScore = 0.0;
        double totalWeight = 0.0;

        double assessmentContribution = 0.0;
        double jobMatchContribution = 0.0;
        double dsaContribution = 0.0;

        if (assessmentSummary.getCompletedAssessments() > 0) {

            assessmentContribution =
                    assessmentSummary.getAverageScore() * 0.40;

            totalScore += assessmentContribution;
            totalWeight += 0.40;
        }

        if (jobMatchSummary.getRecommendedJobs() > 0) {

            jobMatchContribution =
                    jobMatchSummary.getAverageMatchPercentage() * 0.40;

            totalScore += jobMatchContribution;
            totalWeight += 0.40;
        }

        dsaContribution =
                dsaProgressPercentage * 0.20;

        totalScore += dsaContribution;
        totalWeight += 0.20;

        double readinessScore = 0.0;

        if (totalWeight > 0) {
            readinessScore =
                    totalScore / totalWeight;
        }

        String status;

        if (readinessScore >= 85) {
            status = "EXCELLENT";
        } else if (readinessScore >= 70) {
            status = "GOOD";
        } else if (readinessScore >= 50) {
            status = "AVERAGE";
        } else {
            status = "NEEDS_IMPROVEMENT";
        }

        return new PlacementReadinessDTO(
                status,
                readinessScore,
                assessmentContribution,
                jobMatchContribution,
                dsaContribution,
                skillsNeedingImprovement
        );
    }
}