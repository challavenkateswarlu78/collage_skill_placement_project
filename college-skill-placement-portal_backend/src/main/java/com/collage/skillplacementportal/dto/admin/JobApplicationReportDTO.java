package com.collage.skillplacementportal.dto.admin;

public class JobApplicationReportDTO {

    private Long jobId;
    private String jobTitle;
    private String company;
    private long totalApplications;
    private long selectedApplications;

    public JobApplicationReportDTO() {
    }

    public JobApplicationReportDTO(
            Long jobId,
            String jobTitle,
            String company,
            long totalApplications,
            long selectedApplications) {

        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.totalApplications = totalApplications;
        this.selectedApplications = selectedApplications;
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

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getSelectedApplications() {
        return selectedApplications;
    }

    public void setSelectedApplications(long selectedApplications) {
        this.selectedApplications = selectedApplications;
    }
}