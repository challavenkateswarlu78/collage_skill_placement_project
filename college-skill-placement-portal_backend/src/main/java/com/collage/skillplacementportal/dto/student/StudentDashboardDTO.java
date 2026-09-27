package com.collage.skillplacementportal.dto.student;

import com.collage.skillplacementportal.dto.assessment.AssessmentProgressDTO;
import com.collage.skillplacementportal.dto.assessment.AssessmentSummaryDTO;
import com.collage.skillplacementportal.dto.dsa.DSAProgressDTO;
import com.collage.skillplacementportal.dto.job.ApplicationSummaryDTO;
import com.collage.skillplacementportal.dto.job.JobMatchDTO;
import com.collage.skillplacementportal.dto.job.JobMatchSummaryDTO;
import com.collage.skillplacementportal.dto.recommendation.RecommendationDTO;
import com.collage.skillplacementportal.dto.skill.SkillGapDTO;

import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.entity.skill.StudentSkill;

import java.util.List;

public class StudentDashboardDTO {

    private Student student;
    private List<StudentSkill> skills;
    private List<SkillGapDTO> skillGaps;
    private List<RecommendationDTO> recommendations;
    private DSAProgressDTO dsaProgress;
    private List<AssessmentProgressDTO> assessmentProgress;
    private List<JobMatchDTO> recommendedJobs;
    private AssessmentSummaryDTO assessmentSummary;

    private ApplicationSummaryDTO applicationSummary;
    private JobMatchSummaryDTO jobMatchSummary;
    private PlacementReadinessDTO placementReadiness;
    public StudentDashboardDTO() {
    }

    public StudentDashboardDTO(
            Student student,
            List<StudentSkill> skills,
            List<SkillGapDTO> skillGaps,
            List<RecommendationDTO> recommendations,
            DSAProgressDTO dsaProgress,
            List<AssessmentProgressDTO> assessmentProgress,
            ApplicationSummaryDTO applicationSummary,
            List<JobMatchDTO> recommendedJobs,
            AssessmentSummaryDTO assessmentSummary,
            JobMatchSummaryDTO jobMatchSummary,
            PlacementReadinessDTO placementReadiness) {

        this.student = student;
        this.skills = skills;
        this.skillGaps = skillGaps;
        this.recommendations = recommendations;
        this.dsaProgress = dsaProgress;
        this.assessmentProgress = assessmentProgress;
        this.applicationSummary = applicationSummary;
        this.recommendedJobs = recommendedJobs;
        this.assessmentSummary = assessmentSummary;
        this.jobMatchSummary = jobMatchSummary;
        this.placementReadiness = placementReadiness;
    }

    public List<SkillGapDTO> getSkillGaps() {
        return skillGaps;
    }

    public void setSkillGaps(List<SkillGapDTO> skillGaps) {
        this.skillGaps = skillGaps;
    }

    public List<RecommendationDTO> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationDTO> recommendations) {
        this.recommendations = recommendations;
    }

    public DSAProgressDTO getDsaProgress() {
        return dsaProgress;
    }

    public List<AssessmentProgressDTO> getAssessmentProgress() {
        return assessmentProgress;
    }

    public void setAssessmentProgress(List<AssessmentProgressDTO> assessmentProgress) {
        this.assessmentProgress = assessmentProgress;
    }

    public ApplicationSummaryDTO getApplicationSummary() {
        return applicationSummary;
    }

    public void setApplicationSummary(ApplicationSummaryDTO applicationSummary) {
        this.applicationSummary = applicationSummary;
    }

    public AssessmentSummaryDTO getAssessmentSummary() {
        return assessmentSummary;
    }

    public void setAssessmentSummary(AssessmentSummaryDTO assessmentSummary) {
        this.assessmentSummary = assessmentSummary;
    }

    public PlacementReadinessDTO getPlacementReadiness() {
        return placementReadiness;
    }

    public void setPlacementReadiness(PlacementReadinessDTO placementReadiness) {
        this.placementReadiness = placementReadiness;
    }

    public List<JobMatchDTO> getRecommendedJobs() {
        return recommendedJobs;
    }

    public void setRecommendedJobs(List<JobMatchDTO> recommendedJobs) {
        this.recommendedJobs = recommendedJobs;
    }

    public JobMatchSummaryDTO getJobMatchSummary() {
        return jobMatchSummary;
    }

    public void setJobMatchSummary(JobMatchSummaryDTO jobMatchSummary) {
        this.jobMatchSummary = jobMatchSummary;
    }

    public void setDsaProgress(DSAProgressDTO dsaProgress) {
        this.dsaProgress = dsaProgress;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<StudentSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<StudentSkill> skills) {
        this.skills = skills;
    }
}