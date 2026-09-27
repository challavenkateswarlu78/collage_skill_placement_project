package com.collage.skillplacementportal.dto.student;

public class PlacementReadinessDTO {

    private String status;
    private double score;

    private double assessmentContribution;
    private double jobMatchContribution;
    private double dsaContribution;

    private int skillsNeedingImprovement;

    public PlacementReadinessDTO() {
    }

    public PlacementReadinessDTO(
            String status,
            double score,
            double assessmentContribution,
            double jobMatchContribution,
            double dsaContribution,
            int skillsNeedingImprovement) {

        this.status = status;
        this.score = score;
        this.assessmentContribution = assessmentContribution;
        this.jobMatchContribution = jobMatchContribution;
        this.dsaContribution = dsaContribution;
        this.skillsNeedingImprovement = skillsNeedingImprovement;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    public double getAssessmentContribution() {
        return assessmentContribution;
    }

    public void setAssessmentContribution(
            double assessmentContribution) {

        this.assessmentContribution = assessmentContribution;
    }

    public double getJobMatchContribution() {
        return jobMatchContribution;
    }

    public void setJobMatchContribution(
            double jobMatchContribution) {

        this.jobMatchContribution = jobMatchContribution;
    }

    public double getDsaContribution() {
        return dsaContribution;
    }

    public void setDsaContribution(
            double dsaContribution) {

        this.dsaContribution = dsaContribution;
    }
    public int getSkillsNeedingImprovement() {
        return skillsNeedingImprovement;
    }

    public void setSkillsNeedingImprovement(
            int skillsNeedingImprovement) {

        this.skillsNeedingImprovement =
                skillsNeedingImprovement;
    }
}