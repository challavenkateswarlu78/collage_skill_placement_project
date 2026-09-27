package com.collage.skillplacementportal.dto.admin;

public class CGPAStatisticsDTO {

    private double averageCgpa;
    private double highestCgpa;
    private double lowestCgpa;

    public CGPAStatisticsDTO() {
    }

    public CGPAStatisticsDTO(
            double averageCgpa,
            double highestCgpa,
            double lowestCgpa) {

        this.averageCgpa = averageCgpa;
        this.highestCgpa = highestCgpa;
        this.lowestCgpa = lowestCgpa;
    }

    public double getAverageCgpa() {
        return averageCgpa;
    }

    public void setAverageCgpa(double averageCgpa) {
        this.averageCgpa = averageCgpa;
    }

    public double getHighestCgpa() {
        return highestCgpa;
    }

    public void setHighestCgpa(double highestCgpa) {
        this.highestCgpa = highestCgpa;
    }

    public double getLowestCgpa() {
        return lowestCgpa;
    }

    public void setLowestCgpa(double lowestCgpa) {
        this.lowestCgpa = lowestCgpa;
    }
}