package com.collage.skillplacementportal.dto.admin;

import java.util.List;

public class AdminDashboardDTO {

    private long totalStudents;

    private long totalJobs;

    private long totalApplications;

    private long selectedStudents;

    private double placementPercentage;

    private List<DepartmentPlacementDTO> departmentStatistics;
    private List<TopStudentDTO> topStudents;
    private long activeJobs;
    private ApplicationStatusStatisticsDTO applicationStatusStatistics;
    public AdminDashboardDTO() {
    }

    public AdminDashboardDTO(
            long totalStudents,
            long totalJobs,
            long activeJobs,
            long totalApplications,
            long selectedStudents,
            double placementPercentage,
            List<DepartmentPlacementDTO> departmentStatistics,
            List<TopStudentDTO> topStudents,
            ApplicationStatusStatisticsDTO applicationStatusStatistics) {

        this.totalStudents = totalStudents;
        this.totalJobs = totalJobs;
        this.activeJobs = activeJobs;
        this.totalApplications = totalApplications;
        this.selectedStudents = selectedStudents;
        this.placementPercentage = placementPercentage;
        this.departmentStatistics=departmentStatistics;
        this.topStudents = topStudents;
        this.applicationStatusStatistics = applicationStatusStatistics;

    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(long totalJobs) {
        this.totalJobs = totalJobs;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getSelectedStudents() {
        return selectedStudents;
    }

    public void setSelectedStudents(long selectedStudents) {
        this.selectedStudents = selectedStudents;
    }

    public double getPlacementPercentage() {
        return placementPercentage;
    }

    public void setPlacementPercentage(
            double placementPercentage) {

        this.placementPercentage = placementPercentage;
    }
    public List<DepartmentPlacementDTO> getDepartmentStatistics() {
        return departmentStatistics;
    }

    public void setDepartmentStatistics(
            List<DepartmentPlacementDTO> departmentStatistics) {

        this.departmentStatistics = departmentStatistics;
    }
    public List<TopStudentDTO> getTopStudents() {
        return topStudents;
    }

    public void setTopStudents(List<TopStudentDTO> topStudents) {
        this.topStudents = topStudents;
    }
    public long getActiveJobs() {
        return activeJobs;
    }

    public void setActiveJobs(long activeJobs) {
        this.activeJobs = activeJobs;
    }

    public ApplicationStatusStatisticsDTO getApplicationStatusStatistics() {
        return applicationStatusStatistics;
    }

    public void setApplicationStatusStatistics(ApplicationStatusStatisticsDTO applicationStatusStatistics) {
        this.applicationStatusStatistics = applicationStatusStatistics;
    }
}