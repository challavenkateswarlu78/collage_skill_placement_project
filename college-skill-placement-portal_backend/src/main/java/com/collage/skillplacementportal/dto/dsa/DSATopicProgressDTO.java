package com.collage.skillplacementportal.dto.dsa;

public class DSATopicProgressDTO {

    private String topic;

    private int totalProblems;

    private int solvedProblems;

    private int totalPoints;

    private double progressPercentage;

    public DSATopicProgressDTO() {
    }

    public DSATopicProgressDTO(
            String topic,
            int totalProblems,
            int solvedProblems,
            int totalPoints,
            double progressPercentage) {

        this.topic = topic;
        this.totalProblems = totalProblems;
        this.solvedProblems = solvedProblems;
        this.totalPoints = totalPoints;
        this.progressPercentage = progressPercentage;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public void setProgressPercentage(
            double progressPercentage) {

        this.progressPercentage = progressPercentage;
    }
}