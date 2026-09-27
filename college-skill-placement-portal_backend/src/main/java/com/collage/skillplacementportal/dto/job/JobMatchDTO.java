package com.collage.skillplacementportal.dto.job;

public class JobMatchDTO {

    private Long jobId;

    private String jobTitle;

    private String company;

    private double matchPercentage;

    private int totalRequiredSkills;

    private int matchedSkills;

    private String matchStatus;


    public JobMatchDTO() {
    }

    public JobMatchDTO(
            Long jobId,
            String jobTitle,
            String company,
            double matchPercentage,
            int totalRequiredSkills,
            int matchedSkills,
            String matchStatus) {

        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.matchPercentage = matchPercentage;
        this.totalRequiredSkills = totalRequiredSkills;
        this.matchedSkills = matchedSkills;
        this.matchStatus=matchStatus;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public int getTotalRequiredSkills() {
        return totalRequiredSkills;
    }

    public void setTotalRequiredSkills(
            int totalRequiredSkills) {

        this.totalRequiredSkills =
                totalRequiredSkills;
    }

    public int getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(int matchedSkills) {
        this.matchedSkills = matchedSkills;
    }
}