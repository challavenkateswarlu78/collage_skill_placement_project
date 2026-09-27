package com.collage.skillplacementportal.dto.job;

public class ApplicationSummaryDTO {

    private int totalApplications;

    private int applied;

    private int shortlisted;

    private int interview;

    private int selected;

    private int rejected;

    public ApplicationSummaryDTO() {
    }

    public ApplicationSummaryDTO(
            int totalApplications,
            int applied,
            int shortlisted,
            int interview,
            int selected,
            int rejected) {

        this.totalApplications = totalApplications;
        this.applied = applied;
        this.shortlisted = shortlisted;
        this.interview = interview;
        this.selected = selected;
        this.rejected = rejected;
    }

    public int getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(
            int totalApplications) {
        this.totalApplications = totalApplications;
    }

    public int getApplied() {
        return applied;
    }

    public void setApplied(int applied) {
        this.applied = applied;
    }

    public int getShortlisted() {
        return shortlisted;
    }

    public void setShortlisted(int shortlisted) {
        this.shortlisted = shortlisted;
    }

    public int getInterview() {
        return interview;
    }

    public void setInterview(int interview) {
        this.interview = interview;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getRejected() {
        return rejected;
    }

    public void setRejected(int rejected) {
        this.rejected = rejected;
    }
}