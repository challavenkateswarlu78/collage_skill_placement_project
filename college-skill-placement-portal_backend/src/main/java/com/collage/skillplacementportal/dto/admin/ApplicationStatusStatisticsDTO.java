package com.collage.skillplacementportal.dto.admin;

public class ApplicationStatusStatisticsDTO {

    private long applied;
    private long shortlisted;
    private long interview;
    private long selected;
    private long rejected;

    public ApplicationStatusStatisticsDTO() {
    }

    public ApplicationStatusStatisticsDTO(
            long applied,
            long shortlisted,
            long interview,
            long selected,
            long rejected) {

        this.applied = applied;
        this.shortlisted = shortlisted;
        this.interview = interview;
        this.selected = selected;
        this.rejected = rejected;
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