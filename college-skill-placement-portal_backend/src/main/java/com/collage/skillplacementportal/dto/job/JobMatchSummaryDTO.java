package com.collage.skillplacementportal.dto.job;

public class JobMatchSummaryDTO {

    private int recommendedJobs;

    private double bestMatchPercentage;

    private double averageMatchPercentage;

    public JobMatchSummaryDTO() {
    }

    public JobMatchSummaryDTO(
            int recommendedJobs,
            double bestMatchPercentage,
            double averageMatchPercentage) {

        this.recommendedJobs = recommendedJobs;
        this.bestMatchPercentage = bestMatchPercentage;
        this.averageMatchPercentage = averageMatchPercentage;
    }

    public int getRecommendedJobs() {
        return recommendedJobs;
    }

    public void setRecommendedJobs(int recommendedJobs) {
        this.recommendedJobs = recommendedJobs;
    }

    public double getBestMatchPercentage() {
        return bestMatchPercentage;
    }

    public void setBestMatchPercentage(double bestMatchPercentage) {
        this.bestMatchPercentage = bestMatchPercentage;
    }

    public double getAverageMatchPercentage() {
        return averageMatchPercentage;
    }

    public void setAverageMatchPercentage(double averageMatchPercentage) {
        this.averageMatchPercentage = averageMatchPercentage;
    }
}