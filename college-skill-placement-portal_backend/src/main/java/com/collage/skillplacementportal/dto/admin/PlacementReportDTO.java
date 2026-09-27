package com.collage.skillplacementportal.dto.admin;

public class PlacementReportDTO {

    private long totalStudents;
    private long placedStudents;
    private long unplacedStudents;
    private double placementPercentage;

    public PlacementReportDTO() {
    }

    public PlacementReportDTO(
            long totalStudents,
            long placedStudents,
            long unplacedStudents,
            double placementPercentage) {

        this.totalStudents = totalStudents;
        this.placedStudents = placedStudents;
        this.unplacedStudents = unplacedStudents;
        this.placementPercentage = placementPercentage;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getPlacedStudents() {
        return placedStudents;
    }

    public void setPlacedStudents(long placedStudents) {
        this.placedStudents = placedStudents;
    }

    public long getUnplacedStudents() {
        return unplacedStudents;
    }

    public void setUnplacedStudents(long unplacedStudents) {
        this.unplacedStudents = unplacedStudents;
    }

    public double getPlacementPercentage() {
        return placementPercentage;
    }

    public void setPlacementPercentage(double placementPercentage) {
        this.placementPercentage = placementPercentage;
    }
}