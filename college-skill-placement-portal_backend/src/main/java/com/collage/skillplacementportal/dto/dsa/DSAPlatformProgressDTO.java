package com.collage.skillplacementportal.dto.dsa;

public class DSAPlatformProgressDTO {

    private String platform;

    private int totalProblems;

    private int solvedProblems;

    private int totalPoints;

    private double progressPercentage;

    public DSAPlatformProgressDTO() {
    }

    public DSAPlatformProgressDTO(
            String platform,
            int totalProblems,
            int solvedProblems,
            int totalPoints,
            double progressPercentage) {

        this.platform = platform;
        this.totalProblems = totalProblems;
        this.solvedProblems = solvedProblems;
        this.totalPoints = totalPoints;
        this.progressPercentage = progressPercentage;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getTotalProblems() {
        return totalProblems;
    }

    public void setTotalProblems(int totalProblems) {
        this.totalProblems = totalProblems;
    }

    public int getSolvedProblems() {
        return solvedProblems;
    }

    public void setSolvedProblems(int solvedProblems) {
        this.solvedProblems = solvedProblems;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}