package com.collage.skillplacementportal.dto.assessment;

public class AssessmentProgressDTO {

    private Long assessmentId;
    private String status;
    private Integer score;
    private Integer totalMarks;
    private Double percentage;

    public AssessmentProgressDTO() {
    }

    public AssessmentProgressDTO(
            Long assessmentId,
            String status,
            Integer score,
            Integer totalMarks,
            Double percentage) {

        this.assessmentId = assessmentId;
        this.status = status;
        this.score = score;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
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
}