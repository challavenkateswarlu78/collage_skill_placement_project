package com.collage.skillplacementportal.dto.admin;

public class DepartmentPlacementDTO {

    private String department;

    private long totalStudents;

    private long selectedStudents;

    private double placementPercentage;



    public DepartmentPlacementDTO() {
    }

    public DepartmentPlacementDTO(
            String department,
            long totalStudents,
            long selectedStudents,
            double placementPercentage) {

        this.department = department;
        this.totalStudents = totalStudents;
        this.selectedStudents = selectedStudents;
        this.placementPercentage = placementPercentage;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
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
}