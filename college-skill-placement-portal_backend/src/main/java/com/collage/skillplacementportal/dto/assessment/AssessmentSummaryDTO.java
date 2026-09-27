package com.collage.skillplacementportal.dto.assessment;

public class AssessmentSummaryDTO {

    private int totalAssessmentsTaken;
    private int completedAssessments;
    private double averageScore;

    public AssessmentSummaryDTO() {
    }

    public AssessmentSummaryDTO(
            int totalAssessmentsTaken,
            int completedAssessments,
            double averageScore) {

        this.totalAssessmentsTaken = totalAssessmentsTaken;
        this.completedAssessments = completedAssessments;
        this.averageScore = averageScore;
    }

    public int getTotalAssessmentsTaken() {
        return totalAssessmentsTaken;
    }

    public void setTotalAssessmentsTaken(
            int totalAssessmentsTaken) {

        this.totalAssessmentsTaken =
                totalAssessmentsTaken;
    }

    public int getCompletedAssessments() {
        return completedAssessments;
    }

    public void setCompletedAssessments(
            int completedAssessments) {

        this.completedAssessments =
                completedAssessments;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }
}