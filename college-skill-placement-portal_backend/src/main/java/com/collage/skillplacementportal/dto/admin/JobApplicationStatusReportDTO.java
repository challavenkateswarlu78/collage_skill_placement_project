package com.collage.skillplacementportal.dto.admin;

public class JobApplicationStatusReportDTO {

    private Long jobId;
    private String jobTitle;
    private String company;

    private long applied;
    private long shortlisted;
    private long interview;
    private long selected;
    private long rejected;

    public JobApplicationStatusReportDTO() {
    }

    public JobApplicationStatusReportDTO(
            Long jobId,
            String jobTitle,
            String company,
            long applied,
            long shortlisted,
            long interview,
            long selected,
            long rejected) {

        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.applied = applied;
        this.shortlisted = shortlisted;
        this.interview = interview;
        this.selected = selected;
        this.rejected = rejected;
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

    public long getApplied() {
        return applied;
    }

    public void setApplied(long applied) {
        this.applied = applied;
    }

    public long getShortlisted() {
        return shortlisted;
    }

    public void setShortlisted(long shortlisted) {
        this.shortlisted = shortlisted;
    }

    public long getInterview() {
        return interview;
    }

    public void setInterview(long interview) {
        this.interview = interview;
    }

    public long getSelected() {
        return selected;
    }

    public void setSelected(long selected) {
        this.selected = selected;
    }

    public long getRejected() {
        return rejected;
    }

    public void setRejected(long rejected) {
        this.rejected = rejected;
    }
}