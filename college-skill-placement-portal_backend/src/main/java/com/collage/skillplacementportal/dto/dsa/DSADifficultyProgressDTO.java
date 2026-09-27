package com.collage.skillplacementportal.dto.dsa;

public class DSADifficultyProgressDTO {

    private String difficulty;

    private int totalProblems;

    private int solvedProblems;

    private int totalPoints;

    private double progressPercentage;

    public DSADifficultyProgressDTO() {
    }

    public DSADifficultyProgressDTO(
            String difficulty,
            int totalProblems,
            int solvedProblems,
            int totalPoints,
            double progressPercentage) {

        this.difficulty = difficulty;
        this.totalProblems = totalProblems;
        this.solvedProblems = solvedProblems;
        this.totalPoints = totalPoints;
        this.progressPercentage = progressPercentage;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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