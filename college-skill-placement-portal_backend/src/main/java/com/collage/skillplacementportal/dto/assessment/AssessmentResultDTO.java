package com.collage.skillplacementportal.dto.assessment;

import java.time.LocalDateTime;

public class AssessmentResultDTO {

    private Long attemptId;
    private Long assessmentId;
    private String status;
    private Integer score;
    private Integer totalMarks;
    private Double percentage;
    private LocalDateTime submittedAt;

    public AssessmentResultDTO() {
    }

    public AssessmentResultDTO(
            Long attemptId,
            Long assessmentId,
            String status,
            Integer score,
            Integer totalMarks,
            Double percentage,
            LocalDateTime submittedAt) {

        this.attemptId = attemptId;
        this.assessmentId = assessmentId;
        this.status = status;
        this.score = score;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.submittedAt = submittedAt;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
